DROP TABLE IF EXISTS incidencia;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    dni CHAR(9),
    correo TEXT,
    rol TEXT NOT NULL,
    CHECK (rol IN ('Ciudadano', 'Tecnico', 'Operador'))
);

CREATE TABLE incidencia (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    idUsuario INTEGER NOT NULL,
    tipo TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    fechaHora TEXT NOT NULL,
    estado TEXT NOT NULL,
    CHECK (tipo IN ('Alumbrado', 'Limpieza', 'Mobiliario urbano', 'Zonas verdes', 'Senializacion', 'Calzada')),
    CHECK (estado IN ('Nueva', 'Validada', 'Asignada', 'En proceso', 'Resuelta', 'Cerrada')),
    FOREIGN KEY (idUsuario) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);
