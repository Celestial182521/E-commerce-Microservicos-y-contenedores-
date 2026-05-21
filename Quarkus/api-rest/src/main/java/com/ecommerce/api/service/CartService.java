package com.ecommerce.api.service;

import com.ecommerce.api.entity.CarritoEntity;
import com.ecommerce.api.entity.ProductoEntity;
import com.ecommerce.api.mapper.CartMapper;
import com.ecommerce.api.model.Cart;
import com.ecommerce.api.model.CartItemRequest;
import com.ecommerce.api.model.CartItemsItemIdPatchRequest;
import com.ecommerce.api.model.CartProduct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CartService {

    private static final Long DEFAULT_USER_ID = 1L;

    @Inject
    CartMapper cartMapper;

    public Cart getCart() {
        List<CarritoEntity> items = CarritoEntity.list("idUser", DEFAULT_USER_ID);

        List<CartProduct> cartItems = items.stream().map(item -> {
            ProductoEntity producto = ProductoEntity.findById(item.idProducto);
            return cartMapper.toCartProduct(item, producto != null ? producto.nombre : null);
        }).collect(Collectors.toList());

        return cartMapper.toCart(DEFAULT_USER_ID, cartItems);
    }

    @Transactional
    public void clearCart() {
        CarritoEntity.delete("idUser", DEFAULT_USER_ID);
    }

    @Transactional
    public CartProduct addItem(CartItemRequest request) {
        ProductoEntity producto = ProductoEntity.findById(request.getProductId().longValue());
        if (producto == null)
            throw new WebApplicationException(
                Response.status(404).entity("Producto no encontrado").build());

        BigDecimal subtotal = producto.precio.multiply(new BigDecimal(request.getQuantity()));
        CarritoEntity entity = cartMapper.toEntity(
                DEFAULT_USER_ID, request.getProductId().longValue(), request.getQuantity(), subtotal);
        entity.persist();

        return cartMapper.toCartProduct(entity, producto.nombre);
    }

    @Transactional
    public CartProduct updateItemQuantity(Integer itemId, CartItemsItemIdPatchRequest request) {
        CarritoEntity entity = CarritoEntity.findById(itemId.longValue());
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Item no encontrado en carrito").build());

        entity.cantidad = request.getQuantity();
        ProductoEntity producto = ProductoEntity.findById(entity.idProducto);
        if (producto != null)
            entity.total = producto.precio.multiply(new BigDecimal(request.getQuantity()));

        return cartMapper.toCartProduct(entity, producto != null ? producto.nombre : null);
    }

    @Transactional
    public void removeItem(Integer itemId) {
        boolean deleted = CarritoEntity.deleteById(itemId.longValue());
        if (!deleted)
            throw new WebApplicationException(
                Response.status(404).entity("Item no encontrado en carrito").build());
    }
}
