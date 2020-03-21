package org.jmb.business;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ValidationException;

import org.jmb.domain.User;
import org.jmb.integration.persistence.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject private UserRepository userRepository;

    @Override
    public CompletableFuture create(User user) {
        return isValid(user)
            ? userRepository.create(user)
            : CompletableFuture.supplyAsync(() -> {
                throw new ValidationException();
            });
    }

    @Override
    public CompletionStage update(User user, String username) {
        return isValid(user)
            ? userRepository.update(user, username)
            : CompletableFuture.supplyAsync(ValidationException::new);

    }

    @Override
    public CompletionStage delete(String username) {
        return username != null
            ? userRepository.delete(username)
            : CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<List<User>> findAll() {
        return userRepository.findAll();
    }

    @Override
    public CompletionStage<Optional<User>> findByUsername(String username) {
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
