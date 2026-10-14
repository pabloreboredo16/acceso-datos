package interfaces.empleados;

public class MainEmpleados {

    public static void main(String[] args) {
        Empleado fijo = new EmpleadoFijo("Pablo Reboredo", 1500.0);
        Empleado comercial = new Comercial("Antonio Benito", 2000.0, 500.0, 0.10);

        System.out.println("EMPLEADO FIJO");
        fijo.mostrarDatos();
        System.out.println("Salario Total: " + fijo.calcularSalario() + "$");

        System.out.println("COMERCIAL");
        comercial.mostrarDatos();
        System.out.println("Salario Total: " + comercial.calcularSalario() + "$");
    }
}