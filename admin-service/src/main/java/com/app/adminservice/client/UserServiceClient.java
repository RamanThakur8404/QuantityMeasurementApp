package com.app.adminservice.client;

import com.app.adminservice.dto.MessageResponse;
import com.app.adminservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign client to communicate with User Service.
 */
@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users")
    List<UserDTO> getAllUsers();

    @GetMapping("/api/v1/users/{email}")
    UserDTO getUserByEmail(@PathVariable("email") String email);

    @DeleteMapping("/api/v1/users/{id}")
    MessageResponse deleteUser(@PathVariable("id") Long id);
}
