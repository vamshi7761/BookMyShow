
package com.bookmyshow.booking.commandhandler;

import com.bookmyshow.main.commandhandler.CommandHandler;
import com.bookmyshow.main.commandhandler.CommandType;
import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;
import com.bookmyshow.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CommandType(action = "LIST_BY_USER", entity = "BOOKING")
public class ListBookingsByUserCommandHandler implements CommandHandler {

    private final BookingService bookingService;

    @Override
    public OutputCommand execute(InputCommand request) {
        return bookingService.getBookingsByUser(request);
    }
}
