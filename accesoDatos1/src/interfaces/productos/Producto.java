package interfaces.productos;

public class Producto implements Gestionable {

    private String id;
    private String nombre;
    private boolean activo;

    public Producto(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.activo = false; 
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void activar() {
        this.activo = true;
        System.out.println("Producto [" + id + " - " + nombre + "] ACTIVADO.");
    }

    @Override
    public void desactivar() {
        this.activo = false;
        System.out.println("Producto [" + id + " - " + nombre + "] DESACTIVADO.");
    }

    public boolean isActivo() {
        return activo;
    }
}