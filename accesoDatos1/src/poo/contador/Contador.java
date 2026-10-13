package poo.contador;

public class Contador {

    // b) Al estar declarado como static, la variable total 
    // pertenece a la clase y no a los objetos
    private static int total = 0;

    // b) Al estar declarado como final, la variable MAX es una 
    // constante y no puede ser modificada
    public static final int MAX = 3;

    // c) Atributo de instancia nombre
    private String nombre;

    static { 
        System.out.println("Carga"); 
    }

    public Contador() { 
        total++; 
    }

    public static int getTotal() {
        return total; 
    }   

    // c) El metodo static no tiene acceso a this, al ser un metodo de clase
    // por lo que para acceder a los atributos de instancia, se debe acceder 
    // a traves de un objeto de la clase, que se pasa como parametro
    public static void mostrarNombre(Contador c) {
        System.out.println("El nombre del contador es: " + c.nombre);
    }

    // d) En una vatriable la conviere en una constante
    // En un metodo impide sobreescribirlo en una subclase
    // En una clase impide que se pueda heredar
}