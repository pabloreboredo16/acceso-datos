package poo.empleado;

public class Empleado {

    private String nombre;
    private String dni;

    public Empleado() {
        this.nombre = "";
        this.dni = "";
    }

    public Empleado(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Empleado [nombre=" + nombre + ", dni=" + dni + "]";
    }

    // nombre = nombre; no inicializa correctamente el atributo porque se
    // detecta que se esta asignando el valor del parametro nombre a si mismmo
    // Para distinguir se usa this.nombre = nombre;

    // Si se elimina el constructor sin parametros, al intentar hacer new Empleado()  
    // se produce un error de compilacion
    
    // El constructor por defecto no tiene parametros que solo se genera si no se 
    // especifica otro constructor y el constructor sin argumentos se elabora manualmente
}