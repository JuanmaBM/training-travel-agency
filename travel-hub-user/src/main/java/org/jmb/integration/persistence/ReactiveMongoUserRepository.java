package org.jmb.integration.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jmb.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.model.Filters;
import io.quarkus.mongodb.ReactiveMongoClient;
import io.quarkus.mongodb.ReactiveMongoCollection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

@ApplicationScoped
public class ReactiveMongoUserRepository implements UserRepository {

    private ReactiveMongoClient mongoClient;
    private ReactiveMongoCollection<Document> mongoCollection;
    private Logger log = LoggerFactory.getLogger(ReactiveMongoUserRepository.class);

    //@ConfigProperty(name = "mongo.user.database")
    private String USER_DATABASE = "hub";
    //@ConfigProperty(name = "mongo.user.collection")
    private String USER_COLLECTION = "user";
    private static final String IDENTIFIED_FIELD = "username";


    private final Function<Document, User> toUser = document -> new User.Builder()
        .birthDate(document.get("birthDate", Date.class).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
        .email(document.getString("email"))
        .name(document.getString("name"))
        .surname(document.getString("surname"))
        .nif(document.getString("nif"))
        .username(document.getString(IDENTIFIED_FIELD))
        .build();

    @Inject
    public ReactiveMongoUserRepository(final ReactiveMongoClient mongoClient) {
        this.mongoClient = mongoClient;
        log.info("Connecting to database {} and collection {}", USER_DATABASE, USER_COLLECTION);
        mongoCollection = mongoClient.getDatabase(USER_DATABASE).getCollection(USER_COLLECTION);
    }

    @Override public CompletionStage<Void> create(User user) {
        final Document userDocument = toDocument(user);
        return mongoCollection.insertOne(userDocument);
    }

    @Override
    public CompletionStage<Void> update(final User user, final String username) {
        final Document userDocument = toDocument(user);
        mongoCollection.updateOne(Filters.eq(IDENTIFIED_FIELD, username), userDocument);
        return CompletableFuture.completedFuture(null);
    }

    @Override public CompletionStage<Void> delete(String username) {
        mongoCollection.deleteOne(Filters.eq(IDENTIFIED_FIELD));
        return CompletableFuture.completedFuture(null);
    }

    @Override public CompletionStage<List<User>> findAll() {
        return mongoCollection.find()
            .map(toUser)
            .toList()
            .run();
    }

    @Override public CompletionStage<User> findByUsername(String username) {
        return mongoCollection.find(Filters.eq("username", username))
            .map(toUser)
            .limit(1)
            .findFirst()
            .run()
            .thenApply(optional -> optional.orElse(null));
    }

    private Document toDocument(User user) {
        final Document userDocument = new Document();
        userDocument.append("name", user.getName())
            .append("surname", user.getSurname())
            .append("email", user.getEmail())
            .append(IDENTIFIED_FIELD, user.getUserName())
            .append("birthDate", user.getBirthDate())
            .append("nif", user.getNif());
        return userDocument;
    }

}
