package es.aula;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulaPU");
                EntityManager em = emf.createEntityManager()) {
            Object nombreBD = em
                    .createNativeQuery("SELECT DATABASE()")
                    .getSingleResult();
            System.out.println("Conexión correcta.");
            System.out.println("Base de datos: " + nombreBD);

            // Modifica y deshaz un cambio
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Pelicula pelicula = em.find(Pelicula.class, 1);
                if (pelicula != null) {
                    BigDecimal anterior = pelicula.getTarifa();
                    pelicula.setTarifa(anterior.add(new BigDecimal("0.10")));
                    em.flush();
                    System.out.println("Antes: " + anterior);
                    System.out.println("En la transaccion: " + pelicula.getTarifa());
                }
                tx.rollback();
            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
            try (EntityManager verificacion = emf.createEntityManager()) {
                Pelicula pelicula = verificacion.find(Pelicula.class, 1);
                if (pelicula != null) {
                    System.out.println("Tras rollback: " + pelicula.getTarifa());
                }
            }

            // Relaciona film y language
            List<Pelicula> peliculas = em.createQuery("""
                select p from Pelicula p
                join fetch p.idioma
                order by p.id
                """, Pelicula.class)
                .setMaxResults(10)
                .getResultList();
            for (Pelicula p : peliculas) {
                System.out.println(p.getTitulo() + " | " + p.getIdioma().getNombre());
            }
        }
    }
}