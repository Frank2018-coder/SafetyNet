package com.safety;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication @Slf4j
@OpenAPIDefinition(
    info = @Info(title = "OC-JAVA PROJET", version = "1.0", description = "Safetynet Alert by Donald")
)
public class SafetyNet {
    public static void main(String[] args) {
        SpringApplication.run(SafetyNet.class, args);
    }
}
