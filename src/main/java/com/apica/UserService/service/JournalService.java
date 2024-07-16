package com.apica.UserService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apica.UserService.model.Journal;
import com.apica.UserService.repository.JournalRepository;

@Service
public class JournalService {
	
	@Autowired
	private JournalRepository journalRepository;
	
	public List<Journal> getAllJournals(){
		return journalRepository.findAll();
	}
	
	public Journal getJournalById(Long id) {
		return journalRepository.findById(id).orElseThrow(() -> new RuntimeException("Journal Not Found"));
	}
}
