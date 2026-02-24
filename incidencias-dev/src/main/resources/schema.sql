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
    tipo TEXT NOT NULL,
	idCiudadano INTEGER NOT NULL,
    descripcion TEXT NOT NULL,
    localizacion TEXT NOT NULL,
    horasPrevistas INTEGER NULL,
    descripcionTrabajo TEXT NULL,
    CHECK (tipo IN ('Alumbrado', 'Limpieza', 'Mobiliario urbano', 'Zonas verdes', 'Señalización y calzada', 'Otro')),
	FOREIGN KEY (idCiudadano) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
	);

CREATE TABLE historia (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idIncidencia INTEGER NOT NULL,
	idCiudadano INTEGER NOT NULL,
	idTecnico INTEGER NULL,
	idOperador INTEGER NULL,
	fechaCambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	estado TEXT NOT NULL,
	descripcion TEXT NOT NULL,
	CHECK (estado IN ('Nueva', 'Validada', 'Asignada', 'En proceso', 'Resuleta', 'Cerrada')),
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idTecnico) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idOperador) REFERENCES usuarios(id) ON DELETE CASCADE ON UPDATE CASCADE
);
