package conexiones;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VentanaActualizarPrecios extends JDialog{

    // ATRIBUTOS
    private JComboBox<String> comboCategorias = new JComboBox<>();
    private JTextField txtPorcentaje = new JTextField();
    private JButton btnActualizar = new JButton("ACTUALIZAR PRECIOS CATEGORÍA");
    private Connection conexion;

    // CONSTRUCTOR
    public VentanaActualizarPrecios(Frame padre, Connection con) {
        super(padre, "Actualización de precios", true);
        this.conexion = con;

        setSize(400, 180);
        setLocationRelativeTo(padre);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelMain = new JPanel(new GridLayout(3, 2, 5, 10));

        panelMain.add(new JLabel("Categoría:"));
        panelMain.add(comboCategorias);
        panelMain.add(new JLabel("Porcentaje (%):"));
        panelMain.add(txtPorcentaje);
        panelMain.add(new JLabel("")); 
        panelMain.add(btnActualizar);

        add(panelMain);

        cargarCategoriasConProductos();
        btnActualizar.addActionListener(e -> ejecutarActualizacion());
    }

    // METODOS PROPIOS
    // 1. Cargar categorias con productos en el JComboBox
    private void cargarCategoriasConProductos() {
        String sql = "SELECT DISTINCT c.CategoryID, c.CategoryName " +
                     "FROM categories c " +
                     "INNER JOIN products p ON c.CategoryID = p.CategoryID " +
                     "ORDER BY c.CategoryID";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            comboCategorias.removeAllItems();

            while (rs.next()) {
                int id = rs.getInt("CategoryID");
                String nombre = rs.getString("CategoryName");
                comboCategorias.addItem(id + " - " + nombre);
            }

            if (comboCategorias.getItemCount() == 0) {
                btnActualizar.setEnabled(false);
                JOptionPane.showMessageDialog(this, "No se encontraron categorías con productos asociados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 2. Actualizar precios de productos de la categoría seleccionada
    private void ejecutarActualizacion() {
        if (comboCategorias.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoría.");
            return;
        }

        int porcentaje;
        try {
            porcentaje = Integer.parseInt(txtPorcentaje.getText().trim());
            if (porcentaje < 0) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe ser entero positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El porcentaje debe ser un número entero.");
            return;
        }

        if (porcentaje == 0) {
            JOptionPane.showMessageDialog(this, "El porcentaje es 0, no habrá cambios.");
            return;
        }

        String seleccion = comboCategorias.getSelectedItem().toString();
        String[] partes = seleccion.split(" - ");
        int idCat = Integer.parseInt(partes[0]);
        String nombreCat = partes[1];

        int resp = JOptionPane.showConfirmDialog(this, 
            "¿Actualizar precios de '" + nombreCat + "' un " + porcentaje + "%?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resp != JOptionPane.YES_OPTION) return;

        btnActualizar.setEnabled(false);

        String sql = "UPDATE products SET UnitPrice = ROUND(UnitPrice * (1 + ? / 100.0), 2) WHERE CategoryID = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, porcentaje);
            ps.setInt(2, idCat);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "Se han actualizado " + filas + " productos.");
            } else {
                JOptionPane.showMessageDialog(this, "No se ha modificado ninguno.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            btnActualizar.setEnabled(true);
        }
    }


}