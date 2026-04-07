package com.app.userservice.service;

import com.app.userservice.dto.request.AuthRequest;
import com.app.userservice.dto.request.ForgotPasswordRequest;
import com.app.userservice.dto.request.RegisterRequest;
import com.app.userservice.dto.response.AuthResponse;
import com.app.userservice.dto.response.MessageResponse;
import com.app.userservice.entity.User;
import com.app.userservice.enums.AuthProvider;
import com.app.userservice.enums.Role;
import com.app.userservice.repository.UserRepository;
import com.app.userservice.security.UserPrincipal;
import com.app.userservice.security.jwt.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider,
                                 EmailService emailService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email already exists: {}", request.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already in use.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .provider(AuthProvider.LOCAL)
                .role(Role.USER)
                .build();

        userRepository.save(user);
        log.info("User registered: {}", user.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        emailService.sendRegistrationEmail(user.getEmail(),
                user.getName() != null ? user.getName() : "User");

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .email(principal.getEmail())
                .name(principal.getUser().getName())
                .role(principal.getUser().getRole().name())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            emailService.sendLoginNotificationEmail(request.getEmail());

            return AuthResponse.builder()
                    .accessToken(token)
                    .tokenType("Bearer")
                    .email(principal.getEmail())
                    .name(principal.getUser().getName())
                    .role(principal.getUser().getRole().name())
                    .build();

        } catch (AuthenticationException ex) {
            log.warn("Login failed: {}", request.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        }
    }

    public MessageResponse forgotPassword(String email, ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + email));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        emailService.sendForgotPasswordEmail(email);
        log.info("Password updated (forgot): {}", email);

        return new MessageResponse("Password updated successfully!");
    }

    public MessageResponse resetPassword(String email,
                                         String currentPassword,
                                         String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found: " + email));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Current password is incorrect!");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(email);
        log.info("Password reset: {}", email);

        return new MessageResponse("Password reset successfully!");
    }
}
