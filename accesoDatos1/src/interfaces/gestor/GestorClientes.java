package interfaces.gestor;

import java.util.ArrayList;
import java.util.List;

public class GestorClientes implements Gestor<Cliente> {
    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public void anadir(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente añadido: " + cliente);
    }

    @Override
    public Cliente buscar(String id) { 
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String id) {
        Cliente c = buscar(id);
        if (c != null) {
            clientes.remove(c);
            System.out.println("Cliente eliminado: " + c);
        } else {
            System.out.println("Cliente no encontrado");
        }
    }

    @Override
    public List<Cliente> listar() {
        return clientes;
    }
}