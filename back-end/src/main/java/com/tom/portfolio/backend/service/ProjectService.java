// ProjectService.java （只贴改动后的完整内容）
package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.repository.ProjectRepository;
// ✅ 使用 Spring 的事务注解（支持 readOnly）
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll(); // 直接用 JpaRepository 自带的方法
    }


    public Project saveProject(Project project) {
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
        existing.setDescription(newProject.getDescription());
        if (newProject.getScreenshotUrls() != null) {
            existing.setScreenshotUrls(new ArrayList<>(newProject.getScreenshotUrls()));
        }
        return projectRepository.save(existing);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }
}
