package incidencias.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import util.swing.StyleUtil;
import util.swing.SwingUtil;

public class ConsultaIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BG_COLOR = new Color(249, 250, 251);

    private JComboBox<String> cbFiltroEstado;
    private JScrollPane scrollPane;
    private JTable tablaIncidencias;
    private JButton btnClose;

    public ConsultaIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel();
        cbFiltroEstado = new JComboBox<>(new String[]{
                "Todos los estados", "Nueva", "Validada", "Asignada", "En proceso", "Resuelta", "Cerrada"
        });
        tablaIncidencias = new JTable();
        btnClose = new JButton();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);
        
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        final JLabel lblTitle = new JLabel("Mis incidencias registradas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(lblTitle, gbc);
        
        final JLabel lblFiltro = new JLabel("Buscar por estado:");
        lblFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFiltro.setForeground(new Color(107, 114, 128));

        styleComboBox(cbFiltroEstado);
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(lblFiltro);
        filterPanel.add(cbFiltroEstado);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(filterPanel, gbc);
        
        StyleUtil.styleTable(tablaIncidencias);
        scrollPane = new JScrollPane(tablaIncidencias);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(scrollPane, gbc);
        
        StyleUtil.styleCloseButton(btnClose);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(btnClose);
        SwingUtil.enableWindowDrag(this, topPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(200, 35));
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BG_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public JComboBox<String> getListaFiltros() { return this.cbFiltroEstado; }
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JButton getBtnClose() { return this.btnClose; }
     
}