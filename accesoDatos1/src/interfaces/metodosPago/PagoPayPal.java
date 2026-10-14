package interfaces.metodosPago;

public class PagoPayPal implements MetodoPago {

    private String correoElectronico;
    private double importe;

    public PagoPayPal(double importe, String correoElectronico) {
        this.importe = importe;
        this.correoElectronico = correoElectronico;
    }

    @Override
    public boolean procesarPago() {
        if (correoElectronico.contains("@")) {
            System.out.println("[PayPal] Cobro ACEPTADO de " + importe + "$ a la cuenta " + correoElectronico);
            return true;
        }
        System.out.println("[PayPal] Cobro RECHAZADO: Correo electrónico inválido (" + correoElectronico + ")");
        return false;
    }
}