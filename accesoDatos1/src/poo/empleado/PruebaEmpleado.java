package poo.empleado;

public class PruebaEmpleado {
    public static void main(String[] args) {

        Empleado e1 = new Empleado("Pablo Reboredo", "12345678A");
        Empleado e2 = new Empleado("Antonio Benito", "87654321B");

        e1.setNombre("Pablo Reboredo Fernandez");

        System.out.println(e1.toString());
        System.out.println(e2.toString());
    }
}