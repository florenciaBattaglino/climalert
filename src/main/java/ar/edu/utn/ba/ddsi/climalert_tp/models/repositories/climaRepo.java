package ar.edu.utn.ba.ddsi.climalert_tp.models.repositories;

import ar.edu.utn.ba.ddsi.climalert_tp.Config.GeneradorSecuencialIDs;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Clima;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class climaRepo {
  private final List<Clima> climas = new ArrayList<>();
  private final GeneradorSecuencialIDs generadorID = new GeneradorSecuencialIDs();;


  public Optional<Clima> findById(Long id) {
    return climas.stream().filter(c -> c.getId().equals(id)).findFirst();
  }


  public List<Clima> findAll() {
    return new ArrayList<>(climas);
  }

  public Clima save(Clima clima) {
    if (clima.getId() == null) {
      clima.setId(generadorID.siguiente());
    } else {
      climas.removeIf(d -> d.getId().equals(clima.getId()));
    }
    climas.add(clima);
    return clima;
  }

  public Optional<Clima> findUltimoClima() {
    if (climas.isEmpty()) {
      return Optional.empty();
    }
    Clima ultimo = climas.getLast();
    return Optional.of(ultimo);
  }
}
