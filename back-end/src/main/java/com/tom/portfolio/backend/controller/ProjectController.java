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
    public Project update(
            @PathVariable Long id,                // ★ CHANGED: Long -> Integer（与你的实体 id:int 保持一致）
            @RequestBody Project newProject) {
        Project old = projectService.findById(id);   // ★ CHANGED: 方法签名要与 service 对齐（后面一起改）
        old.setTitle(newProject.getTitle());
        old.setDescription(newProject.getDescription());
        old.setScreenshotUrls(newProject.getScreenshotUrls()); // ★ CHANGED: 更新多图列表
        return projectService.saveProject(old);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {  // ★ CHANGED: Long -> Integer
        projectService.deleteProjectById(id);       // ★ CHANGED: 方法签名与 service 对齐（后面一起改）
    }
}
