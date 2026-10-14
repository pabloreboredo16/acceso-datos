package interfaces.empleados;

public abstract class Empleado {

    private String nombre;
    private double salarioBase;

    public Empleado(String nombre, double salarioBase) {
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void mostrarDatos() {
        System.out.println("Empleado: " + nombre + " | Salario base: " + salarioBase + "$");
    }

    public abstract double calcularSalario();

}
