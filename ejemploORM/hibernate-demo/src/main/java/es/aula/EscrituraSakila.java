package es.aula;

import java.math.BigDecimal;
import jakarta.persistence.*;
import org.hibernate.Session;

public class EscrituraSakila {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulaPU")) {
            int id;
            try (EntityManager em = emf.createEntityManager()) {
                EntityTransaction tx = em.getTransaction();
                try {
                    tx.begin();
                    id = operar(em);
                    tx.commit();
                } catch (RuntimeException e) {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                    throw e;
                }
            }
            try (EntityManager em = emf.createEntityManager()) {
                Pelicula p = em.find(Pelicula.class, id);
                System.out.println(p == null ? "Sin fila para ID " + id
                        : id + " | " + p.getTitulo() + " | " + p.getTarifa());
            }
        }
    }

    private static int operar(EntityManager em) {
        int idiomaId = 1;
        if (em.find(Idioma.class, idiomaId) == null) {
            throw new IllegalStateException("El idioma debe existir");
        }
        Pelicula nueva = new Pelicula(
                "PRACTICA HQL 2DAM", idiomaId, new BigDecimal("2.99"));
        em.persist(nueva);
        em.flush();
        return nueva.getId();
    }
}