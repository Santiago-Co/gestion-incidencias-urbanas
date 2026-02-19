package incidencias.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.JTableHeader;

public class ConsultaIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color BG_COLOR = new Color(249, 250, 251);

    private JComboBox<String> cbFiltroEstado;
    private JTable tablaIncidencias;
    private JButton btnClose;
    
    private int mouseX;
    private int mouseY;

    public ConsultaIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel();
        cbFiltroEstado = new JComboBox<>(new String[]{
                "Todos los estados", "Nueva", "Validada", "Asignada", "En proceso", "Resuelta", "Cerrada"
        });
        tablaIncidencias = new JTable();
        btnClose = new JButton("X");
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);
        
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        final JLabel lblTitle = new JLabel("Mis incidencias registradas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(31, 41, 55));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(lblTitle, gbc);
        
        final JLabel lblFiltro = new JLabel("Buscar por estado:");
        lblFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFiltro.setForeground(new Color(107, 114, 128));

        styleComboBox(cbFiltroEstado);
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(lblFiltro);
        filterPanel.add(cbFiltroEstado);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(filterPanel, gbc);
        
        styleTable(tablaIncidencias);
        JScrollPane scrollPane = new JScrollPane(tablaIncidencias);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 2));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(scrollPane, gbc);
        
        styleCloseButton(btnClose);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.add(btnClose);
        enableWindowDrag(topPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }

    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(200, 35));
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BG_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    private void styleCloseButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setForeground(Color.WHITE);
            }
        });

        btn.addActionListener(e -> dispose());
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
    
    public JComboBox<String> getListaFiltros() { return this.cbFiltroEstado; }
    
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
     
}