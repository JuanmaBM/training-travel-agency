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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jmb.business.UserService;
import org.jmb.domain.User;
import org.jmb.integration.persistence.UserRepository;

import java.util.concurrent.CompletionStage;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserService userService;

    @Inject
    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GET
    public CompletionStage<Response> findAll() {
        return userService.findAll()
            .thenApplyAsync(x -> Response.ok(x).build())
            .exceptionally(ex -> Response.status(500).entity(ex).build());
    }

    @GET
    @Path("/{username}")
    public CompletionStage<Response> findByUsername(@PathParam("username") final String username) {
        return userService.findByUsername(username)
            .thenApplyAsync(x -> x.map(Response::ok)
                .map(ResponseBuilder::build)
                .orElse(Response.status(404).build()))
            .exceptionally(ex -> Response.status(500).entity(ex).build());
    }

    @POST
    public CompletionStage<Response> create(final User user) {
        return userService.create(user)
            .thenApplyAsync(x -> Response.status(201).build())
            .exceptionally(ex -> Response.status(500).entity(ex).build());
    }

    @PUT
    @Path("/{username}")
    public CompletionStage<Response> update(@PathParam("username") final String username, final User user) {
        return userService.update(user, username)
            .thenApplyAsync(x -> Response.status(204).build())
            .exceptionally(ex -> Response.status(500).entity(ex).build());
    }

    @DELETE
    @Path("/{username}")
    public CompletionStage<Response> delete(@PathParam("username") final String username) {
        return userService.delete(username)
            .thenApplyAsync(x -> Response.status(204).build())
            .exceptionally(ex -> Response.status(500).entity(ex).build());
    }
}
