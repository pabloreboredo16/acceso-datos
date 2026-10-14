package swingNumeroRacional;

public class ExcepcionIntervalo extends Exception {

    public ExcepcionIntervalo() {
        super("Números fuera del intervalo");
    }

    public ExcepcionIntervalo(String mensaje) {
        super(mensaje);
    }
}