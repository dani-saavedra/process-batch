package com.lafilosofiadelsoftware.processbatch.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class EmailFileProcessorWithThreads {

    private EmailConcurrentInserterService emailConcurrentInserterService;

    public void processFileAndInsertConcurrently(String fileName, int threadCount) {
        log.info("Leyendo informacion de documento");
        List<String[]> emailData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            String line;
            while ((line = br.readLine()) != null) {
                emailData.add(line.split(","));
            }
            emailConcurrentInserterService.insertEmailsConcurrently(emailData, threadCount);
        } catch (Exception e) {
            log.error("error reading file", e);
            throw new RuntimeException("Error reading file: " + fileName + ". " + e.getMessage());
        }
    }
}

