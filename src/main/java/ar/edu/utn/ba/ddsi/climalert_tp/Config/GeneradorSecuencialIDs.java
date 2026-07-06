package ar.edu.utn.ba.ddsi.climalert_tp.Config;

public class GeneradorSecuencialIDs {
  private long siguiente;

  public GeneradorSecuencialIDs() {
    this(1L);
  }

  public GeneradorSecuencialIDs(long valorInicial) {
    this.siguiente = valorInicial;
  }

  public long siguiente() {
    return siguiente++;
  }
}
