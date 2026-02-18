# 🎯 GUÍA PRÁCTICA - Probar CRUD Completo

## 🚀 API ForoHub - Todos los Endpoints

Esta guía te muestra cómo probar **todos los endpoints** en orden lógico.

---

## 📋 ORDEN SUGERIDO DE PRUEBAS:

### 1️⃣ **CREATE** - Crear un nuevo tópico
```http
POST http://localhost:8080/topicos
Content-Type: application/json

{
  "titulo": "¿Cómo implementar paginación en Spring Boot?",
  "mensaje": "Necesito ayuda para implementar paginación en mis endpoints REST",
  "autorId": 1,
  "curso": "Spring Boot"
}
```
✅ **Respuesta**: 201 CREATED + Tópico creado (guarda el ID)

---

### 2️⃣ **READ** - Listar todos los tópicos
```http
GET http://localhost:8080/topicos
```
✅ **Respuesta**: 200 OK + Lista paginada de tópicos

---

### 3️⃣ **READ** - Obtener tópico específico
```http
GET http://localhost:8080/topicos/1
```
✅ **Respuesta**: 200 OK + Tópico con todos sus datos

---

### 4️⃣ **READ** - Buscar con filtros
```http
GET http://localhost:8080/topicos/buscar?curso=Spring Boot
```
✅ **Respuesta**: 200 OK + Tópicos filtrados por curso

---

### 5️⃣ **UPDATE** - Actualizar el tópico
```http
PUT http://localhost:8080/topicos/1
Content-Type: application/json

{
  "titulo": "¿Cómo implementar paginación en Spring Boot? (RESUELTO)",
  "mensaje": "Ya encontré la solución usando Pageable",
  "status": "RESUELTO",
  "curso": "Spring Boot"
}
```
✅ **Respuesta**: 200 OK + Tópico actualizado

---

### 6️⃣ **DELETE** - Eliminar el tópico
```http
DELETE http://localhost:8080/topicos/1
```
✅ **Respuesta**: 204 NO CONTENT

---

### 7️⃣ **VERIFICAR** - Confirmar eliminación
```http
GET http://localhost:8080/topicos/1
```
✅ **Respuesta**: 404 NOT FOUND (ya no existe)

---

## 🧪 COLECCIÓN POSTMAN COMPLETA

### Crear nueva colección "ForoHub - CRUD"

#### Request 1: Crear Tópico
- **Método**: POST
- **URL**: `http://localhost:8080/topicos`
- **Headers**: `Content-Type: application/json`
- **Body**:
```json
{
  "titulo": "Test Tópico",
  "mensaje": "Mensaje de prueba",
  "autorId": 1,
  "curso": "Spring Boot"
}
```

#### Request 2: Listar Tópicos
- **Método**: GET
- **URL**: `http://localhost:8080/topicos`

#### Request 3: Listar con Paginación
- **Método**: GET
- **URL**: `http://localhost:8080/topicos?page=0&size=5&sort=titulo,asc`

#### Request 4: Buscar por Curso
- **Método**: GET
- **URL**: `http://localhost:8080/topicos/buscar?curso=Spring Boot`

#### Request 5: Buscar por Año
- **Método**: GET
- **URL**: `http://localhost:8080/topicos/buscar?anio=2026`

#### Request 6: Obtener por ID
- **Método**: GET
- **URL**: `http://localhost:8080/topicos/1`

#### Request 7: Actualizar Tópico
- **Método**: PUT
- **URL**: `http://localhost:8080/topicos/1`
- **Headers**: `Content-Type: application/json`
- **Body**:
```json
{
  "titulo": "Título actualizado",
  "mensaje": "Mensaje actualizado",
  "status": "RESUELTO",
  "curso": "Spring Boot"
}
```

#### Request 8: Eliminar Tópico
- **Método**: DELETE
- **URL**: `http://localhost:8080/topicos/1`

---

## 💻 SCRIPT BASH COMPLETO

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/topicos"

echo "=== 1. CREAR TÓPICO ==="
curl -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Test","mensaje":"Mensaje de prueba","autorId":1,"curso":"Spring Boot"}'

echo -e "\n\n=== 2. LISTAR TÓPICOS ==="
curl -X GET $BASE_URL

echo -e "\n\n=== 3. OBTENER TÓPICO 1 ==="
curl -X GET $BASE_URL/1

echo -e "\n\n=== 4. BUSCAR POR CURSO ==="
curl -X GET "$BASE_URL/buscar?curso=Spring%20Boot"

echo -e "\n\n=== 5. ACTUALIZAR TÓPICO 1 ==="
curl -X PUT $BASE_URL/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Actualizado","mensaje":"Mensaje actualizado","status":"RESUELTO","curso":"Spring Boot"}'

echo -e "\n\n=== 6. ELIMINAR TÓPICO 1 ==="
curl -X DELETE $BASE_URL/1

echo -e "\n\n=== 7. VERIFICAR ELIMINACIÓN ==="
curl -X GET $BASE_URL/1
```

---

## 💻 SCRIPT POWERSHELL COMPLETO

```powershell
$baseUrl = "http://localhost:8080/topicos"

