package com.apica.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apica.UserService.model.Journal;
import com.apica.UserService.service.JournalService;

@RestController
@RequestMapping("/api/journals")
public class JournalController {
	
	@Autowired
	private JournalService journalService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Journal>> getAllJournals(){
		List<Journal> journals = journalService.getAllJournals();
		return ResponseEntity.ok(journals);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Journal> getAllJournalById(@PathVariable Long id){
		Journal journal  = journalService.getJournalById(id);
		return ResponseEntity.ok(journal);
	}
}
