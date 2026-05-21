package com.ecommerce.api.service;

import com.ecommerce.api.entity.DetallePedidoEntity;
import com.ecommerce.api.entity.PagoEntity;
import com.ecommerce.api.mapper.PaymentMapper;
import com.ecommerce.api.model.PaymentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentMapper paymentMapper;

    public PaymentRequest getPayment(String orderId) {
        Long pedidoId = parseId(orderId);
        PagoEntity pago = PagoEntity.find("idPedido", pedidoId).firstResult();
        if (pago == null)
            throw new WebApplicationException(
                Response.status(404).entity("Pago no encontrado para el pedido").build());
        return paymentMapper.toModel(pago, calcularTotal(pedidoId));
    }

    @Transactional
    public PaymentRequest processPayment(String orderId, PaymentRequest request) {
        Long pedidoId = parseId(orderId);
        long count = PagoEntity.count("idPedido", pedidoId);
        if (count > 0)
            throw new WebApplicationException(
                Response.status(409).entity("El pedido ya tiene un pago registrado").build());

        PagoEntity entity = paymentMapper.toEntity(pedidoId, request);
        entity.persist();
        return paymentMapper.toModel(entity, calcularTotal(pedidoId));
    }

    @Transactional
    public void updatePaymentStatus(String orderId) {
        Long pedidoId = parseId(orderId);
        PagoEntity pago = PagoEntity.find("idPedido", pedidoId).firstResult();
        if (pago == null)
            throw new WebApplicationException(
                Response.status(404).entity("Pago no encontrado").build());
        pago.estado = "PAGADO";
    }

    @Transactional
    public void revertPayment(String orderId) {
        long deleted = PagoEntity.delete("idPedido", parseId(orderId));
        if (deleted == 0)
            throw new WebApplicationException(
                Response.status(404).entity("Pago no encontrado").build());
    }

    private BigDecimal calcularTotal(Long pedidoId) {
        return DetallePedidoEntity.<DetallePedidoEntity>list("idPedido", pedidoId)
                .stream()
                .map(d -> d.precioTotal != null ? d.precioTotal : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
