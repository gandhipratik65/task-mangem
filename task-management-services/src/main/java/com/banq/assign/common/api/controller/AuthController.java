package com.banq.assign.common.api.controller;

import com.banq.assign.common.api.dto.AuthRequestDTO;
import com.banq.assign.common.api.dto.AuthResponseDTO;
import com.banq.assign.common.api.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;
    /**
     * Handles user login requests.
     *
     * @param authRequest The authentication request containing login credentials.
     * @return A response entity containing the authentication response.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO authRequest) {
        AuthResponseDTO authResponse = authService.authenticate(authRequest);
        logger.info("User logged in successfully: {}", authRequest.getUsername());
        return ResponseEntity.ok(authResponse);
    }
}
