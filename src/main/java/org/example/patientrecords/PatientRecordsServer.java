package org.example.patientrecords;

import io.github.vishalmysore.tools4ai.EnableAgent;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAgent
@PropertySource("classpath:application-patientrecords.properties")
@Log
public class PatientRecordsServer {
    public static void main(String[] args) {
        SpringApplication.run(PatientRecordsServer.class, args);
        log.info("Patient Records Server started successfully on port 8871");
    }
}
