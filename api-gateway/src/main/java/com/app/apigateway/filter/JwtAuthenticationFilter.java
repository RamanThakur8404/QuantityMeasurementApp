package com.app.apigateway.filter;

import com.app.apigateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Global filter that validates JWT tokens at the gateway.
 * Public endpoints are skipped; all others require a valid JWT.
 * Extracts user info and passes it as headers to downstream services.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    // Public endpoints that skip JWT validation
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/forgotPassword",
            "/oauth2/",
            "/login/oauth2/",
            "/swagger-ui",
            "/v3/api-docs",
            "/actuator/health",
            "/actuator/info",
            "/h2-console",
            "/eureka"
    );

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Skip public endpoints
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // Check Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        // Validate token
        if (!jwtUtil.validateToken(token)) {
            log.warn("Invalid JWT token for: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract user info from token
        String email = jwtUtil.getEmailFromToken(token);
        String roles = jwtUtil.getRolesFromToken(token);

        // Check admin access for admin endpoints
        if (path.startsWith("/api/v1/admin") && !roles.contains("ROLE_ADMIN")) {
            log.warn("Non-admin user {} tried to access: {}", email, path);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // Pass user info as headers to downstream services
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Email", email)
                .header("X-User-Roles", roles)
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1; // Run before other filters
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
