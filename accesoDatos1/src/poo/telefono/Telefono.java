package poo.telefono;

public class Telefono {
    private String[] historial;
    private int contador;

   public Telefono(int capacidad) {
        if (capacidad < 1) {
            capacidad = 1;
        }
        this.historial = new String[capacidad];
        this.contador = 0;
    }

    public void llamar(String numero) {
        for (int i = historial.length - 1; i > 0; i--) {
            historial[i] = historial[i - 1];
        }
        historial[0] = numero;

        if (contador < historial.length) {
            contador++;
        }
    }

    public String ultimaLlamada() {
        return llamada(0);
    }

    public String llamada(int n) {
        if (contador == 0 || n < 0 || n >= contador) {
            return null;
        }
        return historial[n];
    }

    public String[] getHistorial() {
        return historial;
    }
}