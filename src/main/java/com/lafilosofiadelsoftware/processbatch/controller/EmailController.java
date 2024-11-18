package com.lafilosofiadelsoftware.processbatch.controller;


import com.lafilosofiadelsoftware.processbatch.service.EmailFileProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {


    private EmailFileProcessorService emailService;

    @GetMapping("/load")
    public ResponseEntity<String> processFile() {
        emailService.processFileAndInsert("emails.txt");
        return ResponseEntity.ok("savedEmail");
    }
}
