package com.ecommerce.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class UserResourceTest {

    private static final String BASE = "/api/v1/Users";

    // GET /Users → 200 + lista con campos del contrato
    @Test
    void getUsers_returns200AndContractFields() {
        given()
            .when().get(BASE)
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].address", notNullValue());
    }

    // POST /Users con body válido → 201 + usuario creado
    @Test
    void createUser_validBody_returns201() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "name": "Carlos Ruiz",
                  "email": "carlos@gmail.com",
                  "password": "secreto123",
                  "address": "Calle Reforma #5"
                }
                """)
            .when().post(BASE)
            .then()
                .statusCode(201)
                .contentType("application/json")
                .body("id", notNullValue())
                .body("name", equalTo("Carlos Ruiz"))
                .body("email", equalTo("carlos@gmail.com"))
                .body("address", equalTo("Calle Reforma #5"));
    }

    // POST /Users sin campos requeridos → 400
    @Test
    void createUser_missingRequiredFields_returns400() {
        given()
            .contentType("application/json")
            .body("{\"name\": \"Solo nombre\"}")
            .when().post(BASE)
            .then()
                .statusCode(400);
    }

    // GET /Users/{id} → 200 + usuario específico
    @Test
    void getUserById_returns200() {
        given()
            .when().get(BASE + "/1")
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("email", notNullValue());
    }

    // PATCH /Users/{id} → 200 + datos actualizados
    @Test
    void updateUser_returns200() {
        given()
            .contentType("application/json")
            .body("""
                {
                  "name": "Juan Actualizado",
                  "email": "juan_nuevo@gmail.com",
                  "address": "Nueva Dirección #10"
                }
                """)
            .when().patch(BASE + "/1")
            .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(1))
                .body("name", equalTo("Juan Actualizado"))
                .body("email", equalTo("juan_nuevo@gmail.com"));
    }

    // DELETE /Users/{id} → 204 sin body
    @Test
    void deleteUser_returns204() {
        given()
            .when().delete(BASE + "/1")
            .then()
                .statusCode(204);
    }
}
