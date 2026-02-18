-- Tabla Curso
CREATE TABLE curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_curso_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

