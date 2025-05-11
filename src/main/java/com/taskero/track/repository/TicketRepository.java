package com.taskero.track.repository;

import com.taskero.track.model.Ticket;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CouchbaseRepository<Ticket, String> {

    // Find all tickets for a project
    List<Ticket> findByProjectId(String projectId);

    // Find the ticket with the highest ticketKey for a project (for key generation)
    Optional<Ticket> findTopByTicketKeyStartingWithOrderByTicketKeyDesc(String ticketKeyPrefix);

    // Find by human-readable ticket key (e.g., "MPT-1")
    Optional<Ticket> findByTicketKey(String ticketKey);

    int countByProjectId(String projectId);
}