Write-Host "=== 1. CREAR TÓPICO ===" -ForegroundColor Green
$body = @{
    titulo = "Test Tópico"
    mensaje = "Mensaje de prueba"
    autorId = 1
    curso = "Spring Boot"
} | ConvertTo-Json

Invoke-RestMethod -Uri $baseUrl -Method POST -Body $body -ContentType "application/json"

Write-Host "`n=== 2. LISTAR TÓPICOS ===" -ForegroundColor Green
Invoke-RestMethod -Uri $baseUrl -Method GET

Write-Host "`n=== 3. OBTENER TÓPICO 1 ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/1" -Method GET

Write-Host "`n=== 4. BUSCAR POR CURSO ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/buscar?curso=Spring Boot" -Method GET

Write-Host "`n=== 5. ACTUALIZAR TÓPICO 1 ===" -ForegroundColor Green
$updateBody = @{
    titulo = "Título actualizado"
    mensaje = "Mensaje actualizado"
    status = "RESUELTO"
    curso = "Spring Boot"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/1" -Method PUT -Body $updateBody -ContentType "application/json"

Write-Host "`n=== 6. ELIMINAR TÓPICO 1 ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/1" -Method DELETE

Write-Host "`n=== 7. VERIFICAR ELIMINACIÓN (debería fallar) ===" -ForegroundColor Green
try {
    Invoke-RestMethod -Uri "$baseUrl/1" -Method GET
} catch {
    Write-Host "✓ Tópico eliminado correctamente (404)" -ForegroundColor Yellow
}
```

---

## 📊 TABLA DE RESPUESTAS ESPERADAS

| Acción | Método | Código | Tiene Body |
|--------|--------|--------|------------|
| Crear | POST | 201 | ✅ Tópico |
| Listar | GET | 200 | ✅ Page |
| Obtener | GET | 200 | ✅ Tópico |
| Buscar | GET | 200 | ✅ Page |
| Actualizar | PUT | 200 | ✅ Tópico |
| Eliminar | DELETE | 204 | ❌ Vacío |
| No encontrado | GET/PUT/DELETE | 404 | ✅ Error |
| Validación | POST/PUT | 400 | ✅ Errores |

---

## 🎯 CASOS DE PRUEBA RECOMENDADOS

### ✅ Casos Positivos:
1. Crear tópico válido → 201
2. Listar tópicos → 200
3. Obtener tópico existente → 200
4. Actualizar tópico existente → 200
5. Eliminar tópico existente → 204

### ❌ Casos Negativos:
1. Crear tópico sin título → 400
2. Crear tópico duplicado → 400
3. Crear tópico con curso inexistente → 400
4. Obtener tópico inexistente → 404
5. Actualizar tópico inexistente → 404
6. Eliminar tópico inexistente → 404

---

## 🔍 VERIFICAR EN LA BASE DE DATOS

```sql
-- Ver todos los tópicos
SELECT * FROM topico;

-- Ver tópicos con detalles
SELECT 
    t.id,
    t.titulo,
    t.status,
    u.nombre as autor,
    c.nombre as curso,
    t.fecha_creacion
FROM topico t
INNER JOIN usuario u ON t.autor_id = u.id
INNER JOIN curso c ON t.curso_id = c.id;

-- Contar tópicos
SELECT COUNT(*) as total FROM topico;

-- Ver tópicos por status
SELECT status, COUNT(*) as cantidad
FROM topico
GROUP BY status;
```

---

## 📱 EJEMPLO CON JAVASCRIPT (Frontend)

```javascript
const API_URL = 'http://localhost:8080/topicos';

// Crear
async function crearTopico() {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            titulo: 'Test',
            mensaje: 'Mensaje',
            autorId: 1,
            curso: 'Spring Boot'
        })
    });
    return await response.json();
}

// Listar
async function listarTopicos() {
    const response = await fetch(API_URL);
    return await response.json();
}

// Obtener
async function obtenerTopico(id) {
    const response = await fetch(`${API_URL}/${id}`);
    return await response.json();
}

// Actualizar
async function actualizarTopico(id, datos) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
    });
    return await response.json();
}

// Eliminar
async function eliminarTopico(id) {
    await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
}
```

---

## ✅ CHECKLIST DE TESTING

- [ ] Crear tópico válido
- [ ] Crear con campos vacíos (debe fallar)
- [ ] Crear tópico duplicado (debe fallar)
- [ ] Listar primera página
- [ ] Listar con ordenamiento personalizado
- [ ] Buscar por curso
- [ ] Buscar por año
- [ ] Obtener tópico existente
- [ ] Obtener tópico inexistente (debe fallar)
- [ ] Actualizar tópico válido
- [ ] Actualizar con datos inválidos (debe fallar)
- [ ] Actualizar tópico inexistente (debe fallar)
- [ ] Eliminar tópico existente
- [ ] Eliminar tópico inexistente (debe fallar)

---

**¡Usa esta guía para probar completamente tu API!** 🚀

