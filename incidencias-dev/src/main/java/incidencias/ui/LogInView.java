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
    	
    	// Inicializacion de componentes
    	
        final JLabel titleWin = new JLabel("Sistema de Gestión de Incidencias");
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
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());

        JPanel mainPanel = StyleUtil.createMainPanel();
        
        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(lblTitle, gbcTitle);
        
        GridBagConstraints gbcInstruccion = new GridBagConstraints();
        gbcInstruccion.gridx = 0;
        gbcInstruccion.gridy = 1;
        gbcInstruccion.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblInstruccion, gbcInstruccion);
        
        GridBagConstraints gbcTxtIdentificador = new GridBagConstraints();
        gbcTxtIdentificador.gridx = 0;
        gbcTxtIdentificador.gridy = 2;
        gbcTxtIdentificador.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(txtIdentificador, gbcTxtIdentificador);

        GridBagConstraints gbcBtnIdentificador = new GridBagConstraints();
        gbcBtnIdentificador.gridx = 0;
        gbcBtnIdentificador.gridy = 3;
        gbcBtnIdentificador.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(btnIdentificar, gbcBtnIdentificador);
        
        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, true);
    }

    public String getIdentificador() {
        String texto = txtIdentificador.getText().trim();
        return (PLACEHOLDER_TEXT.equals(texto)) ? "" : texto;
    }

    public JButton getBtnIdentificar() { return btnIdentificar; }
    public JButton getBtnClose() { return btnClose; }
}