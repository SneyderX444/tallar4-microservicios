package co.edu.udistrital.backend.service;

import co.edu.udistrital.backend.dto.ActividadDTO;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de la lógica de negocio de Actividad.
 * Recibe y devuelve siempre {@link ActividadDTO} para desacoplar al cliente de la entidad JPA.
 */
public interface IActividadService {

    /** Crea una actividad y devuelve su representación DTO con el ID generado. */
    ActividadDTO insertarActividad(ActividadDTO actividadDTO);

    /** Devuelve todas las actividades como DTOs (lista vacía si no hay datos). */
    List<ActividadDTO> todasActividades();

    /** Busca una actividad por ID; el Optional viene vacío si no existe. */
    Optional<ActividadDTO> consultarPorID(Long id);

    /** Actualiza los campos modificables de una actividad existente. */
    ActividadDTO modificarActividad(Long id, ActividadDTO actividadDTO);

    /** Elimina una actividad por ID. Lanza IllegalArgumentException si no existe. */
    void borrarActividad(Long id);
}
