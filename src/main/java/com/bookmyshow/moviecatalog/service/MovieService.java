package com.bookmyshow.moviecatalog.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface MovieService {

    public OutputCommand createNewMovieCatalog(InputCommand inputCommand);

    public OutputCommand updatedMovieCatalog(InputCommand inputCommand);

    public OutputCommand getMovieCatalogById(InputCommand inputCommand);

    public OutputCommand deleteMovieCatalogById(InputCommand inputCommand);
}
