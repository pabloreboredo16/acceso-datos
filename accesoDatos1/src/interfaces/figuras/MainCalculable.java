package interfaces.figuras;

public class MainCalculable {

    public static void main(String[] args) {
        Circulo circulo1 = new Circulo(5.0);
        Circulo circulo2 = new Circulo(2.5);
        Rectangulo rectangulo1 = new Rectangulo(4.0, 6.0);
        Rectangulo rectangulo2 = new Rectangulo(3.0, 8.5);

        // Mostrar las áreas por pantalla
        System.out.println("Área del Círculo 1: " + circulo1.calcularArea());
        System.out.println("Área del Círculo 2: " + circulo2.calcularArea());
        
        System.out.println("Área del Rectángulo 1: " + rectangulo1.calcularArea());
        System.out.println("Área del Rectángulo 2: " + rectangulo2.calcularArea());
    }
}
