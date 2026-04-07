package com.app.userservice.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.userservice.entity.User;
import com.app.userservice.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email));

        return UserPrincipal.create(user);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id)
            throws UsernameNotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with id: " + id));

        return UserPrincipal.create(user);
    }
}
