package ar.edu.utn.ba.ddsi.climalert_tp.schedulers;

import ar.edu.utn.ba.ddsi.climalert_tp.models.Clima;
import ar.edu.utn.ba.ddsi.climalert_tp.models.repositories.climaRepo;
import ar.edu.utn.ba.ddsi.climalert_tp.service.alertaService_;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlertaScheduler {
  private final alertaService_ alertaService;
  private final climaRepo climaRepository;

  public AlertaScheduler(alertaService_ alertaService, climaRepo climaRepository) {
    this.alertaService = alertaService;
    this.climaRepository = climaRepository;
  }

  @Scheduled(fixedRate = 60000)
  public void alertarCadaMin(){

    Optional<Clima> ultimoclima = climaRepository.findUltimoClima();

    if(ultimoclima.isPresent()){
      Clima ultimocl = ultimoclima.get();

      if (ultimocl.esCondicionCritica()) {
          alertaService.alertarClima(ultimocl);
      }
      else {
        System.out.println("Ultimo clima verificado");
      }
    }
  }
}
