package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.model.Project;
import com.tom.portfolio.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//对前端的请求做反应，前端访问这个链接，Controller对用Service的服务进行操作，最后返回给前端
//controller 接收请求 → service 负责“处理事情” → repository 负责“查数据库”
@RestController
@RequestMapping
public class ProjectController {

    @Autowired
    private ProjectService projectService;

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
        old.setDescription(newProject.getDescription());
        return projectService.saveProject(old);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.deleteProjectById(id);
    }
}
