package co.edu.udistrital.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa una actividad del hogar asignada por un tutor a un hijo.
 * Mapea la tabla "actividades" en H2.
 */
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaTerminacion;

    @Column(nullable = false)
    private String tipoActividad;

    @Column(nullable = false)
    private Long idQuehacer;

    @Column(nullable = false)
    private Long idTutor;

    @Column(nullable = false)
    private Long idHijo;

    /** Constructor sin argumentos requerido por JPA. */
    public Actividad() {
    }

    /**
     * Constructor con todos los campos.
     * @param id identificador.
     * @param titulo título.
     * @param descripcion descripción.
     * @param fechaInicio fecha de inicio.
     * @param fechaTerminacion fecha de terminación.
     * @param tipoActividad tipo.
     * @param idQuehacer id del quehacer.
     * @param idTutor id del tutor.
     * @param idHijo id del hijo.
     */
    public Actividad(Long id, String titulo, String descripcion,
                     LocalDate fechaInicio, LocalDate fechaTerminacion,
                     String tipoActividad, Long idQuehacer, Long idTutor, Long idHijo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaTerminacion = fechaTerminacion;
        this.tipoActividad = tipoActividad;
        this.idQuehacer = idQuehacer;
        this.idTutor = idTutor;
        this.idHijo = idHijo;
    }

    // ---------- Getters y Setters ----------

    /** @return id de la actividad. */
    public Long getId() { return id; }
    /** @param id nuevo id. */
    public void setId(Long id) { this.id = id; }

    /** @return título. */
    public String getTitulo() { return titulo; }
    /** @param titulo nuevo título. */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /** @return descripción. */
    public String getDescripcion() { return descripcion; }
    /** @param descripcion nueva descripción. */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return fecha de inicio. */
    public LocalDate getFechaInicio() { return fechaInicio; }
    /** @param fechaInicio nueva fecha de inicio. */
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    /** @return fecha de terminación. */
    public LocalDate getFechaTerminacion() { return fechaTerminacion; }
    /** @param fechaTerminacion nueva fecha de terminación. */
    public void setFechaTerminacion(LocalDate fechaTerminacion) { this.fechaTerminacion = fechaTerminacion; }

    /** @return tipo de actividad. */
    public String getTipoActividad() { return tipoActividad; }
    /** @param tipoActividad nuevo tipo. */
    public void setTipoActividad(String tipoActividad) { this.tipoActividad = tipoActividad; }

    /** @return id del quehacer. */
    public Long getIdQuehacer() { return idQuehacer; }
    /** @param idQuehacer nuevo id del quehacer. */
    public void setIdQuehacer(Long idQuehacer) { this.idQuehacer = idQuehacer; }

    /** @return id del tutor. */
    public Long getIdTutor() { return idTutor; }
    /** @param idTutor nuevo id del tutor. */
    public void setIdTutor(Long idTutor) { this.idTutor = idTutor; }

    /** @return id del hijo. */
    public Long getIdHijo() { return idHijo; }
    /** @param idHijo nuevo id del hijo. */
    public void setIdHijo(Long idHijo) { this.idHijo = idHijo; }
}
