package com.banq.assign.common.api.service;

import com.banq.assign.common.api.dto.AuthRequestDTO;
import com.banq.assign.common.api.dto.AuthResponseDTO;
/**
 * Service interface for handling authentication-related operations.
 */
public interface AuthService {
    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param authRequest The authentication request containing login credentials.
     * @return An AuthResponseDTO containing authentication details and token.
     */
    AuthResponseDTO authenticate(AuthRequestDTO authRequest);

}
