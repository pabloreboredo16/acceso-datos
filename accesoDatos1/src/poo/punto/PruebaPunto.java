package poo.punto;

public class PruebaPunto {
    public static void main(String[] args) {

        Punto p1 = new Punto();
        p1.dibujar(); 

        Punto p2 = new Punto(5);
        p2.dibujar();

        Punto p3 = new Punto(4, 6);
        p3.dibujar(); 
    }
}