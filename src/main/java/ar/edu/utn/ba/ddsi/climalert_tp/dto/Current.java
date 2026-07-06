package ar.edu.utn.ba.ddsi.climalert_tp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Current {
  private double temp_c;
  private double temp_f;
  private int humidity;

}
