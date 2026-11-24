package es.aula;

import java.util.List;
import java.util.Comparator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainActoresPeliculas {

    /*
    1. LOCALIZA LAS CLAVES EN MYSQL
        Clave primaria actor: actor_id
        Claves foraneas de film_actor: actor_id, film_id
        Clave primaria de film_actor: formada por las dos claves foraneas: actor_id, film_id
        Un mismo actor_id aparece varias veces en film_actor porque un mismo actor puede aparecer en varias peliculas
    */

    public static void main(String[] args) {

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulaPU");
             EntityManager em = emf.createEntityManager()) {

            // 2. MAPEA ACTOR Y PRUEBA EL LISTADO
            List<Actor> actores = em.createQuery(
                    "from Actor a order by a.actorId", Actor.class)
                    .getResultList();

            for (Actor a : actores) {
                System.out.println(a.getActorId() + " | " + a.getFirstName() + " | " + a.getLastName());
            }
            System.out.println("Total de actores: " + actores.size());

            // 4. PELICULAS DEL ACTOR id_actor=1 POR ORDEN ALFABETICO
            Actor actor1 = em.find(Actor.class, 1);
            if (actor1 != null) {
                System.out.println("Películas de " + actor1.getFirstName() + " " + actor1.getLastName() + ":");

                List<Film> pelisActor1 = actor1.getFilms().stream()
                        .sorted(Comparator.comparing(Film::getTitle))
                        .toList();

                for (Film f : pelisActor1) {
                    System.out.println("- " + f.getTitle());
                }
                System.out.println("Total de películas del actor 1: " + pelisActor1.size());
            }
        }
    }
}
