package interfaces.metodosPago;

public class PagoTarjeta implements MetodoPago {

    private String titular;
    private double importe;
    private String numeroTarjeta;
    
    public PagoTarjeta(String titular, double importe, String numeroTarjeta) {
        this.titular = titular;
        this.importe = importe;
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public boolean procesarPago() {
        if (importe <= 1000.0) {
            System.out.println("[Tarjeta] Cobro ACEPTADO de " + importe + "$");
            return true;
        }
        System.out.println("[Tarjeta] Cobro RECHAZADO: El importe supera el límite");
        return false;
    }
}