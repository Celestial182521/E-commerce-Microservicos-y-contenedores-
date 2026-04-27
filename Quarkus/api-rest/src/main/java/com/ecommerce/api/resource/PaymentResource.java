package com.ecommerce.api.resource;

import com.ecommerce.api.model.PaymentRequest;
import com.ecommerce.api.service.PaymentService;
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

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @GET
    @Path("/orders/{id}/payment")
    public Response getPayment(@PathParam("id") String id) {
        PaymentRequest response = paymentService.getPayment(id);
        return Response.ok(response).build();
    }

    @POST
    @Path("/orders/{id}/payment")
    public Response processPayment(@PathParam("id") String id, @Valid PaymentRequest request) {
        PaymentRequest response = paymentService.processPayment(id, request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PATCH
    @Path("/orders/{id}/payment")
    public Response updatePaymentStatus(@PathParam("id") String id) {
        paymentService.updatePaymentStatus(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/orders/{id}/payment")
    public Response revertPayment(@PathParam("id") String id) {
        paymentService.revertPayment(id);
        return Response.noContent().build();
    }
}
