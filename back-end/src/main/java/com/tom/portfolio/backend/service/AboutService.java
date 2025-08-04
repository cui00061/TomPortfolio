package com.tom.portfolio.backend.service;

import com.tom.portfolio.backend.model.About;
import com.tom.portfolio.backend.repository.AboutRepository;
import com.tom.portfolio.backend.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutService {

    /*
    @Autowired
    private AboutRepository aboutRepository;
     */

    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public About getAbout() {
        //告诉JPA,我要 更新主键为 1 的那条记录，用这个新的对象覆盖它
        return aboutRepository.findById(1L).orElse(null);
    }

    public About updateAbout(About newAbout) {
        newAbout.setId(1L); // 固定 id 为 1
        return aboutRepository.save(newAbout);
    }

    /*用于添加唯一一条内容
    public About createAbout(About about) {
        return aboutRepository.save(about);
    }

     */

}
