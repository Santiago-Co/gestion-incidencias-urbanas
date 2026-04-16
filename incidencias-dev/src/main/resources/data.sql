DELETE FROM usuario;
DELETE FROM tipoIncidencia;
DELETE FROM tipoIncidenciaTecnico;
DELETE FROM incidencia;
DELETE FROM historia;
DELETE FROM trabajo;
DELETE FROM presupuesto;

-- ============================
-- INSERTAR PARAMETROS
-- ============================
INSERT INTO parametros (clave, valor) VALUES ('PRECIO_HORA_TECNICO', '15.0');

-- ============================
-- INSERTAR USUARIOS
-- ============================

INSERT INTO usuario (nombre, apellidos, dni, correo, rol) VALUES
-- Ciudadanos
('Juan',  'Perez Garcia',    '87654321Z', 'juan.perez@email.com', 'Ciudadano'),
('Maria', 'Fernandez Gomez', '88776655Y', 'maria@email.com',      'Ciudadano'),
-- Operadores
(NULL, NULL, '12345678A', 'operador@email.com', 'Operador'),
-- Tecnicos
('Luis',      'Garcia Garcia',       '11223344B', 'tecnico1@email.com', 'Tecnico'),
('Francisco', 'Sanchez Sanchez',     '11223344C', 'tecnico2@email.com', 'Tecnico'),
('Laura',     'Fernandez Fernandez', '11223344D', 'tecnico3@email.com', 'Tecnico'),
('Javier',    'Gomez Torres',        '11223344E', 'tecnico4@email.com', 'Tecnico'),
('Paula',     'Lopez Perez',         '11223344F', 'tecnico5@email.com', 'Tecnico'),
-- Tecnicos de relleno
('Pedro',   'Garcia Estevez', '11223344G', 'tecnico6@email.com',  'Tecnico'),
('Maria',   'Garcia Marquez', '11223344H', 'tecnico7@email.com',  'Tecnico'),
('Jose',    'Suarez Suarez',  '11223344I', 'tecnico8@email.com',  'Tecnico'),
('Alba',    'Menendez Gil',   '11223344J', 'tecnico9@email.com',  'Tecnico'),
('Antonio', 'Perez Perez',    '11223344K', 'tecnico10@email.com', 'Tecnico'),
-- Tecnicos responsables
('Pedro', 'Alvarez Suarez', '44332211A', 'tecnicoresp1@email.com', 'Tecnico responsable');

-- =============================
-- INSERTAR TIPOS DE INCIDENCIAS
-- =============================

INSERT INTO tipoIncidencia (nombre) VALUES
('Alumbrado'),
('Limpieza'),
('Mobiliario urbano'),
('Zonas verdes'),
('Señalización y calzada'),
('Otro');

-- ==================================
-- INSERTAR ASIGNACION DE INCIDENCIAS
-- ==================================
INSERT INTO tipoIncidenciaTecnico (idTipo, idTecnico) VALUES
(1, 4),
(2, 5),
(3, 6),
(4, 7),
(5, 8),
(1, 9),
(2, 10),
(3, 11),
(4, 12),
(5, 13),
(5, 14);

-- ============================
-- INSERTAR INCIDENCIAS
-- ============================

INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion,
						horasPrevistas, horasReales, descripcionPreviaTrabajo, descripcionFinalTrabajo) VALUES
-- Incidencias antiguas (febrero 2026 y anteriores)
(1, 1, 'Farola apagada desde hace varios días',    'Hospital', NULL, NULL, NULL,									  NULL),
(4, 1, 'Árbol caído tras tormenta', 			   'Hospital', NULL, NULL, NULL, 									  NULL),
(2, 2, 'Acumulación de basura en parque infantil', 'Hospital', NULL, NULL, NULL, 									  NULL),
(5, 2, 'Señal de stop caída', 				       'Hospital', NULL, NULL, NULL, 									  NULL),
(1, 2, 'Farola calle A no funciona', 			   'Hospital', NULL, NULL, NULL, 									  NULL),
(3, 2, 'Banca rota en plaza', 				       'Hospital', NULL, NULL, NULL, 								      NULL),
(5, 1, 'Socavón en calle C',     			 	   'Hospital', 2,    NULL, 'Reinstalación de señal vertical',       NULL),
(4, 2, 'Árbol caído', 						       'Hospital', 4,    5,    'Retirada de árbol y revisión de daños', 'Lorem ipsum'),
(3, 2, 'Banco roto en la plaza', 			       'Hospital', 3,    NULL, 'Sustitución de banco dañado',           NULL),

