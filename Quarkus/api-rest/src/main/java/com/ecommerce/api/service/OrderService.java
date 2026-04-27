package com.ecommerce.api.service;

import com.ecommerce.api.model.Order;
import com.ecommerce.api.model.OrderItem;
import com.ecommerce.api.model.OrdersIdPatchRequest;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        Order o1 = new Order();
        o1.setId("ORD-001");
        o1.setUserId(1);
        o1.setStatus("pending");
        o1.setTotal(new BigDecimal("1500.00"));
        o1.setCreatedAt(OffsetDateTime.now());

        Order o2 = new Order();
        o2.setId("ORD-002");
        o2.setUserId(2);
        o2.setStatus("shipped");
        o2.setTotal(new BigDecimal("3200.00"));
        o2.setCreatedAt(OffsetDateTime.now());

        orders.add(o1);
        orders.add(o2);

        return orders;
    }

    public Order createOrder() {
        Order response = new Order();
        response.setId("ORD-" + (System.currentTimeMillis() % 100000));
        response.setUserId(1);
        response.setStatus("pending");
        response.setTotal(new BigDecimal("0.00"));
        response.setCreatedAt(OffsetDateTime.now());

        return response;
    }

    public Order getOrderById(String id) {
        Order response = new Order();
        response.setId(id);
        response.setUserId(1);
        response.setStatus("pending");
        response.setTotal(new BigDecimal("1500.00"));
        response.setCreatedAt(OffsetDateTime.now());

        return response;
    }

    public Order updateOrderStatus(String id, OrdersIdPatchRequest request) {
        Order response = new Order();
        response.setId(id);
        response.setUserId(1);
        response.setStatus(request.getStatus() != null ? request.getStatus().getValue() : "pending");
        response.setTotal(new BigDecimal("1500.00"));
        response.setCreatedAt(OffsetDateTime.now());

        return response;
    }

    public void cancelOrder(String id) {
        // sin persistencia aún — lógica de cancelación pendiente de BD
    }

    public List<OrderItem> getOrderItems(String orderId) {
        List<OrderItem> items = new ArrayList<>();

        OrderItem item = new OrderItem();
        item.setItemId("ITEM-001");
        item.setProductId(1);
        item.setQuantity(2);
        item.setPriceAtPurchase(new BigDecimal("750.00"));

        items.add(item);

        return items;
    }

    public OrderItem getOrderItem(String orderId, String itemId) {
        OrderItem item = new OrderItem();
        item.setItemId(itemId);
        item.setProductId(1);
        item.setQuantity(2);
        item.setPriceAtPurchase(new BigDecimal("750.00"));

        return item;
    }
}
