# 🔄 API REST - Endpoint PUT /topicos/{id} (ACTUALIZACIÓN)

## ✅ IMPLEMENTACIÓN COMPLETADA

Se ha implementado exitosamente el endpoint **PUT /topicos/{id}** para actualizar tópicos existentes con todas las validaciones requeridas.

---

## 🎯 ENDPOINT IMPLEMENTADO:

### **PUT /topicos/{id}** - Actualizar un tópico ✅

- **URI**: `/topicos/{id}`
- **Método**: PUT
- **Parámetro**: `{id}` - ID del tópico (obligatorio, en la URL)
- **Body**: JSON con los datos actualizados
- **Respuesta**: HTTP 200 OK con el tópico actualizado

---

## 📋 REGLAS DE NEGOCIO IMPLEMENTADAS:

### ✅ Mismas validaciones que el registro:

1. **Todos los campos son obligatorios** (@Valid)
   - título
   - mensaje
   - status
   - curso

2. **No permite tópicos duplicados**
   - Verifica que no exista otro tópico con el mismo título y mensaje
   - Excluye el tópico actual que se está actualizando

3. **Verifica que el curso exista**
   - Busca el curso por nombre en la base de datos

4. **Verifica que el tópico exista** (usa isPresent())
   - Usa `Optional.isPresent()` para verificar si el ID existe
   - Retorna 404 si el tópico no existe

---

## 🔧 IMPLEMENTACIÓN TÉCNICA:

### Controller (TopicoController.java):
```java
@PutMapping("/{id}")
public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
        @PathVariable Long id,  // ✅ Usa @PathVariable
        @RequestBody @Valid DatosActualizacionTopico datos) {
    
    DatosRespuestaTopico topicoActualizado = topicoService.actualizarTopico(id, datos);
    return ResponseEntity.ok(topicoActualizado);
}
```

### Service (TopicoService.java):
```java
@Transactional
public DatosRespuestaTopico actualizarTopico(Long id, DatosActualizacionTopico datos) {
    
    // ✅ Usa isPresent() como se solicitó
    var topicoOptional = topicoRepository.findById(id);
    if (!topicoOptional.isPresent()) {
        throw new EntityNotFoundException("Tópico con ID " + id + " no encontrado");
    }
    
    Topico topico = topicoOptional.get();
    
    // ✅ Verifica duplicados (excepto el actual)
    var topicoExistente = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
    if (topicoExistente.isPresent() && !topicoExistente.get().getId().equals(id)) {
        throw new ValidacionException("Ya existe otro tópico con el mismo título y mensaje");
    }
    
    // ✅ Verifica que el curso exista
    Curso curso = cursoRepository.findByNombre(datos.curso())
            .orElseThrow(() -> new ValidacionException("El curso '" + datos.curso() + "' no existe"));
    
    // Actualizar los datos
    topico.setTitulo(datos.titulo());
    topico.setMensaje(datos.mensaje());
    topico.setStatus(datos.status());
    topico.setCurso(curso);
    
    topico = topicoRepository.save(topico);
    
    return new DatosRespuestaTopico(topico);
}
```

### DTO (DatosActualizacionTopico.java):
```java
public record DatosActualizacionTopico(
        @NotBlank(message = "El título es obligatorio")
        String titulo,
        
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,
        
        @NotNull(message = "El estado es obligatorio")
        StatusTopico status,
        
        @NotBlank(message = "El nombre del curso es obligatorio")
        String curso
) {
}
```

---

## 📝 EJEMPLOS DE USO:

### 1️⃣ Actualización Exitosa

**Request**:
```http
PUT http://localhost:8080/topicos/1
Content-Type: application/json

{
  "titulo": "¿Cómo configurar Spring Security? (Actualizado)",
  "mensaje": "Necesito ayuda actualizada para configurar la autenticación en mi proyecto Spring Boot 3.x",
  "status": "RESUELTO",
  "curso": "Spring Boot"
}
```

