package interfaces.gestor;

import java.util.ArrayList;
import java.util.List;

public class GestorProductos implements Gestor<Producto> {

    private List<Producto> productos = new ArrayList<>();

    @Override
    public void anadir(Producto producto) {
        productos.add(producto);
        System.out.println("Producto añadido: " + producto);
    }

    @Override
    public Producto buscar(String id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String id) {
        Producto p = buscar(id);
        if (p != null) {
            productos.remove(p);
            System.out.println("Producto eliminado: " + p);
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    @Override
    public List<Producto> listar() {
        return productos;
    }
}