package incidencias.ui;

import org.jdatepicker.impl.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.swing.DateLabelFormatter;
import util.swing.StyleUtil;

/**
 * Se ha modificado pom.xml para añadir la dependencia correspondiente al DatePicker
 * (ya que no está incluido en Swing)
 */
public class InformesIncidenciasRegistradasView extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel txtPanel, fechasPanel, informePanel;
	private JTabbedPane estadisticasPanel;
	private JScrollPane scrollTabla;
	private JScrollPane scrollEstTipo, scrollEstLocalizacion, scrollEstEstado;
    private JComboBox<String> cbFiltro;
    private JTextField txtFiltro;
	private JTable tablaIncidencias;
	private JTable tablaEstTipo, tablaEstLocalizacion, tablaEstEstado;
	private JButton btnClose, btnBuscar1, btnBuscar2;
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
		
	    final JLabel titleWin = new JLabel("Informe de las incidencias registradas");
	    StyleUtil.styleWindowTitle(titleWin);

	    final JLabel lblTitle = new JLabel("Informe agregado de incidencias registradas");
	    StyleUtil.styleLabel(lblTitle, 26, true);

	    tablaIncidencias = new JTable();
	    StyleUtil.styleTable(tablaIncidencias);
	    scrollTabla = new JScrollPane(tablaIncidencias);
	    scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
	    scrollTabla.setPreferredSize(new Dimension(600, 530));
	    scrollTabla.setMaximumSize(new Dimension(600, 530));
	    scrollTabla.setMinimumSize(new Dimension(600, 530));
	    
	    final JLabel lblFiltro = new JLabel("Selecciona un filtro para la consulta:");
	    StyleUtil.styleLabel(lblFiltro, 14, false);
	    
	    final JLabel lblParam = new JLabel("Introduce un valor para el parámetro seleccionado:");
	    StyleUtil.styleLabel(lblParam, 14, false);
	    
	    txtFiltro = new JTextField();
	    StyleUtil.styleTextField(txtFiltro, 150, 26);

	    final JLabel lblFechas = new JLabel("Introduce un periodo de tiempo:");
	    StyleUtil.styleLabel(lblFechas, 14, false);

	    final JLabel lblFechaIni = new JLabel("Fecha de inicio:");
	    StyleUtil.styleLabel(lblFechaIni, 14, true);
	    datePickerInicio = crearDatePicker();

	    final JLabel lblFechaFin = new JLabel("Fecha de fin:");
	    StyleUtil.styleLabel(lblFechaFin, 14, true);
	    datePickerFin = crearDatePicker();
	    
	    btnBuscar1 = new JButton("Buscar");
	    StyleUtil.styleSmallButton(btnBuscar1);
	    
	    btnBuscar2 = new JButton("Buscar");
	    StyleUtil.styleSmallButton(btnBuscar2);

	    final JLabel lblInforme = new JLabel("Resumen del periodo:");
	    StyleUtil.styleLabel(lblInforme, 14, false);

	    final JLabel lblTotal = new JLabel("Incidencias totales:");
	    StyleUtil.styleLabel(lblTotal, 14, true);
	    txtTotalVal = new JLabel("0");
	    StyleUtil.styleLabel(txtTotalVal, 14, true);

	    final JLabel lblComp = new JLabel("Incidencias completadas:");
	    StyleUtil.styleLabel(lblComp, 14, true);
	    txtCompletadasVal = new JLabel("0");
	    StyleUtil.styleLabel(txtCompletadasVal, 14, true);

	    final JLabel lblRech = new JLabel("Incidencias rechazadas:");
	    StyleUtil.styleLabel(lblRech, 14, true);
	    txtRechazadasVal = new JLabel("0");
	    StyleUtil.styleLabel(txtRechazadasVal, 14, true);

	    cbFiltro = new JComboBox<>(new String[]{"Fecha", "Tipo", "Localización", "Estado"});
	    StyleUtil.styleComboBox(cbFiltro, 100, 35);
	    cbFiltro.setSelectedIndex(-1);
	    
	    tablaEstTipo = new JTable();
	    StyleUtil.styleTable(tablaEstTipo);
	    scrollEstTipo = new JScrollPane(tablaEstTipo);
	    scrollEstTipo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
	    scrollEstTipo.setPreferredSize(new Dimension(600, 200));
	    scrollEstTipo.setMaximumSize(new Dimension(600, 200));
	    
	    tablaEstLocalizacion = new JTable();
	    StyleUtil.styleTable(tablaEstLocalizacion);
	    scrollEstLocalizacion = new JScrollPane(tablaEstLocalizacion);
	    scrollEstLocalizacion.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
	    scrollEstLocalizacion.setPreferredSize(new Dimension(600, 200));
	    scrollEstLocalizacion.setMaximumSize(new Dimension(600, 200));
	    
	    tablaEstEstado = new JTable();
	    StyleUtil.styleTable(tablaEstEstado);
	    scrollEstEstado = new JScrollPane(tablaEstEstado);
	    scrollEstEstado.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
	    scrollEstEstado.setPreferredSize(new Dimension(600, 200));
	    scrollEstEstado.setMaximumSize(new Dimension(600, 200));

	    btnClose = new JButton();
	    StyleUtil.styleCloseButton(btnClose);

        // Creacion de las secciones de la pantalla
	    
	    JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
	    
	    JPanel filtrosPanel = StyleUtil.createAbstractPanel();
	    filtrosPanel.setLayout(new GridBagLayout());
	    
	    txtPanel = StyleUtil.createAbstractPanel();
	    txtPanel.setLayout(new GridBagLayout());
	    
	    fechasPanel = StyleUtil.createAbstractPanel();
	    fechasPanel.setLayout(new GridBagLayout());
	    
	    informePanel = StyleUtil.createAbstractPanel();
	    informePanel.setLayout(new GridBagLayout());
	    
	    JPanel estTipoPanel = new JPanel();
	    estTipoPanel.add(scrollEstTipo);
	    JPanel estLocalizacionPanel = new JPanel();
	    estLocalizacionPanel.add(scrollEstLocalizacion);
	    JPanel estEstadoPanel = new JPanel();
	    estEstadoPanel.add(scrollEstEstado);
	    
	    estadisticasPanel = new JTabbedPane();
	    estadisticasPanel.addTab("Tipo", estTipoPanel);
	    estadisticasPanel.addTab("Localización", estLocalizacionPanel);
	    estadisticasPanel.addTab("Estado", estEstadoPanel);
	    
	    JPanel mainPanel = StyleUtil.createMainPanel();

        // Colocacion de los componentes de la pantalla
	    
	    // Panel de filtros
	    GridBagConstraints gbcLblFiltro = new GridBagConstraints();
	    gbcLblFiltro.gridx = 0;
	    gbcLblFiltro.gridy = 0;
	    gbcLblFiltro.anchor = GridBagConstraints.WEST;
	    gbcLblFiltro.insets = new Insets(0, 0, 10, 10);
	    filtrosPanel.add(lblFiltro, gbcLblFiltro);

	    GridBagConstraints gbcCbFiltro = new GridBagConstraints();
	    gbcCbFiltro.gridx = 1;
	    gbcCbFiltro.gridy = 0;
	    gbcCbFiltro.insets = new Insets(0, 0, 10, 0);
	    filtrosPanel.add(cbFiltro, gbcCbFiltro);
	    
	    // Panel de textfield
	    GridBagConstraints gbcLblParam = new GridBagConstraints();
	    gbcLblParam.gridx = 0;
	    gbcLblParam.gridy = 0;
	    gbcLblParam.anchor = GridBagConstraints.WEST;
	    gbcLblParam.insets = new Insets(0, 0, 10, 10);
	    txtPanel.add(lblParam, gbcLblParam);

	    GridBagConstraints gbcTxtFiltro = new GridBagConstraints();
	    gbcTxtFiltro.gridx = 1;
	    gbcTxtFiltro.gridy = 0;
	    gbcTxtFiltro.insets = new Insets(0, 0, 10, 10);
	    txtPanel.add(txtFiltro, gbcTxtFiltro);
	    
	    GridBagConstraints gbcBtnBuscar1 = new GridBagConstraints();
	    gbcBtnBuscar1.gridx = 2;
	    gbcBtnBuscar1.gridy = 0;
	    gbcBtnBuscar1.anchor = GridBagConstraints.WEST;
	    gbcBtnBuscar1.insets = new Insets(0, 0, 10, 0);
	    txtPanel.add(btnBuscar1, gbcBtnBuscar1);
	    
	    // Panel de date pickers
	    GridBagConstraints gbcLblFechas = new GridBagConstraints();
	    gbcLblFechas.gridx = 0;
	    gbcLblFechas.gridy = 0;
	    gbcLblFechas.anchor = GridBagConstraints.WEST;
	    gbcLblFechas.insets = new Insets(0, 0, 10, 0);
	    fechasPanel.add(lblFechas, gbcLblFechas);
	    
	    GridBagConstraints gbcLblIni = new GridBagConstraints();
	    gbcLblIni.gridx = 0;
	    gbcLblIni.gridy = 1;
	    gbcLblIni.anchor = GridBagConstraints.WEST;
	    gbcLblIni.insets = new Insets(0, 0, 5, 5);
	    fechasPanel.add(lblFechaIni, gbcLblIni);

	    GridBagConstraints gbcDateInicio = new GridBagConstraints();
	    gbcDateInicio.gridx = 0;
	    gbcDateInicio.gridy = 2;
	    gbcDateInicio.anchor = GridBagConstraints.WEST;
	    gbcDateInicio.insets = new Insets(0, 0, 10, 10);
	    fechasPanel.add(datePickerInicio, gbcDateInicio);

	    GridBagConstraints gbcLblFin = new GridBagConstraints();
	    gbcLblFin.gridx = 1;
	    gbcLblFin.gridy = 1;
	    gbcLblFin.anchor = GridBagConstraints.WEST;
	    gbcLblFin.insets = new Insets(0, 0, 5, 0);
	    fechasPanel.add(lblFechaFin, gbcLblFin);

	    GridBagConstraints gbcDateFin = new GridBagConstraints();
	    gbcDateFin.gridx = 1;
	    gbcDateFin.gridy = 2;
	    gbcDateFin.anchor = GridBagConstraints.WEST;
	    gbcDateFin.insets = new Insets(0, 0, 10, 10);
	    fechasPanel.add(datePickerFin, gbcDateFin);
	    
	    GridBagConstraints gbcBtnBuscar2 = new GridBagConstraints();
	    gbcBtnBuscar2.gridx = 2;
	    gbcBtnBuscar2.gridy = 2;
	    gbcBtnBuscar2.anchor = GridBagConstraints.WEST;
	    gbcBtnBuscar2.insets = new Insets(0, 0, 10, 0);
	    fechasPanel.add(btnBuscar2, gbcBtnBuscar2);
	    
	    // Panel de resumen de informe
	    GridBagConstraints gbcLblInforme = new GridBagConstraints();
	    gbcLblInforme.gridx = 0;
	    gbcLblInforme.gridy = 0;
	    gbcLblInforme.anchor = GridBagConstraints.WEST;
	    gbcLblInforme.insets = new Insets(0, 0, 10, 0);
	    informePanel.add(lblInforme, gbcLblInforme);
	    
	    GridBagConstraints gbcLblTotal = new GridBagConstraints();
	    gbcLblTotal.gridx = 0;
	    gbcLblTotal.gridy = 1;
	    gbcLblTotal.anchor = GridBagConstraints.WEST;
	    gbcLblTotal.insets = new Insets(0, 0, 5, 10);
	    informePanel.add(lblTotal, gbcLblTotal);

	    GridBagConstraints gbcLblComp = new GridBagConstraints();
	    gbcLblComp.gridx = 0;
	    gbcLblComp.gridy = 2;
	    gbcLblComp.anchor = GridBagConstraints.WEST;
	    gbcLblComp.insets = new Insets(5, 0, 5, 10);
	    informePanel.add(lblComp, gbcLblComp);

	    GridBagConstraints gbcLblRech = new GridBagConstraints();
	    gbcLblRech.gridx = 0;
	    gbcLblRech.gridy = 3;
	    gbcLblRech.anchor = GridBagConstraints.WEST;
	    gbcLblRech.insets = new Insets(5, 0, 5, 10);
	    informePanel.add(lblRech, gbcLblRech);

	    GridBagConstraints gbcValTotal = new GridBagConstraints();
	    gbcValTotal.gridx = 1;
	    gbcValTotal.gridy = 1;
	    gbcValTotal.anchor = GridBagConstraints.WEST;
	    gbcValTotal.insets = new Insets(5, 0, 0, 0);
	    informePanel.add(txtTotalVal, gbcValTotal);

	    GridBagConstraints gbcValComp = new GridBagConstraints();
	    gbcValComp.gridx = 1;
	    gbcValComp.gridy = 2;
	    gbcValComp.anchor = GridBagConstraints.WEST;
	    gbcValComp.insets = new Insets(5, 0, 0, 0);
	    informePanel.add(txtCompletadasVal, gbcValComp);

	    GridBagConstraints gbcValRech = new GridBagConstraints();
	    gbcValRech.gridx = 1;
	    gbcValRech.gridy = 3;
	    gbcValRech.anchor = GridBagConstraints.WEST;
	    gbcValRech.insets = new Insets(5, 0, 0, 0);
	    informePanel.add(txtRechazadasVal, gbcValRech);
	    
	    // Panel principal
	    GridBagConstraints gbcTitle = new GridBagConstraints();
	    gbcTitle.gridx = 0;
	    gbcTitle.gridy = 0;
	    gbcTitle.gridwidth = 2;
	    gbcTitle.insets = new Insets(0, 0, 30, 0);
	    gbcTitle.fill = GridBagConstraints.HORIZONTAL;
	    mainPanel.add(lblTitle, gbcTitle);

	    GridBagConstraints gbcScrollTabla = new GridBagConstraints();
	    gbcScrollTabla.gridx = 0;
	    gbcScrollTabla.gridy = 1;
	    gbcScrollTabla.gridheight = 6;
	    //gbcScrollTabla.weighty = 0.5;
	    //gbcScrollTabla.fill = GridBagConstraints.VERTICAL;
	    gbcScrollTabla.insets = new Insets(0, 0, 10, 10);
	    mainPanel.add(scrollTabla, gbcScrollTabla);
	    
	    GridBagConstraints gbcPnlFiltro = new GridBagConstraints();
	    gbcPnlFiltro.gridx = 1;
	    gbcPnlFiltro.gridy = 1;
	    gbcPnlFiltro.gridwidth = 1;
	    gbcPnlFiltro.anchor = GridBagConstraints.WEST;
	    gbcPnlFiltro.insets = new Insets(0, 0, 10, 0);
	    mainPanel.add(filtrosPanel, gbcPnlFiltro);
	    
	    GridBagConstraints gbcPnlTxt = new GridBagConstraints();
	    gbcPnlTxt.gridx = 1;
	    gbcPnlTxt.gridy = 2;
	    gbcPnlTxt.gridwidth = 1;
	    gbcPnlTxt.anchor = GridBagConstraints.WEST;
	    gbcPnlTxt.insets = new Insets(0, 0, 10, 0);
	    mainPanel.add(txtPanel, gbcPnlTxt);
	    
	    GridBagConstraints gbcPnlFech = new GridBagConstraints();
	    gbcPnlFech.gridx = 1;
	    gbcPnlFech.gridy = 3;
	    gbcPnlFech.gridwidth = 1;
	    gbcPnlFech.fill = GridBagConstraints.HORIZONTAL;
	    gbcPnlFech.anchor = GridBagConstraints.WEST;
	    gbcPnlFech.insets = new Insets(0, 0, 15, 0);
	    mainPanel.add(fechasPanel, gbcPnlFech);

	    GridBagConstraints gbcPnlInforme = new GridBagConstraints();
	    gbcPnlInforme.gridx = 1;
	    gbcPnlInforme.gridy = 4;
	    gbcPnlInforme.gridwidth = 1;
	    gbcPnlInforme.anchor = GridBagConstraints.WEST;
	    gbcPnlInforme.insets = new Insets(0, 0, 15, 0);
	    mainPanel.add(informePanel, gbcPnlInforme);
	    
	    GridBagConstraints gbcPnlEst = new GridBagConstraints();
	    gbcPnlEst.gridx = 1;
	    gbcPnlEst.gridy = 5;
	    gbcPnlEst.gridwidth = 3;
	    gbcPnlEst.fill = GridBagConstraints.BOTH;
	    gbcPnlEst.insets = new Insets(0, 0, 0, 0);
	    mainPanel.add(estadisticasPanel, gbcPnlEst);

	    JPanel filler = new JPanel();
	    filler.setBackground(Color.WHITE);
	    filler.setPreferredSize(new Dimension(620, 0));
	    GridBagConstraints gbcFiller = new GridBagConstraints();
	    gbcFiller.gridx = 1;
	    gbcFiller.gridy = 6;
	    gbcFiller.gridwidth = 3;
	    gbcFiller.fill = GridBagConstraints.HORIZONTAL;
	    gbcFiller.insets = new Insets(0, 0, 0, 0);
	    mainPanel.add(filler, gbcFiller);

	    
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
	
	public JButton getBtnBuscar1() { return btnBuscar1;	}
	public JButton getBtnBuscar2() { return btnBuscar2;	}

	public JPanel getTxtPanel() { return this.txtPanel; }
	public JPanel getFechasPanel() { return this.fechasPanel; }
	public JPanel getInformePanel() { return this.informePanel; }
	
	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public void setTablaIncidencias(JTable tablaIncidencias) { this.tablaIncidencias = tablaIncidencias; }
	
	public JTable getEstadisticasTipo() { return tablaEstTipo; }
	public JTable getEstadisticasLocalizacion() { return tablaEstLocalizacion; }
	public JTable getEstadisticasEstado() { return tablaEstEstado; }
	
	public JScrollPane getScrollTabla() { return scrollTabla; }
	public void setScrollTabla(JScrollPane scrollTabla) { this.scrollTabla = scrollTabla; }
	
	public JScrollPane getScrollEstTipo() { return scrollEstTipo; }
	public JScrollPane getScrollEstLocalizacion() { return scrollEstLocalizacion; }
	public JScrollPane getScrollEstEstado() { return scrollEstEstado; }

	public JDatePickerImpl getDatePickerInicio() { return datePickerInicio; }
	public void setDatePickerInicio(JDatePickerImpl datePickerInicio) {	this.datePickerInicio = datePickerInicio; }

	public JDatePickerImpl getDatePickerFin() {	return datePickerFin; }
	public void setDatePickerFin(JDatePickerImpl datePickerFin) { this.datePickerFin = datePickerFin; }

	public JLabel getTxtTotalVal() { return txtTotalVal; }
	public JLabel getTxtCompletadasVal() { return txtCompletadasVal; }
	public JLabel getTxtRechazadasVal() { return txtRechazadasVal; }
	
    public JComboBox<String> getListaFiltros() { return this.cbFiltro; }
    
    public String getTxtFiltro() {return this.txtFiltro.getText(); }
    public void setTxtFiltro(String txtFiltro) { this.txtFiltro.setText(txtFiltro); }
    
    public JTabbedPane getEstadisticasPanel() { return this.estadisticasPanel; }
}
