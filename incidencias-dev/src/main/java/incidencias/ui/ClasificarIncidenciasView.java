package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import util.swing.StyleUtil;

public class ClasificarIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;
    
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
        JPanel mainPanel = new JPanel();

        JLabel titleWin = new JLabel("  Clasificación de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Nuevas incidencias registradas");
        StyleUtil.styleLabel(lblTitle, 24, true);
        
        String[] columnas = {"ID", "Tipo", "Descripción", "Fecha/Hora"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaIncidencias = new JTable(modelo) {
			private static final long serialVersionUID = 1L;

			@Override
        	public boolean isCellEditable(int row, int col) {
        		return col == 1; 
        	}
        };
        StyleUtil.styleTable(tablaIncidencias);
        scrollPane = new JScrollPane(tablaIncidencias);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        
        btnValidar = new JButton("Validar");
        StyleUtil.styleSecondaryButton(btnValidar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel accionesPanel = new JPanel(new BorderLayout());
        accionesPanel.setBackground(Color.WHITE);
        accionesPanel.add(btnValidar, BorderLayout.EAST);

        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 10, 5);
        mainPanel.add(lblTitle, gbc);

        gbc.gridy = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbc);
        
        gbc.gridy = 2; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbc);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        pack();
    }
    
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }

    public JTable getTablaIncidencias() { return this.tablaIncidencias; }

    public JButton getBtnClose() { return this.btnClose; }

    public JButton getBtnValidar() { return this.btnValidar; }
}