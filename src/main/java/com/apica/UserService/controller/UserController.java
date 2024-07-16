package com.apica.UserService.controller;



import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apica.UserService.model.Role;
import com.apica.UserService.model.User;
import com.apica.UserService.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserWithRoles userWithRoles) {
        User registeredUser = userService.registerUser(userWithRoles.getUser(), userWithRoles.getRoles());
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long id) {
        User user = userService.getUserDetails(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserWithRoles userWithRoles) {
        User updatedUser = userService.updateUser(id, userWithRoles.getUser(), userWithRoles.getRoles());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody RoleEnter roleName){
    	Role role = userService.createRole(roleName.getRoleName());
    	return ResponseEntity.ok(role);
    }
}

class UserWithRoles {
	private User user;
	private Set<String> roles;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}

class RoleEnter {
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
