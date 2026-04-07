package com.app.adminservice.controller;

import com.app.adminservice.dto.*;
import com.app.adminservice.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Admin-only endpoints.
 * Access is enforced at the API Gateway (ROLE_ADMIN check)
 * and also validated here via X-User-Roles header (defense-in-depth).
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin", description = "Admin dashboard and management APIs")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ===== User Management =====

    @GetMapping("/users")
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getUserByEmail(email));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<MessageResponse> deleteUser(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.deleteUser(id));
    }

    // ===== Measurement Management =====

    @GetMapping("/measurements/errors")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<MeasurementDTO>> getErrorHistory(
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getErrorHistory());
    }

    @GetMapping("/measurements/history/{operation}")
    @Operation(summary = "Get history by operation")
    public ResponseEntity<List<MeasurementDTO>> getHistoryByOperation(
            @PathVariable String operation,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getHistoryByOperation(operation));
    }

    @GetMapping("/measurements/type/{measurementType}")
    @Operation(summary = "Get history by measurement type")
    public ResponseEntity<List<MeasurementDTO>> getHistoryByMeasurementType(
            @PathVariable String measurementType,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getHistoryByMeasurementType(measurementType));
    }

    @GetMapping("/measurements/count/{operation}")
    @Operation(summary = "Get operation count")
    public ResponseEntity<Long> getOperationCount(
            @PathVariable String operation,
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getOperationCount(operation));
    }

    // ===== Dashboard =====

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard stats")
    public ResponseEntity<DashboardDTO> getDashboard(
            @RequestHeader(value = "X-User-Roles", required = false) String roles) {
        validateAdminRole(roles);
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    // ===== Defense-in-depth: validate admin role from gateway header =====

    private void validateAdminRole(String roles) {
        if (roles == null || !roles.contains("ROLE_ADMIN")) {
            log.warn("Non-admin access attempt. Roles: {}", roles);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Access denied. Admin role required.");
        }
    }
}
