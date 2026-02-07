package org.example.billing;

import io.github.vishalmysore.tools4ai.EnableAgent;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAgent
@PropertySource("classpath:application-billing.properties")
@Log
public class BillingServer {
    public static void main(String[] args) {
        SpringApplication.run(BillingServer.class, args);
        log.info("Billing Server started successfully on port 8874");
    }
}
