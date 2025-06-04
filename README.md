# Prueba Inditex – Consulta de precios

Este microservicio permite consultar el precio aplicable de un producto de una cadena (por ejemplo, ZARA) en una fecha
determinada.

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.0
- Spring Web + JPA (Hibernate)
- H2 Database (runtime en memoria)
- Lombok y MapStruct
- Flyway (para migraciones)
- Springdoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito

---

## 🧱 Arquitectura

- Arquitectura Hexagonal
- Separación por capas: domain, application, infrastructure
- Value Objects y Aggregate Roots
- Mapeo entre entidades y DTOs con MapStruct

---

## 📌 Endpoint disponible

### `GET /api/prices`

Consulta el precio aplicable de un producto para una marca y una fecha dada.

#### 🔸 Parámetros (query params):

| Nombre             | Tipo            | Descripción                            | Ejemplo               |
|--------------------|-----------------|----------------------------------------|-----------------------|
| `application_date` | `ISO_DATE_TIME` | Fecha y hora a consultar               | `2020-06-14T10:00:00` |
| `product_id`       | `Long`          | Código del producto                    | `35455`               |
| `brand_id`         | `Long`          | ID de la marca (por ejemplo, ZARA = 1) | `1`                   |

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



## 📦 Construcción del proyecto

```bash
./mvnw clean install
```

---

## 🚀 Instrucciones para ejecutar

```bash
mvn clean spring-boot:run
```
---
## 🧪 Ejecución de tests

```bash
./mvnw test
```

---
El proyecto contiene tres niveles de pruebas:
- ✅ Unitarias: servicios aislados con Mockito (`@ExtendWith(MockitoExtension.class)`)
- 🔄 Integración: pruebas con base de datos en memoria (H2)
- 🌐 Sistema (E2E): llamadas a endpoints REST usando MockMvc
---
## 📄 Documentación API
Al ejecutar la aplicación, accede a:
```
http://localhost:8080/swagger-ui.html
```
---

## 📁 Scripts de base de datos
Flyway ejecuta los scripts ubicados en:
```
src/main/resources/db/migration
```
---
Para acceder a la consola H2:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:pricingdb`
- Usuario: `sa`

---

## 🛠 Requisitos
- Java 17
- Maven 3.9+
---
## ✍️ Autor

Alex Pantoja – Tech Lead Backend  
Proyecto desarrollado para pruebas técnicas de arquitectura y microservicios.
