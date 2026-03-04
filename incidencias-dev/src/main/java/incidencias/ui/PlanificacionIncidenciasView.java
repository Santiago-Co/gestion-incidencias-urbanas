package incidencias.ui;

import javax.swing.*;
import util.swing.StyleUtil;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PlanificacionIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JScrollPane scrollTabla;
    private JTable tablaIncidencias;
    private JTextField textFieldHorasPrevistas;
    private JTextArea textAreaDescripcion;
    private JButton btnGuardar;
    private JButton btnClose;

    public PlanificacionIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	JPanel mainPanel = new JPanel();
    	
        final JLabel titleWin = new JLabel("  Planificación de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Mis incidencias asignadas");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(860, 300));
        
        final JLabel lblHoras = new JLabel("Horas previstas:");
        StyleUtil.styleLabel(lblHoras, 14, false);
        
        textFieldHorasPrevistas = new JTextField();
        StyleUtil.styleTextField(textFieldHorasPrevistas, 100, 30);
        textFieldHorasPrevistas.setColumns(6);
        
        final JLabel lblDesc = new JLabel("Descripción del trabajo:");
        StyleUtil.styleLabel(lblDesc, 14, false);
        
        textAreaDescripcion = new JTextArea(6, 60);
        JScrollPane scrollDesc = new JScrollPane(textAreaDescripcion);
        StyleUtil.styleTextArea(textAreaDescripcion, scrollDesc, 860, 120);
        
        btnGuardar = new JButton("Guardar");
        StyleUtil.styleSecondaryButton(btnGuardar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel horasPanel = new JPanel(new GridBagLayout());
        horasPanel.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;
        g.anchor = GridBagConstraints.WEST;
        horasPanel.add(lblHoras, g);
        g.gridx = 1;
        g.insets = new Insets(0, 10, 0, 0);
        horasPanel.add(textFieldHorasPrevistas, g);
        
        JPanel accionesPanel = new JPanel(new BorderLayout());
        accionesPanel.setBackground(Color.WHITE);
        accionesPanel.add(btnGuardar, BorderLayout.EAST);
        
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblTitle, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(scrollTabla, gbc);
        
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(horasPanel, gbc);
        
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        mainPanel.add(lblDesc, gbc);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(scrollDesc, gbc);
        
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbc);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(StyleUtil.BG_COLOR);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    public JScrollPane getScrollTabla() { return this.scrollTabla; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JTextField getTextFieldHorasPrevistas() { return this.textFieldHorasPrevistas; }
    public JTextArea getTextAreaDescripcion() { return this.textAreaDescripcion; }
    public JButton getBtnGuardar() { return this.btnGuardar; }
    public JButton getBtnClose() { return this.btnClose; }
}