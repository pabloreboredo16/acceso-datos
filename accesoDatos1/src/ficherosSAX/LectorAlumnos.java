package ficherosSAX;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class LectorAlumnos {

    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException {
        XMLReader procesadorXML = XMLReaderFactory.createXMLReader();

        GestionAlumnos gestor = new GestionAlumnos();
        procesadorXML.setContentHandler(gestor);
        
        InputSource fileXML = new InputSource("accesoDatos1/src/ficherosSAX/alumnos.xml");
        procesadorXML.parse(fileXML);
    }
}