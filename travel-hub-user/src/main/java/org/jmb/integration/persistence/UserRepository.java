package org.jmb.integration.persistence;

import org.jmb.domain.User;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface UserRepository {
    /**
     * Create a User and store it in database
     * @param user
     */
    CompletionStage<Void> create(final User user);

    /**
     * Update an user identifies by username parameter
     * @param user
     * @param username
     */
    CompletionStage<Void> update(final User user, final String username);

    /**
     * Delete an user identifies by username parameter
     * @param username
     */
    CompletionStage<Void> delete(final String username);

    /**
     * Retrieve all user stored in database
     * @return
     */
    CompletionStage<List<User>> findAll();

    /**
     * Retrieve the user identifies by username parameter
     * @param username
     * @return
     */
    CompletionStage<User> findByUsername(final String username);
}
