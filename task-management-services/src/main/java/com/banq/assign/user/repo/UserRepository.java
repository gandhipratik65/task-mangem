package com.banq.assign.user.repo;


import com.banq.assign.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 *
 * This interface extends JpaRepository to provide CRUD operations and query methods for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a {@link User} entity by its username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found user, or an empty Optional if no user is found with the given username.
     */
    Optional<User> findByUsername(String username);

}

