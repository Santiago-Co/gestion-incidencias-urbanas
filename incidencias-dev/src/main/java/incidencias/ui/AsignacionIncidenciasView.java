package incidencias.ui;

import java.awt.*; 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import util.swing.StyleUtil;

public class AsignacionIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color PRIMARY_COLOR = new Color(79, 70, 229);
    private static final Color BG_COLOR = new Color(249, 250, 251);
    private static final Color LABEL_COLOR = new Color(107, 114, 128); 

    private JTable tablaIncidencias;
    private JScrollPane scrollIncidencias;
    private JTable tablaTecnicos;
    private JScrollPane scrollTecnicos;
    private JButton btnClose;
    private JButton btnAsignar;

    private int mouseX;
    private int mouseY;

    public AsignacionIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel();
        tablaIncidencias = new JTable();
        tablaTecnicos = new JTable();
        btnClose = new JButton();
        btnAsignar = new JButton("Asignar incidencia");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        final JLabel lblTitle = new JLabel("Asignación de incidencias");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 30, 0);
        mainPanel.add(lblTitle, gbc);

        JPanel tablasPanel = new JPanel(new GridBagLayout());
        tablasPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcTablas = new GridBagConstraints();

        final JLabel lblIncidencias = new JLabel("Seleccione una incidencia para asignarla:");
        lblIncidencias.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblIncidencias.setForeground(LABEL_COLOR);
        gbcTablas.gridx = 0; gbcTablas.gridy = 0;
        gbcTablas.anchor = GridBagConstraints.WEST;
        gbcTablas.fill = GridBagConstraints.NONE;
        gbcTablas.insets = new Insets(0, 0, 10, 20);
        tablasPanel.add(lblIncidencias, gbcTablas);

        final JLabel lblTecnicos = new JLabel("Técnicos disponibles:");
        lblTecnicos.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTecnicos.setForeground(LABEL_COLOR);
        gbcTablas.gridx = 1; gbcTablas.gridy = 0;
        gbcTablas.anchor = GridBagConstraints.WEST;
        gbcTablas.fill = GridBagConstraints.NONE;
        gbcTablas.insets = new Insets(0, 20, 10, 0);
        tablasPanel.add(lblTecnicos, gbcTablas);

        styleTable(tablaIncidencias);
        scrollIncidencias = new JScrollPane(tablaIncidencias);
        scrollIncidencias.setPreferredSize(new Dimension(575, 300));
        scrollIncidencias.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        gbcTablas.gridx = 0; gbcTablas.gridy = 1;
        gbcTablas.insets = new Insets(0, 0, 0, 20);
        tablasPanel.add(scrollIncidencias, gbcTablas);

        styleTable(tablaTecnicos);
        scrollTecnicos = new JScrollPane(tablaTecnicos);
        scrollTecnicos.setPreferredSize(new Dimension(300, 300));
        scrollTecnicos.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        gbcTablas.gridx = 1; gbcTablas.gridy = 1;
        gbcTablas.insets = new Insets(0, 20, 0, 0);
        tablasPanel.add(scrollTecnicos, gbcTablas);

        styleActionButton(btnAsignar);
        btnAsignar.setPreferredSize(new Dimension(300, 40));
        gbcTablas.gridx = 1; gbcTablas.gridy = 2;
        gbcTablas.insets = new Insets(20, 20, 0, 0);
        tablasPanel.add(btnAsignar, gbcTablas);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(tablasPanel, gbc);

        StyleUtil.styleCloseButton(btnClose);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(btnClose);
        enableWindowDrag(topPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(new Color(229, 231, 235));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(55, 65, 81));
    }
    
    private void styleActionButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR.darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR); }
        });
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
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                setLocation(x, y);
            }
        });
    }
    
    public boolean confirmarAsignacion(int idIncidencia, int idTecnico) {
    	int resultado = JOptionPane.showConfirmDialog(
                this,
                String.format("Desea asignar la incidencia %d al técnico %d?", idIncidencia, idTecnico),
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    	
    	return resultado == JOptionPane.YES_OPTION;
    }
    
    // TODO: convertir esto en un método estático de SwingUtil {Angel}
    public void mostrarExito() {
        JOptionPane.showMessageDialog(this, "La incidencia se asignó correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JScrollPane getScrollIncidencias() { return this.scrollIncidencias; }
    public JTable getTablaTecnicos() { return this.tablaTecnicos; }
    public JScrollPane getScrollTecnicos() { return this.scrollTecnicos; }
    public JButton getBtnClose() { return this.btnClose; }
    public JButton getBtnAsignar() { return this.btnAsignar; }
}
