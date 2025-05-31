# Prueba Inditex – Consulta de precios

Este microservicio permite consultar el precio aplicable de un producto de una cadena (por ejemplo, ZARA) en una fecha determinada.

## 🧱 Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.0
- Arquitectura Hexagonal (DDD)
- Spring WebFlux
- JPA + H2 en memoria
- Flyway para migración de base de datos
- Lombok
- JUnit + Mockito

---

## 📌 Endpoint disponible

### `GET /api/prices`

Consulta el precio aplicable de un producto para una marca y una fecha dada.

#### 🔸 Parámetros (query params):

| Nombre            | Tipo        | Descripción                          | Ejemplo                       |
|-------------------|-------------|--------------------------------------|-------------------------------|
| `application_date`| `ISO_DATE_TIME` | Fecha y hora a consultar             | `2020-06-14T10:00:00`         |
| `product_id`      | `Long`      | Código del producto                   | `35455`                       |
| `brand_id`        | `Long`      | ID de la marca (por ejemplo, ZARA = 1)| `1`                          |

---

#### 🔸 Ejemplo de petición

```bash
curl "http://localhost:8080/api/prices?application_date=2020-06-14T10:00:00&product_id=35455&brand_id=1"
```

---

#### 🔸 Respuesta (200 OK)

```json
{
  "product_id": 35455,
  "brand_id": 1,
  "rate_code": 1,
  "application_date": "2020-06-14T10:00:00",
  "price": 35.50
}
```

---

#### 🔸 Respuesta si no se encuentra precio aplicable (404)

```json
{
  "timestamp": "2025-05-31T15:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "No applicable price found"
}
```

---

## 🧪 Casos de prueba incluidos

Los siguientes casos están cubiertos con pruebas unitarias:

- 2020-06-14 a las 10:00 del producto 35455 para la cadena 1 (ZARA)
- 2020-06-14 a las 16:00 del producto 35455 para la cadena 1 (ZARA)
- 2020-06-14 a las 21:00 del producto 35455 para la cadena 1 (ZARA)
- 2020-06-15 a las 10:00 del producto 35455 para la cadena 1 (ZARA)
- 2020-06-16 a las 21:00 del producto 35455 para la cadena 1 (ZARA)

---

## 🚀 Instrucciones para ejecutar

```bash
mvn clean spring-boot:run
```

Para acceder a la consola H2:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:pricingdb`
- Usuario: `sa`

---

## ✍️ Autor

Alex Pantoja – Tech Lead Backend  
Proyecto desarrollado para pruebas técnicas de arquitectura y microservicios.
