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
    	
    	// Inicializacion de componentes
        
        final JLabel titleWin = new JLabel("Asignación de incidencias");
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
        scrollTecnicos.setPreferredSize(new Dimension(350, 300));
        scrollTecnicos.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
        btnAsignar = new JButton("Asignar");
        StyleUtil.styleSecondaryButton(btnAsignar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel tablasPanel = StyleUtil.createAbstractPanel();
        
        JButton[] btns = {btnAsignar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);

        JPanel mainPanel = StyleUtil.createMainPanel();

        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcLblIncidencias = new GridBagConstraints();
        gbcLblIncidencias.gridx = 0;
        gbcLblIncidencias.gridy = 0;
        gbcLblIncidencias.insets = new Insets(0, 0, 10, 20);
        gbcLblIncidencias.anchor = GridBagConstraints.WEST;
        gbcLblIncidencias.fill = GridBagConstraints.NONE;
        tablasPanel.add(lblIncidencias, gbcLblIncidencias);
        
        GridBagConstraints gbcLblTecnicos = new GridBagConstraints();
        gbcLblTecnicos.gridx = 1;
        gbcLblTecnicos.gridy = 0;
        gbcLblTecnicos.insets = new Insets(0, 20, 10, 0);
        gbcLblTecnicos.anchor = GridBagConstraints.WEST;
        gbcLblTecnicos.fill = GridBagConstraints.NONE;
        tablasPanel.add(lblTecnicos, gbcLblTecnicos);
        
        GridBagConstraints gbcScrollInc = new GridBagConstraints();
        gbcScrollInc.gridx = 0;
        gbcScrollInc.gridy = 1;
        gbcScrollInc.insets = new Insets(0, 0, 0, 20);
        gbcScrollInc.anchor = GridBagConstraints.WEST;
        gbcScrollInc.fill = GridBagConstraints.NONE;
        tablasPanel.add(scrollIncidencias, gbcScrollInc);
        
        GridBagConstraints gbcScrollTec = new GridBagConstraints();
        gbcScrollTec.gridx = 1;
        gbcScrollTec.gridy = 1;
        gbcScrollTec.insets = new Insets(0, 20, 0, 0);
        gbcScrollTec.anchor = GridBagConstraints.WEST;
        gbcScrollTec.fill = GridBagConstraints.NONE;
        tablasPanel.add(scrollTecnicos, gbcScrollTec);
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblTitle, gbcTitle);
        
        GridBagConstraints gbcPnlTablas = new GridBagConstraints();
        gbcPnlTablas.gridx = 0;
        gbcPnlTablas.gridy = 1;
        gbcPnlTablas.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(tablasPanel, gbcPnlTablas);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 2;
        gbcPnlAcciones.insets = new Insets(20, 20, 0, 0);
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JScrollPane getScrollIncidencias() { return this.scrollIncidencias; }
    public JTable getTablaTecnicos() { return this.tablaTecnicos; }
    public JScrollPane getScrollTecnicos() { return this.scrollTecnicos; }
    public JButton getBtnClose() { return this.btnClose; }
    public JButton getBtnAsignar() { return this.btnAsignar; }
}
