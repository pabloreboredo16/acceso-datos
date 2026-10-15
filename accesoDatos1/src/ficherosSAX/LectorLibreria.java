package ficherosSAX;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class LectorLibreria {

    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException {
        XMLReader procesadorXML = XMLReaderFactory.createXMLReader();

        GestionLibreria gestor = new GestionLibreria();
        procesadorXML.setContentHandler(gestor);
        procesadorXML.setErrorHandler(gestor);

        InputSource fileXML = new InputSource("accesoDatos1/src/ficherosSAX/libreria.xml");
        procesadorXML.parse(fileXML);
    }
}