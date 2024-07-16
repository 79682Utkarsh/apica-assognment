package com.apica.UserService.kafka;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.apica.UserService.model.Journal;
import com.apica.UserService.repository.JournalRepository;

@Service
public class KafkaConsumer {
	
	@Autowired
	private JournalRepository journalRepository;
	
	@KafkaListener(topics = "user-events", groupId = "group_id")
	public void consume(String message) {
		System.out.println("Consume message: " + message);
		Journal journal = new Journal();
		journal.setMessage(message);
		journal.setTimestamp(LocalDateTime.now());
		journalRepository.save(journal);
	}
}
