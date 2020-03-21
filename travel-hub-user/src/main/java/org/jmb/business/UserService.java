package org.jmb.business;

import org.jmb.domain.User;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface UserService {

    /**
     * Create a User and store it in database
     * @param user
     */
    CompletableFuture create(final User user);

    /**
     * Update an user identifies by username parameter
     * @param user
     * @param username
     */
    CompletionStage update(final User user, final String username);

    /**
     * Delete an user identifies by username parameter
     * @param username
     */
    CompletionStage delete(final String username);

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
    CompletionStage<Optional<User>> findByUsername(final String username);
}
