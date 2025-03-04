package co.analisys.biblioteca.service;

import co.analisys.biblioteca.dto.NotificacionDTO;
import org.springframework.web.client.RestTemplate;

public class NotificacionService {
    private final RestTemplate restTemplate;
    public NotificacionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void enviarNotificacion(NotificacionDTO notificacion) {
        String email = restTemplate.getForObject(
                "http://usuario-service/usuarios/" + notificacion.getUsuarioId() + "/email", String.class);
// Lógica para enviar el email
        System.out.println("Notificación enviada a " + email + ": " + notificacion.getMensaje());
    }
}