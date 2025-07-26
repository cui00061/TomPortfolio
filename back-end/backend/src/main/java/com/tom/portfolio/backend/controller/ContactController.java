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

    //接收访客留言（公开）
    @PostMapping("")
    public Contact save(@RequestBody Contact c) {
        return contactService.saveContact(c);
    }

    @GetMapping("/admin/contact")
    public List<Contact> getAll() {
        return contactService.getAllContacts();
    }

    @DeleteMapping("/admin/contact/{id}")
    public void delete(@PathVariable Long id) {
        contactService.deleteContactById(id);
    }

}
