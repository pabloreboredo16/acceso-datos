package conexiones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaTablas extends JDialog {

    private JComboBox<String> comboTablas = new JComboBox<>();
    private JTable tablaDatos = new JTable();
    private Connection conexion;

    public VentanaTablas(Frame padre, Connection con, String bd) {
        super(padre, "Tablas de " + bd, true);
        this.conexion = con;

        setSize(600, 400);
        setLocationRelativeTo(padre);

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 5, 0));
        panelSuperior.add(new JLabel("Selecciona Tabla:"));
        panelSuperior.add(comboTablas);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaDatos), BorderLayout.CENTER);

        cargarListaTablas();

        comboTablas.addActionListener(e -> {
            if (comboTablas.getSelectedItem() != null) {
                cargarDatosTabla(comboTablas.getSelectedItem().toString());
            }
        });
    }

    private void cargarListaTablas() {
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery("SHOW TABLES")) {
            while (rs.next()) comboTablas.addItem(rs.getString(1));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void cargarDatosTabla(String tabla) {
        DefaultTableModel model = new DefaultTableModel();
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM " + tabla)) {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            for (int i = 1; i <= cols; i++) model.addColumn(meta.getColumnName(i));

            while (rs.next()) {
                Object[] fila = new Object[cols];
                for (int i = 1; i <= cols; i++) fila[i - 1] = rs.getObject(i);
                model.addRow(fila);
            }
            tablaDatos.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}