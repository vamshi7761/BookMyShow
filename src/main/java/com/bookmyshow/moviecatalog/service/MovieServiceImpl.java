package com.bookmyshow.moviecatalog.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.moviecatalog.dto.MovieDto;
import com.bookmyshow.moviecatalog.enums.Language;
import com.bookmyshow.moviecatalog.exception.MovieCatelogException;
import com.bookmyshow.moviecatalog.model.Movie;
import com.bookmyshow.moviecatalog.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public OutputCommand createNewMovieCatalog(InputCommand inputCommand) {

        MovieDto movieDto = (MovieDto) inputCommand.getInputData();

        MovieValidation.validateCreateMovieRequest(movieDto);

        Movie movie = new Movie();

        String name = movieDto.getName();

        Movie existingMovie = movieRepository.findByName(name);

        if (existingMovie != null) {
            throw new MovieCatelogException("Movie with name " + name + " already exists.");
        }

        movie.setName(movieDto.getName());
        movie.setGenre(movieDto.getGenre());
        movie.setRun_time_mins(movieDto.getRunTimeMins());
        movie.setActors(movieDto.getActors());
        List<Language> languages = new ArrayList<>();

        for (String lang : movieDto.getLanguages()) {
            languages.add(Language.valueOf(lang));
        }
        movie.setLanguages(languages);

        movie = movieRepository.save(movie);

        return OutputCommand.builder().outputData(movie).build();
    }

    @Override
    public OutputCommand updatedMovieCatalog(InputCommand inputCommand) {

        MovieDto movieDto = (MovieDto) inputCommand.getInputData();
        MovieValidation.validateGetMovieRequest(movieDto);

        Long movieId = movieDto.getMovieId();

        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieCatelogException("Movie with ID " + movieId + " not found."));

        if (movieDto.getName() != null && !movieDto.getName().isEmpty()) {

            Movie newMovieName = movieRepository.findByName(movieDto.getName());
            if (newMovieName != null && !newMovieName.getId().equals(movieId)) {
                throw new MovieCatelogException("Movie with name " + movieDto.getName() + " already exists.");
            }
            existingMovie.setName(movieDto.getName());
        }

        if (movieDto.getGenre() != null && !movieDto.getGenre().isEmpty()) {
            existingMovie.setGenre(movieDto.getGenre());
        }

        if (movieDto.getRunTimeMins() > 0) {
            existingMovie.setRun_time_mins(movieDto.getRunTimeMins());
        }

        if (movieDto.getActors() != null && !movieDto.getActors().isEmpty()) {
            existingMovie.setActors(movieDto.getActors());
        }

        if (movieDto.getLanguages() != null && !movieDto.getLanguages().isEmpty()) {

            List<Language> languages = existingMovie.getLanguages();

            for (String lang : movieDto.getLanguages()) {
                languages.add(Language.valueOf(lang));
            }
            existingMovie.setLanguages(languages);
        }

        existingMovie = movieRepository.save(existingMovie);

        return OutputCommand.builder().outputData(existingMovie).build();
    }

    @Override
    public OutputCommand getMovieCatalogById(InputCommand inputCommand) {
        MovieDto movieDto = (MovieDto) inputCommand.getInputData();
        MovieValidation.validateGetMovieRequest(movieDto);

        Long movieId = movieDto.getMovieId();

        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieCatelogException("Movie with ID " + movieId + " not found."));

        return OutputCommand.builder().outputData(existingMovie).build();
    }

    @Override
    public OutputCommand deleteMovieCatalogById(InputCommand inputCommand) {

        MovieDto movieDto = (MovieDto) inputCommand.getInputData();
        MovieValidation.validateGetMovieRequest(movieDto);

        Long movieId = movieDto.getMovieId();

        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieCatelogException("Movie with ID " + movieId + " not found."));

        movieRepository.delete(existingMovie);

        return OutputCommand.builder().outputData("movie deleted Successfully").build();
    }
}