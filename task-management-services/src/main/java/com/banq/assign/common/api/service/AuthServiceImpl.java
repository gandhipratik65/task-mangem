package com.banq.assign.common.api.service;

import com.banq.assign.common.api.dto.AuthRequestDTO;
import com.banq.assign.common.api.dto.AuthResponseDTO;
import com.banq.assign.common.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
/**
 * Implementation of AuthService for handling user authentication.
 */
@Service
public class AuthServiceImpl implements AuthService{
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    /**
     * Authenticates the user and generates a JWT token if successful.
     *
     * @param authRequest The authentication request containing login credentials.
     * @return An AuthResponseDTO containing the JWT token.
     */
    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequest) {
        // Perform authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        // Extract user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails);

        // Log successful authentication
        logger.info("User {} authenticated successfully", authRequest.getUsername());

        return new AuthResponseDTO(token);
    }


}
