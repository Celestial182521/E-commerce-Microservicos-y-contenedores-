package com.ecommerce.api.resource;

import com.ecommerce.api.model.Products;
import com.ecommerce.api.service.ProductService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @POST
    @Path("/products")
    public Response createProduct(@Valid Products productRequest) {
        System.out.println("Resource - Product recibido: " + productRequest.getName());
        Products productResponse = productService.createProduct(productRequest);
        return Response.status(Response.Status.CREATED).entity(productResponse).build();
    }

    @GET
    @Path("/products/{productId}")
    public Response getProductById(@PathParam("productId") Integer productId) {
        System.out.println("Resource - Buscando Product con ID: " + productId);

        if (productId == null || productId < 1 || productId > 999999) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Products productResponse = productService.getProductById(productId);
        return Response.ok(productResponse).build();
    }
}
