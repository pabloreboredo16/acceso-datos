package interfaces.gestor;

public class MainGestor {

    public static void main(String[] args) {

        Gestor<Producto> gestorProductos = new GestorProductos();
        Gestor<Cliente> gestorClientes = new GestorClientes();

        System.out.println("PRODUCTOS");
        gestorProductos.anadir(new Producto("P01", "Teclado", 29.99));
        gestorProductos.anadir(new Producto("P02", "Monitor", 180.00));

        System.out.println("Listado:");
        for (Producto p : gestorProductos.listar()) {
            System.out.println(p);
        }

        System.out.println("\nBuscar producto P01:");
        System.out.println(gestorProductos.buscar("P01"));

        System.out.println("\nEliminar producto P01");
        gestorProductos.eliminar("P01");

        System.out.println("Listado tras eliminar:");
        for (Producto p : gestorProductos.listar()) {
            System.out.println(p);
        }


        
        System.out.println("\nCLIENTES");
        gestorClientes.anadir(new Cliente("12345678A", "María García"));
        gestorClientes.anadir(new Cliente("87654321B", "Juan Pérez"));

        System.out.println("Listado:");
        for (Cliente c : gestorClientes.listar()) {
            System.out.println(c);
        }

        System.out.println("\nBuscar cliente 87654321B:");
        System.out.println(gestorClientes.buscar("87654321B"));
    }
}
