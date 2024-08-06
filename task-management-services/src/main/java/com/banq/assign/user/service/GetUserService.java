package com.banq.assign.user.service;

import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.user.entity.User;

/**
 * Service interface for managing user-related operations.
 *
 * This interface defines methods for retrieving {@link User} entities.
 */
public interface GetUserService {

    /**
     * Retrieves a {@link User} entity by its username.
     *
     * @param userName The username of the user to retrieve.
     * @return The User entity associated with the given username.
     * @throws ResourceNotFoundException If no user is found with the given username.
     */
    User getUserByUserName(String userName) throws ResourceNotFoundException;
}
