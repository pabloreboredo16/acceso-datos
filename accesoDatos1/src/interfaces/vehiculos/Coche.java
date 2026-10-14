package interfaces.vehiculos;

public class Coche implements ConfiguracionVehiculo {
    private String marca;
    private int velocidadActual;

    public Coche(String marca) {
        this.marca = marca;
        this.velocidadActual = VELOCIDAD_MINIMA; 
    }

    public int getVelocidadActual() {
        return velocidadActual;
    }

    @Override
    public void acelerar(int incremento) {
        if (this.velocidadActual + incremento > VELOCIDAD_MAXIMA) {
            this.velocidadActual = VELOCIDAD_MAXIMA;
            System.out.println("Se ha alcanzado la velocidad máxima (" + VELOCIDAD_MAXIMA + " km/h).");
        } else {
            this.velocidadActual += incremento;
            System.out.println("Acelerando... Velocidad actual: " + this.velocidadActual + " km/h.");
        }
    }

    @Override
    public void frenar(int decremento) {
        if (this.velocidadActual - decremento < VELOCIDAD_MINIMA) {
            this.velocidadActual = VELOCIDAD_MINIMA;
            System.out.println("El coche se ha detenido (" + VELOCIDAD_MINIMA + " km/h).");
        } else {
            this.velocidadActual -= decremento;
            System.out.println("Frenando... Velocidad actual: " + this.velocidadActual + " km/h.");
        }
    }
}