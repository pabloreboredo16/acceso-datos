package interfaces.metodosPago;

public class PagoTransferencia implements MetodoPago {
    
    private double importe;
    private String iban;

    public PagoTransferencia(double importe, String iban) {
        this.importe = importe;
        this.iban = iban;
    }

    @Override
    public boolean procesarPago() {
        if (importe > 0) {
            System.out.println("[Transferencia] Cobro ACEPTADO de " + importe + "$ al IBAN " + iban);
            return true;
        }
        System.out.println("[Transferencia] Cobro RECHAZADO: El importe debe ser positivo");
        return false;
    }
}