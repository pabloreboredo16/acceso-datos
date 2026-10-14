package ficherosEjercicio3;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import ficherosUtilidades.UtilidadesFicheros;

public class ParejasNumeros {

    public static void main(String[] args) throws IOException {

        File fichero = UtilidadesFicheros.crearFichero();
        if (fichero != null) {
            try (
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(fichero, true));
                Scanner sc = new Scanner(System.in)
            ) {
                System.out.println("Introduce parejas de enteros separadas por espacio, para finalizar pulsa INTRO:");
                String pareja = sc.nextLine().trim();

                while (!pareja.isEmpty()) {

                    String[] numeros = pareja.split("\\s+");

                    if (numeros.length == 2) {
                        try {
                            dos.writeInt(Integer.parseInt(numeros[0]));
                            dos.writeChar(' ');
                            dos.writeInt(Integer.parseInt(numeros[1]));
                            dos.writeChar('\n');
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Debes introducir números enteros válidos.");
                        }
                    } else {
                        System.out.println("Error: Debes introducir DOS números separados por espacio.");
                    }
                    pareja = sc.nextLine().trim();
                }
                System.out.println("Proceso finalizado y fichero guardado.");
            }
        }
    }
}
