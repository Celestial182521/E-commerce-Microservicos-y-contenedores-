package com.ecommerce.api.resource;
import  com.ecommerce.api.model.Cart;
import  com.ecommerce.api.model.CartItemRequest;
import  com.ecommerce.api.model.CartItemsItemIdPatchRequest;
import  com.ecommerce.api.model.CartProduct;
import  com.ecommerce.api.model.Order;
import  com.ecommerce.api.model.OrdersIdPatchRequest;
import  com.ecommerce.api.model.OrderItem;
import  com.ecommerce.api.model.PaymentRequest;
import  com.ecommerce.api.model.Products;
import  com.ecommerce.api.model.ProductUpdate;
import  com.ecommerce.api.model.UserCreate;
import  com.ecommerce.api.model.Users;
import  com.ecommerce.api.model.UserUpdate;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1") // Prefijo base de la API
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource 
{
    

}
