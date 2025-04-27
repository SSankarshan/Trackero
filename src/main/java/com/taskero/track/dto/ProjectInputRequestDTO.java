package com.taskero.track.dto;

import com.taskero.track.model.ProjectStatus;
import java.time.LocalDateTime;

public class ProjectInputRequestDTO {
    private String key; // optional if autoGenerateKey = false
    private boolean autoGenerateKey;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ProjectStatus status; // optional, default PMPLANNING
    private String managerId;
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isAutoGenerateKey() {
		return autoGenerateKey;
	}
	public void setAutoGenerateKey(boolean autoGenerateKey) {
		this.autoGenerateKey = autoGenerateKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String assigneeId) {
		this.managerId = assigneeId;
	}

    // Getters and setters
    
    
}
