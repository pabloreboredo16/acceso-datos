package ficherosXML;

import org.w3c.dom.*;

import ficherosUtilidades.UtilidadesFicheros;

import javax.xml.parsers.*;
import java.io.File;

public class LeerEmpleadoXML {

    public static void main(String[] args) {

        File archivo = UtilidadesFicheros.devolverFichero();

        if (!archivo.exists()) {
            System.err.println("El archivo " + archivo.getName() + " no existe. Asegúrate de haber ejecutado primero CrearEmpleadoXml.");
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivo);
            document.getDocumentElement().normalize();

            System.out.println(document.getDocumentElement().getNodeName());

            NodeList listaEmpleados = document.getElementsByTagName("Empleado");

            for (int i = 0; i < listaEmpleados.getLength(); i++) {
                Node nodo = listaEmpleados.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    String id = getNodoValor("id", elemento);
                    String apellido = getNodoValor("apellido", elemento);
                    String departamento = getNodoValor("departamento", elemento);
                    String salario = getNodoValor("salario", elemento);

                    System.out.printf("ID: %-3s | Apellido: %-12s | Dep: %-3s | Salario: %s €%n",
                            id, apellido, departamento, salario);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al leer el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getNodoValor(String etiqueta, Element elemento) {
        NodeList lista = elemento.getElementsByTagName(etiqueta);
        if (lista.getLength() > 0 && lista.item(0).getFirstChild() != null) {
            return lista.item(0).getTextContent();
        }
        return "";
    }
}