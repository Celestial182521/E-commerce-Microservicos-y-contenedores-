package com.ecommerce.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ProductResourceTest {

    private static final String BASE = "/api/v1/products";

    // GET /products → 200 + array con campos del contrato
    @Test
    void getProducts_returns200AndContractFields() {
        given()
            .when().get(BASE)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].price", notNullValue())
                .body("[0].stock", notNullValue());
    }

    // POST /products con body válido → 201 + objeto creado
    @Test
    void createProduct_validBody_returns201() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "name": "Teclado Mecánico",
                  "description": "Teclado RGB",
                  "price": 850.00,
                  "stock": 15
                }
                """)
            .when().post(BASE)
            .then()
                .statusCode(201)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("name", equalTo("Teclado Mecánico"))
                .body("price", notNullValue())
                .body("stock", equalTo(15));
    }

    // POST /products sin campos requeridos → 400
    @Test
    void createProduct_missingRequiredFields_returns400() {
        given()
            .contentType("application/json")
            .body("{\"description\": \"Sin nombre ni precio\"}")
            .when().post(BASE)
            .then()
                .statusCode(400);
    }

    // PATCH /products/{id} → 200 + producto actualizado
    @Test
    void updateProduct_returns200() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "name": "Mochila Actualizada",
                  "price": 1300.00,
                  "stock": 20
                }
                """)
            .when().patch(BASE + "/1")
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("name", equalTo("Mochila Actualizada"));
    }

    // DELETE /products/{id} → 204 sin body
    @Test
    void deleteProduct_returns204() {
        given()
            .when().delete(BASE + "/1")
            .then()
                .statusCode(204);
    }
}
