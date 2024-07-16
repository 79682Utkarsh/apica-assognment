package com.apica.UserService.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.apica.UserService.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
