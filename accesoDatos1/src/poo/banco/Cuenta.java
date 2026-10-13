package poo.banco;

public class Cuenta {

    private double saldo;
    String codigo;
    protected String oficina;
    public String titular;

    // a) Tabla de acceso
    // Desde la propia clase Cuenta
    // saldo: Si, codigo: Si, oficina: Si, titular: Si  

    // Desde otra clase del paquete banco
    // saldo: No, codigo: Si, oficina: Si, titular: Si

    // Desde otra clase del paquete tienda que no hereda de Cuenta
    // saldo: No, codigo: No, oficina: No, titular: Si

    // b) Permite a la clase hija usar los atributos de la clase padre 
    // aun estando en un paquete distinto

    Cuenta() {}

    // c) Constructor
    // Una clase del paquete tienda no puede ejecutar new Cuenta
    // porque solo es accesible desde el paquete banco aunque se importe
    // Para que funcione el constructor debe ser publico: public Cuenta() {}

    // d) Para una clase de nivel superior:
    // public: la clase es accesible desde otras clases de cualquier paquete
    // defecto: la clase es accesible desde otras clases del mismo paquete
}
