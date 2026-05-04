package co.edu.udistrital.backend.exception;

import java.time.LocalDateTime;

/**
 * Cuerpo JSON estándar que devuelve el GlobalExceptionHandler ante errores.
 */
public class ErrorResponse {

    private int status;
    private String mensaje;
    private String error;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String mensaje, String error, LocalDateTime timestamp) {
        this.status = status;
        this.mensaje = mensaje;
        this.error = error;
        this.timestamp = timestamp;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
