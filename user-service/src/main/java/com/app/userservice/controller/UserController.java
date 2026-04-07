package com.app.userservice.controller;

import com.app.userservice.dto.response.MessageResponse;
import com.app.userservice.dto.response.UserDTO;
import com.app.userservice.entity.User;
import com.app.userservice.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Internal REST endpoints for inter-service communication (Admin Service).
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Internal user management APIs")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("GET /api/v1/users");
        List<UserDTO> users = userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        log.info("GET /api/v1/users/{}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + email));
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        log.info("DELETE /api/v1/users/{}", id);
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }
}
