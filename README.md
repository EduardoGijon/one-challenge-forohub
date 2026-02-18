# 🎓 ForoHub - API REST

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

API REST completa para la gestión de un foro de discusión, desarrollada como parte del **Challenge Backend de Alura LATAM**. Implementa un CRUD completo con validaciones de negocio, paginación, filtros y manejo robusto de errores.

---

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#️-configuración)
- [Ejecución](#-ejecución)
- [Endpoints de la API](#-endpoints-de-la-api)
- [Ejemplos de Uso](#-ejemplos-de-uso)
- [Modelo de Datos](#-modelo-de-datos)
- [Validaciones](#-validaciones)
- [Contribución](#-contribución)
- [Autor](#-autor)

---

## ✨ Características

### 🎯 CRUD Completo de Tópicos
- ✅ **Crear** tópico con validaciones
- ✅ **Listar** tópicos con paginación y ordenamiento
- ✅ **Buscar** tópicos por curso y/o año
- ✅ **Obtener** tópico específico por ID
- ✅ **Actualizar** tópico existente
- ✅ **Eliminar** tópico (con validación de integridad)

### 🛡️ Validaciones de Negocio
- No permite tópicos duplicados (mismo título y mensaje)
- Validación de campos obligatorios
- Verificación de existencia de entidades relacionadas
- Protección de integridad referencial en eliminaciones

### 📊 Características Avanzadas
- **Paginación**: 10 elementos por defecto (configurable)
- **Ordenamiento**: Por fecha de creación (ASC/DESC)
- **Filtros**: Por nombre de curso y año de creación
- **Manejo de errores**: Respuestas HTTP apropiadas con mensajes claros

### 🗃️ Base de Datos
- Migraciones automáticas con **Flyway**
- Datos iniciales de prueba incluidos
- Modelo relacional normalizado
- Integridad referencial garantizada

---

## 🚀 Tecnologías

| Tecnología | Versión | Descripción |
|------------|---------|-------------|
| **Java** | 21 | Lenguaje de programación |
| **Spring Boot** | 4.0.2 | Framework principal |
| **Spring Data JPA** | - | ORM y persistencia |
| **Spring Validation** | - | Validación de datos |
| **MySQL** | 8.0+ | Base de datos |
| **Flyway** | - | Migraciones de BD |
| **Lombok** | - | Reducción de boilerplate |
| **Maven** | 3.6+ | Gestión de dependencias |

---

## 📋 Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- ☕ **JDK 21** o superior ([Descargar](https://www.oracle.com/java/technologies/downloads/#java21))
- 🗄️ **MySQL 8.0** o superior ([Descargar](https://dev.mysql.com/downloads/mysql/))
- 📦 **Maven 3.6+** (incluido en el proyecto con Maven Wrapper)
- 🔧 **Git** ([Descargar](https://git-scm.com/downloads))

---

## 📥 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/forohub.git
cd forohub
```

### 2. Crear la base de datos

Conéctate a MySQL y ejecuta:

```sql
CREATE DATABASE forohub_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

## ⚙️ Configuración

### 1. Configurar application.properties

Copia el archivo de ejemplo:

```bash
# En Windows:
copy src\main\resources\application.properties.example src\main\resources\application.properties

# En Linux/Mac:
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

### 2. Editar credenciales de MySQL

Abre `src/main/resources/application.properties` y configura:

```properties
# Configuración de MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/forohub_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
```

### 3. Las migraciones se ejecutan automáticamente

Al iniciar la aplicación, Flyway ejecutará automáticamente las migraciones y creará:
- 📊 Tablas: `perfil`, `usuario`, `curso`, `topico`, `respuesta`
- 👥 Usuarios de prueba (contraseña: `123456`)
- 📚 Cursos iniciales
- 💬 Tópicos y respuestas de ejemplo

---

## 🏃 Ejecución

### Opción 1: Con Maven Wrapper (Recomendado)

```bash
# Windows:
.\mvnw.cmd spring-boot:run

# Linux/Mac:
./mvnw spring-boot:run
```

### Opción 2: Con Maven instalado

```bash
mvn spring-boot:run
```

### Opción 3: Ejecutar el JAR

```bash
# Compilar
.\mvnw.cmd clean package -DskipTests

# Ejecutar
java -jar target/forohub-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: **http://localhost:8080**

---

## 🌐 Endpoints de la API

### 📌 Base URL: `http://localhost:8080`

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `POST` | `/topicos` | Crear nuevo tópico | ❌ No |
| `GET` | `/topicos` | Listar tópicos (paginado) | ❌ No |
| `GET` | `/topicos/buscar` | Buscar con filtros | ❌ No |
| `GET` | `/topicos/{id}` | Obtener tópico por ID | ❌ No |
| `PUT` | `/topicos/{id}` | Actualizar tópico | ❌ No |
| `DELETE` | `/topicos/{id}` | Eliminar tópico | ❌ No |

---

## 📝 Ejemplos de Uso

### 1. Crear un Tópico

**Request:**
```http
POST /topicos HTTP/1.1
Content-Type: application/json

{
  "titulo": "¿Cómo implementar paginación en Spring Boot?",
  "mensaje": "Necesito ayuda para implementar paginación en mis endpoints REST",
  "autorId": 1,
  "curso": "Spring Boot"
}
```

**Response (201 CREATED):**
```json
{
  "id": 4,
  "titulo": "¿Cómo implementar paginación en Spring Boot?",
  "mensaje": "Necesito ayuda para implementar paginación en mis endpoints REST",
  "fechaCreacion": "2026-02-18T10:30:00",
  "status": "ABIERTO",
  "autorId": 1,
  "autorNombre": "Admin User",
  "cursoId": 1,
  "cursoNombre": "Spring Boot"
}
```

---

### 2. Listar Tópicos (con paginación)

**Request:**
```http
GET /topicos?page=0&size=10&sort=fechaCreacion,asc
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "¿Cómo configurar Spring Security?",
      "mensaje": "Necesito ayuda...",
      "fechaCreacion": "2026-02-17T10:30:00",
      "status": "ABIERTO",
      "autorNombre": "Juan Pérez",
      "cursoNombre": "Spring Boot"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 1,
  "totalElements": 3
}
```

---

### 3. Buscar Tópicos

**Por curso:**
```http
GET /topicos/buscar?curso=Spring Boot
```

**Por año:**
```http
GET /topicos/buscar?anio=2026
```

**Por ambos:**
```http
GET /topicos/buscar?curso=Java&anio=2026
```

---

### 4. Obtener Tópico por ID

**Request:**
```http
GET /topicos/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo configurar Spring Security?",
  "mensaje": "Necesito ayuda para configurar la autenticación...",
  "fechaCreacion": "2026-02-17T10:30:00",
  "status": "ABIERTO",
  "autorId": 2,
  "autorNombre": "Juan Pérez",
  "cursoId": 1,
  "cursoNombre": "Spring Boot"
}
```

---

### 5. Actualizar Tópico

**Request:**
```http
PUT /topicos/1 HTTP/1.1
Content-Type: application/json

{
  "titulo": "¿Cómo configurar Spring Security? (RESUELTO)",
  "mensaje": "Ya encontré la solución, aquí está la respuesta...",
  "status": "RESUELTO",
  "curso": "Spring Boot"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "titulo": "¿Cómo configurar Spring Security? (RESUELTO)",
  "status": "RESUELTO",
  ...
}
```

---

### 6. Eliminar Tópico

**Request:**
```http
DELETE /topicos/1
```

**Response:**
- **204 NO CONTENT** - Si el tópico se eliminó correctamente
- **400 BAD REQUEST** - Si el tópico tiene respuestas asociadas
- **404 NOT FOUND** - Si el tópico no existe

---

## 🗄️ Modelo de Datos

### Entidades Principales

```
┌──────────┐       ┌──────────┐       ┌──────────┐
│ Perfil   │──┐ ┌──│ Usuario  │──┐ ┌──│  Curso   │
└──────────┘  │ │  └──────────┘  │ │  └──────────┘
              │ │                 │ │
              ↓ ↓                 ↓ ↓
        ┌──────────────┐    ┌──────────┐
        │usuario_perfil│    │  Topico  │
        └──────────────┘    └──────────┘
                                  │
                                  ↓
                            ┌──────────┐
                            │Respuesta │
                            └──────────┘
```

### Estados de Tópico

- `ABIERTO` - Tópico abierto para respuestas
- `CERRADO` - Tópico cerrado
- `RESUELTO` - Tópico con solución
- `DUPLICADO` - Tópico duplicado

---

## ✅ Validaciones

### Crear Tópico
- ✅ Todos los campos obligatorios
- ✅ No permite título y mensaje duplicados
- ✅ Verifica que el autor exista
- ✅ Verifica que el curso exista

### Actualizar Tópico
- ✅ Todos los campos obligatorios
- ✅ No permite duplicados (excepto el actual)
- ✅ Verifica que el tópico exista
- ✅ Verifica que el curso exista

### Eliminar Tópico
- ✅ Verifica que el tópico exista
- ✅ Verifica que NO tenga respuestas asociadas
- ✅ Mensaje claro si no se puede eliminar

---

## 📚 Documentación Adicional

El proyecto incluye documentación detallada en formato Markdown:

- `API-ENDPOINT-TOPICOS.md` - Guía completa del endpoint POST
- `API-ENDPOINT-GET-TOPICOS.md` - Guía de listado y búsqueda
- `API-ENDPOINT-PUT-TOPICOS.md` - Guía de actualización
- `API-ENDPOINT-DELETE-TOPICOS.md` - Guía de eliminación
- `GUIA-RAPIDA-API.md` - Referencia rápida
- `SOLUCION-ERROR-ELIMINACION.md` - Manejo de errores de FK

---

## 🧪 Datos de Prueba

### Usuarios Disponibles:

| ID | Nombre | Email | Contraseña |
|----|--------|-------|------------|
| 1 | Admin User | admin@forohub.com | `123456` |
| 2 | Juan Pérez | juan@example.com | `123456` |
| 3 | María López | maria@example.com | `123456` |

### Cursos Disponibles:
- Spring Boot
- Java
- React
- MySQL
- Python

---

## 🐛 Solución de Problemas

### Error: "Access denied for user"
```bash
# Verifica tus credenciales en application.properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### Error: "Unknown database 'forohub_db'"
```sql
-- Crea la base de datos manualmente
CREATE DATABASE forohub_db;
```

### Error al eliminar tópico con respuestas
```json
{
  "error": "No se puede eliminar el tópico porque tiene respuestas asociadas"
}
```
**Solución:** Marca el tópico como CERRADO en lugar de eliminarlo.

---

## 🤝 Contribución

Las contribuciones son bienvenidas. Para contribuir:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto fue desarrollado como parte del Challenge Backend de **Alura LATAM**.

---

## 👨‍💻 Autor

Desarrollado con ❤️ como parte del **Challenge Backend de Alura LATAM**

---

## 🙏 Agradecimientos

- [Alura LATAM](https://www.aluracursos.com/) por el Challenge
- [Oracle Next Education](https://www.oracle.com/mx/education/oracle-next-education/) por el programa
- La comunidad de Spring Boot

---

## 📞 Contacto

¿Preguntas o sugerencias? Abre un [Issue](https://github.com/tu-usuario/forohub/issues) en el repositorio.

---

⭐ Si este proyecto te fue útil, ¡dale una estrella en GitHub!

