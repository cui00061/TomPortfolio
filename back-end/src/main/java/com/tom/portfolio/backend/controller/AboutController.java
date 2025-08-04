package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.model.About;
import com.tom.portfolio.backend.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/about")
public class AboutController {

    /* 不建议使用字段注入，JUnit 或 Mock 框架不好手动注入依赖， 不利于测试
    @Autowired
    private AboutService aboutService;
     */

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public About getAbout() {
        return aboutService.getAbout();
    }

    @PutMapping
    public About updateAbout(@RequestBody About newAbout) {
        return aboutService.updateAbout(newAbout);
    }

    /* 用于添加唯一一条内容
    @PostMapping
    public About createAbout(@RequestBody About newAbout) {
        return aboutService.createAbout(newAbout);
    }
     */

}
