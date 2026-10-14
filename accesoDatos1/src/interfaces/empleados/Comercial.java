package interfaces.empleados;

public class Comercial extends Empleado implements Bonificable {

    private double ventas;
    private double porcentajeComision;

    public Comercial(String nombre, double salarioBase, double ventas, double porcentajeComision) {
        super(nombre, salarioBase);
        this.ventas = ventas;
        this.porcentajeComision = porcentajeComision;
    }

    @Override
    public double calcularBonificacion() {
        return ventas * porcentajeComision;
    }

    @Override
    public double calcularSalario() {
        return getSalarioBase() + calcularBonificacion();
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Ventas: " + ventas + "$ | Bonificación: " + calcularBonificacion() + "$");
    }
}