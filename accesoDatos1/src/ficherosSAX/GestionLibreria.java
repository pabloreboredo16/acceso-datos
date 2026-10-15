package ficherosSAX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class GestionLibreria extends DefaultHandler {

    private StringBuilder cadenaTexto;

    // ATRIBUTOS
    private String isbn;
    private String idioma;
    private String titulo;
    private List<String> autores;
    private String editorial;
    private int anio;
    private double precio;
    private String moneda;

    // VARIABLES 
    private List<String> librosPost2010;
    private Map<String, Integer> librosPorIdioma;
    private double sumaPrecioEuros = 0.0;
    private int contadorLibrosEuros = 0;
    private String libroMasAutores = "";
    private int maxAutores = 0;

    // CONSTRUCTOR
    public GestionLibreria() {
        super();
        cadenaTexto = new StringBuilder();
        autores = new ArrayList<>();
        librosPost2010 = new ArrayList<>();
        librosPorIdioma = new HashMap<>();
    }

    // Mensaje al iniciar la lectura del documento
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Comienzo del Documento XML - Catálogo de Librería");
    }

    // Lectura de etiquetas y atributos
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        cadenaTexto.setLength(0);

        if (qName.equalsIgnoreCase("libro")) {
            // Reiniciar la lista de autores y datos por cada libro nuevo
            autores.clear();
            isbn = attributes.getValue("isbn");
            idioma = attributes.getValue("idioma");
        } else if (qName.equalsIgnoreCase("precio")) {
            moneda = attributes.getValue("moneda");
        }
    }

    // Acumulación de texto del elemento
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        cadenaTexto.append(ch, start, length);
    }

    // Procesamiento en endElement()
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String valor = cadenaTexto.toString().trim();

        if (qName.equalsIgnoreCase("titulo")) {
            titulo = valor;
        } else if (qName.equalsIgnoreCase("autor")) {
            autores.add(valor);
        } else if (qName.equalsIgnoreCase("editorial")) {
            editorial = valor;
        } else if (qName.equalsIgnoreCase("anio")) {
            try {
                anio = Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                anio = 0;
            }
        } else if (qName.equalsIgnoreCase("precio")) {
            try {
                precio = Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                precio = 0.0;
            }
        } else if (qName.equalsIgnoreCase("libro")) {
            String cadenaAutores = String.join(", ", autores);

            String infoLibro = String.format("%s (%d) - %s - %s - %.2f %s - [%s] - %s",
                    titulo, anio, cadenaAutores, editorial, precio, moneda, isbn, idioma);
            System.out.println(infoLibro);

            if (anio >= 2010) {
                librosPost2010.add(infoLibro);
            }

            if (idioma != null) {
                librosPorIdioma.put(idioma, librosPorIdioma.getOrDefault(idioma, 0) + 1);
            }

            if ("EUR".equalsIgnoreCase(moneda)) {
                sumaPrecioEuros += precio;
                contadorLibrosEuros++;
            }

            if (autores.size() > maxAutores) {
                maxAutores = autores.size();
                libroMasAutores = titulo + " (" + maxAutores + " autores)";
            }
        }
    }

    // Mensaje al finalizar y muestra de estadísticas requeridas
    @Override
    public void endDocument() throws SAXException {
        System.out.println("Final del Documento XML");
        System.out.println("\n--- RESULTADOS Y ESTADÍSTICAS ---");

        System.out.println("\nLibros publicados a partir de 2010:");
        for (String l : librosPost2010) {
            System.out.println(" - " + l);
        }

        System.out.println("\nLibros por idioma:");
        for (Map.Entry<String, Integer> entry : librosPorIdioma.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nPrecios en EUR:");
        System.out.printf(" - Precio total (EUR): %.2f $%n", sumaPrecioEuros);
        if (contadorLibrosEuros > 0) {
            System.out.printf(" - Precio medio (EUR): %.2f $%n", (sumaPrecioEuros / contadorLibrosEuros));
        } else {
            System.out.println(" - No se encontraron libros con moneda EUR.");
        }

        System.out.println("\nLibro con más autores: " + libroMasAutores);
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        System.out.printf("[AVISO] Línea %d, Columna %d: %s%n",
                e.getLineNumber(), e.getColumnNumber(), e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.out.printf("[ERROR] Línea %d, Columna %d: %s%n",
                e.getLineNumber(), e.getColumnNumber(), e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.printf("[ERROR FATAL] Línea %d, Columna %d: %s%n",
                e.getLineNumber(), e.getColumnNumber(), e.getMessage());
        throw e; 
    }
}