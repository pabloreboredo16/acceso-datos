package interfaces.notificaciones;

public class GestorNotificaciones {

    private ServicioNotificacion servicio;

    public GestorNotificaciones(ServicioNotificacion servicio) {
        this.servicio = servicio;
    }

    public void procesarEnvio(String destinatario, String mensaje) {
        if (servicio.validarDestinatario(destinatario)) {
            servicio.enviarMensaje(destinatario, mensaje);
        } else {
            System.out.println("[ERROR] Destinatario no válido: " + destinatario);
        }
    }
}
