package incidencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public class DashboardView extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229); 
    private static final Color BG_COLOR = new Color(249, 250, 251); 
    private static final Color DANGER_COLOR = new Color(220, 38, 38); 
    
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
    
    private int xMouse, yMouse;
    
    public DashboardView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(229, 231, 235));
        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
        
        JLabel titleWin = new JLabel("  Sistema de Gestión de Incidencias - Panel Principal");
        titleWin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleWin.setForeground(new Color(75, 85, 99));
        
        btnClose = new JButton("×");
        styleCloseButton(btnClose);
        
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

        lblBienvenida = new JLabel("Bienvenido, Usuario");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(31, 41, 55));
        lblBienvenida.setMaximumSize(new Dimension(300, 45));

        JLabel lblRol = new JLabel("Seleccione una acción para continuar");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRol.setForeground(new Color(107, 114, 128));
        
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Color.WHITE);
        
        btnCrearIncidencia = new JButton("Nueva Incidencia");
        btnConsultarMisIncidencias = new JButton("Consultar Incidencias");
        btnClasificarIncidencia = new JButton("Clasificar Incidencias");
        btnAsignarIncidencias = new JButton("Asignar Incidencias");
        btnPlanificarIncidencia = new JButton("Planificar Incidencias");
        btnHistorialIncidencia = new JButton("Historia de Incidencia");
        
        styleMenuButton(btnCrearIncidencia);
        styleMenuButton(btnConsultarMisIncidencias);
        styleMenuButton(btnClasificarIncidencia);
        styleMenuButton(btnAsignarIncidencias);
        styleMenuButton(btnPlanificarIncidencia);
        styleMenuButton(btnHistorialIncidencia);
        
        btnLogout = new JButton("Cerrar Sesión");
        styleSecondaryButton(btnLogout); 
        
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

    private void styleMenuButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setMinimumSize(new Dimension(300, 45));
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR.darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR); }
        });
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(DANGER_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setMinimumSize(new Dimension(300, 45));
        btn.setBorder(BorderFactory.createLineBorder(DANGER_COLOR, 1)); 

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { 
                btn.setBackground(DANGER_COLOR); 
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent evt) { 
                btn.setBackground(Color.WHITE);
                btn.setForeground(DANGER_COLOR);
            }
        });
    }

    private void styleCloseButton(JButton btn) {
        btn.setPreferredSize(new Dimension(45, 40));
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { 
                btn.setOpaque(true); 
                btn.setBackground(Color.RED); 
                btn.setForeground(Color.WHITE); 
            }
            public void mouseExited(MouseEvent e) { 
                btn.setOpaque(false); 
                btn.setForeground(Color.BLACK); 
            }
        });
    }
    
    public JButton getBtnClose() { return btnClose; }
    public JButton getBtnLogout() { return btnLogout; }
    
    public JButton getBtnCrearIncidencia() { return btnCrearIncidencia; }
    public JButton getBtnConsultarMisIncidencias() { return btnConsultarMisIncidencias; }
    public JButton getBtnClasificarIncidencia() { return btnClasificarIncidencia; }
    public JButton getBtnAsignarIncidencia() { return btnAsignarIncidencias; }
    public JButton getBtnPlanificarIncidencia() { return btnPlanificarIncidencia; }
	public JButton getBtnHistorialIncidencia() { return btnHistorialIncidencia; }
	public void setBtnHistorialIncidencia(JButton btnHistorialIncidencia) {	this.btnHistorialIncidencia = btnHistorialIncidencia; }
}
