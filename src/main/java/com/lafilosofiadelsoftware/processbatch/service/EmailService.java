package com.lafilosofiadelsoftware.processbatch.service;


import com.lafilosofiadelsoftware.processbatch.sql.jpa.EmailRepository;
import com.lafilosofiadelsoftware.processbatch.sql.orm.EmailEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private EmailRepository emailRepository;

    public EmailEntity saveEmail(EmailEntity emailEntity) {
        return emailRepository.save(emailEntity);
    }
}

