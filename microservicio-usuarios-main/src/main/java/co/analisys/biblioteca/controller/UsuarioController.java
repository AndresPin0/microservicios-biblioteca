package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Email;
import co.analisys.biblioteca.model.Usuario;
import co.analisys.biblioteca.model.UsuarioId;
import co.analisys.biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Administración de Usuarios", description = "Operaciones relacionadas con la gestión de cuentas de usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Consultar detalles de un usuario por ID",
            description = "Permite recuperar información de un usuario específico. Requiere permisos de administrador o gestor de usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos del usuario obtenidos con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso restringido por falta de autorización"),
            @ApiResponse(responseCode = "404", description = "No se encontró el usuario solicitado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER_MANAGER')")
    public Usuario obtenerUsuario(@PathVariable String id) {
        return usuarioService.obtenerUsuario(new UsuarioId(id));
    }

    @Operation(summary = "Modificar la dirección de correo de un usuario",
            description = "Actualiza el email asociado a una cuenta. Exclusivo para usuarios con rol de administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email modificado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Permisos insuficientes para realizar la acción"),
            @ApiResponse(responseCode = "400", description = "El formato del correo proporcionado no es válido")
    })
    @PutMapping("/{id}/email")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void cambiarEmail(@PathVariable String id, @RequestBody String nuevoEmail) {
        usuarioService.cambiarEmailUsuario(new UsuarioId(id), new Email(nuevoEmail));
    }
}