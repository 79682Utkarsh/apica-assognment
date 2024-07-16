package com.apica.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apica.UserService.model.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long>{
	
}
