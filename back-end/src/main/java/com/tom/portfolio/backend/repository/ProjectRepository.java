package com.tom.portfolio.backend.repository;

import com.tom.portfolio.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

//用来做CRUD，后面的<project,Long>分别代表了数据库和id类型
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
