# E-commerce ER Diagram

```mermaid
erDiagram

AUTENTICACION {
  int id_user PK
  string nombre
  string correo
  string contrasena
  string direccion
}

PRODUCTOS {
  int id_producto PK
  string nombre
  string descripcion
  float precio
  int stock
}

CARRITO {
  int id_user FK
  int id_producto FK
  int cantidad
  float total
  date fecha
}

DETALLES_PEDIDO {
  int id_detalle PK
  int id_producto FK
  int id_usuario FK
  int cantidad
  float precio_total
}

PAGOS {
  int id_pago PK
  int id_pedido FK
  string metodo_pago
  string estado
}

PEDIDOS {
  int id_pedido PK
  int id_pago FK
  string estado_pedido
}

AUTENTICACION ||--o{ CARRITO : tiene
PRODUCTOS ||--o{ CARRITO : agrega

AUTENTICACION ||--o{ DETALLES_PEDIDO : genera
PRODUCTOS ||--o{ DETALLES_PEDIDO : contiene

PEDIDOS ||--|| PAGOS : paga
PEDIDOS ||--o{ DETALLES_PEDIDO : incluye