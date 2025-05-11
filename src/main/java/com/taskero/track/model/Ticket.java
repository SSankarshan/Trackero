package com.taskero.track.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @NotBlank
    private String ticketKey;

    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;

    @NotNull(message = "Title is mandatory")
    private TicketType type;

    @NotNull(message = "Title is mandatory")
    private TaskStatus status;
    private String priority;
    private String assigneeId;
    @NotBlank(message = "ReporterId is mandatory")
    private String reporterId;
    @NotBlank(message = "ProjectId is mandatory")
    private String projectId;
    @NotBlank(message = "QAOwner is mandatory")
    private String qaOwnerId;
    // getters, setters, constructors

    private final Long createdAt;
    private final Long updatedAt;


    private Ticket(Builder builder) {
        this.id = builder.id;
        this.ticketKey = builder.ticketKey;
        this.title = builder.title;
        this.description = builder.description;
        this.type = builder.type;
        this.status = builder.status;
        this.priority = builder.priority;
        this.assigneeId = builder.assigneeId;
        this.reporterId = builder.reporterId;
        this.projectId = builder.projectId;
        this.qaOwnerId = builder.qaOwnerId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        private String id;
        private String ticketKey;
        private String title;
        private String description;
        private TicketType type;
        private TaskStatus status;
        private String priority;
        private String assigneeId;
        private String reporterId;
        private String projectId;
        private String qaOwnerId;
        private Long createdAt;
        private Long updatedAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ticketKey(String ticketKey) {
            this.ticketKey = ticketKey;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder type(TicketType type) {
            this.type = type;
            return this;
        }

        public Builder status(TaskStatus status) {
            this.status = status;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder assigneeId(String assigneeId) {
            this.assigneeId = assigneeId;
            return this;
        }

        public Builder reporterId(String reporterId) {
            this.reporterId = reporterId;
            return this;
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder qaOwnerId(String qaOwnerId) {
            this.qaOwnerId = qaOwnerId;
            return this;
        }

        public Builder createdAt(Long createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Long updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getTicketKey() {
        return ticketKey;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketType getType() {
        return type;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getQaOwnerId() {
        return qaOwnerId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void  setStatus(TaskStatus status) {
        this.status = status;
    }

    public void  setPriority()

}

