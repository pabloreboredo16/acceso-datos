package poo.maquinaExpendedora;

public class Almacen {

    private int capacidad;
    private int cantidad;

    public Almacen(int capacidad) {
        if (capacidad > 0) {
            this.capacidad = capacidad;
        } else {
            this.capacidad = 1;
        }       
        this.cantidad = 0;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean meter() {
        if (cantidad < capacidad) {
            cantidad++;
            return true;
        }
        return false;
    }

    public boolean sacar() {
        if (cantidad > 0) {
            cantidad--;
            return true;
        }
        return false;
    }

    public void rellenar() {
        this.cantidad = this.capacidad;
    }
}