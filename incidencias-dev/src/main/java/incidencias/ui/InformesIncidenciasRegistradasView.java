package incidencias.ui;

import org.jdatepicker.impl.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
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
	private JButton btnClose, btnBuscar;
	private JDatePickerImpl datePickerInicio, datePickerFin;
	private JLabel txtTotalVal, txtCompletadasVal, txtRechazadasVal;
	
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
        
        final JLabel lblTitle = new JLabel("Consulta de incidencias por periodo");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(500, 400));
        
        final JLabel lblFechas = new JLabel("Introduce un periodo de tiempo:");
        StyleUtil.styleLabel(lblFechas, 14, false);
        
        final JLabel lblFechaIni = new JLabel("Fecha de inicio:");
        StyleUtil.styleLabel(lblFechaIni, 14, false);
        
        final JLabel lblFechaFin = new JLabel("Fecha de fin:");
        StyleUtil.styleLabel(lblFechaFin, 14, false);

        datePickerInicio = crearDatePicker();
        
        datePickerFin = crearDatePicker();
        
        final JLabel lblInforme = new JLabel("Resumen del periodo:");
        StyleUtil.styleLabel(lblInforme, 14, false);
        
        JPanel informePanel = StyleUtil.createAbstractPanel();
        informePanel.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel lblTotal = new JLabel("Incidencias totales:");
        StyleUtil.styleLabel(lblTotal, 14, true);
        JLabel lblComp = new JLabel("Incidencias completadas:");
        StyleUtil.styleLabel(lblComp, 14, true);
        JLabel lblRech = new JLabel("Incidencias rechazadas:");
        StyleUtil.styleLabel(lblRech, 14, true);
        
        txtTotalVal = new JLabel("0");
        txtCompletadasVal = new JLabel("0");
        txtRechazadasVal = new JLabel("0");
        
        StyleUtil.styleLabel(txtTotalVal, 14, true);
        StyleUtil.styleLabel(txtCompletadasVal, 14, true);
        StyleUtil.styleLabel(txtRechazadasVal, 14, true);
        
        informePanel.add(lblTotal); informePanel.add(txtTotalVal);
        informePanel.add(lblComp);  informePanel.add(txtCompletadasVal);
        informePanel.add(lblRech);  informePanel.add(txtRechazadasVal);
        
        btnBuscar = new JButton("Buscar");
        StyleUtil.styleSecondaryButton(btnBuscar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        //JPanel informePanel = StyleUtil.createAbstractPanel();
        
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
        mainPanel.add(lblFechas, gbcLblFechas);
        
        GridBagConstraints gbcLblIni = new GridBagConstraints();
        gbcLblIni.gridx = 1;
        gbcLblIni.gridy = 2;
        gbcLblIni.insets = new Insets(0, 0, 5, 0);
        gbcLblIni.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblFechaIni, gbcLblIni);
        
        GridBagConstraints gbcDateInicio = new GridBagConstraints();
        gbcDateInicio.gridx = 1;
        gbcDateInicio.gridy = 3;
        gbcDateInicio.insets = new Insets(0, 0, 5, 5);
        gbcDateInicio.anchor = GridBagConstraints.WEST;
        mainPanel.add(datePickerInicio, gbcDateInicio);

        GridBagConstraints gbcLblFin = new GridBagConstraints();
        gbcLblFin.gridx = 2;
        gbcLblFin.gridy = 2;
        gbcLblFin.insets = new Insets(0, 0, 5, 0);
        gbcLblFin.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblFechaFin, gbcLblFin);
        
        GridBagConstraints gbcDateFin = new GridBagConstraints();
        gbcDateFin.gridx = 2;
        gbcDateFin.gridy = 3;
        gbcDateFin.insets = new Insets(0, 0, 5, 0);
        gbcDateFin.anchor = GridBagConstraints.WEST;
        mainPanel.add(datePickerFin, gbcDateFin);
        
        GridBagConstraints gbcLblInforme = new GridBagConstraints();
        gbcLblInforme.gridx = 1;
        gbcLblInforme.gridy = 4;
        gbcLblInforme.insets = new Insets(0, 0, 5, 0);
        gbcLblInforme.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblInforme, gbcLblInforme);
        
        GridBagConstraints gbcPnlInforme = new GridBagConstraints();
        gbcPnlInforme.gridx = 1;
        gbcPnlInforme.gridy = 5;
        gbcPnlInforme.insets = new Insets(0, 0, 15, 0);
        gbcPnlInforme.anchor = GridBagConstraints.WEST;
        mainPanel.add(informePanel, gbcPnlInforme);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 1;
        gbcPnlAcciones.gridy = 6;
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
	}
	
	private JDatePickerImpl crearDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(panel, new DateLabelFormatter());
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

	public JLabel getTxtTotalVal() { return txtTotalVal; }
	public JLabel getTxtCompletadasVal() { return txtCompletadasVal; }
	public JLabel getTxtRechazadasVal() { return txtRechazadasVal; }
}
