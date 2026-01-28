package com.bookmyshow.theatre.validations;

import com.bookmyshow.theatre.dto.SeatDto;
import com.bookmyshow.theatre.dto.SeatDtoList;
import com.bookmyshow.theatre.exception.SeatValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatValidation {

    public static void validateOnAddSeatsToScreen(SeatDtoList seatDtoList) {
        if(seatDtoList == null) {
            throw new SeatValidationException("Seat details cannot be null");
        }

        if(seatDtoList.getScreenId() == null) {
            throw new SeatValidationException("Screen ID cannot be null");
        }

        if(seatDtoList.getSeats() == null || seatDtoList.getSeats().isEmpty()) {
            throw new SeatValidationException("Seat list cannot be null or empty");
        }

        validateSeatList(seatDtoList.getSeats());
    }

    public static void validateSeatList(List<SeatDto> seatDto) {
        Map<String, List<Integer>> seatPositionMap = new HashMap<>();
        for(SeatDto seat : seatDto) {
            String rowId = seat.getRowId();
            Integer columnId = seat.getColumnId();
            String seatTypeName = seat.getSeatTypeName();
            if(rowId == null || columnId == null) {
                throw new SeatValidationException("Row ID and Column ID cannot be null");
            }
            if(seatTypeName == null || seatTypeName.isEmpty()) {
                throw new SeatValidationException("Seat Type cannot be null or empty");
            }
            if(!seatPositionMap.containsKey(rowId)) {
                seatPositionMap.put(rowId, new ArrayList<>());
            }
            List<Integer> columns = seatPositionMap.get(rowId);
            if(columns.contains(columnId)) {
                throw new SeatValidationException("Duplicate seat found at Row: " + rowId + ", Column: " + columnId);
            }
            columns.add(columnId);
        }
    }
}