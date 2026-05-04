package co.edu.udistrital.backend.controller;

import co.edu.udistrital.backend.dto.ActividadDTO;
import co.edu.udistrital.backend.exception.ActividadNoEncontradaException;
import co.edu.udistrital.backend.service.IActividadService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador MVC con vistas Thymeleaf.
 *
 * <p>Expone el CRUD completo bajo {@code /inicio/*} para poder probar el
 * backend desde el navegador. DELETE va como GET porque el navegador no
 * puede emitir DELETE sin JS.</p>
 *
 * <p>Los errores que ocurren durante las operaciones se propagan a la
 * vista {@code error.html} con un mensaje legible, en lugar de mostrar el
 * stacktrace de Spring, cumpliendo la indicación del profesor de que los
 * errores del backend lleguen al frontend.</p>
 */
@Controller
@RequestMapping("/")
public class InicioController {

    /** Servicio de lógica de negocio. */
    private final IActividadService actividadService;

    /**
     * Constructor con inyección de dependencias.
     * @param actividadService servicio inyectado por Spring.
     */
    public InicioController(IActividadService actividadService) {
        this.actividadService = actividadService;
    }

    /**
     * GET / - página de bienvenida con la lista de actividades.
     * @param model modelo de la vista.
     * @return nombre lógico de la plantilla {@code index}.
     */
    @GetMapping
    public String inicio(Model model) {
        List<ActividadDTO> actividades = actividadService.todasActividades();
        model.addAttribute("actividades", actividades);
        return "index";
    }

    /**
     * GET /listar - alias histórico que reenvía al listado actual.
     * @param model modelo de la vista.
     * @return nombre lógico de la plantilla {@code listar}.
     */
    @GetMapping("/listar")
    public String listarLegacy(Model model) {
        return listarActividadesCrud(model);
    }

    /**
     * GET /inicio/listar - tabla con acciones Editar/Eliminar.
     * @param model modelo de la vista.
     * @return nombre lógico de la plantilla {@code listar}.
     */
    @GetMapping("/inicio/listar")
    public String listarActividadesCrud(Model model) {
        model.addAttribute("actividades", actividadService.todasActividades());
        return "listar";
    }

    /**
     * GET /inicio/ver/{id} - detalle de una actividad concreta.
     * @param id identificador de la actividad.
     * @param model modelo de la vista.
     * @return plantilla {@code detalle}.
     */
    @GetMapping("/inicio/ver/{id}")
    public String verActividad(@PathVariable Long id, Model model) {
        ActividadDTO actividad = actividadService.consultarPorID(id)
                .orElseThrow(() -> new ActividadNoEncontradaException(id));
        model.addAttribute("actividad", actividad);
        return "detalle";
    }

    /**
     * GET /inicio/formulario - formulario vacío para crear una actividad.
     * @param model modelo de la vista.
     * @return plantilla {@code formulario}.
     */
    @GetMapping("/inicio/formulario")
    public String formularioNuevo(Model model) {
        model.addAttribute("actividad", new ActividadDTO());
        return "formulario";
    }

    /**
     * GET /inicio/editar/{id} - formulario precargado para editar.
     * @param id identificador de la actividad.
     * @param model modelo de la vista.
     * @return plantilla {@code formulario}.
     */
    @GetMapping("/inicio/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        ActividadDTO actividad = actividadService.consultarPorID(id)
                .orElseThrow(() -> new ActividadNoEncontradaException(id));
        model.addAttribute("actividad", actividad);
        return "formulario";
    }

    /**
     * POST /inicio/guardar - inserta y vuelve al listado.
     *
     * <p>{@code @Valid} dispara las validaciones del DTO; si fallan,
     * regresa al formulario mostrando los errores junto a cada campo.</p>
     *
     * @param actividad datos del formulario.
     * @param resultado resultado de la validación.
     * @return redirección al listado o vuelta al formulario con errores.
     */
    @PostMapping("/inicio/guardar")
    public String guardar(@Valid @ModelAttribute("actividad") ActividadDTO actividad,
                          BindingResult resultado) {
        if (resultado.hasErrors()) {
            return "formulario";
        }
        actividadService.insertarActividad(actividad);
        return "redirect:/inicio/listar";
    }

    /**
     * POST /inicio/actualizar/{id} - actualiza y vuelve al listado.
     * @param id identificador de la actividad.
     * @param actividad datos del formulario.
     * @param resultado resultado de la validación.
     * @return redirección al listado o vuelta al formulario con errores.
     */
    @PostMapping("/inicio/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("actividad") ActividadDTO actividad,
                             BindingResult resultado) {
        if (resultado.hasErrors()) {
            actividad.setId(id);
            return "formulario";
        }
        actividadService.modificarActividad(id, actividad);
        return "redirect:/inicio/listar";
    }

    /**
     * GET /inicio/eliminar/{id} - borra una actividad y vuelve al listado.
     *
     * <p>Si el id no existe, el handler local convierte la excepción en una
     * vista de error amigable, en lugar de tragársela en silencio.</p>
     *
     * @param id identificador de la actividad a eliminar.
     * @return redirección al listado.
     */
    @GetMapping("/inicio/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        actividadService.borrarActividad(id);
        return "redirect:/inicio/listar";
    }

    // ---------------------------------------------------------------
    // Manejo local de excepciones para las vistas Thymeleaf
    // ---------------------------------------------------------------

    /**
     * Maneja {@link ActividadNoEncontradaException} en las vistas y muestra
     * la plantilla {@code error.html} con el mensaje del backend.
     * @param ex excepción capturada.
     * @param model modelo de la vista.
     * @return plantilla {@code error}.
     */
    @ExceptionHandler(ActividadNoEncontradaException.class)
    public String manejarNoEncontradaEnVista(ActividadNoEncontradaException ex, Model model) {
        model.addAttribute("titulo", "Actividad no encontrada");
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("codigo", "404");
        return "error";
    }

    /**
     * Maneja {@link IllegalArgumentException} en las vistas (datos inválidos).
     * @param ex excepción capturada.
     * @param model modelo de la vista.
     * @return plantilla {@code error}.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarDatosInvalidosEnVista(IllegalArgumentException ex, Model model) {
        model.addAttribute("titulo", "Datos inválidos");
        model.addAttribute("mensaje", ex.getMessage());
        model.addAttribute("codigo", "400");
        return "error";
    }
}
