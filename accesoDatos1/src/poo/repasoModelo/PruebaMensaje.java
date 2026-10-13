package poo.repasoModelo;

public class PruebaMensaje {
    public static void main(String[] args) {
        poo.repasoModelo.Mensaje msg = new poo.repasoModelo.Mensaje();
        System.out.println(msg.getTexto());
    }
}

// Si la clase Mensaje deja de ser publica se produce un 
// error de compilacion porque PuebaMensaje no puede acceder a 
// la Clase Mensaje al estar en paquetes distintos 