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
    	
    	// Inicializacion de componentes
        
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
        
        // Creacion de las secciones de la pantalla

        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel filterPanel = StyleUtil.createAbstractPanel();

        JPanel mainPanel = StyleUtil.createMainPanel();
                        
        // Colocacion de los componentes de la pantalla

        GridBagConstraints gbcLblFiltro = new GridBagConstraints();
        gbcLblFiltro.gridx = 0;
        gbcLblFiltro.gridy = 0;
        gbcLblFiltro.anchor = GridBagConstraints.WEST;
        filterPanel.add(lblFiltro, gbcLblFiltro);
        
        GridBagConstraints gbcCmbFiltro = new GridBagConstraints();
        gbcCmbFiltro.gridx = 1;
        gbcCmbFiltro.gridy = 0;
        gbcCmbFiltro.insets = new Insets(0, 10, 0, 0);
        gbcCmbFiltro.anchor = GridBagConstraints.WEST;
        filterPanel.add(cbFiltroEstado, gbcCmbFiltro);
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcPnlFiltro = new GridBagConstraints();
        gbcPnlFiltro.gridx = 0;
        gbcPnlFiltro.gridy = 1;
        gbcPnlFiltro.insets = new Insets(0, 0, 15, 0);
        gbcPnlFiltro.anchor = GridBagConstraints.WEST;
        mainPanel.add(filterPanel, gbcPnlFiltro);
        
        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 2;
        mainPanel.add(scrollPane, gbcScroll);
        
        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }
    
    public JComboBox<String> getListaFiltros() { return this.cbFiltroEstado; }
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JButton getBtnClose() { return this.btnClose; }
     
}