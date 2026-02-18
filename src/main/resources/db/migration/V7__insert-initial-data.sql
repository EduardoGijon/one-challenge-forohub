-- Insertar datos iniciales para pruebas

-- Insertar perfiles
INSERT INTO perfil (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO perfil (nombre) VALUES ('ROLE_USER');
INSERT INTO perfil (nombre) VALUES ('ROLE_MODERADOR');

-- Insertar cursos
INSERT INTO curso (nombre, categoria) VALUES ('Spring Boot', 'Backend');
INSERT INTO curso (nombre, categoria) VALUES ('Java', 'Backend');
INSERT INTO curso (nombre, categoria) VALUES ('React', 'Frontend');
INSERT INTO curso (nombre, categoria) VALUES ('MySQL', 'Base de Datos');
INSERT INTO curso (nombre, categoria) VALUES ('Python', 'Backend');

-- Insertar usuarios (contraseña en texto plano: "123456" - en producción usar BCrypt)
-- En un proyecto real, deberías hashear las contraseñas: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
INSERT INTO usuario (nombre, correo_electronico, contrasena)
VALUES ('Admin User', 'admin@forohub.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

INSERT INTO usuario (nombre, correo_electronico, contrasena)
VALUES ('Juan Pérez', 'juan@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

INSERT INTO usuario (nombre, correo_electronico, contrasena)
VALUES ('María López', 'maria@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');

-- Asignar perfiles a usuarios
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES (1, 1); -- Admin tiene ROLE_ADMIN
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES (2, 2); -- Juan tiene ROLE_USER
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES (3, 2); -- María tiene ROLE_USER
INSERT INTO usuario_perfil (usuario_id, perfil_id) VALUES (3, 3); -- María también tiene ROLE_MODERADOR

-- Insertar algunos tópicos de ejemplo
INSERT INTO topico (titulo, mensaje, autor_id, curso_id, status)
VALUES ('¿Cómo configurar Spring Security?', 'Necesito ayuda para configurar la autenticación en mi proyecto Spring Boot.', 2, 1, 'ABIERTO');

INSERT INTO topico (titulo, mensaje, autor_id, curso_id, status)
VALUES ('Error al conectar con MySQL', 'Me aparece un error de conexión cuando intento conectar mi aplicación con MySQL.', 3, 4, 'ABIERTO');

INSERT INTO topico (titulo, mensaje, autor_id, curso_id, status)
VALUES ('Mejores prácticas en React', 'Me gustaría conocer las mejores prácticas para estructurar un proyecto React.', 2, 3, 'ABIERTO');

-- Insertar algunas respuestas de ejemplo
INSERT INTO respuesta (mensaje, topico_id, autor_id, solucion)
VALUES ('Debes agregar la dependencia spring-boot-starter-security en tu pom.xml y crear una clase de configuración que extienda WebSecurityConfigurerAdapter.', 1, 1, TRUE);

INSERT INTO respuesta (mensaje, topico_id, autor_id, solucion)
VALUES ('Verifica que tu MySQL esté corriendo en el puerto correcto y que las credenciales en application.properties sean correctas.', 2, 1, FALSE);

INSERT INTO respuesta (mensaje, topico_id, autor_id, solucion)
VALUES ('También asegúrate de agregar allowPublicKeyRetrieval=true en tu URL de conexión.', 2, 3, TRUE);

