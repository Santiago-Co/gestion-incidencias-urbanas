package incidencias.ui;

import org.jdatepicker.impl.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import util.swing.DateLabelFormatter;
import util.swing.StyleUtil;

/**
 * Se ha modificado pom.xml para añadir la dependencia correspondiente al DatePicker
 * (ya que no está incluido en Swing)
 */
public class InformesIncidenciasRegistradasView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollTabla;
	private JTable tablaIncidencias;
	private JButton btnClose;
	private JButton btnBuscar;
	private JDatePickerImpl datePickerInicio;
	private JDatePickerImpl datePickerFin;
	
	public InformesIncidenciasRegistradasView() {
		setUndecorated(true);
	    initComponents();
	    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
	    setLocationRelativeTo(null);
	}

	private void initComponents() {
		// Inicializacion de componentes
    	
        final JLabel titleWin = new JLabel("  Informe de las incidencias registradas");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Incidencias registradas entre ...");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(500, 400));
        
        final JLabel lblFechas = new JLabel("Introduce un periodo de tiempo:");
        StyleUtil.styleLabel(lblFechas, 14, false);
        
        UtilDateModel modelInicio = new UtilDateModel();
        Properties pInicio = new Properties();
        pInicio.put("text.today", "Today");
        pInicio.put("text.month", "Month");
        pInicio.put("text.year", "Year");

        JDatePanelImpl datePanelInicio = new JDatePanelImpl(modelInicio, pInicio);
        datePickerInicio = new JDatePickerImpl(datePanelInicio, new DateLabelFormatter());

        UtilDateModel modelFin = new UtilDateModel();
        Properties pFin = new Properties();
        pFin.put("text.today", "Today");
        pFin.put("text.month", "Month");
        pFin.put("text.year", "Year");

        JDatePanelImpl datePanelFin = new JDatePanelImpl(modelFin, pFin);
        datePickerFin = new JDatePickerImpl(datePanelFin, new DateLabelFormatter());
        
        final JLabel lblInforme = new JLabel("Informe de las incidencias del periodo seleccionado:");
        StyleUtil.styleLabel(lblInforme, 14, false);
        
        // más cosas del informe
        
        btnBuscar = new JButton("Buscar");
        StyleUtil.styleSecondaryButton(btnBuscar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel informePanel = StyleUtil.createAbstractPanel();
        
        JButton[] btns = {btnBuscar};
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
        
        GridBagConstraints gbcLblFechas = new GridBagConstraints();
        gbcLblFechas.gridx = 1;
        gbcLblFechas.gridy = 1;
        gbcLblFechas.insets = new Insets(0, 0, 5, 0);
        gbcLblFechas.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblInforme, gbcLblFechas);
        
        GridBagConstraints gbcDateInicio = new GridBagConstraints();
        gbcDateInicio.gridx = 1;
        gbcDateInicio.gridy = 2;
        gbcDateInicio.insets = new Insets(0, 0, 5, 5);
        gbcDateInicio.anchor = GridBagConstraints.WEST;
        mainPanel.add(datePickerInicio, gbcDateInicio);

        GridBagConstraints gbcDateFin = new GridBagConstraints();
        gbcDateFin.gridx = 2;
        gbcDateFin.gridy = 2;
        gbcDateFin.insets = new Insets(0, 0, 5, 0);
        gbcDateFin.anchor = GridBagConstraints.WEST;
        mainPanel.add(datePickerFin, gbcDateFin);
        
        GridBagConstraints gbcPnlInforme = new GridBagConstraints();
        gbcPnlInforme.gridx = 1;
        gbcPnlInforme.gridy = 3;
        gbcPnlInforme.insets = new Insets(0, 0, 15, 0);
        gbcPnlInforme.anchor = GridBagConstraints.WEST;
        mainPanel.add(informePanel, gbcPnlInforme);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 1;
        gbcPnlAcciones.gridy = 4;
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
	}

	public JButton getBtnClose() { return btnClose;	}
	public void setBtnClose(JButton btnClose) {	this.btnClose = btnClose; }
	
	public JButton getBtnBuscar() { return btnBuscar;	}
	public void setBtnBuscar(JButton btnBuscar) {	this.btnBuscar = btnBuscar; }

	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public void setTablaIncidencias(JTable tablaIncidencias) { this.tablaIncidencias = tablaIncidencias; }
	
	public JScrollPane getScrollTabla() { return scrollTabla; }
	public void setScrollTabla(JScrollPane scrollTabla) { this.scrollTabla = scrollTabla; }

	public JDatePickerImpl getDatePickerInicio() { return datePickerInicio; }
	public void setDatePickerInicio(JDatePickerImpl datePickerInicio) {	this.datePickerInicio = datePickerInicio; }

	public JDatePickerImpl getDatePickerFin() {	return datePickerFin; }
	public void setDatePickerFin(JDatePickerImpl datePickerFin) { this.datePickerFin = datePickerFin; }
}
