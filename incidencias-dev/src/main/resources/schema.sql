-- Usar camel case para mantener la correlación con las variables de java
DROP TABLE IF EXISTS trabajo;
DROP TABLE IF EXISTS historia;
DROP TABLE IF EXISTS incidencia;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NULL,
    apellidos TEXT NULL,
    dni TEXT UNIQUE NOT NULL,
    correo TEXT UNIQUE NOT NULL,
    especialidad TEXT NULL CHECK (especialidad IN ('Electricidad urbana', 'Servicios de limpieza', 'Mantenimiento', 'Jardineria', 'Seguridad vial')), 
    rol TEXT NOT NULL CHECK (rol IN ('Ciudadano', 'Operador', 'Tecnico', 'Tecnico responsable'))
);

CREATE TABLE incidencia (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo TEXT NOT NULL,
	idCiudadano INTEGER NOT NULL,
    descripcion TEXT NOT NULL,
    localizacion TEXT NOT NULL,
    horasPrevistas INTEGER NULL,
    horasReales INTEGER NULL,
    descripcionPreviaTrabajo TEXT NULL,
    descripcionFinalTrabajo TEXT NULL,
    CHECK (tipo IN ('Alumbrado', 'Limpieza', 'Mobiliario urbano', 'Zonas verdes', 'Señalización y calzada', 'Otro')),
    CHECK (localizacion IN ('Centro', 'Norte', 'Sur', 'Este', 'Oeste', 'Parque Central', 'Zona Universitaria', 'Poligono Industrial', 'Estacion', 'Hospital')),
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
	CHECK (estado IN ('Nueva', 'Validada', 'Asignada', 'En proceso', 'Resuelta', 'Cerrada', 'Rechazado')),
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idTecnico) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idOperador) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE trabajo (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idIncidencia INTEGER NOT NULL,
	fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	descripcion TEXT NOT NULL,
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE
);