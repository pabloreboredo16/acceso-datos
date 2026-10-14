package interfaces.productos;

public class Cliente implements Gestionable {

    private String dni;
    private String nombre;
    private boolean activo;

    public Cliente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
        this.activo = false; 
    }

    @Override
    public String getId() {
        return dni;
    }

    @Override
    public void activar() {
        this.activo = true;
        System.out.println("Cliente [" + dni + " - " + nombre + "] ACTIVADO.");
    }

    @Override
    public void desactivar() {
        this.activo = false;
        System.out.println("Cliente [" + dni + " - " + nombre + "] DESACTIVADO.");
    }

    @Override
    public boolean isActivo() {
        return activo;
    }
}