package es.aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "language")
public class Idioma {
    
    @Id
    @Column(name = "language_id")
    private Integer id;
    @Column(name = "name")
    private String nombre;

    protected Idioma() {
    }

    public String getNombre() {
        return nombre;
    }
}