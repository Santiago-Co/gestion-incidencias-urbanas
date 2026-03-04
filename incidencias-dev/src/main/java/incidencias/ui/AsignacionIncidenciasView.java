package incidencias.ui;

import java.awt.*; 
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import util.swing.StyleUtil;

public class AsignacionIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color TABLE_BORDER = new Color(120, 120, 120);
    
    private JTable tablaIncidencias;
    private JScrollPane scrollIncidencias;
    private JTable tablaTecnicos;
    private JScrollPane scrollTecnicos;
    private JButton btnClose;
    private JButton btnAsignar;

    public AsignacionIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        
        final JLabel titleWin = new JLabel("  Asignación de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Asignación de incidencias");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        final JLabel lblIncidencias = new JLabel("Seleccione una incidencia para asignarla:");
        StyleUtil.styleLabel(lblIncidencias, 16, false);
        
        final JLabel lblTecnicos = new JLabel("Técnicos disponibles:");
        StyleUtil.styleLabel(lblTecnicos, 16, false);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollIncidencias = new JScrollPane(tablaIncidencias);
        scrollIncidencias.setPreferredSize(new Dimension(575, 300));
        scrollIncidencias.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
        tablaTecnicos = new JTable();
        StyleUtil.styleTable(tablaTecnicos);
        scrollTecnicos = new JScrollPane(tablaTecnicos);
        scrollTecnicos.setPreferredSize(new Dimension(300, 300));
        scrollTecnicos.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
        btnAsignar = new JButton("Asignar");
        StyleUtil.styleSecondaryButton(btnAsignar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel tablasPanel = new JPanel(new GridBagLayout());
        tablasPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcTablas = new GridBagConstraints();
        gbcTablas.gridx = 0; gbcTablas.gridy = 0;
        gbcTablas.anchor = GridBagConstraints.WEST;
        gbcTablas.fill = GridBagConstraints.NONE;
        gbcTablas.insets = new Insets(0, 0, 10, 20);
        tablasPanel.add(lblIncidencias, gbcTablas);
        gbcTablas.gridx = 1; gbcTablas.gridy = 0;
        gbcTablas.anchor = GridBagConstraints.WEST;
        gbcTablas.fill = GridBagConstraints.NONE;
        gbcTablas.insets = new Insets(0, 20, 10, 0);
        tablasPanel.add(lblTecnicos, gbcTablas);
        gbcTablas.gridx = 0; gbcTablas.gridy = 1;
        gbcTablas.insets = new Insets(0, 0, 0, 20);
        tablasPanel.add(scrollIncidencias, gbcTablas);
        gbcTablas.gridx = 1; gbcTablas.gridy = 1;
        gbcTablas.insets = new Insets(0, 20, 0, 0);
        tablasPanel.add(scrollTecnicos, gbcTablas);
        
        JPanel accionesPanel = new JPanel(new BorderLayout());
        accionesPanel.setBackground(Color.WHITE);
        accionesPanel.add(btnAsignar, BorderLayout.EAST);

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 30, 0);
        mainPanel.add(lblTitle, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(tablasPanel, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 20, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbc);

        setBackground(StyleUtil.BG_COLOR);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JScrollPane getScrollIncidencias() { return this.scrollIncidencias; }
    public JTable getTablaTecnicos() { return this.tablaTecnicos; }
    public JScrollPane getScrollTecnicos() { return this.scrollTecnicos; }
    public JButton getBtnClose() { return this.btnClose; }
    public JButton getBtnAsignar() { return this.btnAsignar; }
}
