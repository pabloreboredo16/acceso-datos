package swingNumeroRacional;

public class NumeroRacional {
    private double numerador;
    private double denominador;

    public NumeroRacional() {
        this.numerador = 0;
        this.denominador = 1;
    }

    public NumeroRacional(double numerador, double denominador) throws ExcepcionIntervalo, ArithmeticException {
        setNumerador(numerador);
        setDenominador(denominador);
    }

    public double getNumerador() {
        return numerador;
    }
    public void setNumerador(double numerador) throws ExcepcionIntervalo {
        if (numerador >= 100) {
            throw new ExcepcionIntervalo("Números fuera del intervalo");
        }
        this.numerador = numerador;
    }

    public double getDenominador() {
        return denominador;
    }

    public void setDenominador(double denominador) throws ExcepcionIntervalo, ArithmeticException {
        if (denominador <= -5) {
            throw new ExcepcionIntervalo("Números fuera del intervalo");
        }
        if (denominador == 0) {
            throw new ArithmeticException("División entre cero.");
        }
        this.denominador = denominador;
    }

    public double calcularCociente() {
        return this.numerador / this.denominador;
    }
}
