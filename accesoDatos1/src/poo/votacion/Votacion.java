package poo.votacion;

public class Votacion {

    private String nombre;
    private int votos;

    private static String nombreMasVotado = "Nadie";
    private static int votosMasVotado = 0;

    public Votacion(String nombre) {
        this.nombre = nombre;
        this.votos = 0;
    }



    public String getNombre() {
        return nombre;
    }

    public int getVotos() {
        return votos;
    }

    public static String getNombreMasVotado() {
        return nombreMasVotado;
    }

    public static int getVotosMasVotado() {
        return votosMasVotado;
    }
    public void votar() {
        this.votos++;
        if (this.votos > votosMasVotado) {
            votosMasVotado = this.votos;
            nombreMasVotado = this.nombre;
        }
    }

    // d) Si votos fuera static, todos los objetos de la clase compartirían 
    // el mismo contador de votos. 
    // Cada vez que se llame al método votar() se incrementaría un 
    // contador comun y todos tendrian el mismo numero de votos siempre. 

    // Si votosMasVotado fuera un atributo de instancia, 
    // cada objeto tendría su propio contador maximo y no sabriamos quien es el lider.
}