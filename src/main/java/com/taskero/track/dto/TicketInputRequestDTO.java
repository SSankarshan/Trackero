package com.taskero.track.dto;

import com.taskero.track.model.TaskStatus;
import com.taskero.track.model.TicketType;

public class TicketInputRequestDTO {
    private String title;
    private String description;
    private TicketType type;
    private TaskStatus status;
    private String priority;
    private String assigneeId;
    private String reporterId; // Usually set from Authentication, but included for completeness
    private String projectId;
    private String qaOwnerId;

    public TicketInputRequestDTO() {
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getQaOwnerId() {
        return qaOwnerId;
    }

    public void setQaOwnerId(String qaOwnerId) {
        this.qaOwnerId = qaOwnerId;
    }
}
