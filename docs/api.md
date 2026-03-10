# API del Sistema

La API del sistema permite interactuar con los diferentes módulos del e-commerce mediante endpoints REST.

---

# Auth / Usuarios

**GET /users**
Lista todos los usuarios (admin).

**GET /users/{id}**
Obtiene un usuario específico (admin).

**POST /auth/register**
Registra un nuevo usuario.

**POST /auth/login**
Inicia sesión y devuelve un token.

**GET /auth/me**
Obtiene el perfil del usuario autenticado.

**PATCH /users/{id}**
Actualiza información de usuario.

**DELETE /users/{id}**
Elimina un usuario.

---

# Productos

**GET /products**
Lista productos disponibles.

**GET /products/{id}**
Obtiene un producto específico.

**POST /products**
Crea producto (admin).

**PATCH /products/{id}**
Actualiza producto (admin).

**DELETE /products/{id}**
Elimina producto (admin).

---

# Carrito

**GET /cart**
Obtiene el carrito del usuario autenticado.

**POST /cart/items**
Agrega producto al carrito.

**PATCH /cart/items/{itemId}**
Modifica cantidad del producto.

**DELETE /cart/items/{itemId}**
Elimina producto del carrito.

**DELETE /cart**
Vacía completamente el carrito.

---

# Pedidos

**GET /orders**
Lista pedidos del usuario (o todos si es admin).

**GET /orders/{id}**
Obtiene un pedido específico.

**POST /orders**
Crea un pedido a partir del carrito.

**PATCH /orders/{id}**
Actualiza estado del pedido.

**DELETE /orders/{id}**
Cancela pedido.

---

# Detalles de Pedido

**GET /orders/{id}/items**
Lista items del pedido.

**GET /orders/{id}/items/{itemId}**
Obtiene un item específico.

---

# Pagos

**GET /orders/{id}/payment**
Obtiene el pago asociado.

**POST /orders/{id}/payment**
Realiza el pago del pedido.

**PATCH /orders/{id}/payment**
Actualiza estado del pago.

**DELETE /orders/{id}/payment**
Revierte o elimina el pago.
