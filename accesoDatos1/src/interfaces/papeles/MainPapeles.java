package interfaces.papeles;

public class MainPapeles {

    public static void main(String[] args) {

        Factura factura = new Factura(101, "Pablo Reboredo", 1500);
        Informe informe = new Informe(201, "Análisis mensual", "Pablo Reboredo", "Los resultados han sido positivos.");

        System.out.println("Imprimible");
        factura.imprimir(); 
        informe.imprimir(); 


        System.out.println("\nResumible");
        factura.resumir();
    }
}
