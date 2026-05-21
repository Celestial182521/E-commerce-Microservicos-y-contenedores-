package com.ecommerce.api.mapper;

import com.ecommerce.api.entity.PagoEntity;
import com.ecommerce.api.model.PaymentRequest;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;

@ApplicationScoped
public class PaymentMapper {

    public PaymentRequest toModel(PagoEntity e, BigDecimal amount) {
        if (e == null) return null;
        PaymentRequest model = new PaymentRequest();
        try {
            model.setMethod(PaymentRequest.MethodEnum.fromValue(e.metodoPago));
        } catch (Exception ex) {
            model.setMethod(PaymentRequest.MethodEnum.CREDIT_CARD);
        }
        model.setAmount(amount);
        return model;
    }

    public PagoEntity toEntity(Long pedidoId, PaymentRequest request) {
        PagoEntity entity = new PagoEntity();
        entity.idPedido   = pedidoId;
        entity.metodoPago = request.getMethod() != null ? request.getMethod().getValue() : "EFECTIVO";
        entity.estado     = "PENDIENTE";
        return entity;
    }
}
