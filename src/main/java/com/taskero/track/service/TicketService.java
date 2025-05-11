package com.taskero.track.service;

import com.taskero.track.dto.TicketInputRequestDTO;
import com.taskero.track.dto.TicketResponseDTO;
import com.taskero.track.exception.HttpCodeBasedException;
import com.taskero.track.model.*;
import com.taskero.track.repository.ProjectRepository;
import com.taskero.track.repository.TicketRepository;
import com.taskero.track.util.RoleUtils;
import com.taskero.track.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static final int MAX_TICKET_TITLE_LEN = 100;
    private static final int MAX_TICKET_DESCRIPTION_LEN = 2000;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ValidationUtils validationUtils;

    private TicketResponseDTO toResponse(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTicketKey(ticket.getTicketKey());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setType(ticket.getType());
        dto.setStatus(ticket.getStatus());
        dto.setPriority(ticket.getPriority());
        dto.setAssigneeId(ticket.getAssigneeId());
        dto.setReporterId(ticket.getReporterId());
        dto.setProjectId(ticket.getProjectId());
        dto.setQaOwnerId(ticket.getQaOwnerId());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());
        return dto;
    }

    public TicketResponseDTO createTicket(TicketInputRequestDTO request, Authentication auth) {
        // All roles can create tickets
        validationUtils.validateInputSize("title", request.getTitle(), MAX_TICKET_TITLE_LEN);
        validationUtils.validateInputSize("description", request.getDescription(), MAX_TICKET_DESCRIPTION_LEN);

        if (request.getProjectId() == null || !projectRepository.existsById(request.getProjectId())) {
            throw new HttpCodeBasedException.BadRequestException("Project does not exist");
        }

        // Generate ticket key (e.g., MPT-1)
        String ticketKey = generateTicketKey(request.getProjectId());

        Ticket ticket = new Ticket.Builder()
                .ticketKey(ticketKey)
                .title(request.getTitle())
                .description(request.getDescription())
                .type(request.getType())
                .status(request.getStatus())
                .priority(request.getPriority())
                .assigneeId(request.getAssigneeId())
                .reporterId(auth.getName())
                .projectId(request.getProjectId())
                .qaOwnerId(request.getQaOwnerId())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return toResponse(saved);
    }

    private String generateTicketKey(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new HttpCodeBasedException.BadRequestException("Project not found"));
        String prefix = project.getKey();
        int count = ticketRepository.countByProjectId(projectId);
        return prefix + "-" + (count + 1);
    }

    public TicketResponseDTO getTicketById(String id, Authentication auth) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new HttpCodeBasedException.ResourceNotFoundException("Ticket not found"));
        // All roles can read
        return toResponse(ticket);
    }

    public List<TicketResponseDTO> getTicketsByProjectId(String projectId, Authentication auth) {
        // All roles can read
        return ticketRepository.findByProjectId(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TicketResponseDTO updateTicket(String id, TicketInputRequestDTO request, Authentication auth) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new HttpCodeBasedException.ResourceNotFoundException("Ticket not found"));

        boolean isAdmin = RoleUtils.isAdmin(auth);
        boolean isManager = RoleUtils.isManager(auth);
        boolean isReporterOrAssignee = auth.getName().equals(ticket.getReporterId()) ||
                auth.getName().equals(ticket.getAssigneeId());

        if (!(isAdmin || isManager || isReporterOrAssignee)) {
            throw new HttpCodeBasedException.ForbiddenException("Not allowed to update this ticket");
        }

        if (request.getTitle() != null) {
            validationUtils.validateInputSize("title", request.getTitle(), MAX_TICKET_TITLE_LEN);
            ticket.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            validationUtils.validateInputSize("description", request.getDescription(), MAX_TICKET_DESCRIPTION_LEN);
            ticket.setDescription(request.getDescription());
        }
        if (request.getType() != null) ticket.setType(request.getType());
        if (request.getStatus() != null) ticket.setStatus(request.getStatus());
        if (request.getPriority() != null) ticket.setPriority(request.getPriority());
        if (request.getAssigneeId() != null) ticket.setAssigneeId(request.getAssigneeId());
        if (request.getQaOwnerId() != null) ticket.setQaOwnerId(request.getQaOwnerId());

        ticket.setUpdatedAt(System.currentTimeMillis());

        Ticket updated = ticketRepository.save(ticket);
        return toResponse(updated);
    }

    public void deleteTicket(String id, Authentication auth) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new HttpCodeBasedException.ResourceNotFoundException("Ticket not found"));

        boolean isAdmin = RoleUtils.isAdmin(auth);
        boolean isManager = RoleUtils.isManager(auth);

        if (!(isAdmin || isManager)) {
            throw new HttpCodeBasedException.ForbiddenException("Not allowed to delete this ticket");
        }
        ticketRepository.deleteById(id);
    }
}
