package interfaces.notificaciones;

public class MainNotificaciones {

    public static void main(String[] args) {

        GestorNotificaciones g1 = new GestorNotificaciones(new ServicioEmail());
        g1.procesarEnvio("pablo@gmail.com", "Tu pedido ha sido enviado.");
        g1.procesarEnvio(null, "Intento fallido.");

        GestorNotificaciones g2 = new GestorNotificaciones(new ServicioEmail());
        g2.procesarEnvio("123456789", "Tu código de verificación es 12345.");
        g2.procesarEnvio(null, "Intento fallido.");

        GestorNotificaciones g3 = new GestorNotificaciones(new ServicioApp());
        g3.procesarEnvio("@pabloreboredo", "Tienes un nuevo mensaje.");
    }
}
