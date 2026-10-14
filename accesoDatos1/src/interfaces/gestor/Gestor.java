package interfaces.gestor;

import java.util.List;

//T representa un parametro genérico, que puede ser cualquiera que implemente la interfaz
public interface Gestor<T> {
    void anadir(T elemento);
    T buscar(String id);
    void eliminar(String id);
    List<T> listar();
}