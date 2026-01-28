package com.bookmyshow.main.dto;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class InputCommand {
    private Object inputData;
    private String action;
    private String entity;
}
