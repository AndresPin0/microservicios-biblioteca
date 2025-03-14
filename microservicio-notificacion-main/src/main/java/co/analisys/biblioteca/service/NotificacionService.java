package co.analisys.biblioteca.service;

import co.analisys.biblioteca.dto.NotificacionDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class NotificacionService {
    @KafkaListener(topics = "devolucion-libro", groupId = "notificacion-group")
    public void enviarNotificacion(NotificacionDTO notificacion) {
// Simular envío de notificación
        System.out.println("Notificación enviada a " + notificacion.getUsuarioId() + ": " +
                notificacion.getMensaje());
    }
}