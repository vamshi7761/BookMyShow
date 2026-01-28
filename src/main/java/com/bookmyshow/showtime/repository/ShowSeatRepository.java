package com.bookmyshow.showtime.repository;

import com.bookmyshow.showtime.enums.ShowSeatStatus;
import com.bookmyshow.showtime.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    List<ShowSeat> findByShow_Id(Long showId);

    List<ShowSeat> findByShow_IdAndStatus(Long showId, ShowSeatStatus status);

    boolean existsByShow_IdAndSeat_Id(Long showId, Long seatId);


}
