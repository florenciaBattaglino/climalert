package ar.edu.utn.ba.ddsi.climalert_tp.models.repositories;

import ar.edu.utn.ba.ddsi.climalert_tp.Config.GeneradorSecuencialIDs;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Alerta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class alertaRepo {
  private final List<Alerta> alertas = new ArrayList<>();
  private final GeneradorSecuencialIDs generadorID = new GeneradorSecuencialIDs();;


  public Optional<Alerta> findById(Long id) {
    return alertas.stream().filter(c -> c.getId().equals(id)).findFirst();
  }


  public List<Alerta> findAll() {
    return new ArrayList<>(alertas);
  }

  public Alerta save(Alerta alerta) {
    if (alerta.getId() == null) {
      alerta.setId(generadorID.siguiente());
    } else {
      alertas.removeIf(d -> d.getId().equals(alerta.getId()));
    }
    alertas.add(alerta);
    return alerta;
  }

  public Optional<Alerta> findUltimaAlerta() {
    if (alertas.isEmpty()) {
      return Optional.empty();
    }
    Alerta ultimo = alertas.getLast();
    return Optional.of(ultimo);
  }
}
