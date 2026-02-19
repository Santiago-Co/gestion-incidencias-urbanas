package incidencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;


public class LogInView extends javax.swing.JFrame {

   
	private static final long serialVersionUID = 1L;
	public static final String PLACEHOLDER_TEXT = "Pon aquí tu DNI o correo";
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229); 
    private static final Color BG_COLOR = new Color(249, 250, 251); 

    
    private JTextField txtIdentificador;
    private JButton btnIdentificar;
    private JButton btnClose;
    
    private int xMouse, yMouse;
    
    public LogInView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        txtIdentificador = new JTextField();
        btnIdentificar = new JButton();
        btnClose = new JButton("×");
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(229, 231, 235));
        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
        
        JLabel titleWin = new JLabel("  Sistema de Gestión de Incidencias");
        titleWin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleWin.setForeground(new Color(75, 85, 99));
        
        topPanel.add(titleWin, BorderLayout.WEST);
        topPanel.add(btnClose, BorderLayout.EAST);
        
        topPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                xMouse = evt.getX();
                yMouse = evt.getY();
            }
        });
        topPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                int x = evt.getXOnScreen();
                int y = evt.getYOnScreen();
                setLocation(x - xMouse, y - yMouse);
            }
        });
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        
        JLabel lblTitle = new JLabel("Bienvenido");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(lblTitle, gbc);

        
        JLabel lblInstruccion = new JLabel("Debe identificarse para acceder al sistema");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInstruccion.setForeground(new Color(107, 114, 128));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblInstruccion, gbc);

        
        txtIdentificador.setText(PLACEHOLDER_TEXT);
        styleTextFieldIdentificador(txtIdentificador);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(txtIdentificador, gbc);


        btnIdentificar.setText("Acceder al Sistema");
        stylePrimaryButton(btnIdentificar);
        gbc.gridy = 3;
        mainPanel.add(btnIdentificar, gbc);

        
        styleCloseButton(btnClose);
        

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    

    private void styleTextFieldIdentificador(JTextField tf) {
        tf.setForeground(Color.GRAY);
        tf.setPreferredSize(new Dimension(300, 45));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tf.getText().equals(PLACEHOLDER_TEXT)) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                    tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(4, 14, 4, 14)));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tf.getText().isEmpty()) {
                    tf.setText(PLACEHOLDER_TEXT);
                    tf.setForeground(Color.GRAY);
                    tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)));
                }
            }
        });
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR.darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR); }
        });
    }

    private void styleCloseButton(JButton btn) {
        btn.setPreferredSize(new Dimension(45, 40));
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setOpaque(true); btn.setBackground(Color.RED); btn.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setOpaque(false); btn.setForeground(Color.BLACK); }
        });
    }


    public String getIdentificador() {
        String texto = txtIdentificador.getText().trim();
        return (PLACEHOLDER_TEXT.equals(texto)) ? "" : texto;
    }

    public JButton getBtnIdentificar() { return btnIdentificar; }
    
    public JButton getBtnClose() { return btnClose; }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}