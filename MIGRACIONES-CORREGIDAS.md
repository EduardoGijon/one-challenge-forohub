# 🔴 PROBLEMAS ENCONTRADOS EN LAS MIGRACIONES DE FLYWAY

## ❌ Errores Identificados:

### 1. **V1__create-table-perfil.sql estaba VACÍO**
   - **Problema**: Flyway intentaba ejecutar la primera migración pero no encontraba ningún SQL
   - **Impacto**: Las migraciones fallaban desde el inicio porque V3 necesita la tabla `perfil` (foreign key)
   - **Solución**: ✅ Creé el contenido correcto con la tabla `perfil`

### 2. **V7__insert-initial-data.sql tenía contenido duplicado/mezclado**
   - **Problema**: Al final del archivo había un `CREATE TABLE perfil` que ya debía existir en V1
   - **Impacto**: Conflicto entre crear una tabla que ya debería existir
   - **Solución**: ✅ Eliminé el CREATE TABLE del final, dejando solo los INSERT statements

## ✅ SOLUCIÓN APLICADA:

### Archivos Corregidos:

1. **V1__create-table-perfil.sql** - Ahora contiene:
```sql
CREATE TABLE perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_perfil_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

2. **V7__insert-initial-data.sql** - Ahora solo contiene INSERT statements (sin CREATE TABLE)

## 📋 ORDEN CORRECTO DE MIGRACIONES:

1. ✅ **V1**: Crea tabla `perfil`
2. ✅ **V2**: Crea tabla `usuario`
3. ✅ **V3**: Crea tabla `usuario_perfil` (relación many-to-many)
4. ✅ **V4**: Crea tabla `curso`
5. ✅ **V5**: Crea tabla `topico`
6. ✅ **V6**: Crea tabla `respuesta`
7. ✅ **V7**: Inserta datos iniciales de prueba

## 🚀 PASOS PARA EJECUTAR LAS MIGRACIONES:

### Opción 1: Desde tu cliente MySQL (MySQL Workbench, phpMyAdmin, etc.)
Ejecuta el archivo `reset-database.sql` que creé:
```sql
DROP DATABASE IF EXISTS forohub_db;
CREATE DATABASE forohub_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Opción 2: Desde línea de comandos (si tienes mysql en PATH)
```bash
mysql -u root -p < reset-database.sql
```

### Opción 3: Manualmente en MySQL Workbench
1. Abre MySQL Workbench
2. Conecta con usuario: `root`, password: `root`
3. Ejecuta:
   ```sql
   DROP DATABASE IF EXISTS forohub_db;
   CREATE DATABASE forohub_db;
   ```

### Luego, inicia tu aplicación Spring Boot:
```bash
mvn spring-boot:run
```

## 🔍 VERIFICACIÓN:

Flyway debería ejecutar las migraciones en orden y verás en la consola:
```
Flyway: Migrating schema `forohub_db` to version 1 - create-table-perfil
Flyway: Migrating schema `forohub_db` to version 2 - create-table-usuario
Flyway: Migrating schema `forohub_db` to version 3 - create-table-usuario-perfil
...
```

## 📊 DATOS DE PRUEBA INCLUIDOS:

Después de ejecutar todas las migraciones tendrás:
- 3 Perfiles: ROLE_ADMIN, ROLE_USER, ROLE_MODERADOR
- 5 Cursos: Spring Boot, Java, React, MySQL, Python
- 3 Usuarios: admin@forohub.com, juan@example.com, maria@example.com
  - **Contraseña para todos**: `123456` (hasheada con BCrypt)
- 3 Tópicos de ejemplo
- 3 Respuestas de ejemplo

## ⚠️ IMPORTANTE:
- Si ya habías ejecutado las migraciones parcialmente, DEBES eliminar la base de datos
- Flyway mantiene un historial en la tabla `flyway_schema_history`
- Si no eliminas la BD, Flyway pensará que las migraciones ya se ejecutaron

