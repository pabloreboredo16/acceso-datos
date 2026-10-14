package interfaces.notificaciones;

public class ServicioSMS implements ServicioNotificacion {

    @Override
    public boolean validarDestinatario(String destinatario) {
        return destinatario != null; 
    }

    @Override
    public void enviarMensaje(String destinatario, String mensaje) {
        System.out.println("SMS Enviado al número " + destinatario + ": " + "'" + mensaje + "'");
    }
}