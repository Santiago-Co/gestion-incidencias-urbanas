DELETE FROM usuario;
DELETE FROM incidencia;
DELETE FROM historia;
DELETE FROM trabajo;

-- ============================
-- INSERTAR USUARIOS
-- ============================

INSERT INTO usuario (nombre, apellidos, dni, correo, especialidad, rol) VALUES
-- Ciudadanos
('Juan',  'Perez Garcia',    '87654321Z', 'juan.perez@email.com', NULL, 'Ciudadano'),
('Maria', 'Fernandez Gomez', '88776655Y', 'maria@email.com',      NULL, 'Ciudadano'),
-- Operadores
(NULL, NULL, '12345678A', 'operador@email.com', NULL, 'Operador'),
-- Técnicos
('Luis',      'Garcia Garcia',       '11223344B', 'tecnico1@email.com', 'Electricidad urbana',    'Tecnico'),
('Francisco', 'Sanchez Sanchez',     '11223344C', 'tecnico2@email.com', 'Servicios de limpieza',  'Tecnico'),
('Laura',     'Fernandez Fernandez', '11223344D', 'tecnico3@email.com', 'Mantenimiento',          'Tecnico'),
('Javier',    'Gomez Torres',        '11223344E', 'tecnico4@email.com', 'Jardineria',             'Tecnico'),
('Paula',     'Lopez Perez',         '11223344G', 'tecnico5@email.com', 'Seguridad vial',         'Tecnico');

-- ============================
-- INSERTAR INCIDENCIAS
-- ============================

INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion,
						horasPrevistas, horasReales, descripcionPreviaTrabajo, descripcionFinalTrabajo) VALUES
-- Incidencias antiguas (febrero 2026 y anteriores)
('Alumbrado',    		   1, 'Farola apagada desde hace varios días',    'Calle Mayor 15', 		NULL, NULL, NULL,									 NULL),
('Zonas verdes', 		   1, 'Árbol caído tras tormenta', 				  'Avenida del Río', 		NULL, NULL, NULL, 									 NULL),
('Limpieza',     		   2, 'Acumulación de basura en parque infantil', 'Parque Central', 		NULL, NULL, NULL, 									 NULL),
('Señalización y calzada', 2, 'Señal de stop caída', 					  'Calle Sol esquina Luna', NULL, NULL, NULL, 									 NULL),
('Alumbrado', 		       2, 'Farola calle A no funciona', 			  'Calle A, 12', 			NULL, NULL, NULL, 									 NULL),
('Mobiliario urbano', 	   2, 'Banca rota en plaza', 					  'Plaza Mayor', 			NULL, NULL, NULL, 									 NULL),
('Señalización y calzada', 1, 'Socavón en calle C',     				  'Calle C, 9',   			2,    NULL, 'Reinstalación de señal vertical',       NULL),
('Zonas verdes', 		   2, 'Árbol caído', 							  'Parque Sur',   			4,    5,    'Retirada de árbol y revisión de daños', 'Lorem ipsum'),
('Mobiliario urbano', 	   2, 'Banco roto en la plaza', 			      'Plaza España', 			3,    NULL, 'Sustitución de banco dañado',           NULL),
-- Incidencias marzo 2026
('Alumbrado',    		   1, 'Farola intermitente en calle B', 		'Calle B, 22',   2,    3,    'Revisión de cableado', 'Dolor sit'),
('Limpieza',     		   2, 'Papelera desbordada', 					'Parque Norte',  1,    NULL, 'Retirada de residuos', NULL),
('Zonas verdes', 		   1, 'Rama caída tras viento fuerte',  		'Paseo del Río', NULL, NULL, NULL,                   NULL),
('Señalización y calzada', 2, 'Pintura de paso de peatones desgastada', 'Calle Luna', 	 4,    NULL, 'Repintado',            NULL),
-- Incidencias abril 2026
('Mobiliario urbano', 	   1, 'Papeleras rotas en plaza', 	   'Plaza Mayor',   2,    1,    'Sustitución de papeleras', 'No se me ocurre'),
('Zonas verdes', 	  	   2, 'Setos sin podar',			   'Parque Este',   NULL, NULL, NULL,                       NULL),
('Alumbrado', 		  	   1, 'Luz parpadeante en túnel',      'Túnel Central', 5,    NULL, 'Revisión de luminarias',   NULL),
('Señalización y calzada', 2, 'Señal de ceda el paso doblada', 'Av. del Mar',   2,    2,    'Recolocación de señal',    'Piensa en algo');


-- ============================
-- INSERTAR HISTORIAL
-- ============================

INSERT INTO historia (idIncidencia, idTecnico, idOperador, fechaCambio, estado, descripcion) VALUES
-- Incidencias antiguas (febrero 2026 y anteriores)
(1, NULL, NULL, '2026-02-23 09:00:00', 'Nueva', 'Incidencia registrada por el ciudadano 1'),

