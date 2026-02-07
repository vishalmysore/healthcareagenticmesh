package org.example.appointments;

import io.github.vishalmysore.tools4ai.EnableAgent;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAgent
@PropertySource("classpath:application-appointments.properties")
@Log
public class AppointmentsServer {
    public static void main(String[] args) {
        SpringApplication.run(AppointmentsServer.class, args);
        log.info("Appointments Server started successfully on port 8872");
    }
}
