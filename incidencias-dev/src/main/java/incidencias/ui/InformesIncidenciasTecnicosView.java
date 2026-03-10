package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import util.swing.StyleUtil;

public class InformesIncidenciasTecnicosView extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JScrollPane scrollPane;
    private JTable tablaInforme;
    private JButton btnClose;

    public InformesIncidenciasTecnicosView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	
    	// Inicializacion de componentes

        JLabel titleWin = new JLabel("  Informes ...");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Número de incidencias resueltas por los técnicos en el último mes:");
        StyleUtil.styleLabel(lblTitle, 24, true);
        
        tablaInforme = new JTable();
        StyleUtil.styleTable(tablaInforme);
        scrollPane = new JScrollPane(tablaInforme);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());

        JPanel mainPanel = StyleUtil.createMainPanel();

        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(5, 5, 10, 5);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 1;
        gbcScroll.insets = new Insets(5, 5, 10, 5);
        gbcScroll.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbcScroll);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }
    
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }

    public JTable getTablaIncidencias() { return this.tablaInforme; }

    public JButton getBtnClose() { return this.btnClose; }
}