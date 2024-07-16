package com.apica.UserService.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.apica.UserService.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
