package co.edu.udistrital.backend.exception;

/**
 * Excepción lanzada cuando se intenta operar sobre una Actividad que no existe.
 * El GlobalExceptionHandler la traduce a HTTP 404.
 */
public class ActividadNoEncontradaException extends RuntimeException {

    public ActividadNoEncontradaException(Long id) {
        super("Actividad con id " + id + " no encontrada.");
    }
}
