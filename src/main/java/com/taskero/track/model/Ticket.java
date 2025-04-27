package com.taskero.track.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Document
public class Ticket {
	@Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    private String title;
    private String description;
    private TicketType type;
    private TaskStatus status;
    private String priority;
    private String assigneeId;
    private String reporterId;
    private String projectId;
    private String qaOwnerId;
    // getters, setters, constructors
}

