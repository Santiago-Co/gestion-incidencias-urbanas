package incidencias.ui;

import javax.swing.*;
import util.swing.StyleUtil;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DashboardView extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
        
    private JLabel lblBienvenida;
    private JButton btnClose;
    private JButton btnLogout; 
    private JPanel buttonsPanel;
    private JButton btnCrearIncidencia;    
    private JButton btnConsultarMisIncidencias;
    private JButton btnClasificarIncidencia;
	private JButton btnAsignarIncidencias;
    private JButton btnPlanificarIncidencia;
    private JButton btnHistorialIncidencia;   
   
    public DashboardView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        
        final JLabel titleWin = new JLabel("  Sistema de Gestión de Incidencias - Panel Principal");
        StyleUtil.styleWindowTitle(titleWin);
        
        lblBienvenida = new JLabel("Bienvenido, Usuario");
        lblBienvenida.setMaximumSize(new Dimension(300, 45));
        StyleUtil.styleLabel(lblBienvenida, 24, true);
        
        final JLabel lblRol = new JLabel("Seleccione una acción para continuar");
        StyleUtil.styleLabel(lblRol, 14, false);
        
        btnCrearIncidencia = new JButton("Nueva Incidencia");
        StyleUtil.styleMainButton(btnCrearIncidencia);

        btnConsultarMisIncidencias = new JButton("Consultar Incidencias");
        StyleUtil.styleMainButton(btnConsultarMisIncidencias);

        btnClasificarIncidencia = new JButton("Clasificar Incidencias");
        StyleUtil.styleMainButton(btnClasificarIncidencia);

        btnAsignarIncidencias = new JButton("Asignar Incidencias");
        StyleUtil.styleMainButton(btnAsignarIncidencias);

        btnPlanificarIncidencia = new JButton("Planificar Incidencias");
        StyleUtil.styleMainButton(btnPlanificarIncidencia);

        btnHistorialIncidencia = new JButton("Historia de Incidencia");
        StyleUtil.styleMainButton(btnHistorialIncidencia);
        
        btnLogout = new JButton("Cerrar Sesión");
        StyleUtil.styleLogoutButton(btnLogout); 
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        topPanel.add(titleWin, BorderLayout.WEST);
        topPanel.add(btnClose, BorderLayout.EAST);
        
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Color.WHITE);

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbcBienvenida = new GridBagConstraints();
        gbcBienvenida.gridx = 0;
        gbcBienvenida.gridy = 0;
        gbcBienvenida.weightx = 1.0;
        gbcBienvenida.weighty = 0.0;
        gbcBienvenida.insets = new Insets(0, 0, 5, 0);
        gbcBienvenida.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblBienvenida, gbcBienvenida);
        
        GridBagConstraints gbcRol = new GridBagConstraints();
        gbcRol.gridx = 0;
        gbcRol.gridy = 1;
        gbcRol.weightx = 1.0;
        gbcRol.weighty = 0.0;
        gbcRol.insets = new Insets(0, 0, 30, 0);
        gbcRol.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblRol, gbcRol);
        
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 2;
        gbcButtons.weightx = 1;
        gbcButtons.weighty = 1;
        gbcButtons.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonsPanel, gbcButtons);

        GridBagConstraints gbcLogout = new GridBagConstraints();
        gbcLogout.gridx = 0;
        gbcLogout.gridy = 3;
        gbcLogout.weightx = 1;
        gbcLogout.weighty = 0;
        gbcLogout.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(btnLogout, gbcLogout);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(StyleUtil.BG_COLOR);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(425, 550));
        pack();
    }

    private void addButtonToPanel(JButton btn) {
    	buttonsPanel.add(btn);
    	buttonsPanel.add(Box.createVerticalStrut(15));
    }
    
    public void configurarVistaPorRol(String rol, String nombreUsuario) {
        lblBienvenida.setText("Hola, " + nombreUsuario);

        buttonsPanel.removeAll();
        
        switch (rol.trim()) {
            case "Ciudadano":
            	addButtonToPanel(btnCrearIncidencia);
            	addButtonToPanel(btnConsultarMisIncidencias);               
                break;
            case "Operador":
            	addButtonToPanel(btnClasificarIncidencia);
            	addButtonToPanel(btnAsignarIncidencias);
            	addButtonToPanel(btnHistorialIncidencia);
                break;
                
            case "Tecnico":
            	addButtonToPanel(btnPlanificarIncidencia);
            	addButtonToPanel(btnHistorialIncidencia);
                break;
                
            default:
                // En caso de tener que añadir un nuevo rol	
                break;
        }
        
        this.revalidate();
        this.repaint();
        this.pack();
    }
    
    public JButton getBtnClose() { return btnClose; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnCrearIncidencia() { return btnCrearIncidencia; }
    public JButton getBtnConsultarMisIncidencias() { return btnConsultarMisIncidencias; }
    public JButton getBtnClasificarIncidencia() { return btnClasificarIncidencia; }
    public JButton getBtnAsignarIncidencia() { return btnAsignarIncidencias; }
    public JButton getBtnPlanificarIncidencia() { return btnPlanificarIncidencia; }
	public JButton getBtnHistorialIncidencia() { return btnHistorialIncidencia; }
}
