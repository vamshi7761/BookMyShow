package com.bookmyshow.moviecatalog.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.moviecatalog.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "DELETE", entity = "MOVIE")
public class DeleteMovieCommandHandler implements CommandHandler {

    private final MovieService movieService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return movieService.deleteMovieCatalogById(request);
    }
}