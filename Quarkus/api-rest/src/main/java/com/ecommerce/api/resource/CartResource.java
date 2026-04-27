package com.ecommerce.api.resource;

import com.ecommerce.api.model.Cart;
import com.ecommerce.api.model.CartItemRequest;
import com.ecommerce.api.model.CartItemsItemIdPatchRequest;
import com.ecommerce.api.model.CartProduct;
import com.ecommerce.api.service.CartService;
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
public class CartResource {

    @Inject
    CartService cartService;

    @GET
    @Path("/cart")
    public Response getCart() {
        Cart cart = cartService.getCart();
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/cart")
    public Response clearCart() {
        cartService.clearCart();
        return Response.noContent().build();
    }

    @POST
    @Path("/cart/items")
    public Response addItem(@Valid CartItemRequest request) {
        CartProduct response = cartService.addItem(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PATCH
    @Path("/cart/items/{itemId}")
    public Response updateItemQuantity(@PathParam("itemId") Integer itemId, CartItemsItemIdPatchRequest request) {
        CartProduct response = cartService.updateItemQuantity(itemId, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/cart/items/{itemId}")
    public Response removeItem(@PathParam("itemId") Integer itemId) {
        cartService.removeItem(itemId);
        return Response.noContent().build();
    }
}
