
package com.bookmyshow.booking.controller;

import com.bookmyshow.main.commandhandler.CommandExecutor;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.booking.dto.BookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookingController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand createBooking(@RequestBody BookingDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("CREATE")
                .entity("BOOKING")
                .build();

        return CommandExecutor.executeCommand(command);
    }


    @PostMapping(value = "/by-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public OutputCommand getBookingsByUser(@RequestBody BookingDto request) {
        InputCommand command = InputCommand.builder()
                .inputData(request)
                .action("LIST_BY_USER")
                .entity("BOOKING")
                .build();

        return CommandExecutor.executeCommand(command);
    }

}

