package com.banq.assign.user.service;

import com.banq.assign.user.entity.User;
import com.banq.assign.common.exception.ResourceNotFoundException;
import com.banq.assign.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Implementation of the {@link GetUserService} interface.
 * Provides methods to retrieve user information from the repository.
 */
@Service
public class GetUserServiceImpl implements GetUserService{

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a {@link User} by their username.
     *
     * @param userName the username of the user to retrieve
     * @return the {@link User} object if found
     * @throws ResourceNotFoundException if no user is found with the provided username
     */
    @Override
    public User getUserByUserName(String userName) throws ResourceNotFoundException {
        // Fetch the user from the repository using the username
        Optional<User> optionalUser = userRepository.findByUsername(userName);

        // Check if the user is present in the repository
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        // Throw an exception if the user is not found
        throw new ResourceNotFoundException("User is not present");

    }
}
