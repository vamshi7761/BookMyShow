package com.bookmyshow.theatre.repository;

import com.bookmyshow.theatre.model.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType,Long> {
    Optional<SeatType> findByType(String type);
}
