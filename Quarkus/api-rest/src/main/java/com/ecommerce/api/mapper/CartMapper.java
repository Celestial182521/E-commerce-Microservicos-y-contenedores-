package com.ecommerce.api.mapper;

import com.ecommerce.api.entity.CarritoEntity;
import com.ecommerce.api.model.Cart;
import com.ecommerce.api.model.CartProduct;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CartMapper {

    public CartProduct toCartProduct(CarritoEntity item, String productName) {
        if (item == null) return null;
        CartProduct cp = new CartProduct();
        cp.setItemId(item.idCarrito.intValue());
        cp.setProductId(item.idProducto.intValue());
        cp.setQuantity(item.cantidad);
        cp.setSubtotal(item.total);
        cp.setProductName(productName);
        return cp;
    }

    public Cart toCart(Long userId, List<CartProduct> items) {
        BigDecimal total = items.stream()
                .map(i -> i.getSubtotal() != null ? i.getSubtotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cart cart = new Cart();
        cart.setIdCart(userId.intValue());
        cart.setUserId(userId.intValue());
        cart.setItems(items);
        cart.setTotalPrice(total);
        return cart;
    }

    public CarritoEntity toEntity(Long userId, Long productId, Integer quantity, BigDecimal subtotal) {
        CarritoEntity entity = new CarritoEntity();
        entity.idUser     = userId;
        entity.idProducto = productId;
        entity.cantidad   = quantity;
        entity.total      = subtotal;
        return entity;
    }
}
