package com.ecommerce.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CartResourceTest {

    private static final String BASE = "/api/v1/cart";

    // GET /cart → 200 + estructura del contrato
    @Test
    void getCart_returns200AndContractFields() {
        given()
            .when().get(BASE)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id_cart", notNullValue())
                .body("userId", notNullValue())
                .body("totalPrice", notNullValue())
                .body("items", not(empty()))
                .body("items[0].itemId", notNullValue())
                .body("items[0].productId", notNullValue())
                .body("items[0].quantity", notNullValue());
    }

    // POST /cart/items con body válido → 201 + producto añadido
    @Test
    void addCartItem_validBody_returns201() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "productId": 2,
                  "quantity": 3
                }
                """)
            .when().post(BASE + "/items")
            .then()
                .statusCode(201)
                .contentType("application/json")
                .body("itemId", notNullValue())
                .body("productId", equalTo(2))
                .body("quantity", equalTo(3));
    }

    // POST /cart/items sin campos requeridos → 400
    @Test
    void addCartItem_missingRequiredFields_returns400() {
        given()
            .contentType("application/json")
            .body("{}")
            .when().post(BASE + "/items")
            .then()
                .statusCode(400);
    }

    // PATCH /cart/items/{itemId} → 200 + cantidad actualizada
    @Test
    void updateCartItemQuantity_returns200() {
        given()
            .contentType("application/json")
            .body("{\"quantity\": 5}")
            .when().patch(BASE + "/items/1")
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("itemId", equalTo(1))
                .body("quantity", equalTo(5));
    }

    // DELETE /cart/items/{itemId} → 204 sin body
    @Test
    void removeCartItem_returns204() {
        given()
            .when().delete(BASE + "/items/1")
            .then()
                .statusCode(204);
    }

    // DELETE /cart → 204 vacía el carrito
    @Test
    void clearCart_returns204() {
        given()
            .when().delete(BASE)
            .then()
                .statusCode(204);
    }
}
