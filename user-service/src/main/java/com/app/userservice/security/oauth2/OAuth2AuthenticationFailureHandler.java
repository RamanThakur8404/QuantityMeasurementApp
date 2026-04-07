package com.app.userservice.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.oauth2.redirect-uri:http://localhost:8080/swagger-ui.html}")
    private String redirectUri;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String errorMessage = exception.getLocalizedMessage();
        log.warn("OAuth2 authentication failed: {}", errorMessage);

        String encodedError = URLEncoder.encode(
                errorMessage != null ? errorMessage : "Authentication failed",
                StandardCharsets.UTF_8);

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("error", encodedError)
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
