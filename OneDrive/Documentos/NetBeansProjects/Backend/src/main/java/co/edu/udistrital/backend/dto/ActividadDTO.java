package co.edu.udistrital.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO de Actividad para transportar datos entre el cliente y el backend.
 *
 * <p>Esta clase recibe las peticiones HTTP del frontend (POST y PUT) y, por
 * petición del profesor, lleva las anotaciones de Bean Validation que
 * verifican los datos antes de que lleguen a la capa de servicio. Si alguna
 * validación falla, el {@code GlobalExceptionHandler} convierte el error
 * en un {@code ErrorResponse} JSON que el frontend muestra en pantalla.</p>
 *
 * <p>El campo {@code id} es nulo al crear (lo asigna la BD) y obligatorio
 * al recibir respuestas. Por eso no lleva {@code @NotNull}.</p>
 */
public class ActividadDTO {

    /** Identificador asignado por la BD; nulo al crear. */
    private Long id;

    /** Título corto y descriptivo de la actividad. */
    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 120, message = "El título no puede exceder 120 caracteres")
    private String titulo;

    /** Descripción detallada de la actividad. */
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    /** Fecha de inicio en formato YYYY-MM-DD. */
    @NotBlank(message = "La fecha de inicio es obligatoria")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
             message = "La fecha de inicio debe tener formato YYYY-MM-DD")
    private String fechaInicio;

    /** Fecha de terminación en formato YYYY-MM-DD. */
    @NotBlank(message = "La fecha de terminación es obligatoria")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}",
             message = "La fecha de terminación debe tener formato YYYY-MM-DD")
    private String fechaTerminacion;

    /**
     * Tipo de actividad. Debe ser uno de los cuatro valores del enunciado:
     * Física, Acompañamiento, Supervisión, Creatividad.
     */
    @NotBlank(message = "El tipo de actividad es obligatorio")
    @Pattern(regexp = "F[ií]sica|Acompa[ñn]amiento|Supervisi[oó]n|Creatividad",
             message = "El tipo debe ser: Física, Acompañamiento, Supervisión o Creatividad")
    private String tipoActividad;

    /** Identificador del quehacer del hogar al que pertenece la actividad. */
    @NotNull(message = "El id del quehacer es obligatorio")
    @Positive(message = "El id del quehacer debe ser un número positivo")
    private Long idQuehacer;

    /** Identificador del tutor que asigna la actividad. */
    @NotNull(message = "El id del tutor es obligatorio")
    @Positive(message = "El id del tutor debe ser un número positivo")
    private Long idTutor;

    /** Identificador del hijo a cargo de ejecutar la actividad. */
    @NotNull(message = "El id del hijo es obligatorio")
    @Positive(message = "El id del hijo debe ser un número positivo")
    private Long idHijo;

    // ------------------- Setters y Getters -------------------

    /** @return identificador único de la actividad. */
    public Long getId() {
        return id;
    }

    /** @param id identificador a asignar. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return título de la actividad. */
    public String getTitulo() {
        return titulo;
    }

    /** @param titulo nuevo título. */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /** @return descripción detallada. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @param descripcion nueva descripción. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return fecha de inicio en formato YYYY-MM-DD. */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /** @param fechaInicio nueva fecha de inicio. */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /** @return fecha de terminación en formato YYYY-MM-DD. */
    public String getFechaTerminacion() {
        return fechaTerminacion;
    }

    /** @param fechaTerminacion nueva fecha de terminación. */
    public void setFechaTerminacion(String fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    /** @return tipo de actividad. */
    public String getTipoActividad() {
        return tipoActividad;
    }

    /** @param tipoActividad nuevo tipo. */
    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    /** @return identificador del quehacer asociado. */
    public Long getIdQuehacer() {
        return idQuehacer;
    }

    /** @param idQuehacer nuevo id del quehacer. */
    public void setIdQuehacer(Long idQuehacer) {
        this.idQuehacer = idQuehacer;
    }

    /** @return identificador del tutor que asigna. */
    public Long getIdTutor() {
        return idTutor;
    }

    /** @param idTutor nuevo id del tutor. */
    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    /** @return identificador del hijo a cargo. */
    public Long getIdHijo() {
        return idHijo;
    }

    /** @param idHijo nuevo id del hijo. */
    public void setIdHijo(Long idHijo) {
        this.idHijo = idHijo;
    }
}
