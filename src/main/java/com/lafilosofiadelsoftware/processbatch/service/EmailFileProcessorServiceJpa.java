package com.lafilosofiadelsoftware.processbatch.service;


import com.lafilosofiadelsoftware.processbatch.sql.jpa.EmailRepository;
import com.lafilosofiadelsoftware.processbatch.sql.orm.EmailEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class EmailFileProcessorServiceJpa {

    private EmailRepository emailRepository;


    public void processFileAndInsert(String fileName) {
        List<EmailEntity> emailEntities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String emailAddress = parts[0];
                Integer email = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) : 1;
                EmailEntity emailEntity = getEmailEntity(parts, emailAddress, email);
                emailEntities.add(emailEntity);
            }
        } catch (IOException e) {
            log.error("Impossible to read file", e);
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
        emailRepository.saveAll(emailEntities);
    }

    private static EmailEntity getEmailEntity(String[] parts, String emailAddress, Integer email) {
        Integer application = parts.length > 2 && !parts[2].isEmpty() ? Integer.parseInt(parts[2]) : 1;
        Integer flow = parts.length > 3 && !parts[3].isEmpty() ? Integer.parseInt(parts[3]) : 0;
        LocalDateTime storageUpdate = parts.length > 4 && !parts[4].isEmpty()
                ? LocalDateTime.parse(parts[4]) : null;
        LocalDateTime storageRequest = parts.length > 5 && !parts[5].isEmpty()
                ? LocalDateTime.parse(parts[5]) : null;

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setEmailAddress(emailAddress);
        emailEntity.setEmail(email);
        emailEntity.setApplication(application);
        emailEntity.setFlow(flow);
        emailEntity.setStorageUpdate(storageUpdate);
        emailEntity.setStorageRequest(storageRequest);
        return emailEntity;
    }
}

