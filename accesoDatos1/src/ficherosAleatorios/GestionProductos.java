package ficherosAleatorios;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class GestionProductos {

    public static void main(String[] args) throws IOException {
        
        String[] nombres = {"Teclado", "Raton", "Monitor", "Impresora", "Webcam"};
        int[] existencias = {25, 40, 12, 8, 30};
        double[] precios = {19.99, 9.5, 149.9, 85.0, 24.75};

        Scanner sc = new Scanner(System.in);
        RandomAccessFile raf = new RandomAccessFile("productos.dat", "rw");

        // Crear el fichero e introducir los productos sin seek()
        raf.setLength(0);
        for (int i = 0; i < nombres.length; i++) {
            String nombre = nombres[i];
            if (nombre.length() > 12) {
                nombre = nombre.substring(0, 12);
            }
            while (nombre.length() < 12) {
                nombre = nombre + " ";
            }
            raf.writeInt(i + 1);
            raf.writeChars(nombre);
            raf.writeInt(existencias[i]);
            raf.writeDouble(precios[i]);
        }

        // Leer y mostrar todos los productos
        mostrarTodos(raf);

        // Solicitar un identificador y mostrar su producto con seek()
        System.out.print("Identificador: ");
        int id = sc.nextInt();
        if (id >= 1 && id <= raf.length() / 40) {
            raf.seek((id - 1) * 40);
            mostrarRegistro(raf);
        } else {
            System.out.println("Identificador no valido.");
        }

        // Solicitar un identificador y nuevas existencias; actualizar solo ese campo
        System.out.print("Identificador: ");
        id = sc.nextInt();
        if (id >= 1 && id <= raf.length() / 40) {
            System.out.print("Nuevas existencias: ");
            int nuevasExistencias = sc.nextInt();
            if (nuevasExistencias >= 0) {
                raf.seek((id - 1) * 40 + 28);
                raf.writeInt(nuevasExistencias);
            } else {
                System.out.println("Las existencias no pueden ser negativas.");
            }
        } else {
            System.out.println("Identificador no valido.");
        }

        // Solicitar un identificador y un nuevo precio; actualizar solo el precio
        System.out.print("Identificador: ");
        id = sc.nextInt();
        if (id >= 1 && id <= raf.length() / 40) {
            System.out.print("Nuevo precio: ");
            double nuevoPrecio = sc.nextDouble();
            if (nuevoPrecio >= 0) {
                raf.seek((id - 1) * 40 + 32);
                raf.writeDouble(nuevoPrecio);
            } else {
                System.out.println("El precio no puede ser negativo.");
            }
        } else {
            System.out.println("Identificador no valido.");
        }

        // Mostrar de nuevo todos los productos
        mostrarTodos(raf);

        raf.close();
        sc.close();
    }

    // Lee y muestra el registro situado en la posicion actual del puntero
    static void mostrarRegistro(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        String nombre = "";
        for (int i = 0; i < 12; i++) {
            nombre = nombre + raf.readChar();
        }
        int existencias = raf.readInt();
        double precio = raf.readDouble();
        System.out.println("Id: " + id + " Nombre: " + nombre
                + " Existencias: " + existencias + " Precio: " + precio);
    }

    static void mostrarTodos(RandomAccessFile raf) throws IOException {
        raf.seek(0);
        long registros = raf.length() / 40;
        for (long i = 0; i < registros; i++) {
            mostrarRegistro(raf);
        }
    }
}