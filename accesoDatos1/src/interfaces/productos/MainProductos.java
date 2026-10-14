package interfaces.productos;

import java.util.ArrayList;
import java.util.List;

public class MainProductos {

    public static void main(String[] args) {

        List<Gestionable> elementos = new ArrayList<>();

        elementos.add(new Producto("P01", "Portátil"));
        elementos.add(new Cliente("12345678A", "Pablo Reboredo"));
        elementos.add(new Producto("P02", "Tecaldo"));

        System.out.println("ACTIVACIÓN");
        for (Gestionable e : elementos) {
            System.out.println("ID: " + e.getId() + " | Activo: " + e.isActivo());
            e.activar(); 
        }

        System.out.println("\nDESACTIVACIÓN");
        for (Gestionable e : elementos) {
            e.desactivar(); 
        }
    }
}
