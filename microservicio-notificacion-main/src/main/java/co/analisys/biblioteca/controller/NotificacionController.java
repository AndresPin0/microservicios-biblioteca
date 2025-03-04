package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.dto.NotificacionDTO;
import co.analisys.biblioteca.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificar")
@Tag(name = "Notificaciones", description = "Operaciones para envío de notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Operation(summary = "Transmitir un mensaje de notificación",
            description = "Exclusivo para usuarios con el rol ROLE_NOTIFICATION_MANAGER, permite emitir notificaciones.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje de notificación procesado y enviado con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado por falta de permisos"),
            @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados en la solicitud")
    })
    @PostMapping("/enviar")
    @PreAuthorize("hasRole('ROLE_NOTIFICATION_MANAGER')")
    public void enviarNotificacion(@RequestBody NotificacionDTO notificacion) {
        notificacionService.enviarNotificacion(notificacion);
    }
}
