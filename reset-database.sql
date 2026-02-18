-- Script para limpiar la base de datos y permitir que Flyway ejecute las migraciones desde cero
-- Ejecuta este script en tu cliente MySQL antes de iniciar la aplicación

DROP DATABASE IF EXISTS forohub_db;
CREATE DATABASE forohub_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Ahora puedes iniciar tu aplicación Spring Boot y Flyway ejecutará todas las migraciones

