package com.ecommerce.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class OrderResourceTest {

    private static final String BASE = "/api/v1/orders";
    private static final String SAMPLE_ID = "ORD-001";

    // GET /orders → 200 + lista con campos del contrato
    @Test
    void getOrders_returns200AndContractFields() {
        given()
            .when().get(BASE)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].userId", notNullValue())
                .body("[0].status", notNullValue())
                .body("[0].total", notNullValue())
                .body("[0].createdAt", notNullValue());
    }

    // POST /orders → 201 + pedido generado
    @Test
    void createOrder_returns201() {
        given()
            .contentType("application/json")
            .when().post(BASE)
            .then()
                .statusCode(201)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("status", equalTo("pending"))
                .body("createdAt", notNullValue());
    }

    // GET /orders/{id} → 200 + detalle del pedido
    @Test
    void getOrderById_returns200() {
        given()
            .when().get(BASE + "/" + SAMPLE_ID)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(SAMPLE_ID))
                .body("status", notNullValue())
                .body("total", notNullValue());
    }

    // PATCH /orders/{id} → 200 + estado actualizado
    @Test
    void updateOrderStatus_returns200() {
        given()
            .contentType("application/json")
            .body("{\"status\": \"shipped\"}")
            .when().patch(BASE + "/" + SAMPLE_ID)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(SAMPLE_ID))
                .body("status", equalTo("shipped"));
    }

    // DELETE /orders/{id} → 204
    @Test
    void cancelOrder_returns204() {
        given()
            .when().delete(BASE + "/" + SAMPLE_ID)
            .then()
                .statusCode(204);
    }

    // GET /orders/{id}/items → 200 + lista de items
    @Test
    void getOrderItems_returns200AndContractFields() {
        given()
            .when().get(BASE + "/" + SAMPLE_ID + "/items")
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", not(empty()))
                .body("[0].itemId", notNullValue())
                .body("[0].productId", notNullValue())
                .body("[0].quantity", notNullValue())
                .body("[0].priceAtPurchase", notNullValue());
    }

    // GET /orders/{id}/items/{itemId} → 200 + item específico
    @Test
    void getOrderItem_returns200() {
        given()
            .when().get(BASE + "/" + SAMPLE_ID + "/items/ITEM-001")
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("itemId", equalTo("ITEM-001"))
                .body("productId", notNullValue())
                .body("quantity", notNullValue());
    }
}
