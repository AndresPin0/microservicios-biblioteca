package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Libro;
import co.analisys.biblioteca.model.LibroId;
import co.analisys.biblioteca.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gestión de Catálogo", description = "Endpoints para administrar y consultar el catálogo de la biblioteca")
@RestController
@RequestMapping("/libros")
public class CatalogoController {

    private final CatalogoService catalogoService;

    @Autowired
    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(summary = "Consultar información de un libro por su ID",
            description = "Devuelve los detalles de un libro específico. Requiere permisos de administrador o visualizador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles del libro recuperados con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso restringido por falta de permisos"),
            @ApiResponse(responseCode = "404", description = "No se encontró un libro con ese ID")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CATALOG_MANAGER', 'ROLE_VIEWER')")
    public Libro obtenerLibro(@PathVariable String id) {
        return catalogoService.obtenerLibro(new LibroId(id));
    }

    @Operation(summary = "Comprobar si un libro está disponible",
            description = "Indica si un libro puede ser prestado actualmente. Acceso para administradores y visualizadores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de disponibilidad devuelto correctamente"),
            @ApiResponse(responseCode = "403", description = "Permisos insuficientes para realizar la consulta"),
            @ApiResponse(responseCode = "404", description = "El libro solicitado no existe en el catálogo")
    })
    @GetMapping("/{id}/disponible")
    @PreAuthorize("hasAnyRole('ROLE_CATALOG_MANAGER', 'ROLE_VIEWER')")
    public boolean isLibroDisponible(@PathVariable String id) {
        Libro libro = catalogoService.obtenerLibro(new LibroId(id));
        return libro != null && libro.isDisponible();
    }

    @Operation(summary = "Modificar el estado de disponibilidad de un libro",
            description = "Actualiza si un libro está disponible o no. Exclusivo para administradores del catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del libro modificado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado por falta de autorización"),
            @ApiResponse(responseCode = "400", description = "Error en los datos enviados para la actualización")
    })
    @PutMapping("/{id}/disponibilidad")
    @PreAuthorize("hasRole('ROLE_CATALOG_MANAGER')")
    public void actualizarDisponibilidad(@PathVariable String id, @RequestBody boolean disponible) {
        catalogoService.actualizarDisponibilidad(new LibroId(id), disponible);
    }

    @Operation(summary = "Realizar una búsqueda de libros en el catálogo",
            description = "Permite encontrar libros según un criterio de búsqueda. Disponible para administradores y visualizadores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada, lista de libros devuelta"),
            @ApiResponse(responseCode = "403", description = "Solicitud rechazada por falta de permisos")
    })
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ROLE_CATALOG_MANAGER', 'ROLE_VIEWER')")
    public List<Libro> buscarLibros(@RequestParam String criterio) {
        return catalogoService.buscarLibros(criterio);
    }
}