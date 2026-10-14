package interfaces.productos;

public interface Gestionable extends Identificable {
    void activar();
    void desactivar();
    boolean isActivo();
}