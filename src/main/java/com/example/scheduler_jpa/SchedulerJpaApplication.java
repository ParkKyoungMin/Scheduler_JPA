package com.example.scheduler_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // JPA Auditing 활성화
public class SchedulerJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerJpaApplication.class, args);
    }

}
