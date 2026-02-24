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
    
    private static DashboardView instance;
    
    private JLabel lblBienvenida;
    private JButton btnClose;
    private JButton btnLogout; 
    
    
    private JButton btnCrearIncidencia;    
    private JButton btnConsultarMisIncidencias;
    private JButton btnClasificarIncidencia; 
    private JButton btnPlanificarIncidencia;
    
    private int xMouse, yMouse;
    
    public static DashboardView getInstance() {
    	if(instance == null) {
    		instance = new DashboardView(); 
    	}
    	return instance; 
    }
    
    private DashboardView() {
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblBienvenida = new JLabel("Bienvenido, Usuario");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(lblBienvenida, gbc);

        JLabel lblRol = new JLabel("Seleccione una acción para continuar");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRol.setForeground(new Color(107, 114, 128));
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblRol, gbc);

        JPanel buttonsPanel = new JPanel(new GridLayout(0, 1, 10, 15)); 
        buttonsPanel.setBackground(Color.WHITE);

        
        btnCrearIncidencia = new JButton("Nueva Incidencia");
        btnConsultarMisIncidencias = new JButton("Consultar Incidencias");
        btnClasificarIncidencia = new JButton("Clasificar Incidencias");
        btnPlanificarIncidencia = new JButton("Planificar Incidencias");
        
        
        styleMenuButton(btnCrearIncidencia);
        styleMenuButton(btnConsultarMisIncidencias);
        styleMenuButton(btnClasificarIncidencia);
        styleMenuButton(btnPlanificarIncidencia);
        
        buttonsPanel.add(btnCrearIncidencia);
        buttonsPanel.add(btnConsultarMisIncidencias);
        buttonsPanel.add(btnClasificarIncidencia);
        buttonsPanel.add(btnPlanificarIncidencia);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(buttonsPanel, gbc);

        
        btnLogout = new JButton("Cerrar Sesión");
        styleSecondaryButton(btnLogout); 
        gbc.gridy = 3;
        mainPanel.add(btnLogout, gbc);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(400, 550));
        pack();
    }

    
    public void configurarVistaPorRol(String rol, String nombreUsuario) {
        lblBienvenida.setText("Hola, " + nombreUsuario);
        
        btnCrearIncidencia.setVisible(false);
        btnConsultarMisIncidencias.setVisible(false);
        btnClasificarIncidencia.setVisible(false);
        btnPlanificarIncidencia.setVisible(false);
        
        switch (rol.trim()) {
            case "Ciudadano":
                btnCrearIncidencia.setVisible(true);
                break;
            case "Operador":
            	btnClasificarIncidencia.setVisible(true);
                break;
            case "Tecnico":
            	btnPlanificarIncidencia.setVisible(true);
                break;
                
            default:
                // En caso de tener que añadir un nuevo rol	
                break;
        }
        
        //btnCrearIncidencia.setVisible(true);
        //btnConsultarMisIncidencias.setVisible(true);
        
        this.revalidate();
        this.repaint();
    }

   

    private void styleMenuButton(JButton btn) {
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

    private void styleSecondaryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(DANGER_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(300, 45));
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
    public JButton getBtnPlanificarIncidencia() { return btnPlanificarIncidencia; }
}
