package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import util.swing.StyleUtil;

public class ConsultaIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

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
        
        final JLabel titleWin = new JLabel("  Consulta de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Mis incidencias registradas");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        final JLabel lblFiltro = new JLabel("Buscar por estado:");
        StyleUtil.styleLabel(lblFiltro, 14, false);
        
        cbFiltroEstado = new JComboBox<>(new String[]{
                "Todos los estados", "Nueva", "Validada", "Asignada", "En proceso", "Resuelta", "Cerrada"
        });
        StyleUtil.styleComboBox(cbFiltroEstado, 200, 35);

        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollPane = new JScrollPane(tablaIncidencias);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0;
        g.anchor = GridBagConstraints.WEST;
        filterPanel.add(lblFiltro, g);
        g.gridx = 1;
        g.insets = new Insets(0, 10, 0, 0);
        filterPanel.add(cbFiltroEstado, g);
                
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblTitle, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(filterPanel, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(scrollPane, gbc);

        setBackground(StyleUtil.BG_COLOR);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }
    
    public JComboBox<String> getListaFiltros() { return this.cbFiltroEstado; }
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JButton getBtnClose() { return this.btnClose; }
     
}