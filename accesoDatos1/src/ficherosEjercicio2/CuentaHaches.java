package ficherosEjercicio2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class CuentaHaches {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            int totalHaches = 0;
            int totalPalabrasConH = 0;

            try {
                Scanner sc = new Scanner(archivo);

                while (sc.hasNext()) {
                    String palabra = sc.next().toLowerCase();
                    int hachesEnEstaPalabra = 0;

                    for (int i = 1; i < palabra.length(); i++) {
                        // Si la la letra es una h y a su vez no la letra anterior no es una c (para evitar ch)
                        if (palabra.charAt(i) == 'h' && palabra.charAt(i - 1) != 'c') {
                            hachesEnEstaPalabra++;
                        }
                    }
                    totalHaches += hachesEnEstaPalabra;

                    if (hachesEnEstaPalabra > 0) {
                        totalPalabrasConH++;
                    }
                }

                System.out.println("Total de haches intercaladas: " + totalHaches);
                System.out.println("Total de palabras con hache intercalada: " + totalPalabrasConH);

            } catch (FileNotFoundException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }
    }
}