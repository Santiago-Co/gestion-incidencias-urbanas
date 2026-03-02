package incidencias.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.JTableHeader;

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
    private static final Color BG_COLOR = new Color(249, 250, 251);
    private static final Color TABLE_BORDER = new Color(209, 213, 219);

    private JScrollPane scrollInc;
    private JScrollPane scrollHist;
    private JTable tablaIncidencias;
    private JTable tablaHistorial;
    private JButton btnClose;
    private int mouseX, mouseY;

    public HistorialIncidenciaView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        getContentPane().setBackground(BG_COLOR); 
        getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20), // Margen exterior
            BorderFactory.createEmptyBorder(10, 30, 20, 30)  // Padding interior blanco
        ));
        
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Título ---
        JLabel lblTitle = new JLabel("Seguimiento de Incidencias");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 25, 0);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblTitle, gbc);

        // --- Tablas y SplitPane ---
        tablaIncidencias = new JTable();
        tablaHistorial = new JTable();
        styleTable(tablaIncidencias);
        styleTable(tablaHistorial);

        scrollInc = new JScrollPane(tablaIncidencias);
        scrollInc.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(TABLE_BORDER), "Tus Incidencias"));
        
        scrollHist = new JScrollPane(tablaHistorial);
        scrollHist.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(TABLE_BORDER), "Detalle de la Incidencia"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollInc, scrollHist);
        splitPane.setDividerLocation(500);
        splitPane.setDividerSize(10);
        splitPane.setBackground(Color.WHITE);
        splitPane.setBorder(null);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(splitPane, gbc);

        // --- Barra superior ---
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topPanel.setBackground(new Color(229, 231, 235));
        topPanel.add(btnClose);
        enableWindowDrag(topPanel);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(229, 231, 235));
        table.setSelectionBackground(new Color(243, 244, 246));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(55, 65, 81));
        header.setReorderingAllowed(false);
    }

    private void enableWindowDrag(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - mouseX, e.getYOnScreen() - mouseY);
            }
        });
    }

    public JScrollPane getScrollInc() { return scrollInc; }
    public JScrollPane getScrollHist() { return scrollHist; }
    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JTable getTablaHistorial() { return tablaHistorial; }
    public JButton getBtnClose() { return btnClose; }
}