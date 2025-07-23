package com.tom.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fName;
    private String lName;
    private String title;
    private String bio;
    private String portfolioImage;

    //@ElementCollection仅可用在固定的数据类型如：String,Integer 等。JPA会自动为你生成一个额外的数据库表，用于保存这组值，并且自动关联回主实体。
    /*如果是自定义实体数据结构，可以使用 “@OneToMany"来做注解,比如
        @OneToMany(mappedBy = "about", cascade = CascadeType.ALL)
        private List<Education> educations;*/
    @ElementCollection
    private List<String> skills;
    @ElementCollection
    private List<String> educations;
    @ElementCollection
    private List<String> languages;

    /*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPortfolioImage() {
        return portfolioImage;
    }

    public void setPortfolioImage(String portfolioImage) {
        this.portfolioImage = portfolioImage;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getEducations() {
        return educations;
    }

    public void setEducations(List<String> educations) {
        this.educations = educations;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
   */
}
