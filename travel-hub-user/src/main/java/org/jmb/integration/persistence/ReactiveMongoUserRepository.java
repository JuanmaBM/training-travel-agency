package org.jmb.integration.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.jmb.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.quarkus.mongodb.ReactiveMongoClient;
import io.quarkus.mongodb.ReactiveMongoCollection;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public CompletionStage<UpdateResult> update(final User user, final String username) {
        final Document userDocument = toDocument(user);
        final BasicDBObject setQuery = new BasicDBObject("$set", userDocument);
        return mongoCollection.updateOne(Filters.eq(IDENTIFIED_FIELD, username), setQuery);
    }

    @Override
    public CompletionStage<DeleteResult> delete(String username) {
        return mongoCollection.deleteOne(Filters.eq(IDENTIFIED_FIELD, username));
    }

    @Override public CompletionStage<List<User>> findAll() {
        return mongoCollection.find()
            .map(toUser)
            .toList()
            .run();
    }

    @Override public CompletionStage<Optional<User>> findByUsername(String username) {
        return mongoCollection.find(Filters.eq(IDENTIFIED_FIELD, username))
            .limit(1)
            .map(toUser)
            .findFirst()
            .run();
    }

    private Document toDocument(User user) {
        return new Document()
            .append("name", user.getName())
            .append("surname", user.getSurname())
            .append("email", user.getEmail())
            .append(IDENTIFIED_FIELD, user.getUserName())
            .append("birthDate", user.getBirthDate())
            .append("nif", user.getNif());
    }

}