**Respuesta** (HTTP 200 OK):
```json
{
  "id": 1,
  "titulo": "¿Cómo configurar Spring Security? (Actualizado)",
  "mensaje": "Necesito ayuda actualizada para configurar la autenticación en mi proyecto Spring Boot 3.x",
  "fechaCreacion": "2026-02-17T10:30:00",
  "status": "RESUELTO",
  "autorId": 2,
  "autorNombre": "Juan Pérez",
  "cursoId": 1,
  "cursoNombre": "Spring Boot"
}
```

---

### 2️⃣ Cambiar Estado del Tópico

**Request**:
```http
PUT http://localhost:8080/topicos/2
Content-Type: application/json

{
  "titulo": "Error al conectar con MySQL",
  "mensaje": "Me aparece un error de conexión cuando intento conectar mi aplicación con MySQL",
  "status": "CERRADO",
  "curso": "MySQL"
}
```

---

### 3️⃣ Cambiar Curso del Tópico

**Request**:
```http
PUT http://localhost:8080/topicos/3
Content-Type: application/json

{
  "titulo": "Mejores prácticas en desarrollo web",
  "mensaje": "Me gustaría conocer las mejores prácticas para estructurar un proyecto moderno",
  "status": "ABIERTO",
  "curso": "Java"
}
```

---

## ❌ CASOS DE ERROR:

### Error 1: Tópico no encontrado (404)

**Request**:
```http
PUT http://localhost:8080/topicos/999
Content-Type: application/json

{
  "titulo": "Test",
  "mensaje": "Test mensaje",
  "status": "ABIERTO",
  "curso": "Java"
}
```

**Respuesta** (HTTP 404 NOT FOUND):
```json
{
  "campo": "notFound",
  "error": "Tópico con ID 999 no encontrado"
}
```

---

### Error 2: Campo obligatorio faltante (400)

**Request**:
```http
PUT http://localhost:8080/topicos/1
Content-Type: application/json

{
  "titulo": "",
  "mensaje": "Test mensaje",
  "status": "ABIERTO",
  "curso": "Java"
}
```

**Respuesta** (HTTP 400 BAD REQUEST):
```json
[
  {
    "campo": "titulo",
    "error": "El título es obligatorio"
  }
]
```

---

### Error 3: Tópico duplicado (400)

**Request** (intentando actualizar a un título/mensaje que ya existe en otro tópico):
```http
PUT http://localhost:8080/topicos/1
Content-Type: application/json

{
  "titulo": "Error al conectar con MySQL",
  "mensaje": "Me aparece un error de conexión...",
  "status": "ABIERTO",
  "curso": "MySQL"
}
```

**Respuesta** (HTTP 400 BAD REQUEST):
```json
{
  "campo": "validacion",
  "error": "Ya existe otro tópico con el mismo título y mensaje"
}
```

---

### Error 4: Curso no existe (400)

**Request**:
```http
PUT http://localhost:8080/topicos/1
Content-Type: application/json

{
  "titulo": "Test",
  "mensaje": "Test mensaje",
  "status": "ABIERTO",
  "curso": "Curso Inexistente"
}
```

**Respuesta** (HTTP 400 BAD REQUEST):
```json
{
  "campo": "validacion",
  "error": "El curso 'Curso Inexistente' no existe"
}
```

---

## 🧪 PROBAR CON POSTMAN:

### Request:
1. **Método**: PUT
2. **URL**: `http://localhost:8080/topicos/1`
3. **Headers**: `Content-Type: application/json`
4. **Body** (raw JSON):
```json
{
  "titulo": "Título actualizado",
  "mensaje": "Mensaje actualizado",
  "status": "RESUELTO",
  "curso": "Spring Boot"
}
```

---

## 🧪 PROBAR CON cURL:

