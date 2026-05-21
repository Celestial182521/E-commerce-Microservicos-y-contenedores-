package com.ecommerce.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PaymentResourceTest {

    private static final String BASE = "/api/v1/orders/ORD-001/payment";

    // GET /orders/{id}/payment → 200 + datos del pago
    @Test
    void getPayment_returns200AndContractFields() {
        given()
            .when().get(BASE)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("method", notNullValue())
                .body("amount", notNullValue());
    }

    // POST /orders/{id}/payment con body válido → 201 + pago procesado
    @Test
    void processPayment_validBody_returns201() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "method": "credit_card",
                  "amount": 1500.00
                }
                """)
            .when().post(BASE)
            .then()
                .statusCode(201)
                .contentType("application/json")
                .body("method", equalTo("credit_card"))
                .body("amount", notNullValue());
    }

    // POST /orders/{id}/payment sin método (campo requerido) → 400
    @Test
    void processPayment_missingMethod_returns400() {
        given()
            .contentType("application/json")
            .body("{\"amount\": 500.00}")
            .when().post(BASE)
            .then()
                .statusCode(400);
    }

    // PATCH /orders/{id}/payment → 200
    @Test
    void updatePaymentStatus_returns200() {
        given()
            .contentType("application/json")
            .when().patch(BASE)
            .then()
                .statusCode(200);
    }

    // DELETE /orders/{id}/payment → 204
    @Test
    void revertPayment_returns204() {
        given()
            .when().delete(BASE)
            .then()
                .statusCode(204);
    }
}
