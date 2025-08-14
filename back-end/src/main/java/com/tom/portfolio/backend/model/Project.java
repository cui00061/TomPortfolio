package com.tom.portfolio.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// ★ 新增：用于多图列表的 JPA 注解
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.OrderColumn;

import lombok.Data;

// ★ 新增：列表类型
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // ★ 可选：给描述放宽一点长度（不想改动可以删掉这行）
    @Column(length = 2000) // ★
    private String description;

    // ★ 替换：原来的单图 -> 多图列表（保持顺序）
    // private String screenshotUrl; // ★ 旧字段（移除）
    @ElementCollection // ★
    @CollectionTable(
            name = "project_screenshots",                 // ★ 从表名
            joinColumns = @JoinColumn(name = "project_id")// ★ 外键
    )
    @Column(name = "url", length = 512)               // ★ 单个 URL 列
    @OrderColumn(name = "sort_index")                 // ★ 保序
    private List<String> screenshotUrls = new ArrayList<>(); // ★

    /*
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // ★ 旧的 getter/setter（单图）留档
    // public String getScreenshotUrl() {
    //     return screenshotUrl;
    // }
    // public void setScreenshotUrl(String screenshotUrl) {
    //     this.screenshotUrl = screenshotUrl;
    // }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
     */
}
