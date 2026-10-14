package interfaces.papeles;

public class Informe implements Imprimible {

    private int numero;
    private String titulo;
    private String autor;
    private String contenido;

    public Informe(int numero, String titulo, String autor, String contenido) {
        this.numero = numero;   
        this.titulo = titulo;
        this.autor = autor;
        this.contenido = contenido;
    }

    @Override
    public void imprimir() {
        System.out.println("Informe #" + numero + " | " + titulo + " | Autor: " + autor + "\nContenido: " + contenido);
    }
}