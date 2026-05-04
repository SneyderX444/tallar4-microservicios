package co.edu.udistrital.backend.service;

import co.edu.udistrital.backend.model.Actividad;
import co.edu.udistrital.backend.dto.ActividadDTO;
import co.edu.udistrital.backend.exception.ActividadNoEncontradaException;
import co.edu.udistrital.backend.repository.ActividadRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Implementación de la lógica de negocio de Actividad.
 *
 * <p>Convierte entre DTO y entidad JPA y orquesta llamadas al repositorio.
 * Las validaciones de formato (campos no vacíos, formato de fecha, tipo
 * válido) las hace Bean Validation sobre el DTO antes de llegar aquí. Esta
 * clase se encarga de las validaciones de negocio (rango de fechas, ID
 * positivo) y de lanzar las excepciones que el {@code GlobalExceptionHandler}
 * traducirá a respuestas HTTP estructuradas.</p>
 */
@Service
public class ActividadServiceImpl implements IActividadService {

    /** Repositorio JPA inyectado por constructor. */
    private final ActividadRepository actividadRepository;

    /**
     * Constructor con inyección de dependencias.
     * @param actividadRepository repositorio inyectado por Spring.
     */
    public ActividadServiceImpl(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    /**
     * {@inheritDoc}
     * @param actividadDTO datos validados de la actividad.
     * @return DTO con el id generado por la BD.
     */
    @Override
    public ActividadDTO insertarActividad(ActividadDTO actividadDTO) {
        if (actividadDTO == null) {
            throw new IllegalArgumentException("DTO no puede ser nulo");
        }

        Actividad actividad = new Actividad();
        actividad.setTitulo(actividadDTO.getTitulo());
        actividad.setDescripcion(actividadDTO.getDescripcion());
        actividad.setFechaInicio(parsearFecha(actividadDTO.getFechaInicio()));
        actividad.setFechaTerminacion(parsearFecha(actividadDTO.getFechaTerminacion()));
        actividad.setTipoActividad(actividadDTO.getTipoActividad());
        actividad.setIdQuehacer(actividadDTO.getIdQuehacer());
        actividad.setIdTutor(actividadDTO.getIdTutor());
        actividad.setIdHijo(actividadDTO.getIdHijo());

        validarRangoFechas(actividad);

        return convertirEntidadADTO(actividadRepository.save(actividad));
    }

    /**
     * {@inheritDoc}
     * @return lista de DTOs (vacía si no hay actividades).
     */
    @Override
    public List<ActividadDTO> todasActividades() {
        return actividadRepository.findAll().stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * @param id identificador de la actividad.
     * @return Optional con el DTO si existe; vacío si no.
     */
    @Override
    public Optional<ActividadDTO> consultarPorID(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        return actividadRepository.findById(id).map(this::convertirEntidadADTO);
    }

    /**
     * {@inheritDoc}
     * @param id identificador de la actividad a modificar.
     * @param actividadDTO nuevos datos validados.
     * @return DTO con la actividad ya modificada.
     */
    @Override
    public ActividadDTO modificarActividad(Long id, ActividadDTO actividadDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        if (actividadDTO == null) {
            throw new IllegalArgumentException("DTO no puede ser nulo");
        }

        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ActividadNoEncontradaException(id));

        actividad.setTitulo(actividadDTO.getTitulo());
        actividad.setDescripcion(actividadDTO.getDescripcion());
        actividad.setFechaInicio(parsearFecha(actividadDTO.getFechaInicio()));
        actividad.setFechaTerminacion(parsearFecha(actividadDTO.getFechaTerminacion()));
        actividad.setTipoActividad(actividadDTO.getTipoActividad());
        actividad.setIdQuehacer(actividadDTO.getIdQuehacer());
        actividad.setIdTutor(actividadDTO.getIdTutor());
        actividad.setIdHijo(actividadDTO.getIdHijo());

        validarRangoFechas(actividad);

        return convertirEntidadADTO(actividadRepository.save(actividad));
    }

    /**
     * {@inheritDoc}
     * @param id identificador de la actividad a borrar.
     */
    @Override
    public void borrarActividad(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        if (!actividadRepository.existsById(id)) {
            throw new ActividadNoEncontradaException(id);
        }
        actividadRepository.deleteById(id);
    }

    /**
     * Convierte una entidad JPA a su representación DTO.
     * @param actividad entidad recuperada de la BD.
     * @return DTO con los mismos datos, o null si la entidad es null.
     */
    private ActividadDTO convertirEntidadADTO(Actividad actividad) {
        if (actividad == null) {
            return null;
        }
        ActividadDTO dto = new ActividadDTO();
        dto.setId(actividad.getId());
        dto.setTitulo(actividad.getTitulo());
        dto.setDescripcion(actividad.getDescripcion());
        if (actividad.getFechaInicio() != null) {
            dto.setFechaInicio(actividad.getFechaInicio().toString());
        }
        if (actividad.getFechaTerminacion() != null) {
            dto.setFechaTerminacion(actividad.getFechaTerminacion().toString());
        }
        dto.setTipoActividad(actividad.getTipoActividad());
        dto.setIdQuehacer(actividad.getIdQuehacer());
        dto.setIdTutor(actividad.getIdTutor());
        dto.setIdHijo(actividad.getIdHijo());
        return dto;
    }

    /**
     * Convierte una cadena ISO (YYYY-MM-DD) a LocalDate.
     * @param fecha cadena con formato YYYY-MM-DD; puede ser null o vacía.
     * @return LocalDate o null si la cadena estaba vacía.
     * @throws IllegalArgumentException si la fecha es inválida.
     */
    private LocalDate parsearFecha(String fecha) {
        if (fecha == null || fecha.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fecha);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }

    /**
     * Verifica que la fecha de terminación no sea anterior a la de inicio.
     * @param actividad actividad cuyas fechas se validan.
     * @throws IllegalArgumentException si las fechas son inconsistentes.
     */
    private void validarRangoFechas(Actividad actividad) {
        if (actividad.getFechaInicio() != null
                && actividad.getFechaTerminacion() != null
                && actividad.getFechaTerminacion().isBefore(actividad.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de terminación no puede ser anterior a la de inicio");
        }
    }
}
