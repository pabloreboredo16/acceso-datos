package ficherosUtilidades;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UtilidadesFicheros {

    public static File devolverFichero() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public static File devolverFicheroDAT() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(
                new FileNameExtensionFilter("Archivos DAT (*.dat)","dat"));
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public static File crearFichero() {
        System.out.println("ENTRANDO EN crearFichero sin nombre");

        JOptionPane.showMessageDialog(
                null,
                "Se está ejecutando crearFichero(String)"
        );
        return UtilidadesFicheros.devolverFichero();
    }


    public static File crearFichero(String nombre) throws IOException {
        System.out.println("ENTRANDO EN crearFichero(String): " + nombre);


        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Selecciona la carpeta de destino");

        int seleccion = fileChooser.showDialog(null, "Crear aquí");

        if (seleccion != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File carpeta = fileChooser.getSelectedFile();

        if (!carpeta.isDirectory()) {
            throw       new IOException("Debes seleccionar una carpeta");
        }

        File fichero = new File(carpeta, nombre);

        if (!fichero.createNewFile()) {
            throw new IOException("El fichero ya existe: " + fichero);
        }

        return fichero;
    }
}
