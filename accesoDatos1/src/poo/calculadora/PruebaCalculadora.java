package poo.calculadora;

public class PruebaCalculadora {
    public static void main(String[] args) {
        Calculadora calc = new Calculadora();

        int r1 = calc.sumar(2, 3);
        System.out.println("suma = " + r1);

        int r2 = calc.sumar(2, 3, 4);
        System.out.println("suma = " + r2);

        double r3 = calc.sumar(2.5, 3.0);
        System.out.println("suma = " + r3);
    }
}