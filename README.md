# 🗣️ ForoHub API

API REST para gestión de foros desarrollada con Spring Boot como parte del Challenge de Alura Latam.  
Permite a los usuarios crear, consultar, actualizar y eliminar tópicos de discusión, con autenticación segura mediante JWT.

---

## 🚀 Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.2 |
| Spring Security | 7.x |
| Spring Data JPA | - |
| Flyway | 11.x |
| MySQL | 8.x |
| Auth0 Java JWT | 4.4.0 |
| Lombok | - |
| Maven | - |

---

## ⚙️ Configuración

### 1. Requisitos previos
- Java 21+
- MySQL 8+
- Maven (o usar el wrapper `mvnw`)

### 2. Base de datos
Crea la base de datos (Flyway la inicializa automáticamente al arrancar):
```sql
CREATE DATABASE forohub_db;
```

### 3. Variables de entorno / application.properties
Copia `application.properties.example` a `application.properties` y configura:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forohub_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASENA

jwt.secret=TU_CLAVE_SECRETA_MIN_32_CARACTERES
jwt.expiration=86400000
```

### 4. Ejecutar el proyecto
```bash
./mvnw spring-boot:run
```

---

## 🔐 Autenticación JWT

Todos los endpoints (excepto `/login`) requieren un token JWT válido.

### Obtener token
```
POST /login
Content-Type: application/json

{
  "correoElectronico": "admin@forohub.com",
  "contrasena": "123456"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Usar el token
Agrega el header en todas las demás peticiones:
```
Authorization: Bearer <tu_token>
```

---

## 📋 Endpoints

### Tópicos

| Método | URI | Descripción | Auth |
|--------|-----|-------------|------|
| `POST` | `/topicos` | Crear nuevo tópico | ✅ |
| `GET` | `/topicos` | Listar tópicos (paginado) | ✅ |
| `GET` | `/topicos/{id}` | Obtener tópico por ID | ✅ |
| `GET` | `/topicos/buscar` | Buscar por curso y/o año | ✅ |
| `PUT` | `/topicos/{id}` | Actualizar tópico | ✅ |
| `DELETE` | `/topicos/{id}` | Eliminar tópico | ✅ |

---

### `POST /topicos` — Crear tópico
```json
{
  "titulo": "¿Cómo usar Spring Security?",
  "mensaje": "Necesito ayuda para configurar JWT en Spring Boot.",
  "autorId": 1,
  "curso": "Spring Boot"
}
```
**Respuesta:** `201 Created` con el tópico creado y header `Location`.

---

### `GET /topicos` — Listar tópicos
Paginado, ordenado por `fechaCreacion ASC` (10 por página por defecto).

Parámetros opcionales: `page`, `size`, `sort`

**Respuesta:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "¿Cómo usar Spring Security?",
      "mensaje": "Necesito ayuda...",
      "fechaCreacion": "2026-02-19T10:00:00",
      "status": "ABIERTO",
      "autor": "Admin User",
      "curso": "Spring Boot"
    }
  ],
  "totalElements": 3,
  "totalPages": 1
}
```

---

### `GET /topicos/buscar` — Buscar tópicos
Parámetros opcionales de query:
- `curso` — nombre del curso
- `anio` — año de creación

Ejemplos:
```
GET /topicos/buscar?curso=Spring Boot
GET /topicos/buscar?anio=2026
GET /topicos/buscar?curso=Java&anio=2026
```

---

### `GET /topicos/{id}` — Detalle de tópico
**Respuesta:** `200 OK` con datos del tópico, o `404 Not Found`.

---

### `PUT /topicos/{id}` — Actualizar tópico
```json
{
  "titulo": "Título actualizado",
  "mensaje": "Mensaje actualizado",
  "status": "CERRADO",
  "curso": "Java"
}
```
**Respuesta:** `200 OK` con el tópico actualizado.

---

### `DELETE /topicos/{id}` — Eliminar tópico
**Respuesta:** `204 No Content`  
> ⚠️ No se puede eliminar un tópico que tenga respuestas asociadas.

---

## 🗄️ Estructura del proyecto

```
src/main/java/alura/cursos/forohub/
├── controller/
│   ├── AutenticacionController.java   # POST /login
│   └── TopicoController.java          # CRUD /topicos
├── domain/
│   ├── curso/
│   │   ├── Curso.java
│   │   └── CursoRepository.java
│   ├── perfil/
│   │   └── Perfil.java
│   ├── respuesta/
│   │   ├── Respuesta.java
│   │   └── RespuestaRepository.java
│   ├── topico/
│   │   ├── dto/
│   │   │   ├── DatosRegistroTopico.java
│   │   │   ├── DatosActualizacionTopico.java
│   │   │   ├── DatosListadoTopico.java
│   │   │   └── DatosRespuestaTopico.java
│   │   ├── StatusTopico.java
│   │   ├── Topico.java
│   │   ├── TopicoRepository.java
│   │   └── TopicoService.java
│   └── usuario/
│       ├── DatosAutenticacionUsuario.java
│       ├── Usuario.java
│       └── UsuarioRepository.java
└── infra/
    ├── errores/
    │   ├── TratadorDeErrores.java     # Manejo global de excepciones
    │   └── ValidacionException.java
    └── security/
        ├── AutenticacionService.java  # UserDetailsService
        ├── DatosTokenJWT.java
        ├── SecurityConfigurations.java
        ├── SecurityFilter.java        # Filtro JWT por request
        └── TokenService.java          # Generar/validar JWT

src/main/resources/
├── application.properties
├── application.properties.example
└── db/migration/
    ├── V1__create-table-perfil.sql
    ├── V2__create-table-usuario.sql
    ├── V3__create-table-usuario-perfil.sql
    ├── V4__create-table-curso.sql
    ├── V5__create-table-topico.sql
    ├── V6__create-table-respuesta.sql
    └── V7__insert-initial-data.sql
```

---

## 🧪 Usuarios de prueba

| Email | Contraseña | Rol |
|-------|-----------|-----|
| `admin@forohub.com` | `123456` | ROLE_ADMIN |
| `juan@example.com` | `123456` | ROLE_USER |
| `maria@example.com` | `123456` | ROLE_USER |

---

## 📦 Cursos disponibles

- Spring Boot
- Java
- React
- MySQL
- Python

---

## 📌 Reglas de negocio

- No se permiten tópicos duplicados (mismo título **y** mensaje)
- El autor y el curso deben existir en la BD al crear/actualizar
- No se puede eliminar un tópico con respuestas asociadas
- Todos los endpoints (excepto `/login`) requieren token JWT válido
- Los tokens expiran en 24 horas por defecto

---

## 👨‍💻 Autor

Desarrollado como parte del **Challenge ForoHub** de [Alura Latam](https://www.aluracursos.com/).
