package com.lafilosofiadelsoftware.processbatch.controller;


import com.lafilosofiadelsoftware.processbatch.service.EmailFileProcessorServiceJdbc;
import com.lafilosofiadelsoftware.processbatch.service.EmailFileProcessorServiceJpa;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {


    private EmailFileProcessorServiceJpa emailService;
    private EmailFileProcessorServiceJdbc emailFileProcessorServiceJdbc;

    @GetMapping("/load")
    public ResponseEntity<String> processFile() {
        emailService.processFileAndInsert("emails.txt");
        return ResponseEntity.ok("savedEmail");
    }

    @GetMapping("/load-jdbc")
    public ResponseEntity<String> processFileJdbc() {
        emailFileProcessorServiceJdbc.insertEmailsBatch("emails.txt");
        return ResponseEntity.ok("savedEmail");
    }
}
