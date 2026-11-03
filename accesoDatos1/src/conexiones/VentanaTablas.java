package conexiones;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

public class VentanaTablas extends JDialog {

    // ATRIBUSTOS
    private JComboBox<String> comboTablas = new JComboBox<>();
    private JTable tablaDatos = new JTable();
    private JButton btnImportar = new JButton("Importar productos");
    private JButton btnExportar = new JButton("Exportar productos");
    private Connection conexion;

    // CONSTRUCTOR
    public VentanaTablas(Frame padre, Connection con, String bd) {
        super(padre, "Tablas de " + bd, true);
        this.conexion = con;

        setSize(600, 400);
        setLocationRelativeTo(padre);

        JPanel panelSuperior = new JPanel(new GridLayout(1, 4, 5, 0));
        panelSuperior.add(new JLabel("Selecciona Tabla:"));
        panelSuperior.add(comboTablas);
        panelSuperior.add(btnImportar); 
        panelSuperior.add(btnExportar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tablaDatos), BorderLayout.CENTER);

        cargarListaTablas();

        // Eventos de los botones
        comboTablas.addActionListener(e -> {
            if (comboTablas.getSelectedItem() != null) {
                cargarDatosTabla(comboTablas.getSelectedItem().toString());
            }
        });
        btnImportar.addActionListener(e -> importarProductosDesdeTXT());
        btnExportar.addActionListener(e -> exportarProductosATXT());
    }

    // METODOS PROPIOS
    // 1. Cargar lista de tablas
    private void cargarListaTablas() {
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery("SHOW TABLES")) {
            while (rs.next()) comboTablas.addItem(rs.getString(1));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // 2. Cargar datos de la tabla seleccionada
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

    // 3. Importar productos desde un fichero .txt dado usando JFileChooser
    private void importarProductosDesdeTXT() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos TXT", "txt"));

        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File archivo = fc.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            JOptionPane.showMessageDialog(this, "Solo se admiten ficheros .txt");
            return;
        }

        String sql = "INSERT INTO products (ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int insertados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo));
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] v = linea.split(",");
                ps.setString(1, v[0].trim());
                ps.setInt(2, Integer.parseInt(v[1].trim()));
                ps.setInt(3, Integer.parseInt(v[2].trim()));
                ps.setString(4, v[3].trim());
                ps.setDouble(5, Double.parseDouble(v[4].trim()));
                ps.setInt(6, Integer.parseInt(v[5].trim()));
                ps.setInt(7, Integer.parseInt(v[6].trim()));
                ps.setInt(8, Integer.parseInt(v[7].trim()));
                ps.setInt(9, Integer.parseInt(v[8].trim()));

                insertados += ps.executeUpdate();
            }

            comboTablas.setSelectedItem("products");
            cargarDatosTabla("products");

            JOptionPane.showMessageDialog(this, "SE HAN IMPORTADO " + insertados + " REGISTROS.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al importar: " + e.getMessage());
        }
    }

    // 4. Exportar productos a un fichero .txt usando JFileChooser
    private void exportarProductosATXT() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos TXT", "txt"));

        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File archivo = fc.getSelectedFile();
        String nombre = archivo.getName();

        if (!nombre.contains(".")) {
            archivo = new File(archivo.getAbsolutePath() + ".txt");
        } else if (!nombre.toLowerCase().endsWith(".txt")) {
            JOptionPane.showMessageDialog(this, "Solo se admiten ficheros con extensión .txt");
            return;
        }

        if (archivo.exists()) {
            int resp = JOptionPane.showConfirmDialog(this, "¿Deseas sobrescribir el fichero existente?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp != JOptionPane.YES_OPTION) return;
        }

        String sql = "SELECT ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued FROM products";
        int exportados = 0;

        try (Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            while (rs.next()) {
                String fila = rs.getString("ProductName") + "," +
                        rs.getInt("SupplierID") + "," +
                        rs.getInt("CategoryID") + "," +
                        rs.getString("QuantityPerUnit") + "," +
                        rs.getDouble("UnitPrice") + "," +
                        rs.getInt("UnitsInStock") + "," +
                        rs.getInt("UnitsOnOrder") + "," +
                        rs.getInt("ReorderLevel") + "," +
                        rs.getInt("Discontinued");
            pw.println(fila);
            exportados++;
            }
            JOptionPane.showMessageDialog(this, "SE HAN EXPORTADO " + exportados + " REGISTROS.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage());
        }
    }
}