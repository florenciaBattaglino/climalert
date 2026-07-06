package ar.edu.utn.ba.ddsi.climalert_tp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
  private Location location;
  private Current current;
}
