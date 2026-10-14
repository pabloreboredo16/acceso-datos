package interfaces.saludos;

public class Persona implements Saludable {
    private String nombre;

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void saludar() {
        System.out.println("Buenas, me llamo " + nombre);
    }
}