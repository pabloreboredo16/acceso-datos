package interfaces.papeles;

public class Factura implements Imprimible, Resumible {

    private int numero;
    private String cliente;
    private double total;

    public Factura(int numero, String cliente, double total) {
        this.numero = numero;
        this.cliente = cliente;
        this.total = total;
    }

    @Override
    public void imprimir() {
        System.out.println("Factura #" + numero + " | Cliente: " + cliente + " | Total: " + total + "$");
    }

    @Override
    public void resumir() {
        System.out.println("Factura #" + numero + " - " + total + "$");
    }
}