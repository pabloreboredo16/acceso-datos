package poo.telefono;

import java.util.Arrays;

public class PruebaTelefono {
    public static void main(String[] args) {

        // c) Capacidad 3 y registros "111" a "555"
        Telefono t = new Telefono(3);
        String[] numeros = {"111", "222", "333", "444", "555"};

        System.out.println("REGISTRO DE LLAMADAS");
        for (String num : numeros) {
            t.llamar(num);
            System.out.println("Despues de la llamada " + num + " - Historial: " + Arrays.toString(t.getHistorial()));
        }
        System.out.println("");
        
        System.out.println("CONSULTAS DE POSICIONES");
        for (int n = 0; n <= 3; n++) {
            System.out.println("llamada(" + n + "): " + t.llamada(n));
        }
        System.out.println("");

        // d) Pruebas adicionales
        // Teléfono vacío
        Telefono tvacio = new Telefono(3);
        System.out.println("Teléfono vacío - " + Arrays.toString(tvacio.getHistorial()));
        System.out.println("");

        // Pasar de capacidad 0 a capacidad 1 desde el constructor
        Telefono tCap1 = new Telefono(0);
        tCap1.llamar("888");
        tCap1.llamar("999");
        System.out.println("Teléfono Capacidad 1 - llamada(0): " + tCap1.llamada(0)); 
        System.out.println("Teléfono Capacidad 1 - llamada(1): " + tCap1.llamada(1)); // Esta llamada no se guarda
        System.out.println("");

        // Dos teléfonos 
        Telefono t1 = new Telefono(2);
        Telefono t2 = new Telefono(2);
        t1.llamar("777");
        System.out.println("Teléfono 1 - llamada(0): " + t1.llamada(0));
        System.out.println("Teléfono 2 - llamada(0): " + t2.llamada(0));
    }
}