-- Usar camel case para mantener la correlación con las variables de java
DROP TABLE IF EXISTS historial;
DROP TABLE IF EXISTS incidencia;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    dni TEXT UNIQUE NOT NULL,
    correo TEXT UNIQUE NOT NULL, 
    rol TEXT NOT NULL CHECK (rol IN ('Ciudadano', 'Operador', 'Tecnico'))
);

CREATE TABLE incidencia (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ciudadanoId INTEGER NOT NULL,
    tecnicoId INTEGER NULL,
    tipo TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    localizacion TEXT NOT NULL,
    estado TEXT NOT NULL DEFAULT 'Nueva', 
    fechaRegistro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (tipo IN ('Alumbrado', 'Limpieza', 'Mobiliario urbano', 'Zonas verdes', 'Señalización y calzada', 'Otro')),
    CHECK ( estado IN ('Nueva', 'Validada', 'Asignada', 'En proceso', 'Resuelta', 'Cerrada')),
    FOREIGN KEY (ciudadanoId) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tecnicoId) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);