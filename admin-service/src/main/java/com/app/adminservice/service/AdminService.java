package com.app.adminservice.service;

import com.app.adminservice.client.MeasurementServiceClient;
import com.app.adminservice.client.UserServiceClient;
import com.app.adminservice.dto.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Service that orchestrates calls to User Service and Measurement Service.
 */
@Slf4j
@Service
public class AdminService {

    private final UserServiceClient userServiceClient;
    private final MeasurementServiceClient measurementServiceClient;

    public AdminService(UserServiceClient userServiceClient,
                        MeasurementServiceClient measurementServiceClient) {
        this.userServiceClient = userServiceClient;
        this.measurementServiceClient = measurementServiceClient;
    }

    // ===== User Management =====

    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users from user-service");
        return userServiceClient.getAllUsers();
    }

    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        return userServiceClient.getUserByEmail(email);
    }

    public MessageResponse deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        return userServiceClient.deleteUser(id);
    }

    // ===== Measurement Management =====

    public List<MeasurementDTO> getErrorHistory() {
        log.info("Fetching error history from measurement-service");
        return measurementServiceClient.getErrorHistory();
    }

    public List<MeasurementDTO> getHistoryByOperation(String operation) {
        log.info("Fetching history for operation: {}", operation);
        return measurementServiceClient.getHistoryByOperation(operation);
    }

    public List<MeasurementDTO> getHistoryByMeasurementType(String measurementType) {
        log.info("Fetching history for measurement type: {}", measurementType);
        return measurementServiceClient.getHistoryByMeasurementType(measurementType);
    }

    public Long getOperationCount(String operation) {
        log.info("Fetching count for operation: {}", operation);
        return measurementServiceClient.getOperationCount(operation);
    }

    // ===== Dashboard =====

    public DashboardDTO getDashboardStats() {
        log.info("Building dashboard stats");

        List<UserDTO> users = userServiceClient.getAllUsers();
        List<MeasurementDTO> errors = measurementServiceClient.getErrorHistory();

        Map<String, Long> operationCounts = new HashMap<>();
        String[] operations = {"compare", "convert", "add", "subtract", "divide"};
        for (String op : operations) {
            try {
                operationCounts.put(op, measurementServiceClient.getOperationCount(op));
            } catch (Exception e) {
                log.warn("Failed to fetch count for {}: {}", op, e.getMessage());
                operationCounts.put(op, 0L);
            }
        }

        return DashboardDTO.builder()
                .totalUsers(users.size())
                .totalErrors(errors.size())
                .operationCounts(operationCounts)
                .build();
    }
}
