package ar.edu.utn.ba.ddsi.climalert_tp.schedulers;

import ar.edu.utn.ba.ddsi.climalert_tp.models.Clima;
import ar.edu.utn.ba.ddsi.climalert_tp.models.repositories.climaRepo;
import ar.edu.utn.ba.ddsi.climalert_tp.service.ClimaService_;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClimaScheduler {
  private final ClimaService_ climaService;
  private final climaRepo climaRepository;


  public ClimaScheduler(ClimaService_ climaService, climaRepo climaRepository) {
    this.climaService = climaService;
    this.climaRepository = climaRepository;
  }

 //cada 5 mins
  @Scheduled(fixedRate = 300000)
  public void obtenerClima(){
    Clima climaActual = climaService.obtenerClima();
    climaActual.setFechayhora(LocalDateTime.now());
    climaRepository.save(climaActual);
  }
}
