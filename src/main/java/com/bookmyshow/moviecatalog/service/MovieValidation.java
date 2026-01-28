package com.bookmyshow.moviecatalog.service;

import com.bookmyshow.moviecatalog.dto.MovieDto;
import com.bookmyshow.moviecatalog.exception.MovieCatelogException;

public class MovieValidation {

    public static void validateCreateMovieRequest(MovieDto movieDto) {

        if(movieDto == null) {
            throw new MovieCatelogException("Movie data cannot be null");
        }

        if (movieDto.getName() == null || movieDto.getName().isEmpty()) {
            throw new MovieCatelogException("Movie name cannot be null or empty");
        }
        if (movieDto.getGenre() == null || movieDto.getGenre().isEmpty()) {
            throw new MovieCatelogException("Movie genre cannot be null or empty");
        }
        if (movieDto.getRunTimeMins() <= 0) {
            throw new MovieCatelogException("Movie runtime must be greater than zero");
        }
        if (movieDto.getActors() == null || movieDto.getActors().isEmpty()) {
            throw new MovieCatelogException("Movie must have at least one actor");
        }
        if (movieDto.getLanguages() == null || movieDto.getLanguages().isEmpty()) {
            throw new MovieCatelogException("Movie must have at least one language");
        }

    }

    public static void validateGetMovieRequest(MovieDto movieDto) {
        if(movieDto == null) {
            throw new MovieCatelogException("Movie data cannot be null");
        }
        Long movieId = movieDto.getMovieId();
        if (movieId == null || movieId <= 0) {
            throw new MovieCatelogException("Invalid movie ID");
        }
    }

}
