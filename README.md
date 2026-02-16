# ForoHub - Alura Challenge

API REST para gestión de foros desarrollada con Spring Boot.

## 🚀 Tecnologías

- Java 21
- Spring Boot 4.0.2
- Spring Data JPA
- MySQL
- Flyway
- Lombok

## 📋 Requisitos Previos

- JDK 21 o superior
- MySQL 8.0 o superior
- Maven 3.6+

## ⚙️ Configuración

1. Clona el repositorio
2. Copia el archivo `application.properties.example` a `application.properties`:
   ```
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
3. Edita `application.properties` con tus credenciales de MySQL:
   ```properties
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```

## 🏃 Ejecución

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📝 Base de Datos

La base de datos `forohub_db` se creará automáticamente al iniciar la aplicación.

## 👨‍💻 Autor

Desarrollado como parte del Challenge de Alura LATAM

