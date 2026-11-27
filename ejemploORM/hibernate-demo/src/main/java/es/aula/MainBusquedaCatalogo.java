package es.aula;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import org.hibernate.Session;

public class MainBusquedaCatalogo {

    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulaPU");
             EntityManager em = emf.createEntityManager()) {

            Session sesion = em.unwrap(Session.class);

            // 24. PRÁCTICA RESUELTA
            List<Tuple> filas = sesion.createSelectionQuery("""
                    select p.id as id, p.titulo as titulo, p.tarifa as tarifa
                    from Pelicula p
                    join p.idioma i
                    where i.nombre = :idioma and p.tarifa <= :tope
                    order by p.tarifa, p.id
                    """, Tuple.class)
                .setParameter("idioma", "English")
                .setParameter("tope", new BigDecimal("2.99"))
                .setMaxResults(5)
                .getResultList();

            for (Tuple fila : filas) {
                System.out.println(
                    fila.get("id", Integer.class) + " | " +
                    fila.get("titulo", String.class) + " | " +
                    fila.get("tarifa", BigDecimal.class)
                );
            }
        }
    }
}
