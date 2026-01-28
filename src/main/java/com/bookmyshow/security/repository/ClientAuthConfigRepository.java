package com.bookmyshow.security.repository;

import com.bookmyshow.security.model.ClientAuthConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAuthConfigRepository extends JpaRepository<ClientAuthConfig, Long> {
    ClientAuthConfig findByClientId(String clientId);
}
