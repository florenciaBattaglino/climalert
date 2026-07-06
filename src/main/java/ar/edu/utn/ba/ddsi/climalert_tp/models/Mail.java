package ar.edu.utn.ba.ddsi.climalert_tp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
  private long id;
  private List<String> destinatario;
  private String remitente;
  private String asunto;
  private String contenido;




}