```bash
curl -X PUT http://localhost:8080/topicos/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"titulo\":\"Título actualizado\",\"mensaje\":\"Mensaje actualizado\",\"status\":\"RESUELTO\",\"curso\":\"Spring Boot\"}"
```

---

## 🧪 PROBAR CON PowerShell:

```powershell
$body = @{
    titulo = "Título actualizado"
    mensaje = "Mensaje actualizado"
    status = "RESUELTO"
    curso = "Spring Boot"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/topicos/1" `
    -Method PUT `
    -Body $body `
    -ContentType "application/json"
```

---

## 📊 ESTADOS DISPONIBLES (StatusTopico):

| Estado | Descripción |
|--------|-------------|
| ABIERTO | Tópico abierto para respuestas |
| CERRADO | Tópico cerrado, no acepta más respuestas |
| RESUELTO | Tópico resuelto con solución |
| DUPLICADO | Tópico marcado como duplicado |

---

## 🔍 DIFERENCIAS ENTRE POST Y PUT:

| Aspecto | POST (Crear) | PUT (Actualizar) |
|---------|--------------|------------------|
| ID | No se envía (se genera automático) | Se envía en la URL (@PathVariable) |
| Autor | Se envía `autorId` | NO se actualiza (permanece igual) |
| Fecha creación | Se asigna automáticamente | NO se actualiza (permanece igual) |
| Status | Se asigna "ABIERTO" por defecto | Se envía en el body (obligatorio) |
| Curso | Se envía nombre del curso | Se envía nombre del curso |
| Respuesta | 201 CREATED | 200 OK |

---

## ✅ CHECKLIST DE VALIDACIONES IMPLEMENTADAS:

- [x] Campo ID obligatorio (@PathVariable)
- [x] Verifica que el tópico exista (isPresent())
- [x] Todos los campos obligatorios (@Valid)
- [x] No permite duplicados (mismo título y mensaje)
- [x] Excluye el tópico actual de la validación de duplicados
- [x] Verifica que el curso exista
- [x] Transacción (@Transactional)
- [x] Retorna 200 OK con el tópico actualizado
- [x] Retorna 404 si el tópico no existe
- [x] Retorna 400 para validaciones fallidas

---

## 🎯 FLUJO DE ACTUALIZACIÓN:

```
1. Cliente envía PUT /topicos/{id}
          ↓
2. @PathVariable captura el ID
          ↓
3. @Valid valida el body
          ↓
4. Service verifica que el tópico existe (isPresent())
          ↓
5. Valida que no haya duplicados (excepto el actual)
          ↓
6. Valida que el curso exista
          ↓
7. Actualiza los campos del tópico
          ↓
8. Guarda en la BD con save()
          ↓
9. Retorna 200 OK con el tópico actualizado
```

---

## 📁 ARCHIVOS CREADOS/MODIFICADOS:

### ✅ Nuevos:
- `DatosActualizacionTopico.java` - DTO para actualización

### ✅ Modificados:
- `TopicoController.java` - Endpoint PUT agregado
- `TopicoService.java` - Método actualizarTopico() agregado

---

## 🚀 ESTADO FINAL:

✅ **POST /topicos** - Crear tópico  
✅ **GET /topicos** - Listar con paginación  
✅ **GET /topicos/buscar** - Buscar con filtros  
✅ **GET /topicos/{id}** - Obtener por ID  
✅ **PUT /topicos/{id}** - **Actualizar tópico** ⭐ NUEVO

---

## 🎓 CONCEPTOS TÉCNICOS APLICADOS:

1. **@PathVariable**: Captura el ID de la URL
2. **@RequestBody @Valid**: Valida el body del request
3. **Optional.isPresent()**: Verifica si un valor existe
4. **@Transactional**: Asegura integridad de datos
5. **Validación de duplicados**: Excluye el registro actual
6. **HTTP 200 OK**: Respuesta estándar para PUT exitoso

---

**¡Endpoint PUT /topicos/{id} implementado y listo para usar!** 🎉

