package org.jmb.web;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jmb.business.UserService;
import org.jmb.domain.User;

import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject private UserService userService;

    @GET
    public CompletionStage<List<User>> findAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{username}")
    public CompletionStage<User> findByUsername(@PathParam("username") final String username) {
        return userService.findByUsername(username);
    }

    @POST
    public CompletionStage<Void> create(final User user) {
        return userService.create(user);
    }

    @PUT
    @Path("/{username}")
    public CompletionStage<Void> update(@PathParam("username") final String username, final User user) {
        return userService.update(user, username);
    }

    @DELETE
    @Path("/{username}")
    public CompletionStage<Void> delete(@PathParam("username") final String username) {
        return userService.delete(username);
    }
}
