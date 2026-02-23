--generado por gemini para hacer pruebas

INSERT INTO usuarios (dni, correo, rol) VALUES 
('12345678A', 'ciudadano@email.com', 'Ciudadano'),
('87654321B', 'operador@email.com', 'Operador'),
('11223344C', 'tecnico@email.com', 'Tecnico');

INSERT INTO incidencia (ciudadanoId, tecnicoId, tipo, descripcion, localizacion, estado, fechaRegistro) VALUES
(2, NULL, 'Alumbrado', 'Farola fundida y parpadeando', 'Calle Mayor 10, frente al bar', 'Nueva', '2025-11-12 21:00:00'),
(2, NULL, 'Limpieza', 'Contenedor de plástico desbordado', 'Plaza España, esquina norte', 'Nueva', '2025-12-21 12:00:00'),
(2, NULL, 'Mobiliario urbano', 'Banco con las tablas rotas', 'Parque del Sur, zona infantil', 'Nueva', '2026-01-02 17:00:00'),
(2, NULL, 'Zonas verdes', 'Rama de árbol a punto de caer', 'Avenida Libertad 45', 'Nueva', '2026-02-11 19:00:00');

-- Grupo B: Incidencia VALIDADA (Revisada por operador, pero aún sin técnico)
-- Simulamos que se registró ayer
INSERT INTO incidencia (ciudadanoId, tecnicoId, tipo, descripcion, localizacion, estado, fechaRegistro) VALUES
(2, NULL, 'Señalización y calzada', 'Bache profundo en mitad de la carretera', 'Calle Ancha 22', 'Validada', '2026-01-25 09:00:00'),
(2, NULL, 'Limpieza', 'lorem ipsum dolor sit amet', 'Calle ABCD', 'Validada', '2025-11-05 09:00:00');

-- Grupo C: Incidencia EN PROCESO (Ya tiene técnico asignado: ID 3)
INSERT INTO incidencia (ciudadanoId, tecnicoId, tipo, descripcion, localizacion, estado, fechaRegistro) VALUES
(2, 3, 'Alumbrado', 'Cableado expuesto en farola', 'Calle Pez 8', 'En proceso', '2025-09-24 09:30:00');

-- Grupo D: Incidencia CERRADA (Ya tiene técnico asignado: ID 3)
INSERT INTO incidencia (ciudadanoId, tecnicoId, tipo, descripcion, localizacion, estado, fechaRegistro) VALUES
(2, 3, 'Limpieza', 'Pintadas ofensivas en fachada', 'Ayuntamiento lateral', 'Cerrada', '2023-10-20 15:45:00');