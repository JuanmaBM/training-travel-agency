package org.jmb.business;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jmb.domain.User;
import org.jmb.integration.persistence.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject private UserRepository userRepository;

    @Override
    public CompletionStage<Void> create(User user) {
        return isValid(user)
            ? userRepository.create(user)
            : CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<Void> update(User user, String username) {
        return userRepository.update(user, username);
    }

    @Override public CompletionStage<Void> delete(String username) {
        return username != null
            ? userRepository.delete(username)
            : CompletableFuture.completedFuture(null);
    }

    @Override public CompletionStage<List<User>> findAll() {
        return userRepository.findAll();
    }

    @Override public CompletionStage<User> findByUsername(String username) {
        return username != null
            ? userRepository.findByUsername(username)
            : CompletableFuture.completedFuture(null);
    }

    /**
     * Check if a user is valid. An user will be valid when email and username are defined
     * @param user
     * @return
     */
    private boolean isValid(User user) {
        return user != null
            && user.getUserName() != null
            && user.getEmail() != null;
    }
}
