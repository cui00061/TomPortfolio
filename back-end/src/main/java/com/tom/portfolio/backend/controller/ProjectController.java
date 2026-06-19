package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.service.ProjectService;
// import lombok.RequiredArgsConstructor; // 未使用，先移除以避免告警
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ProjectController.java（只贴方法部分）
@RestController
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllProjects() {
        return projectService.getAllProjects(); // ✅ 与 Service 对齐
    }

    @PostMapping
    public Project save(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project newProject) {
        Project old = projectService.findById(id);
        old.setTitle(newProject.getTitle());
        old.setDescription(newProject.getDescription());
        old.setScreenshotUrls(newProject.getScreenshotUrls());
        return projectService.saveProject(old);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }
}

