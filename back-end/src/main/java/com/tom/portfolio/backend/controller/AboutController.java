// src/main/java/com/tom/portfolio/backend/controller/AboutController.java
package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.model.About;
import com.tom.portfolio.backend.service.AboutService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/about")
public class AboutController {

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public About getAbout() {
        return aboutService.getAbout();
    }

    @PostMapping
    public About createAbout(@RequestBody About newAbout) {
        return aboutService.createAbout(newAbout);
    }

    @PutMapping
    public About updateAbout(@RequestBody About newAbout) {
        return aboutService.updateAbout(newAbout);
    }
}
