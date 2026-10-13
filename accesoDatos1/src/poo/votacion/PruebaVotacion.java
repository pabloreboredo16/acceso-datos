package poo.votacion;

public class PruebaVotacion {
    public static void main(String[] args) {

        Votacion ana = new Votacion("Ana");
        Votacion bruno = new Votacion("Bruno");
        Votacion carla = new Votacion("Carla");

        System.out.println("Líder antes: " + Votacion.getNombreMasVotado() + ", " + Votacion.getVotosMasVotado() + " votos");

        ana.votar();
        bruno.votar();
        bruno.votar();
        carla.votar();
        carla.votar();
        ana.votar();
        ana.votar();

        System.out.println("Recuento de votos: ");
        System.out.println(ana.getNombre() + ": " + ana.getVotos() + " votos");
        System.out.println(bruno.getNombre() + ": " + bruno.getVotos() + " votos");
        System.out.println(carla.getNombre() + ": " + carla.getVotos() + " votos");

        System.out.println("Líder al final: " + Votacion.getNombreMasVotado() + ", " + Votacion.getVotosMasVotado() + " votos");
    } 
}