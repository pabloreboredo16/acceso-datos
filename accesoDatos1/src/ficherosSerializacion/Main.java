package ficherosSerializacion;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        Estudiante e1 = new Estudiante("Pablo", 25, 8.5, "1234567890");
        System.out.println("Antes: " + e1);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("estudiante.ser"))) {
            oos.writeObject(e1);
        } catch (IOException e) {
            e.printStackTrace();
        }



        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("estudiante.ser"))) {
            Estudiante eRecuperado = (Estudiante) ois.readObject();
            System.out.println("Después: " + eRecuperado);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}