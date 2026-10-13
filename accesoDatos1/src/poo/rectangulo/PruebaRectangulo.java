package poo.rectangulo;

public class PruebaRectangulo {
    public static void main(String[] args) {
        Rectangulo r1 = new Rectangulo();

        r1.setAlto(3);   
        r1.setAncho(6);  
        r1.setAlto(-4); 
        r1.setAncho(0);  

        System.out.println("Alto final: " + r1.getAlto());         
        System.out.println("Ancho final: " + r1.getAncho());      
        System.out.println("Área final: " + r1.area());           
        System.out.println("Perímetro final: " + r1.perimetro()); 

        Rectangulo r2 = new Rectangulo();
        r1.setAlto(5); 

        System.out.println("Alto r1: " + r1.getAlto()); 
        System.out.println("Alto r2: " + r2.getAlto()); 

        // this es una referencia al objeto que está ejecutando el método.
        // En los setters this.alto = alto; sirve para 
        // asignar el valor del parámetro alto introducido en el setter 
        // al atributo alto de este objeto.
    }
}