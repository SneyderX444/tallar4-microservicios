-- ===================================================================
-- DATOS INICIALES PARA TESTING
-- ===================================================================
-- Este archivo se ejecuta automáticamente al iniciar la aplicación.
-- Spring Data JPA crea las tablas primero (gracias a Hibernate)
-- y luego ejecuta este script SQL para popular datos iniciales.
--
-- Los datos se pierden cuando apagas la aplicación (H2 en memoria),
-- pero es útil para testing sin insertar manualmente.
-- ===================================================================

-- TABLA: actividades
-- Estructura generada por Hibernate a partir de la entidad Actividad.

-- Insertamos 7 actividades variadas, una por cada uno de los 4 tipos
-- definidos en el enunciado del taller:
--   Física, Acompañamiento, Supervisión, Creatividad

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Planchar la ropa',
    'Planchar toda la ropa limpia que está en el closet. Hazlo con cuidado para no quemar la tela.',
    '2026-04-27', '2026-04-27', 'Física',
    1, 100, 200
);

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Regar las plantas',
    'Regar todas las plantas del balcón. Asegúrate de que el agua alcance bien las raíces.',
    '2026-04-28', '2026-04-28', 'Física',
    2, 100, 201
);

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Hacer los deberes',
    'Completar los ejercicios de matemáticas de las páginas 45-47. Necesita supervisor presente.',
    '2026-04-29', '2026-04-29', 'Supervisión',
    3, 100, 200
);

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Ir al parque',
    'Ir al parque con un adulto. Juega en los columpios por 30 minutos. Necesita acompañamiento.',
    '2026-04-30', '2026-04-30', 'Acompañamiento',
    4, 100, 201
);

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Dibujar o pintar',
    'Crear un dibujo o pintura libre. Puedes usar lápices, acuarelas o cualquier material.',
    '2026-05-01', '2026-05-01', 'Creatividad',
    5, 101, 200
);

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Lavar los platos',
    'Lavar TODOS los platos del almuerzo. Usa jabón y agua caliente. Sécalos después.',
    '2026-05-02', '2026-05-02', 'Física',
    6, 101, 201
);

INSERT INTO actividades (titulo, descripcion, fecha_inicio, fecha_terminacion, tipo_actividad, id_quehacer, id_tutor, id_hijo)
VALUES (
    'Escribir en el diario',
    'Escribe sobre tu día en el diario. Puede ser corto o largo, lo importante es expresar tus sentimientos.',
    '2026-05-03', '2026-05-03', 'Creatividad',
    7, 101, 200
);
