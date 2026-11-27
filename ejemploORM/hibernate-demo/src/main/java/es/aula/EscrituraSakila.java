package es.aula;

import java.math.BigDecimal;
import org.hibernate.Session;
import jakarta.persistence.*;

// 19. TRANSACCIONES
public class EscrituraSakila {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulaPU")) {
            int id;
            try (EntityManager em = emf.createEntityManager()) {
                EntityTransaction tx = em.getTransaction();
                try {
                    tx.begin();
                    id = operar23(em);
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

    // 20. ALTA
    private static int operar20(EntityManager em) {
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

    // 21. MODIFICACION
    private static int operar21(EntityManager em) {
        int id = 1001; // Sustituye 1001 por el ID de tu alta.
        Pelicula p = em.find(Pelicula.class, id);
        if (p == null || !"PRACTICA HQL 2DAM".equals(p.getTitulo())) {
            throw new IllegalStateException("Comprueba el ID de tu película");
        }
        p.setTarifa(new BigDecimal("3.49"));
        return id;
    }

    // 22. UPDATE HQL
    private static int operar22(EntityManager em) {
        int id = 1001; // Sustituye 1001 por el ID de tu alta.
        Session sesion = em.unwrap(Session.class);
        int total = sesion.createMutationQuery("""
                update Pelicula p
                set p.tarifa = :tarifa
                where p.id = :id and p.titulo = :titulo
                """)
            .setParameter("tarifa", new BigDecimal("3.99"))
            .setParameter("id", id)
            .setParameter("titulo", "PRACTICA HQL 2DAM")
            .executeUpdate();
        if (total != 1) {
            throw new IllegalStateException("Entidades afectadas: " + total);
        }
        System.out.println("Entidades afectadas: " + total);
        return id;
    }
    
    // 23. CICLO DE VIDA
    private static int operar23(EntityManager em) {
        int id = 1001; // Sustituye 1001 por el ID de tu alta.
        Pelicula p = em.find(Pelicula.class, id);
        if (p == null || !"PRACTICA HQL 2DAM".equals(p.getTitulo())) {
            throw new IllegalStateException("Comprueba el ID de tu película");
        }
        em.detach(p);
        p.setTarifa(new BigDecimal("4.49"));
        Pelicula gestionada = em.merge(p);
        return gestionada.getId();
        // em.remove(p);
        // return id;
    }
}