import java.nio.file.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class PracticaXML {

    static void configurarSeguridad(DocumentBuilderFactory f) throws Exception {
        f.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
    }

    static void configurarSeguridad(SAXParserFactory f) throws Exception {
        f.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
    }

    static void listarDOM(Path p) throws Exception { 
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        configurarSeguridad(dbf);
        Document doc = dbf.newDocumentBuilder().parse(p.toFile());
        doc.getDocumentElement().normalize();

        NodeList lista = doc.getElementsByTagName("alumno");

        for (int i = 0; i < lista.getLength(); i++) {
            Element elemento = (Element) lista.item(i);
            String id = elemento.getAttribute("id");
            String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
            String ciclo = elemento.getElementsByTagName("ciclo").item(0).getTextContent();
            String nota = elemento.getElementsByTagName("nota").item(0).getTextContent();

            System.out.println("Alumno [ID: " + id + ", Nombre: " + nombre + ", Ciclo: " + ciclo + ", Nota: " + nota + "]");
        }
    }

    static void actualizarDOM(Path origen,Path destino,int id,double nota) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        configurarSeguridad(dbf);
        Document doc = dbf.newDocumentBuilder().parse(origen.toFile());

        NodeList lista = doc.getElementsByTagName("alumno");

        boolean encontrado = false;
        for (int i = 0; i < lista.getLength(); i++) {
            Element elem = (Element) lista.item(i);
            if (Integer.parseInt(elem.getAttribute("id")) == id) {
                elem.getElementsByTagName("nota").item(0).setTextContent(String.valueOf(nota));
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("Alumno no encontrado: " + id);
        }
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource(doc), new StreamResult(destino.toFile()));
    }

    static void resumenSAX(Path p,String ciclo) throws Exception { 
        SAXParserFactory spf = SAXParserFactory.newInstance();
        configurarSeguridad(spf);
        SAXParser parser = spf.newSAXParser();

        ResumenHandler handler = new ResumenHandler(ciclo);
        parser.parse(p.toFile(), handler);

        // Imprimir resumen
        if (handler.contador > 0) {
            double media = handler.sumaNotas / handler.contador;
            System.out.println("Resumen SAX (" + ciclo + "): " + handler.contador + " alumnos, media = " + media);
        } else {
            System.out.println("Resumen SAX (" + ciclo + "): No se encontraron alumnos.");
        }
    }

    static class ResumenHandler extends DefaultHandler {
        private String cicloBuscado;
        private String cicloActual = "";
        private StringBuilder textoAcumulado = new StringBuilder();
        double sumaNotas = 0;
        int contador = 0;
        private double notaActual = 0;

        public ResumenHandler(String cicloBuscado) {
            this.cicloBuscado = cicloBuscado;
        }

        @Override
        public void startElement(String uri,String local,String q,Attributes a) {
            textoAcumulado.setLength(0);
        }

        @Override
        public void characters(char[] ch,int inicio,int longitud) {
            textoAcumulado.append(ch, inicio, longitud);
        }

        @Override
        public void endElement(String uri,String local,String q) {
            String texto = textoAcumulado.toString().trim();

            if (q.equals("ciclo")) {
                cicloActual = texto;
            } else if (q.equals("nota") && !texto.isEmpty()) {
                notaActual = Double.parseDouble(texto); 
            } else if (q.equals("alumno")) {
                if (cicloActual.equals(cicloBuscado)) {
                    sumaNotas += notaActual;
                    contador++;
                }
                cicloActual = "";
            }
        }
    }
    public static void main(String[] args) {
        try {
            Path p=Path.of("alumnos.xml");
            listarDOM(p);
            actualizarDOM(p,Path.of("alumnos_actualizados.xml"),20,9.0);
            resumenSAX(p,"DAM"); resumenSAX(p,"ASIR");
        } catch (Exception e) { 
            System.err.println(e.getClass().getSimpleName()+": "+e.getMessage()); 
        }

        // Añade pruebas negativas independientes.
        System.out.println("\nPRUEBA 1 (Buscar ID inexistente):");
        try {
            actualizarDOM(Path.of("alumnos.xml"), Path.of("alumnos_actualizados.xml"), 99, 9.0);
        } catch (Exception e) {
            System.err.println("Excepcion: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        System.out.println("\nPRUEBA 2 (Fichero malformado)");
        try {
            resumenSAX(Path.of("alumnos-roto.xml"), "DAM");
        } catch (Exception e) {
            System.err.println("Excepcion: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}