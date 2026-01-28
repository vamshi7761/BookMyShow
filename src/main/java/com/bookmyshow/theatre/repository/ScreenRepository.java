package com.bookmyshow.theatre.repository;

import com.bookmyshow.theatre.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {

}