-- Incidencias marzo 2026
(1, 1, 'Farola intermitente en calle B', 		 'Hospital', 2,    3,    'Revisión de cableado', 'Dolor sit'),
(2, 2, 'Papelera desbordada', 				     'Hospital', 1,    NULL, 'Retirada de residuos', NULL),
(4, 1, 'Rama caída tras viento fuerte',  		 'Hospital', NULL, NULL, NULL,                   NULL),
(5, 2, 'Pintura de paso de peatones desgastada', 'Hospital', 4,    6,    'Repintado',            'Nosenosenose'),

-- Incidencias abril 2026
(3, 1, 'Papeleras rotas en plaza', 	    'Hospital', 2,    1,    'Sustitución de papeleras', 'No se me ocurre'),
(4, 2, 'Setos sin podar',			    'Hospital', NULL, NULL, NULL,                       NULL),
(1, 1, 'Luz parpadeante en túnel',      'Hospital', 5,    NULL, 'Revisión de luminarias',   NULL),
(5, 2, 'Señal de ceda el paso doblada', 'Hospital', 2,    2,    'Recolocación de señal',    'Piensa en algo');

-- ============================
-- INSERTAR HISTORIAL
-- ============================

INSERT INTO historia (idIncidencia, idOperador, fechaCambio, estado, descripcion) VALUES
-- Incidencias antiguas (febrero 2026 y anteriores)
(1, NULL, '2026-02-23 09:00:00', 'Nueva', 'Incidencia registrada por el ciudadano 1'),

