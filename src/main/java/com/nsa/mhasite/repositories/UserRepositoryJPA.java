package com.nsa.mhasite.repositories;


import com.nsa.mhasite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryJPA extends JpaRepository<User, Long> {

    User findByUsername(String username);
}