(2, NULL, NULL, '2026-01-03 11:10:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(2, NULL, 3, 	'2026-01-04 18:00:00', 'Validada', 'Incidencia validada por el operador 3'),

(3, NULL, NULL, '2026-02-15 07:20:00', 'Nueva', 'Incidencia registrada por el ciudadano 2'),

(4, NULL, NULL, '2026-01-11 19:30:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(4, NULL, 3, 	'2026-01-12 10:15:00', 'Validada', 'Incidencia validada por el operador 3'),

(5, NULL, NULL, '2026-02-09 09:40:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(5, NULL, 3, 	'2026-02-09 17:20:00', 'Validada', 'Incidencia validada por el operador 3'),
(5, 4, 	  3, 	'2026-02-10 15:00:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 4'),

(6, NULL, NULL, '2025-12-23 09:50:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(6, NULL, 3, 	'2026-01-08 12:30:00', 'Validada', 'Incidencia validada por el operador 3'),

(7, NULL, NULL, '2025-11-26 10:00:00', 'Nueva', 	 'Incidencia registrada por el ciudadano 1'),
(7, NULL, 3, 	'2025-11-27 17:40:00', 'Validada',   'Incidencia validada por el operador 3'),
(7, 8, 	  3,	'2025-12-03 16:10:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(7, 8,    3, 	'2025-12-18 12:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(8, NULL, NULL, '2026-01-20 10:10:00', 'Nueva', 	 'Incidencia registrada por el ciudadano 2'),
(8, NULL, 3,    '2026-01-21 09:50:00', 'Validada',   'Incidencia validada por el operador 3'),
(8, 7,    3,    '2026-02-03 11:20:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 7'),
(8, 7,    3,    '2026-02-10 12:10:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(8, 7,    3,    '2026-02-17 14:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),
(8, 7,    3,    '2026-02-23 15:00:00', 'Cerrada',    'Incidencia cerrada'),

(9, NULL, NULL, '2025-12-07 10:20:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(9, NULL, 3,    '2025-12-08 11:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(9, 6,    3,    '2025-12-13 11:30:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 6'),

-- Incidencias marzo 2026
(10, NULL, NULL, '2026-03-02 09:10:00', 'Nueva', 	  'Incidencia registrada por el ciudadano 1'),
(10, NULL, 3,    '2026-03-02 12:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(10, 4,    3,    '2026-03-03 10:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 4'),
(10, 4,    3,    '2026-03-04 15:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(10, 4,    3,    '2026-03-05 17:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),
(10, 4,    3,    '2026-03-06 09:00:00', 'Cerrada',    'Incidencia cerrada'),

(11, NULL, NULL, '2026-03-10 08:30:00', 'Nueva', 	  'Incidencia registrada por el ciudadano 2'),
(11, NULL, 3,    '2026-03-10 11:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(11, 5,    3,    '2026-03-11 09:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 5'),
(11, 5,    3,    '2026-03-12 14:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(12, NULL, NULL, '2026-03-15 10:00:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(12, NULL, 3,    '2026-03-15 12:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(12, 7,    3,    '2026-03-16 09:30:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 7'),

(13, NULL, NULL, '2026-03-20 08:00:00', 'Nueva',      'Incidencia registrada por el ciudadano 2'),
(13, NULL, 3,    '2026-03-20 10:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(13, 8,    3,    '2026-03-21 11:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(13, 8,    3,    '2026-03-22 16:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

-- Incidencias abril 2026
(14, NULL, NULL, '2026-04-03 09:00:00', 'Nueva', 	  'Incidencia registrada por el ciudadano 1'),
(14, NULL, 3,    '2026-04-03 12:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(14, 6,    3,    '2026-04-04 10:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 6'),
(14, 6,    3,    '2026-04-05 15:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(14, 6,    3,    '2026-04-06 17:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),
(14, 6,    3,    '2026-04-07 09:00:00', 'Cerrada',    'Incidencia cerrada'),

(15, NULL, NULL, '2026-04-10 08:00:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(15, NULL, 3,    '2026-04-10 11:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(15, 7,    3,    '2026-04-11 09:00:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 7'),

(16, NULL, NULL, '2026-04-15 10:00:00', 'Nueva', 	  'Incidencia registrada por el ciudadano 1'),
(16, NULL, 3,    '2026-04-15 12:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(16, 4,    3,    '2026-04-16 09:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 4'),
(16, 4,    3,    '2026-04-17 14:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(17, NULL, NULL, '2026-04-20 09:00:00', 'Nueva',      'Incidencia registrada por el ciudadano 2'),
(17, NULL, 3,    '2026-04-20 11:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(17, 8,    3,    '2026-04-21 10:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(17, 8,    3,    '2026-04-22 16:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(17, 8,    3,    '2026-04-23 18:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum');

-- ===============================
-- INSERTAR TRABAJOS DE INCIDENCIA
-- ===============================

INSERT INTO trabajo (idIncidencia, fecha, descripcion) VALUES
(8, '2026-01-13 14:00:00', 'lorem ipsum');
