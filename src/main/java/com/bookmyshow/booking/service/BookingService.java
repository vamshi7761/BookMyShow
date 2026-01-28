package com.bookmyshow.booking.service;

import com.bookmyshow.main.dto.InputCommand;
import com.bookmyshow.main.dto.OutputCommand;

public interface BookingService {

    public OutputCommand createBooking(InputCommand request);

    public OutputCommand getBookingsByUser(InputCommand request);
}
