package com.bookmyshow.moviecatalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDto {

    private Long movieId;
    private String name;
    private String genre;
    private int runTimeMins;
    private List<String> actors;
    private List<String> languages;
}
