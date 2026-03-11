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
    	
    	// Inicializacion de componentes

        JLabel titleWin = new JLabel("  Clasificación de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Nuevas incidencias registradas");
        StyleUtil.styleLabel(lblTitle, 24, true);
        
        String[] columnas = {"ID", "Tipo", "Descripción", "Fecha/Hora", "Estado"};
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
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JButton[] btns = {btnValidar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);

        JPanel mainPanel = StyleUtil.createMainPanel();

        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(5, 5, 10, 5);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 1;
        gbcScroll.insets = new Insets(5, 5, 10, 5);
        gbcScroll.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollPane, gbcScroll);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 2;
        gbcPnlAcciones.insets = new Insets(5, 5, 10, 5);
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }
    
    public JScrollPane getScrollIncidencias() { return this.scrollPane; }

    public JTable getTablaIncidencias() { return this.tablaIncidencias; }

    public JButton getBtnClose() { return this.btnClose; }

    public JButton getBtnValidar() { return this.btnValidar; }
}