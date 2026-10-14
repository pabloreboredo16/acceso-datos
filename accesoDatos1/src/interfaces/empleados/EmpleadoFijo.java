package interfaces.empleados;

public class EmpleadoFijo extends Empleado {

    public EmpleadoFijo(String nombre, double salarioBase) {
        super(nombre, salarioBase);
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase();
    }
}