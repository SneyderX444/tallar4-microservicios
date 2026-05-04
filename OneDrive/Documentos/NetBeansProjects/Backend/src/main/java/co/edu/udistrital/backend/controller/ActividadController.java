package co.edu.udistrital.backend.controller;

import co.edu.udistrital.backend.dto.ActividadDTO;
import co.edu.udistrital.backend.exception.ActividadNoEncontradaException;
import co.edu.udistrital.backend.service.IActividadService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST de Actividad.
 *
 * <p>Expone el CRUD bajo {@code /actividad} y delega la lógica en
 * {@link IActividadService}. CORS abierto para GET/POST/PUT/DELETE. El
 * manejo de excepciones se delega al {@code GlobalExceptionHandler}.</p>
 *
 * <p>La anotación {@code @Valid} sobre los parámetros marcados con
 * {@code @RequestBody} dispara la validación de Bean Validation declarada
 * en {@code ActividadDTO}; si alguna anotación falla, Spring lanza
 * {@code MethodArgumentNotValidException} y el handler global la convierte
 * en una respuesta 400 con detalle de los campos inválidos.</p>
 */
@RestController
@RequestMapping("/actividad")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ActividadController {

    /** Servicio de lógica de negocio inyectado por constructor. */
    private final IActividadService actividadService;

    /**
     * Constructor con inyección de dependencias.
     * @param actividadService servicio inyectado por Spring.
     */
    public ActividadController(IActividadService actividadService) {
        this.actividadService = actividadService;
    }

    /**
     * POST /actividad/insertar - crea una actividad.
     * @param actividadDTO datos validados del nuevo registro.
     * @return 201 Created con la actividad creada y su id.
     */
    @RequestMapping(value = "/insertar", method = RequestMethod.POST)
    public ResponseEntity<ActividadDTO> insertarActividad(@Valid @RequestBody ActividadDTO actividadDTO) {
        ActividadDTO creada = actividadService.insertarActividad(actividadDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /**
     * GET /actividad/consultar - lista todas las actividades.
     * @return 200 OK con la lista (puede estar vacía).
     */
    @RequestMapping(value = "/consultar", method = RequestMethod.GET)
    public ResponseEntity<List<ActividadDTO>> consultarActividades() {
        return ResponseEntity.ok(actividadService.todasActividades());
    }

    /**
     * GET /actividad/{id} - consulta por id.
     * @param id identificador de la actividad.
     * @return 200 OK con la actividad, o 404 si no existe.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ActividadDTO> consultarActividadPorID(@PathVariable Long id) {
        ActividadDTO dto = actividadService.consultarPorID(id)
                .orElseThrow(() -> new ActividadNoEncontradaException(id));
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /actividad/{id} - actualiza una actividad existente.
     * @param id identificador de la actividad a modificar.
     * @param actividadDTO nuevos datos validados.
     * @return 200 OK con la actividad ya modificada.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ActividadDTO> modificarActividad(@PathVariable Long id, @Valid @RequestBody ActividadDTO actividadDTO) {
        return ResponseEntity.ok(actividadService.modificarActividad(id, actividadDTO));
    }

    /**
     * DELETE /actividad/{id} - elimina una actividad.
     * @param id identificador de la actividad a eliminar.
     * @return 204 No Content si se eliminó con éxito.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> borrarActividad(@PathVariable Long id) {
        actividadService.borrarActividad(id);
        return ResponseEntity.noContent().build();
    }
}
