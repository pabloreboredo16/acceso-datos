package ficherosEjercicio1;

import java.io.File;
import javax.swing.JFileChooser;

public class PruebaFileChooser3 {

    public static void main(String[] args) {
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(".")); // Para entrar en el directorio actual
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            listarContenido(carpetaSeleccionada);
        }
    }

    private static void listarContenido(File directorio) {
        File[] elementos = directorio.listFiles();

        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isFile()) {
                    System.out.println("[FICHERO] " + elemento.getName());
                } else if (elemento.isDirectory()) {
                    System.out.println("[DIRECTORIO] " + elemento.getName());
                    // Llamada para entrar en el subdirectorio
                    listarContenido(elemento);
                }
            }
        }
    }
}
