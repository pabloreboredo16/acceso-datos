package conexiones;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ConexionSwing extends JFrame {

    private JTextField txtUsuario = new JTextField("root", 12);
    private JPasswordField txtContrasena = new JPasswordField(12);
    private JTextField txtServidor = new JTextField("127.0.0.1", 8);
    private JTextField txtPuerto = new JTextField("3306", 4);
    private JComboBox<String> comboBaseDatos = new JComboBox<>();
    private JButton btnCargarBD = new JButton("Cargar BDs");
    private JButton btnConectar = new JButton("Conectar");
    private JButton btnTablas = new JButton("Mostrar Tablas");
    private boolean conectado = false;

    public ConexionSwing() {

        setTitle("MySQL");
        setSize(525, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelMain = new JPanel(new GridLayout(6, 2, 5, 5));

        panelMain.add(new JLabel("Usuario:")); 
        panelMain.add(txtUsuario);
        panelMain.add(new JLabel("Contraseña:")); 
        panelMain.add(txtContrasena);
        panelMain.add(new JLabel("Servidor:")); 
        panelMain.add(txtServidor);
        panelMain.add(new JLabel("Puerto:")); 
        panelMain.add(txtPuerto);
        panelMain.add(new JLabel("Base de datos:"));
        
        JPanel panelBD = new JPanel(new GridLayout(1, 2, 5, 0));
        panelBD.add(comboBaseDatos);
        panelBD.add(btnCargarBD);
        panelMain.add(panelBD);

        panelMain.add(new JLabel("")); 
        
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 5, 0));
        panelBotones.add(btnConectar);
        panelBotones.add(btnTablas);
        panelMain.add(panelBotones);
        
        add(panelMain);

        // Eventos de los bottones
        btnCargarBD.addActionListener(e -> cargarBasesDeDatos());
        btnConectar.addActionListener(e -> conectar());
        btnTablas.addActionListener(e -> mostrarTablas());
    }

    // 2. Validaciones
    private boolean validarCampos() {
        if (txtUsuario.getText().trim().isEmpty() || txtServidor.getText().trim().isEmpty() || txtPuerto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa Usuario, Servidor y Puerto.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            int p = Integer.parseInt(txtPuerto.getText().trim());
            if (p < 0 || p > 45003) throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "El puerto debe ser numérico entre 0 y 45003.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Método para obtener la conexión
    private Connection getConexion(String bd) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://" + txtServidor.getText().trim() + ":" + txtPuerto.getText().trim() + "/" + bd;
        return DriverManager.getConnection(url, txtUsuario.getText().trim(), new String(txtContrasena.getPassword()));
    }

    // 4. Metodo pata cargar BDs en JComboBox
    private void cargarBasesDeDatos() {
        if (!validarCampos()) return;

        try (Connection con = getConexion(""); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SHOW DATABASES")) {
            comboBaseDatos.removeAllItems();
            while (rs.next()) comboBaseDatos.addItem(rs.getString(1));
            JOptionPane.showMessageDialog(this, "Bases de datos cargadas.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 1, 3. Conectar y ejecutar consulta SELECT
    private void conectar() {

        if (conectado) {
            JOptionPane.showMessageDialog(this, "Ya estás conectado a la base de datos.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!validarCampos()) return;
        String bd = comboBaseDatos.getSelectedItem() != null ? comboBaseDatos.getSelectedItem().toString() : "";
        
        try (Connection con = getConexion(bd)) {
            conectado = true;
            JOptionPane.showMessageDialog(this, "Conexión realizada con éxito.", "Conectado", JOptionPane.INFORMATION_MESSAGE);
            
            if (!bd.isEmpty()) {
                try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM categories")) {
                    System.out.println("CONSULTA SELECT");
                    while (rs.next()) System.out.println(rs.getObject(1) + " | " + rs.getString(2));
                } catch (Exception e) {
                    System.out.println("Consulta finalizada / Tabla no existe.");
                }
            }
        } catch (Exception e) {
            conectado = false;
            JOptionPane.showMessageDialog(this, "Imposible conectar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTablas() {
        if (!conectado) {
            JOptionPane.showMessageDialog(this, "Debes conectarte antes de mostrar las tablas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validarCampos()) return;
        String bd = (String) comboBaseDatos.getSelectedItem();

        try {
            Connection con = getConexion(bd);
            new VentanaTablas(this, con, bd).setVisible(true);
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConexionSwing().setVisible(true));
    }
}