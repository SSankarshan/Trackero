package com.taskero.track.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;

@Document
public class Project {
	@Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
	private String key;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ProjectStatus status;
    private String managerId;

    private Project() {
    }
 // Private constructor to force use of builder
    private Project(ProjectBuilder builder) {
        this.key = builder.key;
        this.name = builder.name;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
        this.managerId = builder.managerId;
    }
    
    public static class ProjectBuilder {
        private String key;
        private String name;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private ProjectStatus status = ProjectStatus.PMPLANNING; // default
        private String managerId;

        public ProjectBuilder key(String key) {
            this.key = key;
            return this;
        }

        public ProjectBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProjectBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public ProjectBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public ProjectBuilder status(ProjectStatus status) {
            if (status != null) {
                this.status = status;
            }
            return this;
        }

        public ProjectBuilder managerId(String assigneeId) {
            this.managerId = assigneeId;
            return this;
        }

        public Project build() {
            // You can add validations here if needed
            return new Project(this);
        }
    }

	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public String getManagerId() {
		return managerId;
	}
    
    public String setManagerId(String managerId) {
    	return this.managerId = managerId;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
    
    
    
    
}
