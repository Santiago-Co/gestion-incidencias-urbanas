-- Usar camel case para mantener la correlación con las variables de java
DROP TABLE IF EXISTS historia;
DROP TABLE IF EXISTS incidencia;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT,
    apellidos TEXT,
    dni TEXT UNIQUE NOT NULL,
    correo TEXT UNIQUE NOT NULL,
    especialidad TEXT CHECK (especialidad IN ('Electricidad urbana', 'Servicios de limpieza', 'Mantenimiento', 'Jardineria', 'Seguridad vial')), 
    rol TEXT NOT NULL CHECK (rol IN ('Ciudadano', 'Operador', 'Tecnico', 'Tecnico responsable'))
);

CREATE TABLE incidencia (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo TEXT NOT NULL,
	idCiudadano INTEGER NOT NULL,
    descripcion TEXT NOT NULL,
    localizacion TEXT NOT NULL,
    horasPrevistas INTEGER NULL,
    descripcionTrabajo TEXT NULL,
    CHECK (tipo IN ('Alumbrado', 'Limpieza', 'Mobiliario urbano', 'Zonas verdes', 'Señalización y calzada', 'Otro')),
	FOREIGN KEY (idCiudadano) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE historia (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idIncidencia INTEGER NOT NULL,
	idTecnico INTEGER NULL,
	idOperador INTEGER NULL,
	fechaCambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	estado TEXT NOT NULL,
	descripcion TEXT NOT NULL,
	CHECK (estado IN ('Nueva', 'Validada', 'Asignada', 'En proceso', 'Resuelta', 'Cerrada')),
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idTecnico) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idOperador) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);
