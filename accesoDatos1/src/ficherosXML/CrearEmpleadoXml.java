package ficherosXML;

import org.w3c.dom.*;

import ficherosUtilidades.UtilidadesFicheros;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class CrearEmpleadoXml {

        public static void main(String[] args) throws IOException, ParserConfigurationException {

        File fichero = UtilidadesFicheros.devolverFichero();
        RandomAccessFile file=new RandomAccessFile(fichero,"r");
        Double salario;
        int id,dep,posicion=0;
        char [] apellido=new char[10];
        char [] aux=new char[10];
        DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder=factory.newDocumentBuilder();
            DOMImplementation implementation=builder.getDOMImplementation();
            Document doc= implementation.createDocument(null,"Empleados",null);
            doc.setXmlVersion("1.0");
            for(;;) {
                file.seek(posicion);
                id = file.readInt();
                for (int i = 0; i < apellido.length; i++) {
                    aux[i] = file.readChar();
                    apellido[i] = aux[i];
                }
                String apellidos = new String(apellido);
                dep = file.readInt();
                salario = file.readDouble();
                if (id > 0) {
                    Element raiz = doc.createElement("Empleado");
                    doc.getDocumentElement().appendChild(raiz);
                    CrearElemento("id", String.valueOf(id), raiz, doc);
                    CrearElemento("apellido", apellidos.trim(), raiz, doc);
                    CrearElemento("departamento", Integer.toString(dep), raiz, doc);
                    CrearElemento("salario", String.valueOf(salario), raiz, doc);
                }

                posicion += 36;
                if (file.getFilePointer() == file.length()) {
                    break;
                }
            }
            Source source=new DOMSource(doc);
            Result result=new StreamResult(new java.io.File("Empleados.xml"));
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            transformer.transform(source,result);

                }catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (TransformerConfigurationException ex) {
            throw new RuntimeException(ex);
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        } catch(Exception e) {
            e.printStackTrace();
        }

    file.close();


    }
    static void CrearElemento(String datoEmp,String valor, Element raiz, Document document) {
        Element elem = document.createElement(datoEmp);
        Text text = document.createTextNode(valor);
        elem.appendChild(text);
        raiz.appendChild(elem);
    }
}