(2, NULL, '2026-01-03 11:10:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(2, 3, 	  '2026-01-04 18:00:00', 'Validada', 'Incidencia validada por el operador 3'),

(3, NULL, '2026-02-15 07:20:00', 'Nueva', 'Incidencia registrada por el ciudadano 2'),

(4, NULL, '2026-01-11 19:30:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(4, 3, 	  '2026-01-12 10:15:00', 'Validada', 'Incidencia validada por el operador 3'),

(5, NULL, '2026-02-09 09:40:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(5, 3, 	  '2026-02-09 17:20:00', 'Validada', 'Incidencia validada por el operador 3'),
(5, 3, 	  '2026-02-10 15:00:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 4'),

(6, NULL, '2025-12-23 09:50:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(6, 3, 	  '2026-01-08 12:30:00', 'Validada', 'Incidencia validada por el operador 3'),

(7, NULL, '2025-11-26 10:00:00', 'Nueva', 	   'Incidencia registrada por el ciudadano 1'),
(7, 3, 	  '2025-11-27 17:40:00', 'Validada',   'Incidencia validada por el operador 3'),
(7, 3,	  '2025-12-03 16:10:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(7, NULL, '2025-12-18 12:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(8, NULL, '2026-01-20 10:10:00', 'Nueva', 	   'Incidencia registrada por el ciudadano 2'),
(8, 3,    '2026-01-21 09:50:00', 'Validada',   'Incidencia validada por el operador 3'),
(8, 3,    '2026-02-03 11:20:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 7'),
(8, NULL, '2026-02-10 12:10:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(8, NULL, '2026-02-17 14:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),

(9, NULL, '2025-12-07 10:20:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(9, 3,    '2025-12-08 11:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(9, 3,    '2025-12-13 11:30:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 6'),

-- Incidencias marzo 2026
(10, NULL, '2026-03-02 09:10:00', 'Nueva', 	    'Incidencia registrada por el ciudadano 1'),
(10, 3,    '2026-03-02 12:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(10, 3,    '2026-03-03 10:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 4'),
(10, NULL, '2026-03-04 15:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(10, NULL, '2026-03-05 17:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),
(10, NULL, '2026-03-06 09:00:00', 'Cerrada',    'Incidencia cerrada'),

(11, NULL, '2026-03-10 08:30:00', 'Nueva', 	    'Incidencia registrada por el ciudadano 2'),
(11, 3,    '2026-03-10 11:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(11, 3,    '2026-03-11 09:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 5'),
(11, NULL, '2026-03-12 14:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(12, NULL, '2026-03-15 10:00:00', 'Nueva',    'Incidencia registrada por el ciudadano 1'),
(12, 3,    '2026-03-15 12:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(12, 3,    '2026-03-16 09:30:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 7'),

(13, NULL, '2026-03-20 08:00:00', 'Nueva',      'Incidencia registrada por el ciudadano 2'),
(13, 3,    '2026-03-20 10:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(13, 3,    '2026-03-21 11:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(13, NULL, '2026-03-22 16:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(13, NULL, '2026-03-26 19:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),

-- Incidencias abril 2026
(14, NULL, '2026-04-03 09:00:00', 'Nueva', 	    'Incidencia registrada por el ciudadano 1'),
(14, 3,    '2026-04-03 12:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(14, 3,    '2026-04-04 10:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 6'),
(14, NULL, '2026-04-05 15:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(14, NULL, '2026-04-06 17:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum'),
(14, NULL, '2026-04-07 09:00:00', 'Cerrada',    'Incidencia cerrada'),

(15, NULL, '2026-04-10 08:00:00', 'Nueva',    'Incidencia registrada por el ciudadano 2'),
(15, 3,    '2026-04-10 11:00:00', 'Validada', 'Incidencia validada por el operador 3'),
(15, 3,    '2026-04-11 09:00:00', 'Asignada', 'Incidencia asignada por el operador 3 al tecnico 7'),

(16, NULL, '2026-04-15 10:00:00', 'Nueva', 	    'Incidencia registrada por el ciudadano 1'),
(16, 3,    '2026-04-15 12:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(16, 3,    '2026-04-16 09:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 4'),
(16, NULL, '2026-04-17 14:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(17, NULL, '2026-04-20 09:00:00', 'Nueva',      'Incidencia registrada por el ciudadano 2'),
(17, 3,    '2026-04-20 11:00:00', 'Validada',   'Incidencia validada por el operador 3'),
(17, 3,    '2026-04-21 10:00:00', 'Asignada',   'Incidencia asignada por el operador 3 al tecnico 8'),
(17, NULL, '2026-04-22 16:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(17, NULL, '2026-04-23 18:00:00', 'Resuelta',   'Incidencia resuelta. Trabajos realizados: lorem ipsum');

-- =====================================
-- INSERTAR GRUPOS DE TECNICOS ASIGNADOS
-- =====================================

INSERT INTO equipoTecnicos (idTecnico, idHistoria) VALUES
(4, 9),
(8, 14),
(13, 14),
(8, 15),
(7, 18),
(12, 18),
(7, 19),
(7, 20),
(6, 23),
(4, 26),
(9, 26),
(4, 27),
(4, 28),
(4, 29),
(5, 32),
(5, 33),
(7, 36),
(8, 39),
(13, 39),
(8, 40),
(8, 41),
(6, 44),
(11, 44),
(6, 45),
(6, 46),
(6, 47),
(7, 48),
(4, 53),
(4, 54),
(8, 57),
(8, 58),
(8, 59);

-- ===============================
-- INSERTAR TRABAJOS DE INCIDENCIA
-- ===============================

INSERT INTO trabajo (idIncidencia, fecha, descripcion) VALUES
(8, '2026-01-13 14:00:00', 'lorem ipsum');

-- ===============================
-- INSERTAR PRESUPUESTOS
-- ===============================

INSERT INTO presupuesto (idTipoIncidencia, importe, concepto) 
VALUES (5, 200.0, 'Señalización y calzada');
UPDATE incidencia SET costeTotal = 150.0 WHERE id = 13;
UPDATE incidencia SET costeTotal = 100.0 WHERE id = 17;
