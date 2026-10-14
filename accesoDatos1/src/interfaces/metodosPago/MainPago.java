package interfaces.metodosPago;

import java.util.ArrayList;
import java.util.List;

public class MainPago {

    public static void main(String[] args) {

        MetodoPago p1 = new PagoTarjeta("Pablo Reboredo", 120.50, "1234 5678 9012 3456");
        MetodoPago p2 = new PagoPayPal(45.00, "pablo.reboredo@gmail.com");
        MetodoPago p3 = new PagoTransferencia(890.00, "ES1 234 5678 9012 3456 7890");

        List<MetodoPago> pagos = new ArrayList<>();
        pagos.add(p1);
        pagos.add(p2);
        pagos.add(p3);

        System.out.println("PAGOS REALIZADOS:");
        for (MetodoPago pago : pagos) {
            pago.procesarPago();
        }
    }
}
