package com.lafilosofiadelsoftware.processbatch.sql.jpa;


import com.lafilosofiadelsoftware.processbatch.sql.orm.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {
}

