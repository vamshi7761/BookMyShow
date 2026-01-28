package com.bookmyshow.showtime.repository;



import com.bookmyshow.showtime.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByScreen_Id(Long screenId);

    List<Show> findByScreen_IdAndStartTimeGreaterThanOrEqual(Long screenId, Date startTime);

}
