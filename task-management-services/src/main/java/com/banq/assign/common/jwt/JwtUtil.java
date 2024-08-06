package com.banq.assign.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for generating and validating JSON Web Tokens (JWT).
 * Handles operations related to JWT such as token creation, extraction, and validation.
 */
@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 1800000; // 30 min
    private final String SECRET_KEY = "C+O72z1aNDNPEvpxBdLWX0dVYu2aavLoFTNignrhQF4="; // Use a more secure key in production
    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails The user details for which the token is generated.
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which the username is extracted.
     * @return The username extracted from the token.
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    /**
     * Extracts claims from the given JWT token.
     *
     * @param token The JWT token from which the claims are extracted.
     * @return The claims extracted from the token.
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Checks if the given JWT token is expired.
     *
     * @param token The JWT token to be checked.
     * @return True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    /**
     * Validates the given JWT token by checking its username and expiration.
     *
     * @param token The JWT token to be validated.
     * @param username The username to be checked against the token.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}