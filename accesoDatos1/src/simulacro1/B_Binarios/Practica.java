import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Practica {

    // Leer un fichero binario y convertir sus datos a una lista de Alumnos
    static List<Alumno> leerSecuencial(Path p) throws IOException { 
         List<Alumno> lista = new ArrayList<>();

         // dis (DataInputStream): Flujo de entrada binario para leer datos
         try (DataInputStream dis = new DataInputStream(Files.newInputStream(p))) {

            // Linea para consumir el primer entero del archivo dat, que indica el numero de alumnos que almacena el fichero.
            // Se conume para que el puntero no quede desplazado y los datos se lean de forma correcta
            dis.readInt();

            // Lee mientras hay dats disponibles
            while (true) { 
                int id = dis.readInt();
                String nombre = dis.readUTF();
                double nota = dis.readDouble();
                // Crea un Alumno y lo añade a la lista
                lista.add(new Alumno(id, nombre, nota));
            }
        } catch (EOFException e) {
        }   
        return lista;
    }

    // Modificar una nota específica mediante acceso aleatorio 
    static void actualizarNota(Path p,int indice,double nota) throws IOException {

        // raf (RandomAccessFile): Permite lectura y escritura aleatoria en cualquier posición del fichero 
        try (RandomAccessFile raf = new RandomAccessFile(p.toFile(), "rw")) { // "rw" para lectura y escritura
            // Cada double ocupa 8 bytes, tenemos que calcular la posicion en el archivo
            long posicion = (long) indice * 8; 
            
            if (posicion >= raf.length() || posicion < 0) {
                throw new IndexOutOfBoundsException("Indice fuera de rango.");
            }
            
            // Mueve el puntero a la posición del índice
            raf.seek(posicion); 
            // Escribe la nota nueva en esa posicion
            raf.writeDouble(nota); 
        }
    }

    // Leer e imprimir registros de tamaño fijo de tipo double
    static void leerFijos(Path p) throws IOException { 

        try (DataInputStream dis = new DataInputStream(Files.newInputStream(p))) {
            int i = 0;
            // Lee mientras hay dats disponibles    
            while (true) {
                // Lee la nota en la posicion actual
                double nota = dis.readDouble();
                // Muestra la nota leida
                System.out.println("Registro " + i + ": Nota = " + nota);
                // Avanza a la siguiente
                i++;
            } 
        } catch (EOFException e) {
        }
    }

    // Guardar la lista de Alumnos
    static void guardarObjetos(Path p,List<Alumno> lista) throws IOException { 

        // oos (ObjectOutputStream): Flujo de salida para convertir objetos Java a bytes
         try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(p))) {
            oos.writeObject(lista); // Serializa y escribe la lista
        }
    }

    @SuppressWarnings("unchecked") // Esto evita el aviso sobre que el cast puede no ser una Lista de Alumnos
    // Recuperar la lista de objetos Alumno
    static List<Alumno> recuperarObjetos(Path p) throws IOException,ClassNotFoundException { 

        // ois (ObjectInputStream): Flujo de entrada para reconstruir objetos Java desde bytes
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(p))) {
            return (List<Alumno>) ois.readObject(); // Castear a lista el objeto leido
        }
    }

    public static void main(String[] args) {
        try {
            // Necesito cambiar la ruta a B_binarios/ para que los metodos encuentren los ficheros

            System.out.println("\n1. LECTURA SECUENCIAL (alumnos.dat)");
            List<Alumno> alumnos=leerSecuencial(Path.of("alumnos.dat"));
            alumnos.forEach(System.out::println);

            System.out.println("\n2. ACTUALIZAR NOTA Y LEER REGISTROS FIJOS (notas_fijas.dat)");
            actualizarNota(Path.of("notas_fijas.dat"),1,9.0);
            leerFijos(Path.of("notas_fijas.dat"));
            
            System.out.println("\n3. SERIALIZAR Y DESERIALIZAR OBJETOS (alumnos.ser)");
            guardarObjetos(Path.of("alumnos.ser"),alumnos);
            recuperarObjetos(Path.of("alumnos.ser")).forEach(System.out::println);

        } catch (Exception e) { 
            System.err.println(e.getClass().getSimpleName()+": "+e.getMessage()); 
        }

        // Añade pruebas negativas independientes con sus propios try/catch.
        System.out.println("\n4. PRUEBA 1 (Indice fuera de rango)");
        try {
            actualizarNota(Path.of("notas_fijas.dat"), 99, 10.0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Excepcion: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Excepcion: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        System.out.println("\n5. PRUEBA 2 (Fichero truncado)");
        try {
            leerSecuencial(Path.of("alumnos_truncado.dat"));
        } catch (Exception e) {
            System.out.println("Excepcion: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}