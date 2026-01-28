package com.bookmyshow.theatre.repository;

import com.bookmyshow.theatre.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {

    public List<Seat> findByScreenIdId(Long screenId);

    public Seat findByScreenIdIdAndRowIdAndColumnId(Long screenId, String rowId, Integer columnId);
}
