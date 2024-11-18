package com.lafilosofiadelsoftware.processbatch.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class EmailFileProcessorServiceJdbc {

    private final JdbcTemplate jdbcTemplate;

    public void insertEmailsBatch(String fileName) {
        String sql = "INSERT INTO t_crc_email (EMAIL_ADDRESS, EMAIL, APPLICATION, FLOW, STORAGE_UPDATE, STORAGE_REQUEST) VALUES (?, ?, ?, ?, ?, ?)";
        log.info("Comenzando inserción");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName))))) {
            List<Object[]> batchArgs = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String emailAddress = parts[0];
                Integer email = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) : 1;
                Integer application = parts.length > 2 && !parts[2].isEmpty() ? Integer.parseInt(parts[2]) : 1;
                Integer flow = parts.length > 3 && !parts[3].isEmpty() ? Integer.parseInt(parts[3]) : 0;
                String storageUpdate = parts.length > 4 && !parts[4].isEmpty() ? parts[4] : null;
                String storageRequest = parts.length > 5 && !parts[5].isEmpty() ? parts[5] : null;
                batchArgs.add(new Object[]{emailAddress, email, application, flow, storageUpdate, storageRequest});
                if (batchArgs.size() == 500) {
                    log.info("Guardando batch");
                    jdbcTemplate.batchUpdate(sql, batchArgs);
                    batchArgs.clear();
                }
            }
            if (!batchArgs.isEmpty()) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
            }
        } catch (Exception e) {
            log.error("Impossible to read file", e);
            throw new RuntimeException("Error reading file: " + fileName + ". " + e.getMessage());
        }
        log.info("finaliza inserción");
    }
}

