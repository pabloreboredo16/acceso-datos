package es.aula;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;

public class Main {
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulaPU");
                EntityManager em = emf.createEntityManager()) {
            Object base = em
                    .createNativeQuery("SELECT DATABASE()")
                    .getSingleResult();
            System.out.println("Conexión correcta.");
            System.out.println("Base de datos: " + base);

            Session sesion = em.unwrap(Session.class);

            // PRIMERAS LECTURAS
            // Busca la pelicula 1
            System.out.println("Busca la pelicula 1");
            Pelicula pelicula = em.find(Pelicula.class, 1);
            if (pelicula == null) {
                System.out.println("No existe la película 1.");
            } else {
                System.out.println(pelicula.getTitulo() + " | " + pelicula.getTarifa());
            }

            // Busca peliculas de un idioma
            List<Pelicula> peliculas0 = em.createQuery("""
                    select p from Pelicula p
                    where p.idiomaId = :idioma
                    order by p.id
                    """, Pelicula.class)
                .setParameter("idioma", 1)
                .setMaxResults(5)
                .getResultList();
            peliculas0.forEach(p -> System.out.println(p.getTitulo()));

            // 9. HQL
            // Consulta HQL básica: Primeras 5 películas
            System.out.println("Consulta HQL básica: Primeras 5 películas");
            List<Pelicula> peliculas = sesion.createSelectionQuery(
                    "select p from Pelicula p order by p.id", Pelicula.class)
                    .setMaxResults(5)
                    .getResultList();
            peliculas.forEach(p -> System.out.println(p.getTitulo()));

            // 10. PARAMETROS
            // Idiomas 1 o 2 y tarifa <= 2.99
            System.out.println("Idiomas 1 o 2 y tarifa <= 2.99");
            List<Pelicula> filtradas = sesion.createSelectionQuery("""
                    from Pelicula p
                    where p.idiomaId in (:idiomas) and p.tarifa <= :tope
                    order by p.id
                    """, Pelicula.class)
                    .setParameterList("idiomas", List.of(1, 2))
                    .setParameter("tope", new BigDecimal("2.99"))
                    .setMaxResults(5) // Limitado a 5 para no saturar la consola
                    .getResultList();
            filtradas.forEach(p -> System.out.println(p.getTitulo()));

            // 12.PAGINACION
            // Paginación: segunda página de 5 películas
            System.out.println("Paginación: Segunda página de 5 películas");
            int numeroPagina = 2;
            int tamano = 5;
            int inicio = (numeroPagina - 1) * tamano;
            List<Pelicula> pagina = sesion.createSelectionQuery("""
                    from Pelicula p
                    order by p.tarifa desc, p.id asc
                    """, Pelicula.class)
                    .setFirstResult(inicio)
                    .setMaxResults(tamano)
                    .getResultList();
            for (Pelicula p : pagina) {
                System.out.println(p.getId() + " | " + p.getTarifa()
                        + " | " + p.getTitulo());
            }

            // 13. PROYECCIONES
            // Proyección con Tuple: título y tarifa
            System.out.println("Proyección con Tuple: título y tarifa");
            List<Tuple> filas = sesion.createSelectionQuery("""
                    select p.titulo as titulo, p.tarifa as tarifa
                    from Pelicula p
                    order by p.id
                    """, Tuple.class)
                    .setMaxResults(5)
                    .getResultList();
            for (Tuple fila : filas) {
                String titulo = fila.get("titulo", String.class);
                BigDecimal tarifa = fila.get("tarifa", BigDecimal.class);
                System.out.println(titulo + " | " + tarifa);
            }

            // 16. JOINS
            // Filtra por el nombre del idioma
            System.out.println("Filtra por nombre del idioma");
            List<Pelicula> peliculasPorIdioma = sesion.createSelectionQuery("""
                    select p from Pelicula p
                    join p.idioma i
                    where i.nombre = :nombre
                    order by p.id
                    """, Pelicula.class)
                    .setParameter("nombre", "English")
                    .setMaxResults(5)
                    .getResultList();
            peliculasPorIdioma.forEach(p -> System.out.println(p.getTitulo()));

            // Carga el idioma en la misma consulta con join fetch
            System.out.println("Carga película e idioma con join fetch");
            List<Pelicula> peliculasConIdioma = sesion.createSelectionQuery("""
                    select p from Pelicula p
                    join fetch p.idioma
                    order by p.id
                    """, Pelicula.class)
                    .setMaxResults(5)
                    .getResultList();
            for (Pelicula p : peliculasConIdioma) {
                System.out.println(p.getTitulo() + " | " + p.getIdioma().getNombre());
            }

            // 17. AGREGACION
            // Una sola cifra para todo el catálogo
            System.out.println("Una sola cifra para todo el catálogo");
            Long total = sesion.createSelectionQuery(
                    "select count(p) from Pelicula p", Long.class)
                    .getSingleResult();
            System.out.println("Películas: " + total);

            // Total y media de tarifa por idioma
            System.out.println("Total y media de tarifa por idioma");
            List<Tuple> resumen = sesion.createSelectionQuery("""
                    select i.nombre as idioma, count(p) as total,
                    avg(p.tarifa) as media
                    from Pelicula p
                    join p.idioma i
                    where p.tarifa >= :tarifaMinima
                    group by i.id, i.nombre
                    having count(p) >= :peliculasMinimas
                    order by count(p) desc, i.id
                    """, Tuple.class)
                    .setParameter("tarifaMinima", new BigDecimal("0.99"))
                    .setParameter("peliculasMinimas", 2L)
                    .getResultList();
            for (Tuple fila : resumen) {
                System.out.println(fila.get("idioma", String.class) + " | "
                        + fila.get("total", Long.class) + " | "
                        + fila.get("media", Double.class));
            }

            // 18. SUBCONSULTAS
            // Subconsulta de agregación: supera la tarifa media
            System.out.println("Subconsulta de agregación: supera la tarifa media");
            List<Pelicula> peliculasCaras = sesion.createSelectionQuery("""
                    from Pelicula p
                    where p.tarifa > (select avg(otra.tarifa) from Pelicula otra)
                    order by p.tarifa desc, p.id
                    """, Pelicula.class)
                    .setMaxResults(5)
                    .getResultList();

            peliculasCaras.forEach(p -> System.out.println(p.getTitulo() + " | " + p.getTarifa()));

            // Subconsulta correlacionada: idiomas sin películas
            System.out.println("Subconsulta correlacionada: idiomas sin películas");
            List<String> sinPeliculas = sesion.createSelectionQuery("""
                    select i.nombre from Idioma i
                    where not exists (
                        select 1 from Pelicula p where p.idioma = i
                    )
                    order by i.nombre, i.id
                    """, String.class)
                    .getResultList();

            System.out.println("Idiomas sin películas: " + sinPeliculas);
        }
    }
}