package com.taskero.track.model;

import java.util.Optional;

public enum UserRole {
	ADMIN, CONTRIBUTOR, MANAGER;
	
	public static Optional<UserRole> tryParse(String input) {
        if (input == null) return Optional.empty();
        try {
            return Optional.of(UserRole.valueOf(input.trim().toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
