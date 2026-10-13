package poo.maquinaExpendedora;

public class Maquina {


    public static final int CODIGO_AGUA = 0;
    public static final int CODIGO_NARANJA = 1;
    public static final int CODIGO_COLA = 2;

    private Almacen agua;
    private Almacen naranja;
    private Almacen cola;

    private int precioAgua;
    private int precioNaranja;
    private int precioCola;


    public Maquina() {
        this.agua = new Almacen(3);
        this.naranja = new Almacen(2);
        this.cola = new Almacen(2);

        this.precioAgua = 60;
        this.precioNaranja = 130;
        this.precioCola = 100;
    }

    

    private Almacen obtenerAlmacen(int codigo) {
        switch (codigo) {
            case CODIGO_AGUA: 
                return agua;
            case CODIGO_NARANJA: 
                return naranja;
            case CODIGO_COLA: 
                return cola;
            default: 
                return null;
        }
    }



    public void reponer(int codigo) {
        Almacen almacen = obtenerAlmacen(codigo);
        if (almacen != null) {
            almacen.rellenar();
        }
    }



    public int getExistencias(int codigo) {
        Almacen almacen = obtenerAlmacen(codigo);
        if (almacen != null) {
            return almacen.getCantidad();
        }
        return -1;
    }

    public boolean setPrecio(int codigo, int nuevoPrecio) {
        if (nuevoPrecio <= 0) {
            return false;
        }

        switch (codigo) {
            case CODIGO_AGUA:
                precioAgua = nuevoPrecio;
                return true;
            case CODIGO_NARANJA:
                precioNaranja = nuevoPrecio;
                return true;
            case CODIGO_COLA:
                precioCola = nuevoPrecio;
                return true;
            default:
                return false;
        }
    }

    public int getPrecio(int codigo) {
        switch (codigo) {
            case CODIGO_AGUA: 
                return precioAgua;
            case CODIGO_NARANJA: 
                return precioNaranja;
            case CODIGO_COLA: 
                return precioCola;
            default: 
                return -1;
        }
    }



    public boolean comprar(int codigo, int pagoCentimos) {
        Almacen almacen = obtenerAlmacen(codigo);
        int precio = getPrecio(codigo);
        if (almacen != null && pagoCentimos == precio) {
            return almacen.sacar();
        }
        return false;
    }
}