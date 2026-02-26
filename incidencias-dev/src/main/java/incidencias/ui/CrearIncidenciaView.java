package incidencias.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.LogSystem;


public class CrearIncidenciaView extends javax.swing.JFrame {


	private static final long serialVersionUID = 1L; // Ns que hace pero para eliminar el warning
	private JComboBox<String> cmbTipo;
    private JTextField txtLocalizacion;
    private JTextArea txtDescripcion;
    private JButton btnEnviar;
    private JButton btnClose;
    private int ciudadanoId;
	
	
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229);
    private static final Color TEXT_COLOR = new Color(31, 41, 55);     
    private static final Color LABEL_COLOR = new Color(107, 114, 128); 
    private static final Color BORDER_COLOR = new Color(209, 213, 219);
    

    private int mouseX, mouseY;

    public CrearIncidenciaView() {
    	LogSystem.logFlow("Inicio", "Cargando componentes de la interfaz");
        setUndecorated(true); 
        initComponents();
        setLocationRelativeTo(null); 
        
        
        setShape(new RoundRectangle2D.Double(0, 0, 400, 580, 20, 20));
        setSize(400, 580);
    }

    private void initComponents() {
    	LogSystem.logFlow("Init", "Inicio de la construcción de la interfaz del usuario");
        JPanel mainPanel = new JPanel();
        
        JLabel lblTitle = new JLabel("Nueva Incidencia");
        JLabel lblTipo = createStyledLabel("Tipo de incidencia");
        cmbTipo = new JComboBox<>(new String[] {
        	    "Alumbrado",
        	    "Limpieza",
        	    "Mobiliario urbano",
        	    "Zonas verdes",
        	    "Señalización y calzada",
        	    "Otro"
        	});
        
        JLabel titleWin = new JLabel("  Creación de incidencias");
        titleWin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleWin.setForeground(new Color(75, 85, 99));
        
        JLabel lblLocalizacion = createStyledLabel("Localización");
        txtLocalizacion = createStyledTextField();
        JLabel lblDescripcion = createStyledLabel("Descripción de la incidencia");
        txtDescripcion = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtDescripcion);
        btnEnviar = createPrimaryButton("Enviar Reporte");
        
        btnClose = new JButton("×");
        styleCloseButton(btnClose); 
        
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0); 

        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(new Color(229, 231, 235));
        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
        
        
        topPanel.add(titleWin, BorderLayout.WEST);
        topPanel.add(btnClose, BorderLayout.EAST);
        enableWindowDrag(topPanel);


        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.insets = new Insets(0, 0, 20, 0); 
        mainPanel.add(lblTitle, gbc);

        gbc.gridy++; gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(lblTipo, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 15, 0);
        styleComboBox(cmbTipo);
        mainPanel.add(cmbTipo, gbc);

        gbc.gridy++; gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(lblLocalizacion, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(txtLocalizacion, gbc);

        gbc.gridy++; gbc.insets = new Insets(5, 0, 2, 0);
        mainPanel.add(lblDescripcion, gbc);
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 20, 0);
        styleTextArea(txtDescripcion, scrollPane);
        mainPanel.add(scrollPane, gbc);

        gbc.gridy++; gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(btnEnviar, gbc); 
        
        gbc.gridy++; gbc.insets = new Insets(0, 0, 0, 0);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
        LogSystem.logFlow("Ready", "Interfaz construida y lista para el usuario");
    }

    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12)); 
        label.setForeground(LABEL_COLOR);
        return label;
    }
    
    private void enableWindowDrag(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) { setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY); }
        });
    }
    
    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setForeground(TEXT_COLOR);
        txt.setPreferredSize(new Dimension(300, 40));
        
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return txt;
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(300, 40));
        
        ((JComponent) box.getRenderer()).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }

    private void styleTextArea(JTextArea area, JScrollPane scroll) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(TEXT_COLOR);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        
        scroll.setPreferredSize(new Dimension(300, 100));
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1)); 
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        
        btn.setBorder(null); 
        btn.setPreferredSize(new Dimension(300, 45)); 

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR.darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR); }
        });
        return btn;
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
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrarResumen(String mensaje, String resumen) {
    	LogSystem.logFlow("SUCCESS", resumen);
        JOptionPane.showMessageDialog(this, mensaje, "Incidencia registrada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public int getCiudadanoId() { return this.ciudadanoId; }
    public JComboBox<String> getCmbTipo() { return this.cmbTipo; }
    public JTextField getTxtLocalizacion() { return this.txtLocalizacion; }
    public JTextArea getTxtDescripcion() { return this.txtDescripcion; }
    public JButton getBtnEnviar() { return this.btnEnviar; }
    public JButton getBtnClose() { return this.btnClose; }
}