package com.ecommerce.api.service;

import com.ecommerce.api.entity.CarritoEntity;
import com.ecommerce.api.entity.DetallePedidoEntity;
import com.ecommerce.api.entity.PedidoEntity;
import com.ecommerce.api.mapper.OrderMapper;
import com.ecommerce.api.model.Order;
import com.ecommerce.api.model.OrderItem;
import com.ecommerce.api.model.OrdersIdPatchRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderService {

    private static final Long DEFAULT_USER_ID = 1L;

    private static final Map<String, String> API_TO_DB = Map.of(
        "pending",   "PENDIENTE",
        "shipped",   "ENVIADO",
        "completed", "ENTREGADO"
    );

    @Inject
    OrderMapper orderMapper;

    public List<Order> getAllOrders() {
        return PedidoEntity.<PedidoEntity>listAll()
                .stream().map(this::toOrderWithTotal).collect(Collectors.toList());
    }

    public Order getOrderById(String id) {
        PedidoEntity entity = PedidoEntity.findById(parseId(id));
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Pedido no encontrado").build());
        return toOrderWithTotal(entity);
    }

    @Transactional
    public Order createOrder() {
        PedidoEntity pedido = new PedidoEntity();
        pedido.idUser       = DEFAULT_USER_ID;
        pedido.estadoPedido = "PENDIENTE";
        pedido.fecha        = LocalDateTime.now();
        pedido.persistAndFlush();

        List<CarritoEntity> carrito = CarritoEntity.list("idUser", DEFAULT_USER_ID);
        for (CarritoEntity item : carrito) {
            DetallePedidoEntity detalle = new DetallePedidoEntity();
            detalle.idPedido    = pedido.idPedido;
            detalle.idProducto  = item.idProducto;
            detalle.cantidad    = item.cantidad;
            detalle.precioTotal = item.total != null ? item.total : BigDecimal.ZERO;
            detalle.persist();
        }
        CarritoEntity.delete("idUser", DEFAULT_USER_ID);

        return toOrderWithTotal(pedido);
    }

    @Transactional
    public Order updateOrderStatus(String id, OrdersIdPatchRequest request) {
        PedidoEntity entity = PedidoEntity.findById(parseId(id));
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Pedido no encontrado").build());
        if (request.getStatus() != null) {
            String apiStatus = request.getStatus().getValue();
            entity.estadoPedido = API_TO_DB.getOrDefault(apiStatus.toLowerCase(), apiStatus.toUpperCase());
        }
        return toOrderWithTotal(entity);
    }

    @Transactional
    public void cancelOrder(String id) {
        PedidoEntity entity = PedidoEntity.findById(parseId(id));
        if (entity == null)
            throw new WebApplicationException(
                Response.status(404).entity("Pedido no encontrado").build());
        entity.estadoPedido = "CANCELADO";
    }

    public List<OrderItem> getOrderItems(String orderId) {
        return DetallePedidoEntity.<DetallePedidoEntity>list("idPedido", parseId(orderId))
                .stream().map(orderMapper::toItemModel).collect(Collectors.toList());
    }

    public OrderItem getOrderItem(String orderId, String itemId) {
        DetallePedidoEntity entity = DetallePedidoEntity.findById(parseId(itemId));
        if (entity == null || !entity.idPedido.equals(parseId(orderId)))
            throw new WebApplicationException(
                Response.status(404).entity("Item de pedido no encontrado").build());
        return orderMapper.toItemModel(entity);
    }

    private Order toOrderWithTotal(PedidoEntity e) {
        BigDecimal total = DetallePedidoEntity.<DetallePedidoEntity>list("idPedido", e.idPedido)
                .stream()
                .map(d -> d.precioTotal != null ? d.precioTotal : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return orderMapper.toModel(e, total);
    }

    private Long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(
                Response.status(400).entity("ID inválido: " + id).build());
        }
    }
}
