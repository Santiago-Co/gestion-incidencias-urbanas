package incidencias.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import util.LogSystem;
import util.swing.StyleUtil;

public class CrearIncidenciaView extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> cmbTipo;
    private JTextField txtLocalizacion;
    private JTextArea txtDescripcion;
    private JButton btnEnviar;
    private JButton btnClose;
    private int ciudadanoId;
	
    public CrearIncidenciaView() {
    	LogSystem.logFlow("Inicio", "Cargando componentes de la interfaz");
        setUndecorated(true); 
        initComponents();
        setLocationRelativeTo(null);    
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    private void initComponents() {
    	LogSystem.logFlow("Init", "Inicio de la construcción de la interfaz del usuario");
        JPanel mainPanel = new JPanel();
        
        final JLabel titleWin = new JLabel("  Creación de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Nueva Incidencia");
        StyleUtil.styleLabel(lblTitle, 24, true);
        
        final JLabel lblTipo = new JLabel("Tipo de incidencia");
        StyleUtil.styleLabel(lblTipo, 12, false);
        
        cmbTipo = new JComboBox<>(new String[] {
        	    "Alumbrado",
        	    "Limpieza",
        	    "Mobiliario urbano",
        	    "Zonas verdes",
        	    "Señalización y calzada",
        	    "Otro"
        });
        StyleUtil.styleComboBox(cmbTipo, 300, 40);
        
        final JLabel lblLocalizacion = new JLabel("Localización");
        StyleUtil.styleLabel(lblLocalizacion, 12, false);
        
        txtLocalizacion = new JTextField();
        StyleUtil.styleTextField(txtLocalizacion, 300, 45);
        
        final JLabel lblDescripcion = new JLabel("Descripción de la incidencia");
        StyleUtil.styleLabel(lblDescripcion, 12, false);
        
        txtDescripcion = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtDescripcion);
        StyleUtil.styleTextArea(txtDescripcion, scrollPane, 300, 100);

        btnEnviar = new JButton("Enviar Reporte");
        StyleUtil.styleMainButton(btnEnviar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose); 
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 5, 0); 

        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.insets = new Insets(0, 0, 20, 0); 
        mainPanel.add(lblTitle, gbc);

        gbc.gridy++; gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(lblTipo, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(cmbTipo, gbc);

        gbc.gridy++; gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(lblLocalizacion, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(txtLocalizacion, gbc);

        gbc.gridy++; gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(lblDescripcion, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(scrollPane, gbc);

        gbc.gridy++; gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(btnEnviar, gbc); 
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 0, 0);
        
        setBackground(StyleUtil.BG_COLOR);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
        
        LogSystem.logFlow("Ready", "Interfaz construida y lista para el usuario");
    }
    
    public int getCiudadanoId() { return this.ciudadanoId; }
    public JComboBox<String> getCmbTipo() { return this.cmbTipo; }
    public JTextField getTxtLocalizacion() { return this.txtLocalizacion; }
    public JTextArea getTxtDescripcion() { return this.txtDescripcion; }
    public JButton getBtnEnviar() { return this.btnEnviar; }
    public JButton getBtnClose() { return this.btnClose; }
}