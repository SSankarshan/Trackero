package com.taskero.track.util;

import com.taskero.track.exception.HttpCodeBasedException;
import com.taskero.track.model.ProjectStatus;
import com.taskero.track.model.User;
import com.taskero.track.repository.ProjectRepository;
import com.taskero.track.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
public class ValidationUtils {

	private final UserRepository userRepository;

	private static final Map<ProjectStatus, Set<ProjectStatus>> ALLOWED_STATUS_TRANSITIONS = Map.of(
			ProjectStatus.PMPLANNING, Set.of(ProjectStatus.INPROGRESS, ProjectStatus.ARCHIVED),
			ProjectStatus.INPROGRESS, Set.of(ProjectStatus.DONE, ProjectStatus.ARCHIVED), ProjectStatus.DONE,
			Set.of(ProjectStatus.ARCHIVED), ProjectStatus.ARCHIVED, Set.of() // No transitions allowed from ARCHIVED
	);

	@Autowired
	public ValidationUtils(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Validates that a user with the given ID exists. Throws
	 * IllegalArgumentException if user not found.
	 */

	public boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	public User getUserIfExists(String userId) {
		if (isNullOrEmpty(userId)) {
			throw new HttpCodeBasedException.BadRequestException("User Id is null or empty");
		}

		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new HttpCodeBasedException.BadRequestException("User does not exist: " + userId);
		}

		return user.get();
	}

	public void validateManagerId(String managerId, Authentication auth) {
		if (managerId == null)
			throw new HttpCodeBasedException.BadRequestException("Manager id cannot be null");

		// Check user exists
		if (!userRepository.existsById(managerId)) {
			throw new HttpCodeBasedException.ResourceNotFoundException("Cannot assign manager to the Project, Manager user does not exist: " + managerId);
		}

		// Check user has MANAGER role
		User user = userRepository.findById(managerId)
				.orElseThrow(() -> new IllegalArgumentException("Manager user does not exist: " + managerId));

		boolean isManager = user.getRoles().stream().anyMatch(role -> role.name().equalsIgnoreCase("MANAGER"));

		if (!isManager) {
			throw new HttpCodeBasedException.BadRequestException("User with id " + managerId + " is not a Manager");
		}
	}

	/**
	 * Validates that a project key is valid (3-4 letters) and unique. Throws
	 * IllegalArgumentException if invalid or duplicate.
	 */
	public void validateProjectKey(String key, boolean mustBeUnique, ProjectRepository projectRepository) {
		if (key == null || key.length() < 3 || key.length() > 4) {
			throw new HttpCodeBasedException.BadRequestException("Project key must be 3 to 4 letters");
		}
		if (mustBeUnique && projectRepository.existsByKey(key.toUpperCase())) {
			throw new HttpCodeBasedException.BadRequestException("Project key already exists: " + key);
		}
	}

	// Add more reusable validations here as needed
	public void validateStatusTransition(ProjectStatus currentStatus, ProjectStatus newStatus) {
		if (newStatus == null || newStatus.equals(currentStatus)) {
			return; // No change or null is allowed (no update)
		}
		Set<ProjectStatus> allowed = ALLOWED_STATUS_TRANSITIONS.getOrDefault(currentStatus,  Set.of());
		if (!allowed.contains(newStatus)) {
			throw new HttpCodeBasedException.BadRequestException("Invalid status transition from " + currentStatus + " to " + newStatus);
		}
	}

	public void validateInputSize(String field, String input, int size) {
		if (input.length() > size) {
			throw new HttpCodeBasedException.BadRequestException("Input length is too big for field " + field + ". Limit for the field is " + size);
		}
	}

}
