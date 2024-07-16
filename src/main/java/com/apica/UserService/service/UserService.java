package com.apica.UserService.service;



import com.apica.UserService.kafka.KafkaProducer;
import com.apica.UserService.model.Role;
import com.apica.UserService.model.User;
import com.apica.UserService.repository.RoleRepository;
import com.apica.UserService.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String TOPIC = "user-events";

    public User registerUser(User user, Set<String> roleNames) {
    	if(userRepository.findByUsername(user.getUsername()).isPresent()) {
    		throw new RuntimeException("User with username " + user.getUsername() + " already exist");
    	}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for(String roleName : roleNames) {
        	Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role Not Found: " + roleName));
        	roles.add(role);
        }
        user.setRoles(roles);
        User registeredUser = userRepository.save(user);
        kafkaProducer.sendMessage("User registered: " + registeredUser.getUsername());
        return registeredUser;
    }

    public User getUserDetails(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        kafkaProducer.sendMessage("User retrieved: " + user.getUsername());
        return user;
    }

    public User updateUser(Long id, User user, Set<String> roleNames) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            Set<Role> roles = new HashSet<>();
            for(String roleName : roleNames) {
            	roles.add(roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role Not Found " + roleName)));
            }
            existingUser.setRoles(roles);
            User updatedUser = userRepository.save(existingUser);
            kafkaProducer.sendMessage("User updated: " + updatedUser.getUsername());
            return updatedUser;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(Long id) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            userRepository.deleteById(id);
            kafkaProducer.sendMessage("User deleted: " + id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

	public Role createRole(String roleName) {
		
		if(roleRepository.findByName(roleName).isPresent()) {
			throw new RuntimeException("Role Already Exist: " + roleName);
		}
		Role role = new Role();
		role.setName(roleName);
		return roleRepository.save(role);
	}
}