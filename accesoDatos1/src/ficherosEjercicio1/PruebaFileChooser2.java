package ficherosEjercicio1;

import java.io.File;
import javax.swing.JFileChooser;

public class PruebaFileChooser2 {

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/Users/DAM2/Desktop"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        System.out.println(fileChooser.getCurrentDirectory());

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSel = fileChooser.getSelectedFile();
            File elementos[] = archivoSel.listFiles();
            for(File elemento : elementos) {
                if(elemento.isFile()) {
                    System.out.println(elemento.getName());
                }
            }
        }
    }
}