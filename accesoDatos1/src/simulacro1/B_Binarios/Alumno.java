import java.io.Serializable;

public class Alumno implements Serializable { // TODO: serialización, serialVersionUID y transient
    private static final long serialVersionUID = 1L;

    int id; 
    String nombre; 
    double nota; 
    transient String clave; // transient para que no se guarde al serializar

    public Alumno(int id, String nombre, double nota) {
        this.id=id; 
        this.nombre=nombre; 
        this.nota=nota; 
        this.clave="temporal";
    }

    public String toString() { return id + " " + nombre + " " + nota + " clave=" + clave; }
}