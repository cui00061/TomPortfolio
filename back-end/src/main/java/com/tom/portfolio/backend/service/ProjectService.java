package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public Project saveProject(Project project) {
        // ★ 兜底：防止空指针
        if (project.getScreenshotUrls() == null) {
            project.setScreenshotUrls(new ArrayList<>());
        }
        if (project.getDescription() == null) {
            project.setDescription(new ArrayList<>());
        }
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project newProject) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existing.setTitle(newProject.getTitle());
        existing.setDescription(newProject.getDescription()); // ★ List<String>
        if (newProject.getScreenshotUrls() != null) {
            existing.setScreenshotUrls(new ArrayList<>(newProject.getScreenshotUrls()));
        }
        return projectRepository.save(existing);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }
}
