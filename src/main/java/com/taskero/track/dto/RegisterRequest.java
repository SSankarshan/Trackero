package com.taskero.track.dto;

import java.util.List;

import com.taskero.track.model.UserRole;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private List<String> listRoles;
    
	public RegisterRequest(String username, String email, String password, List<String> roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.listRoles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return listRoles;
	}

	public void setRoles(List<String> roles) {
		this.listRoles = roles;
	}
}
