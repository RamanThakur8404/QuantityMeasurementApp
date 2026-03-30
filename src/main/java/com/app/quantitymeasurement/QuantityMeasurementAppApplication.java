package com.app.quantitymeasurement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main class to start Spring Boot application
@SpringBootApplication

// Swagger/OpenAPI configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Quantity Measurement API",
        version = "17.0",
        description = "REST API for measurement operations"
    )
)
public class QuantityMeasurementAppApplication {

    // Entry point (starts embedded server and Spring context)
    public static void main(String[] args) {
        SpringApplication.run(QuantityMeasurementAppApplication.class, args);
    }
}