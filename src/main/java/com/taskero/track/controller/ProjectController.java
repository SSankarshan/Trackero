package com.taskero.track.controller;

import com.taskero.track.dto.ProjectInputRequestDTO;
import com.taskero.track.dto.ProjectResponseDTO;
import com.taskero.track.service.ProjectService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Create project - Admin only
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @RequestBody ProjectInputRequestDTO request,
            Authentication auth) {
        ProjectResponseDTO createdProject;
        try {
            createdProject = projectService.createProject(request, auth);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(createdProject);
    }

    // Get project by ID - All roles can view
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(
            @PathVariable String id,
            Authentication auth) {
        ProjectResponseDTO project = projectService.getProjectById(id, auth);
        return ResponseEntity.ok(project);
    }

    // Get all projects - All roles can view
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(Authentication auth) {
        List<ProjectResponseDTO> projects = projectService.getAllProjects(auth);
        return ResponseEntity.ok(projects);
    }

    // Update project - Admin or assigned Manager only
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable String id,
            @RequestBody ProjectInputRequestDTO request,
            Authentication auth) {
        ProjectResponseDTO updatedProject = projectService.updateProject(id, request, auth);
        return ResponseEntity.ok(updatedProject);
    }

    // Delete project - Admin only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable String id,
            Authentication auth) {
        projectService.deleteProject(id, auth);
        return ResponseEntity.noContent().build();
    }
}
