package com.app.userservice.config;

import com.app.userservice.security.oauth2.CustomOAuth2UserService;
import com.app.userservice.security.CustomUserDetailsService;
import com.app.userservice.security.jwt.JwtAccessDeniedHandler;
import com.app.userservice.security.jwt.JwtAuthenticationEntryPoint;
import com.app.userservice.security.jwt.JwtAuthenticationFilter;
import com.app.userservice.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.app.userservice.security.oauth2.OAuth2AuthenticationSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          CustomOAuth2UserService customOAuth2UserService,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler,
                          OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
                          OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler) {

        this.customUserDetailsService = customUserDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(AbstractHttpConfigurer::disable) // CORS handled by API Gateway
            .csrf(AbstractHttpConfigurer::disable)

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )

            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers(
                        "/api/v1/auth/login",
                        "/api/v1/auth/register",
                        "/api/v1/auth/forgotPassword/**",
                        "/api/v1/auth/oauth2/**",
                        "/oauth2/**",
                        "/login/oauth2/**"
                ).permitAll()

                // Internal endpoints (called by Admin Service via Feign)
                .requestMatchers("/api/v1/users/**").permitAll()

                // Swagger & docs
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/v3/api-docs"
                ).permitAll()

                // H2 console
                .requestMatchers("/h2-console/**").permitAll()

                // Actuator
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()

                // All others require auth
                .anyRequest().authenticated()
            )

            .headers(headers ->
                headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
            )

            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)

            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo ->
                    userInfo.userService(customOAuth2UserService)
                )
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
            )

            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
