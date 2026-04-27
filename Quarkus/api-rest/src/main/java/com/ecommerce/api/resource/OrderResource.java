package com.ecommerce.api.resource;

import com.ecommerce.api.model.Order;
import com.ecommerce.api.model.OrderItem;
import com.ecommerce.api.model.OrdersIdPatchRequest;
import com.ecommerce.api.service.OrderService;
import jakarta.inject.Inject;
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
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Path("/orders")
    public Response getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return Response.ok(orders).build();
    }

    @POST
    @Path("/orders")
    public Response createOrder() {
        Order response = orderService.createOrder();
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/orders/{id}")
    public Response getOrderById(@PathParam("id") String id) {
        Order response = orderService.getOrderById(id);
        return Response.ok(response).build();
    }

    @PATCH
    @Path("/orders/{id}")
    public Response updateOrderStatus(@PathParam("id") String id, OrdersIdPatchRequest request) {
        Order response = orderService.updateOrderStatus(id, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/orders/{id}")
    public Response cancelOrder(@PathParam("id") String id) {
        orderService.cancelOrder(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/orders/{id}/items")
    public Response getOrderItems(@PathParam("id") String id) {
        List<OrderItem> items = orderService.getOrderItems(id);
        return Response.ok(items).build();
    }

    @GET
    @Path("/orders/{id}/items/{itemId}")
    public Response getOrderItem(@PathParam("id") String id, @PathParam("itemId") String itemId) {
        OrderItem item = orderService.getOrderItem(id, itemId);
        return Response.ok(item).build();
    }
}
