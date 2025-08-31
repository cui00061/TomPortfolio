package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @PostMapping
    public Project save(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project newProject) {
        Project old = projectService.findById(id);
        old.setTitle(newProject.getTitle());
        old.setDescription(newProject.getDescription());      // ★ List<String>
        old.setScreenshotUrls(newProject.getScreenshotUrls());
        return projectService.saveProject(old);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }
}
