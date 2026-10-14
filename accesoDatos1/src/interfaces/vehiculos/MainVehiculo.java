package interfaces.vehiculos;

public class MainVehiculo {

    public static void main(String[] args) {
        Coche c1 = new Coche("Seat");

        System.out.println("PRUEBA ACELERACIÓN");
        c1.acelerar(50);  
        c1.acelerar(50);
        c1.acelerar(30);

        System.out.println("\nPRUEBA FRENADO");
        c1.frenar(80);   
        c1.frenar(50);  
    }
}
