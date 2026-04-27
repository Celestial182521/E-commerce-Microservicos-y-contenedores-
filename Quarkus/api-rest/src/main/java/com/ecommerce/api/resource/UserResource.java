package com.ecommerce.api.resource;

import com.ecommerce.api.model.UserCreate;
import com.ecommerce.api.model.Users;
import com.ecommerce.api.model.UserUpdate;
import com.ecommerce.api.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Path("/Users")
    public Response getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return Response.ok(users).build();
    }

    @POST
    @Path("/Users")
    public Response createUser(@Valid UserCreate userCreate) {
        Users response = userService.createUser(userCreate);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/Users/{id}")
    public Response getUserById(@PathParam("id") Integer id) {
        Users response = userService.getUserById(id);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/Users/{id}")
    public Response updateUser(@PathParam("id") Integer id, UserUpdate userUpdate) {
        Users response = userService.updateUser(id, userUpdate);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/Users/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}
