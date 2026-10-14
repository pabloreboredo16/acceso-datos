package ficherosEjercicio2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class ContadorPalabras {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            int totalPalabras = 0;

            try {
                Scanner sc = new Scanner(archivo);
                while (sc.hasNext()) {
                    sc.next();
                    totalPalabras++;
                }

                System.out.println("El archivo contiene " + totalPalabras + " palabras.");

            } catch (FileNotFoundException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }
        }
    }
}