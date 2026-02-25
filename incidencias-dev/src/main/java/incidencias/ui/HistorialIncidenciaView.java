package incidencias.ui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class HistorialIncidenciaView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BG_COLOR = new Color(249, 250, 251);
    private static final Color HEADER_BG = new Color(243, 244, 246);
    private static final Color HEADER_FG = new Color(31, 41, 55);

    private JTable tablaIncidencias;
    private JTable tablaHistorial;
    private JButton btnClose;
    private JLabel lblTitle;

    // Drag window support
    private int mouseX, mouseY;

    public HistorialIncidenciaView() {
        super("Historial de Incidencia");
        setUndecorated(true);
        initComponents();

        pack();
        setSize(800, 600);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(HEADER_BG);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        lblTitle = new JLabel("Historial de Incidencias");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(HEADER_FG);

        btnClose = new JButton("X");
        styleCloseButton(btnClose);

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        rightControls.setOpaque(false);
        rightControls.add(btnClose);

        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(rightControls, BorderLayout.EAST);

        enableWindowDrag(topPanel);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.45);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(null);


        tablaIncidencias = new JTable();
        tablaIncidencias.setFillsViewportHeight(true);
        tablaIncidencias.setRowHeight(28);
        tablaIncidencias.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTableHeader headerInc = tablaIncidencias.getTableHeader();
        headerInc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        headerInc.setBackground(new Color(243, 244, 246));
        headerInc.setForeground(new Color(55, 65, 81));

        JScrollPane scrollInc = new JScrollPane(tablaIncidencias);
        scrollInc.setBorder(BorderFactory.createTitledBorder("Listado de incidencias"));
        scrollInc.setPreferredSize(new Dimension(420, 400));

        // Right - historial de la incidencia seleccionada
        tablaHistorial = new JTable(); // columnas: fechaHora, id, descripcion, estado
        tablaHistorial.setFillsViewportHeight(true);
        tablaHistorial.setRowHeight(26);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JTableHeader headerHist = tablaHistorial.getTableHeader();
        headerHist.setFont(new Font("Segoe UI", Font.BOLD, 13));
        headerHist.setBackground(new Color(243, 244, 246));
        headerHist.setForeground(new Color(55, 65, 81));

        JScrollPane scrollHist = new JScrollPane(tablaHistorial);
        scrollHist.setBorder(BorderFactory.createTitledBorder("Historial (más recientes arriba)"));
        scrollHist.setPreferredSize(new Dimension(520, 400));

        splitPane.setLeftComponent(scrollInc);
        splitPane.setRightComponent(scrollHist);

        getContentPane().add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));
        JLabel lblHint = new JLabel("Seleccione una incidencia para ver su historial.");
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHint.setForeground(new Color(107, 114, 128));
        bottomPanel.add(lblHint, BorderLayout.WEST);

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleCloseButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(44, 28));
        btn.setBackground(new Color(120,120,120));
        btn.setForeground(Color.WHITE);
        btn.setBorder(null);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> dispose());
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(180,40,40)); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(new Color(120,120,120)); }
        });
    }

    private void enableWindowDrag(JComponent comp) {
        comp.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        comp.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                setLocation(x, y);
            }
        });
    }

    public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JTable getTablaHistorial() { return tablaHistorial; }
    public JButton getBtnClose() { return btnClose; }
    public JLabel getLblTitle() { return lblTitle; }
}