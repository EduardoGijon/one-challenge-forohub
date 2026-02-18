# 📋 API REST - Endpoint GET /topicos (LISTADO)

## ✅ IMPLEMENTACIÓN COMPLETADA

Se han implementado exitosamente los endpoints GET para listar y buscar tópicos con **paginación**, **ordenamiento** y **filtros opcionales**.

---

## 🎯 ENDPOINTS IMPLEMENTADOS:

### 1. **GET /topicos** - Listar todos los tópicos ✅
- **Paginación**: 10 elementos por página (configurable)
- **Ordenamiento**: Por fecha de creación ASC (configurable)
- **Respuesta**: HTTP 200 OK con Page<DatosListadoTopico>

### 2. **GET /topicos/buscar** - Buscar con filtros ✅
- **Filtros opcionales**:
  - `curso`: nombre del curso
  - `anio`: año de creación
- **Combinable**: Puedes usar ambos filtros juntos
- **Paginación**: Igual que el listado

### 3. **GET /topicos/{id}** - Obtener un tópico específico ✅
- **Parámetro**: ID del tópico
- **Respuesta**: Tópico completo o 404 NOT FOUND

---

## 📝 EJEMPLOS DE USO:

### 1️⃣ Listar todos los tópicos (con paginación por defecto)

```http
GET http://localhost:8080/topicos
```

**Respuesta** (HTTP 200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "¿Cómo configurar Spring Security?",
      "mensaje": "Necesito ayuda para configurar la autenticación...",
      "fechaCreacion": "2026-02-17T10:30:00",
      "status": "ABIERTO",
      "autorNombre": "Juan Pérez",
      "cursoNombre": "Spring Boot"
    },
    {
      "id": 2,
      "titulo": "Error al conectar con MySQL",
      "mensaje": "Me aparece un error de conexión...",
      "fechaCreacion": "2026-02-17T11:15:00",
      "status": "ABIERTO",
      "autorNombre": "María López",
      "cursoNombre": "MySQL"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalPages": 1,
  "totalElements": 3,
  "last": true,
  "first": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 3
}
```

---

### 2️⃣ Listar con paginación personalizada

```http
GET http://localhost:8080/topicos?page=0&size=5
```

- `page=0`: Primera página (las páginas empiezan en 0)
- `size=5`: 5 elementos por página

---

### 3️⃣ Listar ordenado por título

```http
GET http://localhost:8080/topicos?sort=titulo,asc
```

**Opciones de ordenamiento**:
- `sort=titulo,asc` - Por título ascendente
- `sort=titulo,desc` - Por título descendente
- `sort=fechaCreacion,desc` - Por fecha descendente (más reciente primero)
- `sort=status,asc` - Por estado

---

### 4️⃣ Buscar por curso

```http
GET http://localhost:8080/topicos/buscar?curso=Spring Boot
```

**Respuesta**: Solo tópicos del curso "Spring Boot"

---

### 5️⃣ Buscar por año

```http
GET http://localhost:8080/topicos/buscar?anio=2026
```

**Respuesta**: Solo tópicos creados en 2026

---

### 6️⃣ Buscar por curso Y año

```http
GET http://localhost:8080/topicos/buscar?curso=Java&anio=2026
```

**Respuesta**: Tópicos de Java creados en 2026

---

### 7️⃣ Buscar con paginación

```http
GET http://localhost:8080/topicos/buscar?curso=Spring Boot&page=0&size=5&sort=titulo,asc
```

Combina todos los parámetros:
- Filtra por curso
- Página 0
- 5 elementos
- Ordenado por título

---

### 8️⃣ Obtener un tópico específico

```http
GET http://localhost:8080/topicos/1
```

**Respuesta** (HTTP 200 OK):
```json
{
  "id": 1,
  "titulo": "¿Cómo configurar Spring Security?",
  "mensaje": "Necesito ayuda para configurar la autenticación en mi proyecto Spring Boot.",
  "fechaCreacion": "2026-02-17T10:30:00",
  "status": "ABIERTO",
  "autorId": 2,
  "autorNombre": "Juan Pérez",
  "cursoId": 1,
  "cursoNombre": "Spring Boot"
}
```

---

## 🧪 PROBAR CON POSTMAN:

### Request 1: Listar todos
```
GET http://localhost:8080/topicos
```

### Request 2: Con paginación
```
GET http://localhost:8080/topicos?page=0&size=3&sort=fechaCreacion,desc
```

### Request 3: Buscar por curso
```
GET http://localhost:8080/topicos/buscar?curso=Spring Boot
```

### Request 4: Buscar por año
```
GET http://localhost:8080/topicos/buscar?anio=2026
```

### Request 5: Buscar por ambos
```
GET http://localhost:8080/topicos/buscar?curso=Java&anio=2026&page=0&size=5
```

---

## 🧪 PROBAR CON cURL:

### Listar todos:
```bash
curl http://localhost:8080/topicos
```

### Con paginación:
```bash
curl "http://localhost:8080/topicos?page=0&size=5"
```

### Buscar por curso:
```bash
curl "http://localhost:8080/topicos/buscar?curso=Spring%20Boot"
```

### Buscar por año:
```bash
curl "http://localhost:8080/topicos/buscar?anio=2026"
```

### Obtener por ID:
```bash
curl http://localhost:8080/topicos/1
```

---

## 🧪 PROBAR CON PowerShell:

```powershell
# Listar todos
Invoke-RestMethod -Uri "http://localhost:8080/topicos" -Method GET

