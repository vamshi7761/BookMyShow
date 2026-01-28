package com.bookmyshow.showtime.repository;

import com.bookmyshow.showtime.model.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType,Long> {


    boolean existsByShow_IdAndSeatType_Id(Long showId, Long seatTypeId);

    List<ShowSeatType> findByShow_Id(Long showId);

}
