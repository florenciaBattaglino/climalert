package ar.edu.utn.ba.ddsi.climalert_tp;

import ar.edu.utn.ba.ddsi.climalert_tp.models.NotificadorMail;
import ar.edu.utn.ba.ddsi.climalert_tp.models.repositories.alertaRepo;
import ar.edu.utn.ba.ddsi.climalert_tp.service.AlertaService;
import org.junit.jupiter.api.Test;


import ar.edu.utn.ba.ddsi.climalert_tp.models.Alerta;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Clima;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Mail;

import org.junit.jupiter.api.BeforeEach;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

//Test generados
class AlertaServiceTest {

  private NotificadorMail notificadorEmailMock;
  private alertaRepo alertaRepositoryMock;
  private AlertaService alertaService;

  @BeforeEach
  void setUp() {

    notificadorEmailMock = mock(NotificadorMail.class);
    alertaRepositoryMock = mock(alertaRepo.class);

    alertaService = new AlertaService(notificadorEmailMock, alertaRepositoryMock);
  }

  @Test
  void generarAlerta_DeberiaEnviarMailYGuardarAlerta_CuandoNoHayAlertasPrevias() {

    Clima climaCritico = new Clima();
    climaCritico.setId(1L);
    climaCritico.setTemp_celcius(38.0);
    climaCritico.setHumedad(65);
    climaCritico.setFechayhora(LocalDateTime.now());


    when(alertaRepositoryMock.findUltimaAlerta()).thenReturn(Optional.empty());
    alertaService.alertarClima(climaCritico);
    verify(notificadorEmailMock, times(1)).enviarMailAlerta(any(Mail.class));

    verify(alertaRepositoryMock, times(1)).save(any(Alerta.class));
  }

  @Test
  void generarAlerta_NoDeberiaEnviarMail_CuandoEstaEnPeriodoDeCoolDown() {

    Clima climaCritico = new Clima();
    climaCritico.setId(2L);
    climaCritico.setTemp_celcius(39.0);
    climaCritico.setHumedad(70);

    Alerta alertaPrevia = new Alerta();
    alertaPrevia.setId(100L);
    alertaPrevia.setFechaEnvio(LocalDateTime.now().minusMinutes(10));

    when(alertaRepositoryMock.findUltimaAlerta()).thenReturn(Optional.of(alertaPrevia));


    alertaService.alertarClima(climaCritico);

    verify(notificadorEmailMock, never()).enviarMailAlerta(any(Mail.class));
    verify(alertaRepositoryMock, never()).save(any(Alerta.class));
  }

  @Test
  void generarAlerta_DeberiaEnviarMail_CuandoYaPasoElPeriodoDeCoolDown() {

    Clima climaCritico = new Clima();
    climaCritico.setId(3L);
    climaCritico.setTemp_celcius(36.5);
    climaCritico.setHumedad(62);

    Alerta alertaVieja = new Alerta();
    alertaVieja.setId(99L);
    alertaVieja.setFechaEnvio(LocalDateTime.now().minusMinutes(75));

    when(alertaRepositoryMock.findUltimaAlerta()).thenReturn(Optional.of(alertaVieja));

    alertaService.alertarClima(climaCritico);


    verify(notificadorEmailMock, times(1)).enviarMailAlerta(any(Mail.class));
    verify(alertaRepositoryMock, times(1)).save(any(Alerta.class));
  }
}
