package incidencias.ui;

import javax.swing.*;
import util.swing.StyleUtil;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LogInView extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	
	public static final String PLACEHOLDER_TEXT = "Pon aquí tu DNI o correo";
    
    private JTextField txtIdentificador;
    private JButton btnIdentificar;
    private JButton btnClose;
    
    public LogInView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        
        final JLabel titleWin = new JLabel("  Sistema de Gestión de Incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Bienvenido");
        StyleUtil.styleLabel(lblTitle, 28, true);
        
        final JLabel lblInstruccion = new JLabel("Debe identificarse para acceder al sistema");
        StyleUtil.styleLabel(lblInstruccion, 14, false);
        
        txtIdentificador = new JTextField(PLACEHOLDER_TEXT);
        StyleUtil.styleTextFieldIdentificador(txtIdentificador, PLACEHOLDER_TEXT);
        
        btnIdentificar = new JButton("Acceder al Sistema");
        StyleUtil.styleMainButton(btnIdentificar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);     
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(lblTitle, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblInstruccion, gbc);
       
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(txtIdentificador, gbc);

        gbc.gridy = 3;
        mainPanel.add(btnIdentificar, gbc);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(StyleUtil.BG_COLOR);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    public String getIdentificador() {
        String texto = txtIdentificador.getText().trim();
        return (PLACEHOLDER_TEXT.equals(texto)) ? "" : texto;
    }

    public JButton getBtnIdentificar() { return btnIdentificar; }
    public JButton getBtnClose() { return btnClose; }
}