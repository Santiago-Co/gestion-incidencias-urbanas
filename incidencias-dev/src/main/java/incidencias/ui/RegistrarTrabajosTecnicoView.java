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

import util.swing.StyleUtil;

public class RegistrarTrabajosTecnicoView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollIncidencias, scrollTrabajos;
    private JTable tablaIncidencias, tablaTrabajos;
    private JTextArea textAreaComentarios;
    private JButton btnGuardar, btnClose;
	
	public RegistrarTrabajosTecnicoView() {
		setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
	}

	private void initComponents() {

    	// Inicializacion de componentes
    	
        final JLabel titleWin = new JLabel("Registro de trabajos diarios");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblIncidencias = new JLabel("Mis incidencias en curso");
        StyleUtil.styleLabel(lblIncidencias, 26, false);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollIncidencias = new JScrollPane(tablaIncidencias);
        scrollIncidencias.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollIncidencias.setPreferredSize(new Dimension(500, 250));
        
        final JLabel lblTrabajos = new JLabel("Trabajos registrados de la incidencia");
        StyleUtil.styleLabel(lblTrabajos, 26, false);
        
        tablaTrabajos = new JTable();
        StyleUtil.styleTable(tablaTrabajos);
        scrollTrabajos = new JScrollPane(tablaTrabajos);
        scrollTrabajos.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTrabajos.setPreferredSize(new Dimension(500, 250));
        
        final JLabel lblComentarios = new JLabel("Introduce los comentarios de hoy:");
        StyleUtil.styleLabel(lblComentarios, 14, false);
        
        textAreaComentarios = new JTextArea(3, 30);
        textAreaComentarios.setLineWrap(true);
        textAreaComentarios.setWrapStyleWord(true);
        JScrollPane scrollComentarios = new JScrollPane(textAreaComentarios);
        StyleUtil.styleTextArea(textAreaComentarios, scrollComentarios, 860, 120);
        
        btnGuardar = new JButton("Guardar");
        StyleUtil.styleSecondaryButton(btnGuardar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JButton[] btns = {btnGuardar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);
        
        JPanel mainPanel = StyleUtil.createMainPanel();
     
        // Colocacion de los componentes de la pantalla
        
        
        GridBagConstraints gbcLblIncidencias = new GridBagConstraints();
        gbcLblIncidencias.gridx = 0;
        gbcLblIncidencias.gridy = 0;
        gbcLblIncidencias.anchor = GridBagConstraints.WEST;
        gbcLblIncidencias.insets = new Insets(0, 0, 10, 10);
        mainPanel.add(lblIncidencias, gbcLblIncidencias);

        GridBagConstraints gbcLblTrabajos = new GridBagConstraints();
        gbcLblTrabajos.gridx = 1;
        gbcLblTrabajos.gridy = 0;
        gbcLblTrabajos.anchor = GridBagConstraints.WEST;
        gbcLblTrabajos.insets = new Insets(0, 10, 10, 0);
        mainPanel.add(lblTrabajos, gbcLblTrabajos);

        GridBagConstraints gbcScrollIncidencias = new GridBagConstraints();
        gbcScrollIncidencias.gridx = 0;
        gbcScrollIncidencias.gridy = 1;
        gbcScrollIncidencias.weightx = 0.5;
        gbcScrollIncidencias.weighty = 0.45;
        gbcScrollIncidencias.fill = GridBagConstraints.BOTH;
        gbcScrollIncidencias.insets = new Insets(0, 0, 15, 10);
        mainPanel.add(scrollIncidencias, gbcScrollIncidencias);

        GridBagConstraints gbcScrollTrabajos = new GridBagConstraints();
        gbcScrollTrabajos.gridx = 1;
        gbcScrollTrabajos.gridy = 1;
        gbcScrollTrabajos.weightx = 0.5;
        gbcScrollTrabajos.weighty = 0.45;
        gbcScrollTrabajos.fill = GridBagConstraints.BOTH;
        gbcScrollTrabajos.insets = new Insets(0, 10, 15, 0);
        mainPanel.add(scrollTrabajos, gbcScrollTrabajos);

        GridBagConstraints gbcLblComentarios = new GridBagConstraints();
        gbcLblComentarios.gridx = 0;
        gbcLblComentarios.gridy = 2;
        gbcLblComentarios.gridwidth = 2;
        gbcLblComentarios.anchor = GridBagConstraints.WEST;
        gbcLblComentarios.insets = new Insets(5, 0, 5, 0);
        mainPanel.add(lblComentarios, gbcLblComentarios);

        GridBagConstraints gbcScrollComentarios = new GridBagConstraints();
        gbcScrollComentarios.gridx = 0;
        gbcScrollComentarios.gridy = 3;
        gbcScrollComentarios.gridwidth = 2;
        gbcScrollComentarios.weightx = 1.0;
        gbcScrollComentarios.weighty = 0.15;
        gbcScrollComentarios.fill = GridBagConstraints.BOTH;
        gbcScrollComentarios.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(scrollComentarios, gbcScrollComentarios);

        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 4;
        gbcPnlAcciones.gridwidth = 2;
        gbcPnlAcciones.anchor = GridBagConstraints.CENTER;
        gbcPnlAcciones.insets = new Insets(5, 0, 0, 0);
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
	}
	
	public JScrollPane getScrollIncidencias() { return scrollIncidencias; }
	public void setScrollIncidencias(JScrollPane scrollIncidencias) { this.scrollIncidencias = scrollIncidencias; }
	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public void setTablaIncidencias(JTable tablaIncidencias) { this.tablaIncidencias = tablaIncidencias; }
	public JButton getBtnGuardar() { return btnGuardar;	}
	public void setBtnGuardar(JButton btnGuardar) {	this.btnGuardar = btnGuardar; }
	public JButton getBtnClose() { return btnClose;	}
	public void setBtnClose(JButton btnClose) {	this.btnClose = btnClose; }
	public JScrollPane getScrollTrabajos() { return scrollTrabajos; }
	public void setScrollTrabajos(JScrollPane scrollTrabajos) { this.scrollTrabajos = scrollTrabajos; }
	public JTable getTablaTrabajos() { return tablaTrabajos; }
	public void setTablaTrabajos(JTable tablaTrabajos) { this.tablaTrabajos = tablaTrabajos; }
	public JTextArea getTextAreaComentarios() { return textAreaComentarios; }
	public void setTextAreaComentarios(JTextArea textAreaComentarios) {	this.textAreaComentarios = textAreaComentarios; }
}
