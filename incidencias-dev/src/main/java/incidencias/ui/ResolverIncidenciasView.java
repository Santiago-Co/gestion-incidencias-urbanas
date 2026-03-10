package incidencias.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.swing.StyleUtil;

public class ResolverIncidenciasView extends JFrame{

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollTabla;
    private JTable tablaIncidencias;
    private JTextField textFieldHorasReales;
    private JTextArea textAreaDescripcion;
    private JButton btnGuardar;
    private JButton btnClose;
	
	public ResolverIncidenciasView() {
		setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
	}
	
	public void initComponents() {
        // Inicializacion de componentes
    	
        final JLabel titleWin = new JLabel("  Resolución de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Mis incidencias asignadas");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(500, 400));
        
        final JLabel lblHoras = new JLabel("Horas finales de trabajo:");
        StyleUtil.styleLabel(lblHoras, 14, false);
        
        textFieldHorasReales = new JTextField();
        StyleUtil.styleTextField(textFieldHorasReales, 100, 30);
        textFieldHorasReales.setColumns(6);
        
        final JLabel lblDesc = new JLabel("Descripción general de los trabajos:");
        StyleUtil.styleLabel(lblDesc, 14, false);
        
        textAreaDescripcion = new JTextArea(6, 30);
        JScrollPane scrollDesc = new JScrollPane(textAreaDescripcion);
        StyleUtil.styleTextArea(textAreaDescripcion, scrollDesc, 400, 200);
        
        btnGuardar = new JButton("Guardar");
        StyleUtil.styleSecondaryButton(btnGuardar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel horasPanel = StyleUtil.createAbstractPanel();
        
        JButton[] btns = {btnGuardar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);
        
        JPanel mainPanel = StyleUtil.createMainPanel();
     
        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblTitle, gbcTitle);
        
        GridBagConstraints gbcScrollTabla = new GridBagConstraints();
        gbcScrollTabla.gridx = 0;
        gbcScrollTabla.gridy = 1;
        gbcScrollTabla.gridheight = GridBagConstraints.REMAINDER;
        gbcScrollTabla.insets = new Insets(0, 0, 15, 20);
        mainPanel.add(scrollTabla, gbcScrollTabla);
        
        GridBagConstraints gbcPnlHoras = new GridBagConstraints();
        gbcPnlHoras.gridx = 1;
        gbcPnlHoras.gridy = 1;
        gbcPnlHoras.insets = new Insets(0, 0, 15, 0);
        gbcPnlHoras.anchor = GridBagConstraints.WEST;
        mainPanel.add(horasPanel, gbcPnlHoras);
        
        GridBagConstraints gbcLblDescripcion = new GridBagConstraints();
        gbcLblDescripcion.gridx = 1;
        gbcLblDescripcion.gridy = 2;
        gbcLblDescripcion.insets = new Insets(0, 0, 5, 0);
        gbcLblDescripcion.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblDesc, gbcLblDescripcion);
        
        GridBagConstraints gbcScrollDescripcion = new GridBagConstraints();
        gbcScrollDescripcion.gridx = 1;
        gbcScrollDescripcion.gridy = 3;
        gbcScrollDescripcion.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(scrollDesc, gbcScrollDescripcion);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 1;
        gbcPnlAcciones.gridy = 4;
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);
        
        GridBagConstraints gbcLblHoras = new GridBagConstraints();
        gbcLblHoras.gridx = 0;
        gbcLblHoras.gridy = 0;
        gbcLblHoras.anchor = GridBagConstraints.WEST;
        horasPanel.add(lblHoras, gbcLblHoras);
        
        GridBagConstraints gbcTxtHoras = new GridBagConstraints();
        gbcTxtHoras.gridx = 1;
        gbcTxtHoras.gridy = 0;
        gbcTxtHoras.insets = new Insets(0, 10, 0, 0);
        horasPanel.add(textFieldHorasReales, gbcTxtHoras);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
	}

	public JScrollPane getScrollTabla() { return scrollTabla; }
	public void setScrollTabla(JScrollPane scrollTabla) { this.scrollTabla = scrollTabla; }
	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public void setTablaIncidencias(JTable tablaIncidencias) { this.tablaIncidencias = tablaIncidencias; }
	public JTextField getTextFieldHorasReales() { return textFieldHorasReales; }
	public void setTextFieldHorasReales(JTextField textFieldHorasReales) { this.textFieldHorasReales = textFieldHorasReales; }
	public JTextArea getTextAreaDescripcion() {	return textAreaDescripcion;	}
	public void setTextAreaDescripcion(JTextArea textAreaDescripcion) {	this.textAreaDescripcion = textAreaDescripcion;	}
	public JButton getBtnGuardar() { return btnGuardar;	}
	public void setBtnGuardar(JButton btnGuardar) {	this.btnGuardar = btnGuardar; }
	public JButton getBtnClose() { return btnClose;	}
	public void setBtnClose(JButton btnClose) {	this.btnClose = btnClose; }
}
