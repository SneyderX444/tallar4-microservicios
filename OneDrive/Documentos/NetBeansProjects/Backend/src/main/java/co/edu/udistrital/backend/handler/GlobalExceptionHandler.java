package co.edu.udistrital.backend.handler;

import co.edu.udistrital.backend.exception.ActividadNoEncontradaException;
import co.edu.udistrital.backend.exception.ErrorResponse;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones.
 *
 * <p>Traduce las excepciones lanzadas por el backend a respuestas HTTP con
 * cuerpo {@link ErrorResponse}. Esto cumple lo solicitado por el profesor:
 * <em>"el mensaje de los errores lo producen las clases del backend...
 * deberia generar un error que vaya del backend al frontend para que salga
 * en la pagina"</em>.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * IllegalArgumentException - 400 Bad Request.
     * <p>Se lanza desde el servicio cuando se infringen reglas de negocio
     * (ID nulo, fecha mal formada, fechas en orden incorrecto).</p>
     * @param e excepción capturada.
     * @return respuesta 400 con detalle del error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarIllegalArgument(IllegalArgumentException e) {
        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), "BAD_REQUEST", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    /**
     * ActividadNoEncontradaException - 404 Not Found.
     * @param e excepción capturada.
     * @return respuesta 404 con detalle del error.
     */
    @ExceptionHandler(ActividadNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> manejarActividadNoEncontrada(ActividadNoEncontradaException e) {
        ErrorResponse err = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), e.getMessage(), "NOT_FOUND", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    /**
     * MethodArgumentNotValidException - 400 Bad Request.
     *
     * <p>Lanzada por Spring cuando alguna anotación de Bean Validation del
     * DTO falla (campo vacío, fuera de rango, formato incorrecto). Se
     * recolectan TODOS los errores de campo en un único mensaje para que
     * el frontend pueda mostrarlos de un vistazo.</p>
     *
     * @param e excepción capturada.
     * @return respuesta 400 con la lista de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException e) {
        String detalles = e.getBindingResult().getFieldErrors().stream()
                .map(this::formatearErrorCampo)
                .collect(Collectors.joining("; "));
        ErrorResponse err = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Datos inválidos: " + detalles,
                "VALIDATION_ERROR",
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    /**
     * Da formato al error de un campo concreto para incluirlo en el mensaje.
     * @param error error de campo reportado por Bean Validation.
     * @return cadena del estilo "titulo: El título no puede estar vacío".
     */
    private String formatearErrorCampo(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
