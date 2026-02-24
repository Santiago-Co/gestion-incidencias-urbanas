DELETE FROM usuarios;
DELETE FROM incidencia;
DELETE FROM historial;
-- ============================
-- INSERTAR USUARIOS
-- ============================
INSERT INTO usuarios(dni, correo, rol) VALUES
('12345678A','ciudadano1@gmail.com','Ciudadano'),
('87654321Z','operador1@gmail.com','Operador'),
('11111111A','tecnico1@gmail.com','Tecnico'),
('22222222B','tecnico2@gmail.com','Tecnico');

-- ============================
-- INSERTAR INCIDENCIAS
-- ============================
INSERT INTO incidencia(ciudadanoId, tecnicoId, tipo, descripcion, localizacion, estado, horasPrevistas, descripcionTrabajo) VALUES
(1, 3, 'Alumbrado', 'Farola calle A no funciona', 'Calle A, 12', 'Asignada', NULL, NULL),
(1, 3, 'Limpieza', 'Contenedor roto', 'Calle B, 5', 'Asignada', NULL, NULL),
(1, NULL, 'Señalización y calzada', 'Socavón en calle C', 'Calle C, 9', 'Nueva', NULL, NULL),
(1, 4, 'Señalización y calzada', 'Señalización caída en parque', 'Parque Central', 'Asignada', NULL, NULL),
(1, 4, 'Mobiliario urbano', 'Banca rota en plaza', 'Plaza Mayor', 'Asignada', NULL, NULL),
(1, 3, 'Zonas verdes', 'Árbol caído', 'Parque Sur', 'Asignada', NULL, NULL);

-- ============================
-- INSERTAR HISTORIAL
-- ============================
INSERT INTO historial(idIncidencia, fechaCambio, estado, descripcion) VALUES
(1, '2026-02-23 09:00:00', 'Asignada', 'Incidencia creada y asignada al técnico.'),
(2, '2026-02-23 09:15:00', 'Asignada', 'Incidencia creada y asignada al técnico.'),
(3, '2026-02-23 10:00:00', 'Nueva', 'Incidencia creada por ciudadano.'),
(4, '2026-02-23 11:00:00', 'Asignada', 'Incidencia creada y asignada al técnico.'),
(5, '2026-02-23 11:15:00', 'Asignada', 'Incidencia creada y asignada al técnico.'),
(6, '2026-02-23 11:30:00', 'Asignada', 'Incidencia creada y asignada al técnico.');