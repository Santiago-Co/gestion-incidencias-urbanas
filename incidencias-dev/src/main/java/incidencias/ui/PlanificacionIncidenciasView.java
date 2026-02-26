package incidencias.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class PlanificacionIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BG_COLOR = new Color(249, 250, 251);
    private static final Color HEADER_BG = new Color(243, 244, 246);
    private static final Color HEADER_FG = new Color(31, 41, 55);

    private JScrollPane scrollTabla;
    private JTable tablaIncidencias;
    private JTextField textFieldHorasPrevistas;
    private JTextArea textAreaDescripcion;
    private JButton btnGuardar;
    private JButton btnClose;

    private int mouseX, mouseY;

    public PlanificacionIncidenciasView() {
        setUndecorated(true);
        initComponents();
        pack();
        setSize(900, 620);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);
        getContentPane().setLayout(new BorderLayout());

        // ========== NORTH ==========
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(HEADER_BG);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JLabel title = new JLabel("Incidencias asignadas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(HEADER_FG);

        btnClose = new JButton("X");
        styleCloseButton(btnClose);

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(btnClose, BorderLayout.EAST);
        enableWindowDrag(topPanel);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // ========== CENTER ==========
        tablaIncidencias = new JTable();
        tablaIncidencias.setFillsViewportHeight(true);
        tablaIncidencias.setRowHeight(28);
        tablaIncidencias.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaIncidencias.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(860, 300));
        getContentPane().add(scrollTabla, BorderLayout.CENTER);

        // ========== SOUTH ==========
        JPanel south = new JPanel(new BorderLayout());
        south.setBorder(BorderFactory.createEmptyBorder(12, 18, 18, 18));
        south.setBackground(Color.WHITE);

        // horas previstas
        JPanel horasRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        horasRow.setBackground(Color.WHITE);
        JLabel lblHoras = new JLabel("Horas previstas:");
        lblHoras.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblHoras.setForeground(HEADER_FG);

        textFieldHorasPrevistas = new JTextField();
        textFieldHorasPrevistas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textFieldHorasPrevistas.setColumns(6);
        textFieldHorasPrevistas.setPreferredSize(new Dimension(100, 30));

        horasRow.add(lblHoras);
        horasRow.add(textFieldHorasPrevistas);

        south.add(horasRow, BorderLayout.NORTH);

        // descripción del trabajo
        JPanel descPanel = new JPanel(new BorderLayout(6, 6));
        descPanel.setBackground(Color.WHITE);
        JLabel lblDesc = new JLabel("Descripción del trabajo:");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(HEADER_FG);

        textAreaDescripcion = new JTextArea(6, 60);
        textAreaDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textAreaDescripcion.setLineWrap(true);
        textAreaDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(textAreaDescripcion);
        scrollDesc.setPreferredSize(new Dimension(820, 120));
        scrollDesc.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        descPanel.add(lblDesc, BorderLayout.NORTH);
        descPanel.add(scrollDesc, BorderLayout.CENTER);

        south.add(descPanel, BorderLayout.CENTER);

        // boton guardar a la derecha
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setPreferredSize(new Dimension(120, 34));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(btnGuardar);

        south.add(btnPanel, BorderLayout.SOUTH);

        getContentPane().add(south, BorderLayout.SOUTH);
    }

    private void styleCloseButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(120, 120, 120));
        btn.setBorder(null);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(44, 28));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(180, 40, 40)); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(new Color(120, 120, 120)); }
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

    public JScrollPane getScrollTabla() { return this.scrollTabla; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JTextField getTextFieldHorasPrevistas() { return this.textFieldHorasPrevistas; }
    public JTextArea getTextAreaDescripcion() { return this.textAreaDescripcion; }
    public JButton getBtnGuardar() { return this.btnGuardar; }
    public JButton getBtnClose() { return this.btnClose; }
}