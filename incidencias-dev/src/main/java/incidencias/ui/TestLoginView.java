package test_login.ui;

import javax.swing.*;
import util.LogSystem;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;


public class TestLoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String PLACEHOLDER_TEXT = "Pon aquí tu DNI o correo";
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229); 
    private static final Color BG_COLOR = new Color(249, 250, 251); 

    
    private JTextField txtIdentificador;
    private JButton btnIdentificar;
    private JButton btnClose;

    public TestLoginView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	
        JPanel mainPanel = new JPanel();
        txtIdentificador = new JTextField();
        btnIdentificar = new JButton();
        btnClose = new JButton("x");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        
        final JLabel lblTitle = new JLabel("Bienvenido");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(lblTitle, gbc);

        
        final JLabel lblInstruccion = new JLabel("Debe identificarse para acceder al sistema");
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
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(btnClose);

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
    	
        btn.setText("X");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(new Color(150, 150, 150)); 


        btn.setBorder(null);              
        btn.setContentAreaFilled(false);  
        btn.setBorderPainted(false);      
        btn.setFocusPainted(false);       

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));     
       
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setForeground(new Color(150, 150, 150)); 
            }
        });
        
        btn.addActionListener(e -> {
        	LogSystem.logFlow("EXIT", "Cerrando aplicación desde: Login");
        	dispose();
        });
    }


    public String getIdentificador() {
        String texto = txtIdentificador.getText().trim();
        return (PLACEHOLDER_TEXT.equals(texto)) ? "" : texto;
    }

    public JButton getBtnIdentificar() { return btnIdentificar; }
    
    public JButton getBtnClose() { return btnClose; }
    
}