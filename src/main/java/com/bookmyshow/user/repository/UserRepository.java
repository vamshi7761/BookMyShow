package com.bookmyshow.user.repository;

import com.bookmyshow.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByName(String name);
    User findByEmail(String email);
    User findByPhone(String phone);
}
