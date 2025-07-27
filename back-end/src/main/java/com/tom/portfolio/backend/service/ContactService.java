package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.Contact;
import com.tom.portfolio.backend.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    /* 不建议使用字段注入，JUnit 或 Mock 框架不好手动注入依赖， 不利于测试
    @Autowired
    private ContactRepository contactRepository;
     */

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact saveContact(Contact contact){
        return contactRepository.save(contact);
    }

   public void deleteContactById(Long id){
        contactRepository.deleteById(id);
   }

   public List<Contact> getAllContacts(){
        return contactRepository.findAll();
   }
}
