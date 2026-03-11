# E-commerce ER Diagram

```mermaid
erDiagram


USUARIOS {
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
  int id_carrito PK
  int id_user FK
  int id_producto FK
  int cantidad
  float total
  date fecha
}

DETALLES_PEDIDO {
  int id_detalle PK
  int id_producto FK
  int id_user FK
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
  int id_user FK
  string estado_pedido
  date fecha
}

%% Relaciones según el flujo

USUARIOS ||--o{ PRODUCTOS : visualiza
PRODUCTOS ||--o{ CARRITO : agrega
CARRITO ||--o{ DETALLES_PEDIDO : genera
DETALLES_PEDIDO ||--|| PAGOS : requiere
PAGOS ||--|| PEDIDOS : confirma