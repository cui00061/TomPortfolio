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

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // ★ CHANGED: description 改为多值列表，使用从表持久化（带顺序）
    @ElementCollection
    @CollectionTable(
            name = "project_descriptions",                    // 从表名
            joinColumns = @JoinColumn(name = "project_id")    // 外键
    )
    @Column(name = "text", columnDefinition = "TEXT")     // 每条描述用 TEXT，避免长度限制
    @OrderColumn(name = "sort_index")                     // 保留前端传入顺序
    private List<String> description = new ArrayList<>();

    // ★ 已在你项目中：截图多值
    @ElementCollection
    @CollectionTable(
            name = "project_screenshots",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "url", length = 512)
    @OrderColumn(name = "sort_index")
    private List<String> screenshotUrls = new ArrayList<>();
}
