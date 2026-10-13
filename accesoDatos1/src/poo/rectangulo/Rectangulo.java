package poo.rectangulo;

public class Rectangulo {

    private int alto = 2; // Al cambiar la condicion en el setter, ya no se puede iniciar a 1
    private int ancho = 1;

    public int getAlto() {
        return alto;
    }
    public void setAlto(int alto) {
        if (alto >= 2) {
            this.alto = alto; 
        }
    }

    public int getAncho() {
        return ancho;
    }
    public void setAncho(int ancho) {
        if (ancho > 0) {
            this.ancho = ancho; 
        }
    }


    
    public int area() {
        return this.alto * this.ancho;
    }

    public int perimetro() {
        return 2 * (this.alto + this.ancho);
    }
}