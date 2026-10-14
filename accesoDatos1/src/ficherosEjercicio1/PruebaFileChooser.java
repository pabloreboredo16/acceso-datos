package ficherosEjercicio1;

import java.io.File;
import javax.swing.JFileChooser;

public class PruebaFileChooser {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            // System.out.println("Archivo seleccionado: " + fichero.getAbsolutePath());
            System.out.println("• getName(): " + archivo.getName());
            System.out.println("• getAbsolutePath(): " + archivo.getAbsolutePath());
            System.out.println("• exists(): " + archivo.exists());
            System.out.println("• canRead(): " + archivo.canRead());
            System.out.println("• canWrite(): " + archivo.canWrite());
            System.out.println("• isFile(): " + archivo.isFile());
            System.out.println("• isDirectory(): " + archivo.isDirectory());
        }

        JFileChooser fileChooser2 = new JFileChooser();

        int seleccion2 = fileChooser2.showSaveDialog(null);
        
        if (seleccion2 == JFileChooser.APPROVE_OPTION) {
            File fichero2 = fileChooser2.getSelectedFile();
        }
    }
}