package interfaces.vehiculos;

public interface ConfiguracionVehiculo {
    int VELOCIDAD_MINIMA = 0;
    int VELOCIDAD_MAXIMA = 120;

    void acelerar(int incremento);
    void frenar(int decremento);
}
