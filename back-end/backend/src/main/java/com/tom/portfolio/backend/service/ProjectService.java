package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//用作接收controller的请求并通过Repository进行操作，最后返回给controller
//controller 接收请求 → service 负责“处理事情” → repository 负责“查数据库”
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Long id, Project newProject) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existing.setTitle(newProject.getTitle());
        existing.setDescription(newProject.getDescription());
        // 可添加更多字段
        return projectRepository.save(existing);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }

}
