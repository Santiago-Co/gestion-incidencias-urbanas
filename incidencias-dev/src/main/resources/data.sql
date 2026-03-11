DELETE FROM usuario;
DELETE FROM incidencia;
DELETE FROM historia;
DELETE FROM trabajo;

-- ============================
-- INSERTAR USUARIOS
-- ============================

-- Ciudadanos
INSERT INTO usuario (nombre, apellidos, dni, correo, rol) VALUES 
('Juan',  'Perez Garcia',    '87654321Z', 'juan.perez@email.com', 'Ciudadano'),
('Maria', 'Fernandez Gomez', '88776655Y', 'maria@email.com',      'Ciudadano');

-- Operadores
INSERT INTO usuario (dni, correo, rol) VALUES 
('12345678A', 'operador@email.com', 'Operador');

-- Técnicos
INSERT INTO usuario (nombre, apellidos, dni, correo, especialidad, rol) VALUES 
('Luis',      'Garcia Garcia',       '11223344B', 'tecnico1@email.com', 'Electricidad urbana',    'Tecnico'),
('Francisco', 'Sanchez Sanchez',     '11223344C', 'tecnico2@email.com', 'Servicios de limpieza',  'Tecnico'),
('Laura',     'Fernandez Fernandez', '11223344D', 'tecnico3@email.com', 'Mantenimiento',          'Tecnico'),
('Javier',    'Gomez Torres',        '11223344E', 'tecnico4@email.com', 'Jardineria',             'Tecnico'),
('Paula',     'Lopez Perez',         '11223344G', 'tecnico5@email.com', 'Seguridad vial',         'Tecnico');

-- ============================
-- INSERTAR INCIDENCIAS
-- ============================

-- Incidencias no 'En proceso' (sin notas de tecnico)

INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion,
						horasPrevistas, horasReales, descripcionPreviaTrabajo, descripcionFinalTrabajo) VALUES
('Alumbrado',    		   1, 'Farola apagada desde hace varios días',    'Sur',         				NULL, NULL, NULL, NULL),
('Zonas verdes', 		   1, 'Árbol caído tras tormenta', 				  'Hospital',        			NULL, NULL, NULL, NULL),
('Limpieza',     		   2, 'Acumulación de basura en parque infantil', 'Zona Universitaria',         NULL, NULL, NULL, NULL),
('Señalización y calzada', 2, 'Señal de stop caída', 					  'Poligono Industrial', 		NULL, NULL, NULL, NULL),
('Alumbrado', 		       2, 'Farola calle A no funciona', 			  'Estacion', 		    		NULL, NULL, NULL, NULL),
('Mobiliario urbano', 	   2, 'Banca rota en plaza', 					  'Oeste', 						NULL, NULL, NULL, NULL);

-- Incidencias 'En proceso' (con horasPrevistas y descripcionTrabajo)
INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion,
						horasPrevistas, horasReales, descripcionPreviaTrabajo, descripcionFinalTrabajo) VALUES
('Señalización y calzada', 1, 'Socavón en calle C',     'Zona Universitaria',   2, 4,    'Reinstalación de señal vertical',       'Lorem ipsum'),
('Zonas verdes', 		   2, 'Árbol caído', 			'Hospital',   			4, NULL, 'Retirada de árbol y revisión de daños', NULL),
('Mobiliario urbano', 	   2, 'Banco roto en la plaza', 'Sur', 					3, NULL, 'Sustitución de banco dañado',           NULL);

-- ============================
-- INSERTAR HISTORIAL
-- ============================

INSERT INTO historia (idIncidencia, idTecnico, idOperador, fechaCambio, estado, descripcion) VALUES
(1, NULL, NULL, '2026-02-23 09:00:00', 'Nueva', 'Incidencia registrada por el ciudadano 1'),

(2, NULL, NULL, '2026-01-03 11:10:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(2, NULL, 3, 	'2026-01-04 18:00:00', 'Validada', 'Incidencia validada por el operador 3'),

(3, NULL, NULL, '2026-02-15 07:20:00', 'Nueva', 'Incidencia registrada por el ciudadano 2'),

(4, NULL, NULL, '2026-01-11 19:30:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(4, NULL, 3, 	'2026-01-12 10:15:00', 'Validada', 'Incidencia validada por el operador 3'),

(5, NULL, NULL, '2025-12-09 09:40:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(5, NULL, 3, 	'2025-12-09 17:20:00', 'Validada', 'Incidencia validada por el operador 3'),
(5, 4, 	  2, 	'2026-01-10 15:00:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 4'),

(6, NULL, NULL, '2025-12-23 09:50:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(6, NULL, 3, 	'2026-01-08 12:30:00', 'Validada', 'Incidencia validada por el operador 3'),

(7, NULL, NULL, '2025-11-26 10:00:00', 'Nueva', 	 'Incidencia registrada por el ciudadano 1'),
(7, NULL, 3, 	'2025-11-27 17:40:00', 'Validada',   'Incidencia validada por el operador 3'),
(7, 8, 	  3,	'2025-12-03 16:10:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(7, 8,    3, 	'2025-12-18 12:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(8, NULL, NULL, '2025-10-20 10:10:00', 'Nueva', 	 'Incidencia registrada por el ciudadano 2'),
(8, NULL, 3,    '2025-10-21 09:50:00', 'Validada',   'Incidencia validada por el operador 3'),
(8, 7,    3,    '2025-10-25 11:20:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 7'),
(8, 7,    3,    '2025-11-09 12:10:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(8, 7,    3,    '2026-01-13 14:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),
(8, 7,    3,    '2026-02-23 15:00:00', 'Cerrada',    'Incidencia cerrada'),

(9, NULL, NULL, '2025-12-07 10:20:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(9, NULL, 3,    '2025-12-08 11:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(9, 6,    3,    '2025-12-13 11:30:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 6');

-- ===============================
-- INSERTAR TRABAJOS DE INCIDENCIA
-- ===============================

INSERT INTO trabajo (idIncidencia, fecha, descripcion) VALUES
(8, '2026-01-13 14:00:00', 'lorem ipsum');