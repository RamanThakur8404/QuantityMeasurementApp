package com.app.adminservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(
    info = @Info(
        title = "Admin Service API",
        version = "1.0",
        description = "Admin dashboard and management operations"
    )
)
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }
}
