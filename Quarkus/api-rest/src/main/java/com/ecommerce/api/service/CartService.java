package com.ecommerce.api.service;

import com.ecommerce.api.model.Cart;
import com.ecommerce.api.model.CartItemRequest;
import com.ecommerce.api.model.CartItemsItemIdPatchRequest;
import com.ecommerce.api.model.CartProduct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CartService {

    public Cart getCart() {
        Cart cart = new Cart();
        cart.setIdCart(500);
        cart.setUserId(1);
        cart.setTotalPrice(new java.math.BigDecimal("2500.00"));

        List<CartProduct> items = new ArrayList<>();

        CartProduct item = new CartProduct();
        item.setItemId(1);
        item.setProductId(1);
        item.setProductName("Mochila");
        item.setQuantity(2);
        item.setSubtotal(new java.math.BigDecimal("2500.00"));

        items.add(item);
        cart.setItems(items);

        return cart;
    }

    public void clearCart() {
        // sin persistencia aún — lógica de vaciado pendiente de BD
    }

    public CartProduct addItem(CartItemRequest request) {
        CartProduct response = new CartProduct();
        response.setItemId((int) (System.currentTimeMillis() % 100000));
        response.setProductId(request.getProductId());
        response.setQuantity(request.getQuantity());
        response.setProductName("Producto de Ejemplo");
        response.setSubtotal(new java.math.BigDecimal("0.00"));

        return response;
    }

    public CartProduct updateItemQuantity(Integer itemId, CartItemsItemIdPatchRequest request) {
        CartProduct response = new CartProduct();
        response.setItemId(itemId);
        response.setQuantity(request.getQuantity());
        response.setProductName("Producto de Ejemplo");
        response.setSubtotal(new java.math.BigDecimal("0.00"));

        return response;
    }

    public void removeItem(Integer itemId) {
        // sin persistencia aún — lógica de eliminación pendiente de BD
    }
}
