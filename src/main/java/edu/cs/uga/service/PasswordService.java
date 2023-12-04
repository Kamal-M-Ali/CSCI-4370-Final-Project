package edu.cs.uga.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Simple service class for password encryption.
 */
@Service
public class PasswordService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean isValidPassword(String password) {
        return password.length() > 6;
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}