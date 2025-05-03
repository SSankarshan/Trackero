package com.taskero.track.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.ArrayList;
import java.util.List;

@Document
public class User {
	@Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE) // auto-generate unique ID
    private String id;
    private String username;
    private String email;
    private String password;
	@Field("listRoles")
    private List<UserRole> listRoles = new ArrayList<>();
    // getters, setters, constructors

    
    
    public User() {}

    public User(String username, String email, String password, List<UserRole> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.listRoles = roles;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<UserRole> getRoles() {
		return listRoles;
	}

	public void setRoles(List<UserRole> roles) {
		this.listRoles = roles;
	}
    
    
}

