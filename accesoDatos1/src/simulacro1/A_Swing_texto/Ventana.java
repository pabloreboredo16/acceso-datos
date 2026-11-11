import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.List;

public class Ventana extends JFrame {
    final JTextField nombre = new JTextField(15), nota = new JTextField(5);
    final JComboBox<String> ciclo = new JComboBox<>(new String[]{"DAM","DAW","ASIR"});
    final DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nombre","Ciclo","Nota"},0) {
        public boolean isCellEditable(int fila,int columna) { return false; }
    };

    final JTable tabla = new JTable(modelo);
    final JButton 
        alta = new JButton("Agregar"), baja = new JButton("Eliminar"),
        guardar = new JButton("Guardar"), abrir = new JButton("Abrir");

    public Ventana() {
        super("Alumnos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        tabla.setAutoCreateRowSorter(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alta.addActionListener(e -> agregar()); baja.addActionListener(e -> eliminar());
        guardar.addActionListener(e -> guardar()); abrir.addActionListener(e -> abrir());
        construirUI(); setSize(650,400); setLocationRelativeTo(null);
    }

    /* Crear y asignar paneles con layouts */
    void construirUI() {
        JPanel panelEntrada = new JPanel();
        panelEntrada.add(new JLabel("Nombre:"));
        panelEntrada.add(nombre);
        panelEntrada.add(new JLabel("Ciclo:"));
        panelEntrada.add(ciclo);
        panelEntrada.add(new JLabel("Nota:"));
        panelEntrada.add(nota);
        panelEntrada.add(alta);

        JPanel panelBotones = new JPanel();
        panelBotones.add(baja);
        panelBotones.add(abrir);
        panelBotones.add(guardar);

        setLayout(new BorderLayout());
        add(panelEntrada, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    void agregar() {
        try {
            String txtNombre = nombre.getText();
            String selCiclo = (String) ciclo.getSelectedItem();
            String txtNota = nota.getText();            

            // Validar campos obligatorios
            if (txtNombre.isEmpty() || txtNota.isEmpty()) {
                throw new IllegalArgumentException("Debes introducir el nombre y la nota.");
            }

            // Validar rango numérico de la nota
            double valNota = Double.parseDouble(txtNota);
            if (valNota < 0 || valNota > 10) {
                throw new IllegalArgumentException("La nota debe estar comprendida entre 0 y 10.");
            }

            // Añadir fila a ña tabla
            modelo.addRow(new Object[]{txtNombre, selCiclo, txtNota});

            // Limpia el formulario
            nombre.setText("");
            nota.setText("");
            nombre.requestFocusInWindow();

        } catch (NumberFormatException e) {
            error(new Exception("La nota debe ser un número válido."));
        } catch (Exception e) { 
            error(e);
        }
    }

    void eliminar() {
        int vista = tabla.getSelectedRow();

        if (vista != -1) {
            int filaModelo = tabla.convertRowIndexToModel(vista);
            modelo.removeRow(filaModelo);
        } else {
            error(new Exception("Por favor, selecciona primero una fila de la tabla para eliminar."));
        }
    }

    void guardar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(".")); 
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            // Escribir datos separados por punto y coma
            try (BufferedWriter writer = Files.newBufferedWriter(archivo.toPath(), StandardCharsets.UTF_8)) {
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String linea = modelo.getValueAt(i, 0) + ";" +
                                   modelo.getValueAt(i, 1) + ";" +
                                   modelo.getValueAt(i, 2);
                    writer.write(linea);
                    writer.newLine(); 
                }
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
            } catch (Exception e) {
                error(e);
            }
        }
    }

    void abrir() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try {
                List<String> lineas = Files.readAllLines(archivo.toPath(), StandardCharsets.UTF_8);
                
                // Vaciar tabla antes de cargar el archivo
                modelo.setRowCount(0);

                for (int i = 0; i < lineas.size(); i++) {
                    String linea = lineas.get(i).trim();
                    if (linea.isEmpty()) continue; 

                    String[] partes = linea.split(";", -1);
                    if (partes.length != 3) {
                        throw new IllegalArgumentException("Error en la línea " + (i + 1) + ": formato incorrecto.");
                    }

                    // Validar formato numérico de la nota
                    Double.parseDouble(partes[2]);

                    modelo.addRow(partes);    
                }
            } catch (NumberFormatException e) {
                // Limpia la tabla en caso de error numerico
                modelo.setRowCount(0);
                error(new Exception("El archivo contiene notas con formato inválido."));
            } catch (Exception e) {
                error(e);
            }
        }
    }

    void error(Exception e) { 
        JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new Ventana().setVisible(true)); }
}