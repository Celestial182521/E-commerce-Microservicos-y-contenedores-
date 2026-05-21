package com.ecommerce.api.mapper;

import com.ecommerce.api.entity.DetallePedidoEntity;
import com.ecommerce.api.entity.PedidoEntity;
import com.ecommerce.api.model.Order;
import com.ecommerce.api.model.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@ApplicationScoped
public class OrderMapper {

    private static final Map<String, String> DB_TO_API = Map.of(
        "PENDIENTE",  "pending",
        "CONFIRMADO", "pending",
        "ENVIADO",    "shipped",
        "ENTREGADO",  "completed",
        "CANCELADO",  "pending"
    );

    public Order toModel(PedidoEntity e, BigDecimal total) {
        if (e == null) return null;
        Order o = new Order();
        o.setId(String.valueOf(e.idPedido));
        o.setUserId(e.idUser.intValue());
        o.setStatus(DB_TO_API.getOrDefault(e.estadoPedido, "pending"));
        o.setTotal(total);
        o.setCreatedAt(e.fecha != null
                ? e.fecha.atOffset(ZoneOffset.of("-06:00"))
                : OffsetDateTime.now());
        return o;
    }

    public OrderItem toItemModel(DetallePedidoEntity e) {
        if (e == null) return null;
        OrderItem item = new OrderItem();
        item.setItemId(String.valueOf(e.idDetalle));
        item.setProductId(e.idProducto.intValue());
        item.setQuantity(e.cantidad);
        item.setPriceAtPurchase(e.cantidad > 0
                ? e.precioTotal.divide(new BigDecimal(e.cantidad), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO);
        return item;
    }
}
