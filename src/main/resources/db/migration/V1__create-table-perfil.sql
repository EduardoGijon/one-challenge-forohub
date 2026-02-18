-- Tabla Perfil
CREATE TABLE perfil (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_perfil_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

