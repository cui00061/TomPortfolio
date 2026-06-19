package com.tom.portfolio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// ★ 多值字段相关
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.OrderColumn;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.FetchType;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // 项目描述（EAGER 保证直接取出）
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "project_descriptions",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "text", columnDefinition = "TEXT")
    @OrderColumn(name = "sort_index")
    private List<String> description = new ArrayList<>();

    // 项目截图（EAGER 保证直接取出）
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "project_screenshots",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "url", length = 512)
    @OrderColumn(name = "sort_index")
    private List<String> screenshotUrls = new ArrayList<>();
}
