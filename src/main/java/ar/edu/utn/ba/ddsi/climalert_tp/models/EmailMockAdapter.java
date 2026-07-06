package ar.edu.utn.ba.ddsi.climalert_tp.models;

public class EmailMockAdapter implements NotificadorMail{
  @Override
  public void enviarMailAlerta(Mail mail) {
    System.out.println("Mock de envío de correo:");
    System.out.println("Destinatarios: " + String.join(", ", mail.getDestinatario()));
    System.out.println("Asunto: " + mail.getAsunto());
    System.out.println("Cuerpo:\n" + mail.getContenido());

  }
}
