package com.ecommerce.api.service;

import com.ecommerce.api.model.PaymentRequest;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentService {

    public PaymentRequest getPayment(String orderId) {
        PaymentRequest response = new PaymentRequest();
        response.setMethod(PaymentRequest.MethodEnum.CREDIT_CARD);
        response.setAmount(new java.math.BigDecimal("1500.00"));

        return response;
    }

    public PaymentRequest processPayment(String orderId, PaymentRequest request) {
        PaymentRequest response = new PaymentRequest();
        response.setMethod(request.getMethod());
        response.setAmount(request.getAmount());

        return response;
    }

    public void updatePaymentStatus(String orderId) {
        // sin persistencia aún — lógica de actualización de estado pendiente de BD
    }

    public void revertPayment(String orderId) {
        // sin persistencia aún — lógica de reversión pendiente de BD
    }
}
