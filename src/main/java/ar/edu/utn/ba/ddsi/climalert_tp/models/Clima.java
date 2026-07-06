package ar.edu.utn.ba.ddsi.climalert_tp.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class Clima {
  private Long id;
  private String nombreCiudad;
  private String region;
  private String pais;
  private double temp_celcius;
  private double temp_f;
  private int humedad;
  private boolean procesado = false;
  private LocalDateTime fechayhora;

  public boolean esCondicionCritica() {
    return this.temp_celcius > 35.0 && this.humedad > 60.0;
  }


}
