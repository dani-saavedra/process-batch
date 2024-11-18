package com.lafilosofiadelsoftware.processbatch.sql.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_CRC_EMAIL")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EMAIL_ADDRESS", length = 500, nullable = false)
    private String emailAddress;

    @Column(name = "EMAIL", nullable = false)
    private Integer email = 1;

    @Column(name = "APPLICATION", nullable = false)
    private Integer application = 1;

    @Column(name = "FLOW", nullable = false)
    private Integer flow = 0;

    @Column(name = "STORAGE_UPDATE")
    private LocalDateTime storageUpdate;

    @Column(name = "STORAGE_REQUEST")
    private LocalDateTime storageRequest;

}
