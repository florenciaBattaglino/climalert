package ar.edu.utn.ba.ddsi.climalert_tp.service;

import ar.edu.utn.ba.ddsi.climalert_tp.models.Alerta;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Clima;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Mail;
import ar.edu.utn.ba.ddsi.climalert_tp.models.NotificadorMail;
import ar.edu.utn.ba.ddsi.climalert_tp.models.repositories.alertaRepo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaService implements  alertaService_ {

  private final NotificadorMail notificador;
  private final alertaRepo alertaRepository;

  public AlertaService(NotificadorMail notificador, alertaRepo alertaRepository) {
    this.notificador = notificador;
    this.alertaRepository = alertaRepository;
  }

  @Override
  public void alertarClima(Clima clima) {
    int minutosDesdeprimerAlerta = 60;

    Optional<Alerta> ultimaAlerta = alertaRepository.findUltimaAlerta();

    if (ultimaAlerta.isPresent()) {
      Alerta ultAlerta = ultimaAlerta.get();

      long minutosTranscurridos = Duration.between(
          ultAlerta.getFechaEnvio(),
          LocalDateTime.now()
      ).toMinutes();

      if (minutosTranscurridos < minutosDesdeprimerAlerta) {
        System.out.println("No mando mail pasó menos de 1h");
        return;
      }
    }

    Mail mail = armarMail(clima);
    notificador.enviarMailAlerta(mail);

    Alerta nuevaAlerta = new Alerta();
    nuevaAlerta.setClimaId(clima.getId());
    nuevaAlerta.setFechaEnvio(LocalDateTime.now());
    alertaRepository.save(nuevaAlerta);
  }


  private Mail armarMail(Clima clima) {
    List<String> destinatarios = List.of(
        "admin@clima.com",
        "emergencias@clima.com",
        "meteorologia@clima.com"
    );
    String asunto = "ALERTA METEOROLÓGICA DETECTADA";
    String cuerpo = String.format(
        "Se han detectado condiciones climáticas peligrosas.\n\n" +
            "Detalle completo del clima:\n" +
            "- Ciudad: %d\n" +
            "- Temperatura: %.2f°C (Límite: >35°C)\n" +
            "- Humedad: %d (Límite: >60%%)\n" +
            "- Fecha y Hora: %s\n",
        clima.getNombreCiudad(),
        clima.getTemp_celcius(),
        clima.getHumedad(),
        clima.getFechayhora()
    );

    Mail mail = new Mail();
    mail.setDestinatario(destinatarios);
    mail.setAsunto(asunto);
    mail.setContenido(cuerpo);
    return mail;
  }
}

