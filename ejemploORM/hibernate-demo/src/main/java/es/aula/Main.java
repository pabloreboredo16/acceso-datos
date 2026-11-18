package es.aula;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
        }
    }
}