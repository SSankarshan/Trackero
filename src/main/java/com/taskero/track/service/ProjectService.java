package com.taskero.track.service;

import com.taskero.track.dto.ProjectInputRequestDTO;
import com.taskero.track.dto.ProjectResponseDTO;
import com.taskero.track.exception.HttpCodeBasedException;
import com.taskero.track.model.Project;
import com.taskero.track.model.ProjectStatus;
import com.taskero.track.repository.ProjectRepository;
import com.taskero.track.util.ProjectKeyGenerator;
import com.taskero.track.util.RoleUtils;
import com.taskero.track.util.ValidationUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private static final int MAX_PROJECT_DISCRIPTION_LEN = 5000;
    private static final int MAX_PROJECT_NAME_LEN = 10;
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ValidationUtils validationUtils;


    private ProjectResponseDTO toResponse(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setKey(project.getKey());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setStatus(project.getStatus());
        dto.setManagerId(project.getManagerId());
        return dto;
    }

    public ProjectResponseDTO createProject(ProjectInputRequestDTO request, Authentication auth) throws BadRequestException {
        if (!RoleUtils.isAdmin(auth)) {
            throw new HttpCodeBasedException.ForbiddenException("Only admins can create projects");
        }

        validationUtils.validateManagerId(request.getManagerId(), auth);

        validationUtils.validateInputSize("description", request.getDescription(), MAX_PROJECT_DISCRIPTION_LEN);
        validationUtils.validateInputSize("name", request.getName(), MAX_PROJECT_NAME_LEN);

        String key;
        if (request.isAutoGenerateKey()) {
            key = generateUniqueKey(request.getName());
        } else {
        	validationUtils.validateProjectKey(request.getKey(), true, projectRepository);
            key = request.getKey().toUpperCase();
        }

        if (projectRepository.existsByName(request.getName())) {
            throw new HttpCodeBasedException.BadRequestException(String.format("A project with the name %s already exists", request.getName()));
        }

        Project project = new Project.ProjectBuilder()
                .key(key)
                .name(request.getName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .managerId(request.getManagerId())
                .status(request.getStatus() != null ? request.getStatus() : ProjectStatus.PMPLANNING)
                .build();

        Project saved = projectRepository.save(project);
        return toResponse(saved);
    }


	private String generateUniqueKey(String projectName) {
        String baseKey = ProjectKeyGenerator.generateKeyFromName(projectName);
        String key = baseKey;
        int suffix = 1;
        while (projectRepository.existsByKey(key)) {
            key = baseKey + suffix;
            suffix++;
        }
        return key;
    }

    public ProjectResponseDTO getProjectById(String id, Authentication auth) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // All roles can view project
        return toResponse(project);
    }

    public List<ProjectResponseDTO> getAllProjects(Authentication auth) {
        // All roles can view projects
        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponseDTO updateProject(String id, ProjectInputRequestDTO request, Authentication auth) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new HttpCodeBasedException.ResourceNotFoundException("Project not found"));

        String currentUserId = auth.getName();
        
        boolean isAdmin = RoleUtils.isAdmin(auth);
        boolean isCurrentManager = project.getManagerId() != null && project.getManagerId().equals(currentUserId);

        if (project.getStatus() == ProjectStatus.ARCHIVED) {
        	throw new HttpCodeBasedException.BadRequestException("Archived projects cannot be updated");
        	
            // TODO : Allow admin to unarchive by setting status back to INPROGRESS
        }
        

        if (!isAdmin && !isCurrentManager) {
            throw new HttpCodeBasedException.ForbiddenException("Only admins or the manager can update the project");
        }
        
        if (request.getStatus() != null) {
            validationUtils.validateStatusTransition(project.getStatus(), request.getStatus());
        }

        validationUtils.validateInputSize("description", request.getDescription(), MAX_PROJECT_DISCRIPTION_LEN);
        validationUtils.validateInputSize("name", request.getName(), MAX_PROJECT_NAME_LEN);

        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getStatus() != null) project.setStatus(request.getStatus());

        // can only change managerId if new managerId is a valid manager
        if (request.getManagerId() != null && !request.getManagerId().equals(project.getManagerId())) {
            validationUtils.validateManagerId(request.getManagerId(), auth);
            project.setManagerId(request.getManagerId());
        }
        
        Project updated = projectRepository.save(project);
        return toResponse(updated);
    }


    public void deleteProject(String id, Authentication auth) {
        if (!RoleUtils.isAdmin(auth)) {
            throw new AccessDeniedException("Only admins can delete projects");
        }
        projectRepository.deleteById(id);
    }
}
