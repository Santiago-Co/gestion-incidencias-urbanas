package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import util.swing.StyleUtil;

/**
 * Vista de la pantalla del historial de incidencias, que muestra dos tablas:
 * - A la izquierda, la tabla que muestra los datos principales de las
 * incidencias asociadas al usuario que inició sesión.
 * - A la derecha, la tabla que muestra los datos principales de las
 * historias asociadas a la incidencia que el usuario haya seleccionado 
 * con el ratón. En caso de no estar ninguna seleccionada, no se mostrará
 * nada.
 */
public class HistorialIncidenciaView extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private static final Color TABLE_BORDER = new Color(120, 120, 120);

    private JScrollPane scrollInc;
    private JScrollPane scrollHist;
    private JTable tablaIncidencias;
    private JTable tablaHistorial;
    private JButton btnClose;

    public HistorialIncidenciaView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	
    	// Inicializacion de componentes

        final JLabel titleWin = new JLabel("  Historial de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Seguimiento de incidencias");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        final JLabel lblIncidencias = new JLabel("Tus incidencias:");
        StyleUtil.styleLabel(lblIncidencias, 16, false);
        
        final JLabel lblHistorial = new JLabel("Detalle de la incidencia:");
        StyleUtil.styleLabel(lblHistorial, 16, false);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollInc = new JScrollPane(tablaIncidencias);
        scrollInc.setPreferredSize(new Dimension(675, 300));
        scrollInc.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
        tablaHistorial = new JTable();
        StyleUtil.styleTable(tablaHistorial);        
        scrollHist = new JScrollPane(tablaHistorial);
        scrollHist.setPreferredSize(new Dimension(500, 300));
        scrollHist.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel tablasPanel = StyleUtil.createAbstractPanel();

        JPanel mainPanel = StyleUtil.createMainPanel();
        
        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcLblIncidencia = new GridBagConstraints();
        gbcLblIncidencia.gridx = 0;
        gbcLblIncidencia.gridy = 0;
        gbcLblIncidencia.insets = new Insets(0, 0, 10, 20);
        gbcLblIncidencia.anchor = GridBagConstraints.WEST;
        gbcLblIncidencia.fill = GridBagConstraints.NONE;
        tablasPanel.add(lblIncidencias, gbcLblIncidencia);
        
        GridBagConstraints gbcLblHistorial = new GridBagConstraints();
        gbcLblHistorial.gridx = 1;
        gbcLblHistorial.gridy = 0;
        gbcLblHistorial.insets = new Insets(0, 20, 10, 0);
        gbcLblHistorial.anchor = GridBagConstraints.WEST;
        gbcLblHistorial.fill = GridBagConstraints.NONE;
        tablasPanel.add(lblHistorial, gbcLblHistorial);
        
        GridBagConstraints gbcScrollInc = new GridBagConstraints();
        gbcScrollInc.gridx = 0;
        gbcScrollInc.gridy = 1;
        gbcScrollInc.insets = new Insets(0, 0, 0, 20);
        tablasPanel.add(scrollInc, gbcScrollInc);
        
        GridBagConstraints gbcScrollTec = new GridBagConstraints();
        gbcScrollTec.gridx = 1;
        gbcScrollTec.gridy = 1;
        gbcScrollTec.insets = new Insets(0, 20, 0, 0);
        tablasPanel.add(scrollHist, gbcScrollTec);
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 25, 0);
        gbcTitle.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcPnlTablas = new GridBagConstraints();
        gbcPnlTablas.gridx = 0;
        gbcPnlTablas.gridy = 1;
        gbcPnlTablas.insets = new Insets(0, 0, 25, 0);
        gbcPnlTablas.fill = GridBagConstraints.BOTH;
        mainPanel.add(tablasPanel, gbcPnlTablas);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    public JScrollPane getScrollInc() { return scrollInc; }
    public JScrollPane getScrollHist() { return scrollHist; }
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JTable getTablaHistorial() { return tablaHistorial; }
    public JButton getBtnClose() { return btnClose; }
}