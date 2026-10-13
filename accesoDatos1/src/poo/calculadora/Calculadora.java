package poo.calculadora;

public class Calculadora {

    public int sumar(int a, int b) { // sumar(2,3)
        return a + b;
    }

    public int sumar(int a, int b, int c) { // sumar(2, 3, 4)
        return a + b + c;
    }

    public double sumar(double a, double b) { // sumar(2.5, 3.0)
        return a + b;
    }

    // No puede añadirse double sumar(int a, int b) porque tanto el nombre como la
    // lista de parametros es identica

    // Cambiar el nombre de los parametros tampoco funcionaria porqur el tipo de
    // dato sigue siendo el mismo
}