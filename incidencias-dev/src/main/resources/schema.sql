-- Usar camel case para mantener la correlación con las variables de java
DROP TABLE IF EXISTS trabajo;
DROP TABLE IF EXISTS equipoTecnicos;
DROP TABLE IF EXISTS historia;
DROP TABLE IF EXISTS incidencia;
DROP TABLE IF EXISTS tipoIncidenciaTecnico;
DROP TABLE IF EXISTS tipoIncidencia;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS parametros;

CREATE TABLE parametros (
    clave TEXT PRIMARY KEY,
    valor TEXT NOT NULL
);

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
    descripcion TEXT NOT NULL,
    localizacion TEXT NOT NULL,
    horasPrevistas INTEGER NULL,
    horasReales INTEGER NULL,
    descripcionPreviaTrabajo TEXT NULL,
    descripcionFinalTrabajo TEXT NULL,
    costeMateriales DECIMAL NULL,
    costeTotal DECIMAL NULL,
    CHECK (localizacion IN ('Centro', 'Norte', 'Sur', 'Este', 'Oeste', 'Parque Central', 'Zona Universitaria', 'Poligono Industrial', 'Estacion', 'Hospital')),
    FOREIGN KEY (tipo) REFERENCES tipoIncidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idCiudadano) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE historia (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idIncidencia INTEGER NOT NULL,
	idOperador INTEGER NULL,
	fechaCambio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	estado TEXT NOT NULL,
	descripcion TEXT NOT NULL,
	CHECK (estado IN ('Nueva', 'Validada', 'Asignada', 'En proceso', 'Resuelta', 'Cerrada', 'Rechazado')),
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idOperador) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE equipoTecnicos (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idTecnico INTEGER NOT NULL,
	idHistoria INTEGER NOT NULL,
	FOREIGN KEY (idTecnico) REFERENCES usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (idHistoria) REFERENCES historia(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE trabajo (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	idIncidencia INTEGER NOT NULL,
	fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	descripcion TEXT NOT NULL,
	FOREIGN KEY (idIncidencia) REFERENCES incidencia(id) ON DELETE CASCADE ON UPDATE CASCADE
);