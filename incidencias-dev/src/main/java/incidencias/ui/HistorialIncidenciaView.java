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
        JPanel mainPanel = new JPanel();

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
        scrollInc.setPreferredSize(new Dimension(655, 300));
        scrollInc.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
        tablaHistorial = new JTable();
        StyleUtil.styleTable(tablaHistorial);        
        scrollHist = new JScrollPane(tablaHistorial);
        scrollHist.setPreferredSize(new Dimension(500, 300));
        scrollHist.setBorder(BorderFactory.createLineBorder(TABLE_BORDER, 2));
        
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
        tablasPanel.add(lblHistorial, gbcTablas);
        gbcTablas.gridx = 0; gbcTablas.gridy = 1;
        gbcTablas.insets = new Insets(0, 0, 0, 20);
        tablasPanel.add(scrollInc, gbcTablas);
        gbcTablas.gridx = 1; gbcTablas.gridy = 1;
        gbcTablas.insets = new Insets(0, 20, 0, 0);
        tablasPanel.add(scrollHist, gbcTablas);
        
        /*
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollInc, scrollHist);
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(10);
        splitPane.setBackground(Color.WHITE);
        splitPane.setBorder(null);
        */

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblTitle, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tablasPanel, gbc);

        getContentPane().setBackground(StyleUtil.BG_COLOR); 
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    public JScrollPane getScrollInc() { return scrollInc; }
    public JScrollPane getScrollHist() { return scrollHist; }
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JTable getTablaHistorial() { return tablaHistorial; }
    public JButton getBtnClose() { return btnClose; }
}