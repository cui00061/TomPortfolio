package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList; // ★ NEW

// controller 接收请求 → service 负责“处理事情” → repository 负责“查数据库”
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
        // ★ NEW: 如果前端没传 screenshots，给个空列表，避免持久化时 NPE
        if (project.getScreenshotUrls() == null) {
            project.setScreenshotUrls(new ArrayList<>());
        }
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project newProject) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existing.setTitle(newProject.getTitle());
        existing.setDescription(newProject.getDescription());
        // ★ NEW: 同步更新多图字段（保持顺序）
        if (newProject.getScreenshotUrls() != null) {
            existing.setScreenshotUrls(new ArrayList<>(newProject.getScreenshotUrls())); // 防御性拷贝
        }
        return projectRepository.save(existing);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

}
