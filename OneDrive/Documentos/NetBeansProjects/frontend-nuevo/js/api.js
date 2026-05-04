/* =========================================================================
   API.JS · HomeTask Manager — Datos simulados
   Taller No 4 - Programación Avanzada · Universidad Distrital
   Simula los endpoints REST del backend Spring Boot.
   Expuesto como window.API para consumo desde los HTML.
   ========================================================================= */

(function () {
    'use strict';

    /** Array principal de actividades (datos en memoria). */
    var ACTIVIDADES = [
        {
            id: 1,
            titulo: 'Planchar la ropa',
            descripcion: 'Planchar toda la ropa limpia del cuarto con cuidado de no quemar las prendas delicadas.',
            fechaInicio: '2026-05-05',
            fechaTerminacion: '2026-05-06',
            tipoActividad: 'Física',
            idQuehacer: 1, idTutor: 100, idHijo: 200
        },
        {
            id: 2,
            titulo: 'Regar las plantas del balcón',
            descripcion: 'Regar todas las plantas asegurándose de que el agua llegue bien a las raíces.',
            fechaInicio: '2026-05-06',
            fechaTerminacion: '2026-05-07',
            tipoActividad: 'Física',
            idQuehacer: 2, idTutor: 100, idHijo: 201
        },
        {
            id: 3,
            titulo: 'Supervisar tareas escolares',
            descripcion: 'Revisar que los ejercicios de matemáticas y ciencias estén completos y bien hechos.',
            fechaInicio: '2026-05-07',
            fechaTerminacion: '2026-05-08',
            tipoActividad: 'Supervisión',
            idQuehacer: 3, idTutor: 100, idHijo: 200
        },
        {
            id: 4,
            titulo: 'Paseo al parque',
            descripcion: 'Acompañar a los niños al parque del barrio. Jugar 45 minutos en los columpios.',
            fechaInicio: '2026-05-08',
            fechaTerminacion: '2026-05-08',
            tipoActividad: 'Acompañamiento',
            idQuehacer: 4, idTutor: 101, idHijo: 201
        },
        {
            id: 5,
            titulo: 'Taller de pintura',
            descripcion: 'Crear una pintura libre con acuarelas sobre el tema que el hijo prefiera.',
            fechaInicio: '2026-05-10',
            fechaTerminacion: '2026-05-11',
            tipoActividad: 'Creativa',
            idQuehacer: 5, idTutor: 101, idHijo: 200
        },
        {
            id: 6,
            titulo: 'Lavar los platos del almuerzo',
            descripcion: 'Lavar los platos con jabón y agua caliente. Secarlos y guardarlos en su lugar.',
            fechaInicio: '2026-05-12',
            fechaTerminacion: '2026-05-12',
            tipoActividad: 'Física',
            idQuehacer: 6, idTutor: 100, idHijo: 201
        },
        {
            id: 7,
            titulo: 'Construcción con bloques',
            descripcion: 'Construir una maqueta de su ciudad imaginaria usando bloques de madera y cartón.',
            fechaInicio: '2026-06-01',
            fechaTerminacion: '2026-06-03',
            tipoActividad: 'Creativa',
            idQuehacer: 7, idTutor: 101, idHijo: 200
        }
    ];

    /** Contador autoincremental para nuevos IDs. */
    var nextId = 8;

    /* ─── FUNCIONES CRUD ──────────────────────────────────────────────── */

    /**
     * Retorna una copia del array de actividades.
     * Simula GET /actividad/consultar
     * @returns {Array} lista de actividades
     */
    function getActividades() {
        return ACTIVIDADES.slice();
    }

    /**
     * Busca una actividad por su ID.
     * Simula GET /actividad/{id}
     * @param {number|string} id - ID de la actividad
     * @returns {Object|null} la actividad encontrada o null
     */
    function getActividadById(id) {
        var found = ACTIVIDADES.find(function (a) { return a.id === parseInt(id, 10); });
        return found || null;
    }

    /**
     * Agrega una nueva actividad al array.
     * Simula POST /actividad/insertar
     * @param {Object} data - datos de la nueva actividad
     * @returns {Object} la actividad creada con su ID asignado
     */
    function crearActividad(data) {
        var nueva = {
            id: nextId++,
            titulo: data.titulo,
            descripcion: data.descripcion,
            fechaInicio: data.fechaInicio,
            fechaTerminacion: data.fechaTerminacion,
            tipoActividad: data.tipoActividad,
            idQuehacer: parseInt(data.idQuehacer, 10) || 0,
            idTutor: parseInt(data.idTutor, 10) || 0,
            idHijo: parseInt(data.idHijo, 10) || 0
        };
        ACTIVIDADES.push(nueva);
        return nueva;
    }

    /**
     * Actualiza una actividad existente.
     * Simula PUT /actividad/{id}
     * @param {number|string} id - ID de la actividad a actualizar
     * @param {Object} data - nuevos datos
     * @returns {Object|null} actividad actualizada o null si no existe
     */
    function actualizarActividad(id, data) {
        var idx = ACTIVIDADES.findIndex(function (a) { return a.id === parseInt(id, 10); });
        if (idx === -1) return null;
        ACTIVIDADES[idx] = Object.assign({}, ACTIVIDADES[idx], {
            titulo: data.titulo,
            descripcion: data.descripcion,
            fechaInicio: data.fechaInicio,
            fechaTerminacion: data.fechaTerminacion,
            tipoActividad: data.tipoActividad,
            idQuehacer: parseInt(data.idQuehacer, 10) || ACTIVIDADES[idx].idQuehacer,
            idTutor: parseInt(data.idTutor, 10) || ACTIVIDADES[idx].idTutor,
            idHijo: parseInt(data.idHijo, 10) || ACTIVIDADES[idx].idHijo
        });
        return ACTIVIDADES[idx];
    }

    /**
     * Elimina una actividad del array.
     * Simula DELETE /actividad/{id}
     * @param {number|string} id - ID de la actividad a eliminar
     * @returns {boolean} true si se eliminó, false si no existía
     */
    function eliminarActividad(id) {
        var idx = ACTIVIDADES.findIndex(function (a) { return a.id === parseInt(id, 10); });
        if (idx === -1) return false;
        ACTIVIDADES.splice(idx, 1);
        return true;
    }

    /**
     * Devuelve la clase CSS del badge según el tipo de actividad.
     * @param {string} tipo
     * @returns {string} clase CSS
     */
    function badgeClass(tipo) {
        var map = {
            'Física':         'badge-slate',
            'Acompañamiento': 'badge-berry',
            'Supervisión':    'badge-gold',
            'Creativa':       'badge-crimson'
        };
        return map[tipo] || 'badge-slate';
    }

    /* ─── EXPORTAR ────────────────────────────────────────────────────── */
    window.API = {
        getActividades:      getActividades,
        getActividadById:    getActividadById,
        crearActividad:      crearActividad,
        actualizarActividad: actualizarActividad,
        eliminarActividad:   eliminarActividad,
        badgeClass:          badgeClass
    };

}());
