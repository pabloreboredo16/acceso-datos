package swingNumeroRacional;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainRacional extends JFrame {

    public MainRacional() {
        JFrame frame = new JFrame("My Awesome Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(603, 326);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#1e1e1e"));

        // Fuente estándar limpia (puedes cambiar "SansSerif" por otra si prefieres)
        Font fuenteLabel = new Font("SansSerif", Font.PLAIN, 14);

        JLabel titulo = new JLabel("Introduce número racional");
        titulo.setBounds(55, 15, 180, 17);
        titulo.setFont(fuenteLabel);
        titulo.setForeground(Color.decode("#D9D9D9"));
        panel.add(titulo);

        JLabel numerador = new JLabel("Numerador (<100)");
        numerador.setBounds(55, 70, 150, 17);
        numerador.setFont(fuenteLabel);
        numerador.setForeground(Color.decode("#D9D9D9"));
        panel.add(numerador);

        JLabel denominador = new JLabel("Denominador  (>-5)");
        denominador.setBounds(55, 128, 150, 17);
        denominador.setFont(fuenteLabel);
        denominador.setForeground(Color.decode("#D9D9D9"));
        panel.add(denominador);

        JLabel resultado = new JLabel("Resultado");
        resultado.setBounds(55, 202, 105, 17);
        resultado.setFont(fuenteLabel);
        resultado.setForeground(Color.decode("#D9D9D9"));
        panel.add(resultado);

        JTextField txtNumerador = new JTextField("");
        txtNumerador.setBounds(200, 70, 105, 25);
        txtNumerador.setFont(fuenteLabel);
        txtNumerador.setBackground(Color.decode("#B2B2B2"));
        txtNumerador.setForeground(Color.decode("#353535"));
        txtNumerador.setBorder(new EmptyBorder(2, 5, 2, 5));
        panel.add(txtNumerador);

        JTextField txtDenominador = new JTextField("");
        txtDenominador.setBounds(200, 128, 105, 25);
        txtDenominador.setFont(fuenteLabel);
        txtDenominador.setBackground(Color.decode("#B2B2B2"));
        txtDenominador.setForeground(Color.decode("#353535"));
        txtDenominador.setBorder(new EmptyBorder(2, 5, 2, 5));
        panel.add(txtDenominador);

        JTextField txtResultado = new JTextField("");
        txtResultado.setBounds(200, 202, 105, 25);
        txtResultado.setFont(fuenteLabel);
        txtResultado.setBackground(Color.decode("#B2B2B2"));
        txtResultado.setForeground(Color.decode("#353535"));
        txtResultado.setEditable(false); // Es mejor que el resultado sea de solo lectura
        txtResultado.setBorder(new EmptyBorder(2, 5, 2, 5));
        panel.add(txtResultado);

        JButton boton = new JButton("Continuar");
        boton.setBounds(345, 65, 106, 29);
        boton.setBackground(Color.decode("#2e2e2e"));
        boton.setForeground(Color.decode("#D9D9D9"));
        boton.setFont(fuenteLabel);
        boton.setFocusPainted(false);
        panel.add(boton);

        // Efecto simple al pasar el mouse por encima del botón
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Color.decode("#3e3e3e"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.decode("#2e2e2e"));
            }
        });

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCociente(txtNumerador, txtDenominador, txtResultado);
            }
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void calcularCociente(JTextField txtNumerador, JTextField txtDenominador, JTextField txtResultado) {
        try {
            double numerador = Double.parseDouble(txtNumerador.getText());
            double denominador = Double.parseDouble(txtDenominador.getText());
            NumeroRacional racional = new NumeroRacional(numerador, denominador);

            double resultado = racional.calcularCociente();
            txtResultado.setText(String.format("%.2f", resultado));

            JOptionPane.showMessageDialog(this, 
                "Operación Realizada.",
                "Mensaje",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
            "Se han introducido caracteres no numéricos.", 
            "Mensaje", 
            JOptionPane.INFORMATION_MESSAGE);
        } catch (ExcepcionIntervalo e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
            "Mensaje", 
            JOptionPane.INFORMATION_MESSAGE);
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), 
            "Mensaje", 
            JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MainRacional();
    }
}