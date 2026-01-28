
package com.bookmyshow.showtime.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.moviecatalog.model.Movie;
import com.bookmyshow.moviecatalog.repository.MovieRepository;
import com.bookmyshow.showtime.dto.ShowDto;
import com.bookmyshow.showtime.exception.ShowValidationException;
import com.bookmyshow.showtime.model.Show;
import com.bookmyshow.showtime.repository.ShowRepository;
import com.bookmyshow.showtime.validations.ShowUtils;
import com.bookmyshow.theatre.model.Screen;
import com.bookmyshow.theatre.model.Theatre;
import com.bookmyshow.theatre.repository.ScreenRepository;
import com.bookmyshow.theatre.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private static final int CLEANUP_BUFFER_MINUTES = 15;

    @Override
    public OutputCommand createShow(InputCommand request) {
        ShowDto showDto = (ShowDto) request.getInputData();

        // Basic DTO validation
        ShowUtils.validateShowOnCreate(showDto);

        // Fetch referenced entities
        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(() -> new ShowValidationException("Movie not found with ID: " + showDto.getMovieId()));

        Theatre theatre = theatreRepository.findById(showDto.getTheatreId())
                .orElseThrow(() -> new ShowValidationException("Theatre not found with ID: " + showDto.getTheatreId()));

        Screen screen = screenRepository.findById(showDto.getScreenId())
                .orElseThrow(() -> new ShowValidationException("Screen not found with ID: " + showDto.getScreenId()));

        // Invariant: Screen must belong to the given Theatre
        if (screen.getTheatre() == null || !Objects.equals(screen.getTheatre().getId(), theatre.getId())) {
            throw new ShowValidationException("Screen does not belong to the specified theatre");
        }

        // Start time must be in the future
        Date newStart = showDto.getStartTime();
        if (newStart.before(new Date())) {
            throw new ShowValidationException("startTime must be in the future");
        }

        // Validate runtime and compute new end time
        int runtimeMin = getRuntimeMinutesOrThrow(movie);
        Date newEnd = addMinutes(newStart, runtimeMin + CLEANUP_BUFFER_MINUTES);

        // Overlap check: ensure no currently running or scheduled show conflicts on the same screen
        ensureNoOverlapOnScreen(screen.getId(), newStart, newEnd);

        // Persist new show
        Show show = new Show();
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setScreen(screen);
        show.setStartTime(newStart);
        show.setLanguage(showDto.getLanguage());

        Show saved = showRepository.save(show);
        return OutputCommand.builder()
                .outputData(saved)
                .build();
    }

    @Override
    public OutputCommand updateShow(InputCommand request) {
        ShowDto showDto = (ShowDto) request.getInputData();
        ShowUtils.validateShowOnUpdate(showDto);

        Show existing = showRepository.findById(showDto.getShowId())
                .orElseThrow(() -> new ShowValidationException("Show not found with ID: " + showDto.getShowId()));

        // Keep references to possibly updated movie/screen/theatre
        Movie movieToUse = existing.getMovie();
        Theatre theatreToUse = existing.getTheatre();
        Screen screenToUse = existing.getScreen();
        Date startToUse = existing.getStartTime();

        // Update movie if requested
        if (showDto.getMovieId() != null) {
            movieToUse = movieRepository.findById(showDto.getMovieId())
                    .orElseThrow(() -> new ShowValidationException("Movie not found with ID: " + showDto.getMovieId()));
            existing.setMovie(movieToUse);
        }

        // Update theatre if requested
        if (showDto.getTheatreId() != null) {
            theatreToUse = theatreRepository.findById(showDto.getTheatreId())
                    .orElseThrow(() -> new ShowValidationException("Theatre not found with ID: " + showDto.getTheatreId()));
            existing.setTheatre(theatreToUse);
        }

        // Update screen if requested
        if (showDto.getScreenId() != null) {
            screenToUse = screenRepository.findById(showDto.getScreenId())
                    .orElseThrow(() -> new ShowValidationException("Screen not found with ID: " + showDto.getScreenId()));
            existing.setScreen(screenToUse);
        }

        // Validate screen-theatre relationship (post updates)
        if (screenToUse.getTheatre() == null || !Objects.equals(screenToUse.getTheatre().getId(), theatreToUse.getId())) {
            throw new ShowValidationException("Updated screen does not belong to the (updated) theatre");
        }

        // Update start time if provided
        if (showDto.getStartTime() != null) {
            startToUse = showDto.getStartTime();
            if (startToUse.before(new Date())) {
                throw new ShowValidationException("startTime must be in the future");
            }
            existing.setStartTime(startToUse);
        }

        // Update language if provided
        if (showDto.getLanguage() != null) {
            existing.setLanguage(showDto.getLanguage());
        }

        // Re-check overlap given any changed fields (movie runtime, screen, start)
        int runtimeMin = getRuntimeMinutesOrThrow(movieToUse);
        Date newEnd = addMinutes(startToUse, runtimeMin + CLEANUP_BUFFER_MINUTES);
        ensureNoOverlapOnScreenForUpdate(existing.getId(), screenToUse.getId(), startToUse, newEnd);

        Show updated = showRepository.save(existing);
        return OutputCommand.builder()
                .outputData(updated)
                .build();
    }

    @Override
    public OutputCommand deleteShow(InputCommand request) {
        ShowDto showDto = (ShowDto) request.getInputData();
        ShowUtils.validateShowOnGet(showDto);

        Show existing = showRepository.findById(showDto.getShowId())
                .orElseThrow(() -> new ShowValidationException("Show not found with ID: " + showDto.getShowId()));

        showRepository.deleteById(existing.getId());
        return OutputCommand.builder()
                .outputData("Show deleted successfully")
                .build();
    }

    @Override
    public OutputCommand getShow(InputCommand request) {
        ShowDto showDto = (ShowDto) request.getInputData();
        ShowUtils.validateShowOnGet(showDto);

        Show existing = showRepository.findById(showDto.getShowId())
                .orElseThrow(() -> new ShowValidationException("Show not found with ID: " + showDto.getShowId()));

        return OutputCommand.builder()
                .outputData(existing)
                .build();
    }

    /**
     * Ensures no show on the given screen overlaps with [newStart, newEnd).
     * Loads shows for that screen and checks interval intersection.
     */
    private void ensureNoOverlapOnScreen(Long screenId, Date newStart, Date newEnd) {

        List<Show> candidates = showRepository.findByScreen_IdAndStartTimeGreaterThanOrEqual(screenId, new Date());

        for (Show existing : candidates) {
            int existingRuntime = getRuntimeMinutesOrThrow(existing.getMovie());
            Date existingStart = existing.getStartTime();
            Date existingEnd = addMinutes(existingStart, existingRuntime + CLEANUP_BUFFER_MINUTES);

            if (overlaps(existingStart, existingEnd, newStart, newEnd)) {
                throw new ShowValidationException(
                        "Show overlaps with an existing show (" +
                                existing.getMovie().getName() + " @ " + existingStart + ") on this screen"
                );
            }
        }
    }

    private void ensureNoOverlapOnScreenForUpdate(Long currentShowId, Long screenId, Date newStart, Date newEnd) {
        List<Show> candidates = showRepository.findByScreen_IdAndStartTimeGreaterThanOrEqual(screenId, newStart);

        for (Show existing : candidates) {
            if (existing.getId().equals(currentShowId)) {
                continue;
            }
            int existingRuntime = getRuntimeMinutesOrThrow(existing.getMovie());
            Date existingStart = existing.getStartTime();
            Date existingEnd = addMinutes(existingStart, existingRuntime + CLEANUP_BUFFER_MINUTES);

            if (overlaps(existingStart, existingEnd, newStart, newEnd)) {
                throw new ShowValidationException(
                        "Updated show overlaps with an existing show (" +
                                existing.getMovie().getName() + " @ " + existingStart + ") on this screen"
                );
            }
        }
    }

    /** Interval overlap: [aStart, aEnd) intersects [bStart, bEnd) */
    private boolean overlaps(Date aStart, Date aEnd, Date bStart, Date bEnd) {
        return aStart.before(bEnd) && aEnd.after(bStart);
    }

    /** Add minutes to a Date (uses UTC Instant under the hood). */
    private Date addMinutes(Date base, int minutes) {
        return Date.from(base.toInstant().plusSeconds(minutes * 60L));
    }

    /** Extract runtime (minutes) from Movie or throw if invalid/missing. */
    private int getRuntimeMinutesOrThrow(Movie movie) {
        // Adjust accessor if your Movie entity uses a different field name
        Integer runtime = movie.getRun_time_mins();
        if (runtime == null || runtime <= 0) {
            throw new ShowValidationException("Invalid movie runtime; cannot schedule show");
        }
        return runtime;
    }
}