# Con paginación
Invoke-RestMethod -Uri "http://localhost:8080/topicos?page=0&size=3" -Method GET

# Buscar por curso
Invoke-RestMethod -Uri "http://localhost:8080/topicos/buscar?curso=Spring Boot" -Method GET

# Buscar por año
Invoke-RestMethod -Uri "http://localhost:8080/topicos/buscar?anio=2026" -Method GET

# Obtener por ID
Invoke-RestMethod -Uri "http://localhost:8080/topicos/1" -Method GET
```

---

## 📊 ESTRUCTURA DE RESPUESTA:

### DatosListadoTopico (para listados):
```json
{
  "id": 1,
  "titulo": "Título del tópico",
  "mensaje": "Mensaje completo del tópico",
  "fechaCreacion": "2026-02-17T10:30:00",
  "status": "ABIERTO",
  "autorNombre": "Juan Pérez",
  "cursoNombre": "Spring Boot"
}
```

### DatosRespuestaTopico (para detalle):
```json
{
  "id": 1,
  "titulo": "Título del tópico",
  "mensaje": "Mensaje completo del tópico",
  "fechaCreacion": "2026-02-17T10:30:00",
  "status": "ABIERTO",
  "autorId": 2,
  "autorNombre": "Juan Pérez",
  "cursoId": 1,
  "cursoNombre": "Spring Boot"
}
```

---

## 🔍 PARÁMETROS DE PAGINACIÓN:

| Parámetro | Tipo | Default | Descripción |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Número de página (empieza en 0) |
| `size` | Integer | 10 | Elementos por página |
| `sort` | String | fechaCreacion,asc | Campo y dirección de ordenamiento |

### Ejemplos de sort:
- `sort=titulo,asc`
- `sort=fechaCreacion,desc`
- `sort=status,asc`
- `sort=autorNombre,asc`

---

## 🛡️ MANEJO DE ERRORES:

### Error: Tópico no encontrado (GET /topicos/{id})
**Request**:
```http
GET http://localhost:8080/topicos/999
```

**Respuesta** (HTTP 404 NOT FOUND):
```json
{
  "campo": "notFound",
  "error": "Tópico con ID 999 no encontrado"
}
```

---

## ⚙️ IMPLEMENTACIÓN TÉCNICA:

### 1. Repository (TopicoRepository.java)
```java
// Método de Spring Data JPA
Page<Topico> findAll(Pageable pageable);

// Consultas personalizadas con JPQL
@Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso")
Page<Topico> findByCursoNombre(@Param("nombreCurso") String nombreCurso, Pageable pageable);

@Query("SELECT t FROM Topico t WHERE YEAR(t.fechaCreacion) = :anio")
Page<Topico> findByAnio(@Param("anio") Integer anio, Pageable pageable);
```

### 2. Service (TopicoService.java)
```java
public Page<DatosListadoTopico> listarTopicos(Pageable paginacion) {
    return topicoRepository.findAll(paginacion)
            .map(DatosListadoTopico::new);
}

public Page<DatosListadoTopico> buscarTopicos(String curso, Integer anio, Pageable paginacion) {
    // Lógica de filtrado condicional
}
```

### 3. Controller (TopicoController.java)
```java
@GetMapping
public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
        @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC)
        Pageable paginacion) {
    // ...
}
```

---

## ✅ CHECKLIST COMPLETADO:

- [x] Endpoint GET /topicos creado
- [x] Acepta solicitudes GET
- [x] Devuelve datos en formato JSON
- [x] Incluye título, mensaje, fecha, estado, autor y curso
- [x] Usa findAll() de JpaRepository
- [x] **OPCIONAL**: Paginación con @PageableDefault ✅
- [x] **OPCIONAL**: Ordenamiento por fecha ASC ✅
- [x] **OPCIONAL**: 10 resultados por defecto ✅
- [x] **OPCIONAL**: Búsqueda por curso ✅
- [x] **OPCIONAL**: Búsqueda por año ✅
- [x] Endpoint GET /topicos/{id} ✅

---

## 📈 VENTAJAS DE LA PAGINACIÓN:

1. **Performance**: No carga todos los registros de una vez
2. **UX**: Mejor experiencia para el usuario
3. **Escalabilidad**: Funciona bien con miles de registros
4. **Flexibilidad**: El cliente decide cuántos elementos ver

---

## 🎯 CASOS DE USO:

### Frontend Web:
```javascript
// Listar primera página
fetch('http://localhost:8080/topicos?page=0&size=10')
  .then(response => response.json())
  .then(data => {
    console.log(data.content); // Array de tópicos
    console.log(data.totalPages); // Total de páginas
  });
```

### Mobile App:
```kotlin
// Listar tópicos de Spring Boot
val url = "http://localhost:8080/topicos/buscar?curso=Spring Boot&page=0&size=20"
// ... hacer request
```

---

## 🚀 ESTADO FINAL:

✅ **GET /topicos** - Listado con paginación  
✅ **GET /topicos/buscar** - Búsqueda con filtros  
✅ **GET /topicos/{id}** - Obtener por ID  
✅ **Paginación implementada** (@PageableDefault)  
✅ **Ordenamiento por fecha ASC**  
✅ **10 resultados por defecto**  
✅ **Filtros por curso y año**  
✅ **Método findAll() utilizado**  

**¡Todos los endpoints de listado están listos para ser usados!** 🎉

