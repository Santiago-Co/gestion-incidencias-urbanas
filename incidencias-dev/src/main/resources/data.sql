DELETE FROM usuario;
DELETE FROM incidencia;
DELETE FROM historia;

-- ============================
-- INSERTAR USUARIOS
-- ============================

-- Ciudadanos
INSERT INTO usuario (dni, correo, rol) VALUES 
('87654321Z', 'juan.perez@email.com', 'Ciudadano');

-- Operadores
INSERT INTO usuario (dni, correo, rol) VALUES 
('12345678A', 'operador@email.com', 'Operador');

-- Técnicos
INSERT INTO usuario (dni, correo, rol) VALUES 
('11223344B', 'tecnico@email.com', 'Tecnico');

-- ============================
-- INSERTAR INCIDENCIAS
-- ============================

-- Incidencias no 'En proceso' (sin notas de tecnico)
INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion, horasPrevistas, descripcionTrabajo) VALUES
('Alumbrado', 1, 'Farola apagada desde hace varios días', 'Calle Mayor 15', NULL, NULL),
('Zonas verdes', 1, 'Árbol caído tras tormenta', 'Avenida del Río', NULL, NULL),
('Limpieza', 2, 'Acumulación de basura en parque infantil', 'Parque Central', NULL, NULL),
('Señalización y calzada', 2, 'Señal de stop caída', 'Calle Sol esquina Luna', NULL, NULL),
('Alumbrado', 2, 'Farola calle A no funciona', 'Calle A, 12', NULL, NULL),
('Mobiliario urbano', 2, 'Banca rota en plaza', 'Plaza Mayor', NULL, NULL);

-- Incidencias 'En proceso' (con horasPrevistas y descripcionTrabajo)
INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion, horasPrevistas, descripcionTrabajo) VALUES
('Señalización y calzada', 2, 'Socavón en calle C', 'Calle C, 9', 2, 'Reinstalación de señal vertical'),
('Zonas verdes', 2, 'Árbol caído', 'Parque Sur', 4, 'Retirada de árbol y revisión de daños'),
('Mobiliario urbano', 3, 'Banco roto en la plaza', 'Plaza España', 3, 'Sustitución de banco dañado');

-- ============================
-- INSERTAR HISTORIAL
-- ============================

INSERT INTO historia (idIncidencia, idTecnico, idOperador, fechaCambio, estado, descripcion) VALUES
(1, NULL, NULL, '2026-02-23 09:00:00', 'Nueva', 'Incidencia registrada por un ciudadano'),

(2, NULL, NULL, '2026-01-03 11:10:00', 'Nueva', 'Incidencia registrada por un ciudadano'),
(2, NULL, 2, '2026-01-04 18:00:00', 'Validada', 'Incidencia validada por el operador'),

(3, NULL, NULL, '2026-02-15 07:20:00', 'Nueva', 'Incidencia registrada por un operador'),

(4, NULL, NULL, '2026-01-11 19:30:00', 'Nueva', 'Incidencia registrada por un operador'),
(4, NULL, 2, '2026-01-12 10:15:00', 'Validada', 'Incidencia validada por el operador'),

(5, NULL, NULL, '2025-12-09 09:40:00', 'Nueva', 'Incidencia registrada por un operador'),
(5, NULL, 2, '2025-12-09 17:20:00', 'Validada', 'Incidencia validada por el operador'),
(5, 3, 2, '2026-01-10 15:00:00', 'Asignada', 'Incidencia asignada por el operador a un tecnico'),

(6, NULL, NULL, '2025-12-23 09:50:00', 'Nueva', 'Incidencia registrada por un operador'),
(6, NULL, 2, '2026-01-08 12:30:00', 'Validada', 'Incidencia validada por el operador'),

(7, NULL, NULL, '2025-11-26 10:00:00', 'Nueva', 'Incidencia registrada por un operador'),
(7, NULL, 2, '2025-11-27 17:40:00', 'Validada', 'Incidencia validada por el operador'),
(7, 3, 2, '2025-12-03 16:10:00', 'Asignada', 'Incidencia asignada por el operador a un tecnico'),
(7, 3, 2, '2025-12-18 12:00:00', 'En proceso', 'Incidencia en proceso de reparacion'),

(8, NULL, NULL, '2025-10-20 10:10:00', 'Nueva', 'Incidencia registrada por un operador'),
(8, NULL, 2, '2025-10-21 09:50:00', 'Validada', 'Incidencia validada por el operador'),
(8, 3, 2, '2025-10-25 11:20:00', 'Asignada', 'Incidencia asignada por el operador a un tecnico'),
(8, 3, 2, '2025-11-09 12:10:00', 'En proceso', 'Incidencia en proceso de reparacion'),
(8, 3, 2, '2026-01-13 14:00:00', 'Resuelta', 'Incidencia resuelta'),
(8, 3, 2, '2026-02-23 15:00:00', 'Cerrada', 'Incidencia cerrada'),

(9, NULL, NULL, '2025-12-07 10:20:00', 'Nueva', 'Incidencia registrada por un tecnico'),
(9, NULL, 2, '2025-12-08 11:00:00', 'Validada', 'Incidencia validada por el operador'),
(9, 3, 2, '2025-12-13 11:30:00', 'Asignada', 'Incidencia asignada por el operador');