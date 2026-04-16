package incidencias.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import util.swing.StyleUtil;

public class CerrarIncidenciasEspecialidadView extends JFrame{

	private static final long serialVersionUID = 1L;

	private JTable tablaIncidencias;
    private JButton btnCerrar, btnClose;
    private JScrollPane scrollTabla;
	private JLabel lblTitle;
	
	public CerrarIncidenciasEspecialidadView() {
		setUndecorated(true);
        initComponents();
        
        this.pack();
        this.setSize(900, 700); 
        setLocationRelativeTo(null);
	}

	private void initComponents() {
        // Inicializacion de componentes
    	
        final JLabel titleWin = new JLabel("Cierre de incidencias de mi especialidad");
        StyleUtil.styleWindowTitle(titleWin);
        
        lblTitle = new JLabel("Incidencias resueltas en la especialidad:");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(500, 400));
        tablaIncidencias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Permite múltiple selección con CTRL o SHIFT
        
        btnCerrar = new JButton("Cerrar");
        StyleUtil.styleSecondaryButtonRed(btnCerrar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JButton[] btns = {btnCerrar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);

        JPanel mainPanel = StyleUtil.createMainPanel();
     
        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(5, 5, 5, 5);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 1;
        gbcScroll.insets = new Insets(5, 5, 10, 5);
        gbcScroll.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollTabla, gbcScroll);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 2;
        gbcPnlAcciones.insets = new Insets(5, 5, 0, 5);
        gbcPnlAcciones.fill = GridBagConstraints.CENTER;
        mainPanel.add(accionesPanel, gbcPnlAcciones);
        
        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
	}
	
	public JTable getTablaIncidencias() { return tablaIncidencias; }
    public JButton getBtnCerrar() { return btnCerrar; }
    public JButton getBtnClose() { return btnClose; }
    public JScrollPane getScrollTabla() { return scrollTabla; }
	public JLabel getLblTitle() { return lblTitle; }
}
