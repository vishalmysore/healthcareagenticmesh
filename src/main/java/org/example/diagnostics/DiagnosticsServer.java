package org.example.diagnostics;

import io.github.vishalmysore.tools4ai.EnableAgent;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAgent
@PropertySource("classpath:application-diagnostics.properties")
@Log
public class DiagnosticsServer {
    public static void main(String[] args) {
        SpringApplication.run(DiagnosticsServer.class, args);
        log.info("Diagnostics Server started successfully on port 8873");
    }
}
