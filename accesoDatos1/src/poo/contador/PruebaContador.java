package poo.contador;

public class PruebaContador {

    public static void main(String[] args) {
        // a)
        // Esta linea imprime Carga y 0
        // Carga, porque el bloque static se ejecuta una sola vez, cuando se carga la clase
        // 0, porque no se ha creado ningún objeto de la clase Contador
        System.out.println(Contador.getTotal());

        // El constructor se ejecuta cada vez que se crea un objeto (2 veces)
        Contador a = new Contador();
        Contador b = new Contador();
        
        // Esta linea imprime 2, porque se han creado dos objetos de la clase Contador
        System.out.println(Contador.getTotal());
    }
}