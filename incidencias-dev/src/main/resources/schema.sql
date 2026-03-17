-- Usar camel case para mantener la correlación con las variables de java
DROP TABLE IF EXISTS trabajo;
DROP TABLE IF EXISTS historia;
DROP TABLE IF EXISTS incidencia;
DROP TABLE IF EXISTS tipoIncidenciaTecnico;
DROP TABLE IF EXISTS tipoIncidencia;
DROP TABLE IF EXISTS usuario;

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NULL,
    apellidos TEXT NULL,
    dni TEXT UNIQUE NOT NULL,
    correo TEXT UNIQUE NOT NULL,
    rol TEXT NOT NULL CHECK (rol IN ('Ciudadano', 'Operador', 'Tecnico', 'Tecnico responsable'))
);

CREATE TABLE tipoIncidencia (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre TEXT
);

CREATE TABLE tipoIncidenciaTecnico (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idTipo INTEGER NOT NULL,
	idTecnico INTEGER NOT NULL,
	FOREIGN KEY (idTipo) REFERENCES tipoIncidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idTecnico) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE incidencia (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipo INTEGER NOT NULL,
	idCiudadano INTEGER NOT NULL,
	idAsignacion INTEGER NULL,
    descripcion TEXT NOT NULL,
    localizacion TEXT NOT NULL,
    horasPrevistas INTEGER NULL,
    horasReales INTEGER NULL,
    descripcionPreviaTrabajo TEXT NULL,
    descripcionFinalTrabajo TEXT NULL,
    FOREIGN KEY (tipo) REFERENCES tipoIncidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idCiudadano) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (idAsignacion) REFERENCES tipoIncidenciaTecnico(id) ON DELETE CASCADE ON UPDATE CASCADE
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

CREATE TABLE trabajo (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idIncidencia INTEGER NOT NULL,
	fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	descripcion TEXT NOT NULL,
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE
);