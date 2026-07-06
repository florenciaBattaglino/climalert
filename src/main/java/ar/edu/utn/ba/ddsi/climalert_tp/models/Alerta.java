package ar.edu.utn.ba.ddsi.climalert_tp.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Alerta {
    private Long id;
    private Long climaId;
    private LocalDateTime fechaEnvio;
}
