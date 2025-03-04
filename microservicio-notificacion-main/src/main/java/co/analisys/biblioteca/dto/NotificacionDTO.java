package co.analisys.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private String usuarioId;
    private String mensaje;
}


