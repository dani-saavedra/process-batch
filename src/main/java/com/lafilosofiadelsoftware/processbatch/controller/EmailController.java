package com.lafilosofiadelsoftware.processbatch.controller;


import com.lafilosofiadelsoftware.processbatch.service.EmailFileProcessorServiceJdbc;
import com.lafilosofiadelsoftware.processbatch.service.EmailFileProcessorServiceJpa;
import com.lafilosofiadelsoftware.processbatch.service.EmailFileProcessorWithThreads;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
@Slf4j
public class EmailController {


    private EmailFileProcessorServiceJpa emailService;
    private EmailFileProcessorServiceJdbc emailFileProcessorServiceJdbc;
    private EmailFileProcessorWithThreads emailFileProcessorWithThreads;

    @GetMapping("/load")
    public ResponseEntity<String> processFile() {
        emailService.processFileAndInsert("emails.txt");
        return ResponseEntity.ok("savedEmail");
    }

    @GetMapping("/load-jdbc")
    public ResponseEntity<String> processFileJdbc() {
        emailFileProcessorServiceJdbc.insertEmailsBatch("emails.txt");
        log.info("Finalizado guardado");
        return ResponseEntity.ok("savedEmail");
    }

    @GetMapping("/load-threads")
    public ResponseEntity<String> processFileThreads() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.info("Número de núcleos disponibles: {}", availableProcessors);
        emailFileProcessorWithThreads.processFileAndInsertConcurrently("emails_medium.txt", Runtime.getRuntime().availableProcessors());
        log.info("Finalizado guardado");
        return ResponseEntity.ok("savedEmail");
    }
}
