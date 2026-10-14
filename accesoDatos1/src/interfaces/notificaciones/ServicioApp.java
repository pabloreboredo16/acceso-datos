package interfaces.notificaciones;

public class ServicioApp implements ServicioNotificacion {

    @Override
    public boolean validarDestinatario(String destinatario) {
        return destinatario != null; 
    }

    @Override
    public void enviarMensaje(String destinatario, String mensaje) {
        System.out.println("NOTIFICACION enviada al usuario " + destinatario + ": " + "'" + mensaje + "'");
    }
}