package incidencias.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import util.LogSystem;
import util.swing.StyleUtil;

public class CrearIncidenciaView extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbLocalizacion;
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
    	
    	// Inicializacion de componentes
        
        final JLabel titleWin = new JLabel("Creación de incidencias");
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
        
        cmbLocalizacion = new JComboBox<>(new String[] {
        	    "Centro",
        	    "Norte",
        	    "Sur",
        	    "Este",
        	    "Oeste",
        	    "Parque Central",
        	    "Zona Universitaria",
        	    "Poligono Industrial",
        	    "Estacion",
        	    "Hospital",
        });
        
        StyleUtil.styleComboBox(cmbLocalizacion, 300, 40);
        
        final JLabel lblDescripcion = new JLabel("Descripción de la incidencia");
        StyleUtil.styleLabel(lblDescripcion, 12, false);
        
        txtDescripcion = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtDescripcion);
        StyleUtil.styleTextArea(txtDescripcion, scrollPane, 300, 100);

        btnEnviar = new JButton("Enviar Reporte");
        StyleUtil.styleMainButton(btnEnviar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose); 
        
        // Creacion de las secciones de la pantalla

        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel mainPanel = StyleUtil.createMainPanel();

        // Colocacion de los componentes de la pantalla

        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0; 
        gbcTitle.insets = new Insets(0, 0, 20, 0); 
        gbcTitle.fill = GridBagConstraints.BOTH;
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcLblTipo = new GridBagConstraints();
        gbcLblTipo.gridx = 0;
        gbcLblTipo.gridy = 1;
        gbcLblTipo.insets = new Insets(5, 0, 2, 0);
        gbcLblTipo.fill = GridBagConstraints.BOTH;
        mainPanel.add(lblTipo, gbcLblTipo);
        
        GridBagConstraints gbcCmbTipo = new GridBagConstraints();
        gbcCmbTipo.gridx = 0;
        gbcCmbTipo.gridy = 2;
        gbcCmbTipo.insets = new Insets(0, 0, 15, 0);
        gbcCmbTipo.fill = GridBagConstraints.BOTH;
        mainPanel.add(cmbTipo, gbcCmbTipo);

        GridBagConstraints gbcLblLocalizacion = new GridBagConstraints();
        gbcLblLocalizacion.gridx = 0;
        gbcLblLocalizacion.gridy = 3;
        gbcLblLocalizacion.insets = new Insets(5, 0, 2, 0);
        gbcLblLocalizacion.fill = GridBagConstraints.BOTH;
        mainPanel.add(lblLocalizacion, gbcLblLocalizacion);
        
        GridBagConstraints gbcTxtLocalizacion = new GridBagConstraints();
        gbcTxtLocalizacion.gridx = 0;
        gbcTxtLocalizacion.gridy = 4;
        gbcTxtLocalizacion.insets = new Insets(0, 0, 15, 0);
        gbcTxtLocalizacion.fill = GridBagConstraints.BOTH;
        mainPanel.add(cmbLocalizacion, gbcTxtLocalizacion);

        GridBagConstraints gbcDescripcion = new GridBagConstraints();
        gbcDescripcion.gridx = 0;
        gbcDescripcion.gridy = 5;
        gbcDescripcion.insets = new Insets(5, 0, 2, 0);
        gbcDescripcion.fill = GridBagConstraints.BOTH;
        mainPanel.add(lblDescripcion, gbcDescripcion);
        
        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 6;
        gbcScroll.insets = new Insets(0, 0, 20, 0);
        gbcScroll.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbcScroll);

        GridBagConstraints gbcEnviar = new GridBagConstraints();
        gbcEnviar.gridx = 0;
        gbcEnviar.gridy = 7;
        gbcEnviar.insets = new Insets(10, 0, 10, 0);
        gbcEnviar.fill = GridBagConstraints.BOTH;
        mainPanel.add(btnEnviar, gbcEnviar); 
               
        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
        
        LogSystem.logFlow("Ready", "Interfaz construida y lista para el usuario");
    }
    
    public int getCiudadanoId() { return this.ciudadanoId; }
    public JComboBox<String> getCmbTipo() { return this.cmbTipo; }
    public JComboBox<String> getCmbLocalizacion() { return this.cmbLocalizacion; }
    public JTextArea getTxtDescripcion() { return this.txtDescripcion; }
    public JButton getBtnEnviar() { return this.btnEnviar; }
    public JButton getBtnClose() { return this.btnClose; }
}