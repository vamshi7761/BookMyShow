package com.bookmyshow.booking.repository;

import com.bookmyshow.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByUser_Id(Long userId);

    // Optional: most recent first
    List<Booking> findByUser_IdOrderByCreatedAtDesc(Long userId);

}
