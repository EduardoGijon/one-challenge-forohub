-- Tabla Usuario
CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuario_correo (correo_electronico)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

