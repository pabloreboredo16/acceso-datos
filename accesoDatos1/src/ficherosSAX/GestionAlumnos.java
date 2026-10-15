package ficherosSAX;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GestionAlumnos extends DefaultHandler {

    private StringBuilder cadenaTexto;

    // ATRIBUTOS
    private String id;
    private String nombre;
    private String apellidos;
    private String ciclo;
    private double nota;

    // VARIABLES
    private int totalAlumnos = 0;
    private double sumaNotas = 0.0;
    private String alumnoNotaMax = "";
    private double notaMax = -1.0;
    private String alumnoNotaMin = "";
    private double notaMin = 11.0;
    private int aprobados = 0;
    private int suspensos = 0;

    // CONSTRUCTOR
    public GestionAlumnos() {
        super();
        cadenaTexto = new StringBuilder();
    }

    // Mensaje al iniciar la lectura del documento
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Comienzo del Documento XML");
    }

    // Lectura de etiquetas y atributos
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        cadenaTexto.setLength(0);
        if (qName.equalsIgnoreCase("alumno")) {
            id = attributes.getValue("id");
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

        if (qName.equalsIgnoreCase("nombre")) {
            nombre = valor;
        } else if (qName.equalsIgnoreCase("apellidos")) {
            apellidos = valor;
        } else if (qName.equalsIgnoreCase("ciclo")) {
            ciclo = valor;
        } else if (qName.equalsIgnoreCase("nota")) {
            nota = Double.parseDouble(valor);
        } else if (qName.equalsIgnoreCase("alumno")) {
            // 2. Muestra por consola con el formato exacto: [id] Apellidos, Nombre (Ciclo) - Nota: X.XX
            System.out.printf("[%s] %s, %s (%s) - Nota: %.2f%n", id, apellidos, nombre, ciclo, nota);

            totalAlumnos++;
            sumaNotas += nota;

            if (nota >= 5.0) {
                aprobados++;
            } else {
                suspensos++;
            }

            if (nota > notaMax) {
                notaMax = nota;
                alumnoNotaMax = apellidos + ", " + nombre + " (" + nota + ")";
            }

            if (nota < notaMin) {
                notaMin = nota;
                alumnoNotaMin = apellidos + ", " + nombre + " (" + nota + ")";
            }
        }
    }

    // Mensaje al finalizar y muestra de estadísticas requeridas
    @Override
    public void endDocument() throws SAXException {
        System.out.println("Final del Documento XML");
        System.out.println("Número total de alumnos leídos: " + totalAlumnos);

        if (totalAlumnos > 0) {
            double notaMedia = sumaNotas / totalAlumnos;
            System.out.printf("Nota media de todos los alumnos: %.2f%n", notaMedia);
            System.out.println("Alumno con la nota más alta: " + alumnoNotaMax);
            System.out.println("Alumno con la nota más baja: " + alumnoNotaMin);
            System.out.println("Número de alumnos aprobados: " + aprobados);
            System.out.println("Número de alumnos suspensos: " + suspensos);
        }
    }
}