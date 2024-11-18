package com.lafilosofiadelsoftware.processbatch.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    public void insertEmailsConcurrently(List<String[]> emailData, int threadCount) throws ExecutionException, InterruptedException {
        int batchSize = emailData.size() / threadCount;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        log.info("Iniciando guardado");
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            log.info("Comenzando ejecucción hilo {}", i);
            int start = i * batchSize;
            int end = (i == threadCount - 1) ? emailData.size() : start + batchSize;

            List<String[]> batch = emailData.subList(start, end);
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
        log.info("Guardado de  {}", batch.size());
    }
}
