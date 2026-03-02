package incidencias.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.swing.StyleUtil;
import util.swing.SwingUtil;

public class ClasificarIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color PRIMARY_COLOR = new Color(37, 99, 235);
    //TODO buscar una mejor manera de hacer esto, no me acuerdo ni pq lo hice así {Santiago}
    private static final String MENSAJE_CONFIRMACION_INICIO = "¿Desea validar la incidencia: ";
    private static final String MENSAJE_CONFIRMACION_FIN = "?";
    
    private JScrollPane scrollPane;
    private JTable tablaIncidencias;
    private JButton btnClose;
    private JButton btnValidar;

    public ClasificarIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        String[] columnas = {"ID", "Tipo", "Descripción", "Fecha/Hora"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaIncidencias = new JTable(modelo) {
			private static final long serialVersionUID = 1L;

			@Override
        	public boolean isCellEditable(int row, int col) {
        		return col == 1; 
        	}
        };
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(229, 231, 235));
        topPanel.setPreferredSize(new Dimension(getWidth(), 40));
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JLabel titleWin = new JLabel("  Sistema de Gestión de Incidencias");
        titleWin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleWin.setForeground(new Color(75, 85, 99));
        
        topPanel.add(titleWin, BorderLayout.WEST);
        topPanel.add(btnClose, BorderLayout.EAST);
        SwingUtil.enableWindowDrag(this, topPanel);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblTitle = new JLabel("Nuevas incidencias registradas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);

        JPanel pnlFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlFiltros.setBackground(Color.WHITE);
        
        
        
        gbc.gridy = 1; gbc.gridwidth = 2;
        mainPanel.add(pnlFiltros, gbc);

        StyleUtil.styleTable(tablaIncidencias);
        scrollPane = new JScrollPane(tablaIncidencias);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        gbc.gridy = 2; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);

        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlAcciones.setBackground(Color.WHITE);
        
        btnValidar = new JButton("Validar");
        styleActionButton(btnValidar, PRIMARY_COLOR);

        pnlAcciones.add(btnValidar);
        
        gbc.gridy = 3; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(pnlAcciones, gbc);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
    }

    private void styleActionButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean confirmacionIncidencia(String idIncidencia) {
    	int resultado = JOptionPane.showConfirmDialog(
                this,
                MENSAJE_CONFIRMACION_INICIO + idIncidencia + MENSAJE_CONFIRMACION_FIN,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
    	
    	return resultado == JOptionPane.YES_OPTION;
    }
    
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }

    public JTable getTablaIncidencias() { return this.tablaIncidencias; }

    public JButton getBtnClose() { return this.btnClose; }

    public JButton getBtnValidar() { return this.btnValidar; }
}