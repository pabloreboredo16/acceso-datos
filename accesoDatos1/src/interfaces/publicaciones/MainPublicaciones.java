package interfaces.publicaciones;

public class MainPublicaciones {

    public static int cuentaPrestados(Object[] objetos) {
        int contador = 0;
        for (Object obj : objetos) {
            if (obj instanceof Prestable) {
                Prestable p = (Prestable) obj;
                if (p.estaPrestado()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public static int publicacionesAnterioresA(Publicacion[] publicaciones, int ano) {
        int contador = 0;
        for (Publicacion pub : publicaciones) {
            if (pub.getAnoPublicacion() < ano) {
                contador++;
            }
        }
        return contador;
    }

    public static void main(String[] args) {
        Publicacion[] publicaciones = new Publicacion[4];
        publicaciones[0] = new Libro("L01", "Don Quijote de la Mancha", 1605);
        publicaciones[1] = new Libro("L02", "Cien años de soledad", 1967);
        publicaciones[2] = new Revista("R01", "National Geographic", 1988, 120);
        publicaciones[3] = new Revista("R02", "Muy Interesante", 2005, 300);

        // Publicaciones guardadas
        System.out.println("PUBLICACIONES GUARDADAS");
        for (Publicacion pub : publicaciones) {
            System.out.println(pub);
        }

        // Prestar un libro
        ((Libro) publicaciones[0]).prestar();

        // Publicaciones prestadas
        int prestados = cuentaPrestados(publicaciones);
        System.out.println("\nNúmero de elementos prestados: " + prestados);

        // Publicaciones anteriores a 1990
        int anteriores = publicacionesAnterioresA(publicaciones, 1990);
        System.out.println("Número de publicaciones anteriores a 1990: " + anteriores);
    }
}