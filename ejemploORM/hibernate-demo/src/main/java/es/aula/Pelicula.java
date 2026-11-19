package es.aula;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "film")
public class Pelicula {

    // ATRIBUTOS
    @Id
    @Column(name = "film_id")
    private Integer id;

    @Column(name = "title")
    private String titulo;

    @Column(name = "language_id")
    private Integer idiomaId;

    @Column(name = "rental_rate")
    private BigDecimal tarifa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", insertable = false, updatable = false)
    private Idioma idioma;

    // CONSTRUCTOR
    protected Pelicula() {
    }

    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public BigDecimal getTarifa() { return tarifa; }
    public Idioma getIdioma() { return idioma; }
    
    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }
}