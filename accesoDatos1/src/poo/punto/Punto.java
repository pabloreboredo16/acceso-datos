package poo.punto;

public class Punto {

    private int x, y;

    // a) Este metodo no se reconoce como constructor al ser un void
    /*
    public void Punto() {
        x = 0;
        y = 0;
    }
    */

    // a) Este es el metodo que se ejecuta al hacer new Punto(4,6)
    // al ser x = x y no this.x = x
    // lo que pasa es que no se asigna el valor del parametro al atributo, 
    // sino que se asigna a si mismo
    /* 
    public Punto(int x, int y) {
        x = x;
        y = y;
    }
    */

    // b) 
    public Punto() {
        this.x = 0;
        this.y = 0;
    }
    public Punto(int v) {
        this.x = v;
        this.y = v;
    }

    public Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // c)
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }



    
    public void dibujar() {
        System.out.println("Punto(" + x + ", " + y + ")");
    }
}