// src/main/java/com/tom/portfolio/backend/controller/ContactController.java
package com.tom.portfolio.backend.controller;

import com.tom.portfolio.backend.model.Contact;
import com.tom.portfolio.backend.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // 访客提交（公开）
    @PostMapping
    public Contact save(@RequestBody Contact c) {
        return contactService.saveContact(c);
    }

    // 管理端查询（需要管理员）
    @GetMapping("/admin")
    public List<Contact> getAll() {
        return contactService.getAllContacts();
    }

    // 管理端删除（需要管理员）
    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        contactService.deleteContactById(id);
    }
}
