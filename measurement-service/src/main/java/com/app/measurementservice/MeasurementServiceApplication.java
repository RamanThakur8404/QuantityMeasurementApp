package com.app.measurementservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
    info = @Info(
        title = "Measurement Service API",
        version = "1.0",
        description = "Quantity measurement operations"
    )
)
public class MeasurementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeasurementServiceApplication.class, args);
    }
}
