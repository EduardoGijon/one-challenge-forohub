# 🚀 GUÍA RÁPIDA - API ForoHub

## 📌 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/topicos` | Crear un nuevo tópico |
| GET | `/topicos` | Listar todos los tópicos (paginado) |
| GET | `/topicos/buscar` | Buscar tópicos con filtros |
| GET | `/topicos/{id}` | Obtener un tópico específico |

---

## 🎯 EJEMPLOS PRÁCTICOS

### 1. Crear un Tópico
```json
POST http://localhost:8080/topicos
Content-Type: application/json

{
  "titulo": "¿Cómo implementar JWT en Spring Boot?",
  "mensaje": "Necesito ayuda para implementar autenticación con JWT en mi aplicación Spring Boot",
  "autorId": 1,
  "curso": "Spring Boot"
}
```

---

### 2. Listar Todos los Tópicos (Primera página)
```http
GET http://localhost:8080/topicos
```

---

### 3. Listar con Ordenamiento Personalizado
```http
GET http://localhost:8080/topicos?sort=titulo,asc
```

---

### 4. Ver Segunda Página
```http
GET http://localhost:8080/topicos?page=1&size=10
```

---

### 5. Buscar Tópicos de "Spring Boot"
```http
GET http://localhost:8080/topicos/buscar?curso=Spring Boot
```

---

### 6. Buscar Tópicos de 2026
```http
GET http://localhost:8080/topicos/buscar?anio=2026
```

---

### 7. Buscar Tópicos de Java en 2026
```http
GET http://localhost:8080/topicos/buscar?curso=Java&anio=2026
```

---

### 8. Obtener Tópico por ID
```http
GET http://localhost:8080/topicos/1
```

---

## 📊 Parámetros de Paginación

| Parámetro | Tipo | Default | Ejemplo |
|-----------|------|---------|---------|
| `page` | Integer | 0 | `?page=2` |
| `size` | Integer | 10 | `?size=20` |
| `sort` | String | fechaCreacion,asc | `?sort=titulo,desc` |

---

## 🎨 Cursos Disponibles

- Spring Boot
- Java
- React
- MySQL
- Python

---

## 👥 Usuarios de Prueba

| ID | Nombre | Email |
|----|--------|-------|
| 1 | Admin User | admin@forohub.com |
| 2 | Juan Pérez | juan@example.com |
| 3 | María López | maria@example.com |

---

## ✅ Checklist de Testing

- [ ] Crear tópico exitoso
- [ ] Crear tópico con campos faltantes (debe fallar)
- [ ] Crear tópico duplicado (debe fallar)
- [ ] Listar primera página
- [ ] Listar segunda página
- [ ] Ordenar por título descendente
- [ ] Buscar por curso "Spring Boot"
- [ ] Buscar por año 2026
- [ ] Buscar por curso Y año
- [ ] Obtener tópico por ID existente
- [ ] Obtener tópico por ID inexistente (debe dar 404)

---

## 🔥 Colección Postman

Puedes importar esta colección en Postman:

```json
{
  "info": {
    "name": "ForoHub API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Crear Tópico",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"titulo\": \"Test Tópico\",\n  \"mensaje\": \"Mensaje de prueba\",\n  \"autorId\": 1,\n  \"curso\": \"Spring Boot\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/topicos",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["topicos"]
        }
      }
    },
    {
      "name": "Listar Tópicos",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/topicos",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["topicos"]
        }
      }
    },
    {
      "name": "Buscar por Curso",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/topicos/buscar?curso=Spring Boot",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["topicos", "buscar"],
          "query": [
            {
              "key": "curso",
              "value": "Spring Boot"
            }
          ]
        }
      }
    },
    {
      "name": "Obtener por ID",
      "request": {
        "method": "GET",
        "url": {
          "raw": "http://localhost:8080/topicos/1",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["topicos", "1"]
        }
      }
    }
  ]
}
```

---

## 🎓 Aprendizajes Clave

1. **@PageableDefault**: Paginación automática
2. **@RequestParam(required = false)**: Parámetros opcionales
3. **@PathVariable**: Variables en la URL
4. **@Valid**: Validación automática
5. **Page<T>**: Respuesta paginada de Spring Data
6. **JPQL @Query**: Consultas personalizadas
7. **DTOs**: Separación entre entidades y respuestas

---

**¡API ForoHub lista para usar!** 🚀

