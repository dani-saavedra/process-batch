package com.lafilosofiadelsoftware.processbatch.service;

import com.lafilosofiadelsoftware.processbatch.sql.jpa.EmailRepository;
import com.lafilosofiadelsoftware.processbatch.sql.orm.EmailEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
@Slf4j
public class EmailConcurrentInserterService {

    private final JdbcTemplate jdbcTemplate;
    private final EmailRepository emailRepository;

    public void insertEmailsConcurrently(List<String[]> emailData, int threadCount) throws ExecutionException, InterruptedException {
        int batchSize = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        log.info("Iniciando guardado");
        List<Future<?>> futures = new ArrayList<>();
        int total = emailData.size();
        for (int i = 0; i < total; i += batchSize) {
            List<String[]> batch = emailData.subList(i, Math.min(i + batchSize, total));
            Future<?> submit = executorService.submit(() -> insertBatch(batch));
            futures.add(submit);
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("error al ejecutar el futuro", e);
            }
        }
    }

    private void insertBatch(List<String[]> batch) {
        log.info("Realizando insercción batch de {}", batch.size());
        List<EmailEntity> emailEntities = new ArrayList<>();
        for (String[] row : batch) {
            emailEntities.add(getEmailEntity(row));
        }
        emailRepository.saveAll(emailEntities);
    /*
        String sql = "INSERT INTO t_crc_email (EMAIL_ADDRESS, EMAIL, APPLICATION, FLOW, STORAGE_UPDATE, STORAGE_REQUEST) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        List<Object[]> batchArgs = batch.stream()
                .map(row -> new Object[]{
                        row[0],
                        Integer.parseInt(row[1]),
                        Integer.parseInt(row[2]),
                        Integer.parseInt(row[3]),
                        row[4],
                        row[5]
                })
                .toList();
        jdbcTemplate.batchUpdate(sql, batchArgs);

     */
        log.info("Guardado de  {}", batch.size());
    }

    private static EmailEntity getEmailEntity(String[] parts) {
        Integer application = parts.length > 2 && !parts[2].isEmpty() ? Integer.parseInt(parts[2]) : 1;
        Integer flow = parts.length > 3 && !parts[3].isEmpty() ? Integer.parseInt(parts[3]) : 0;
        LocalDateTime storageUpdate = parts.length > 4 && !parts[4].isEmpty()
                ? LocalDateTime.parse(parts[4]) : null;
        LocalDateTime storageRequest = parts.length > 5 && !parts[5].isEmpty()
                ? LocalDateTime.parse(parts[5]) : null;

        String emailAddress = parts[0];
        Integer email = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) : 1;

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
