package incidencias.ui;

import org.jdatepicker.impl.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JScrollPane scrollEstadisticas;
    private JComboBox<String> cbFiltro;
    private JPanel estadisticasPanel;
	private JTable tablaIncidencias;
	private JTable tablaEstadisticas;
	private JButton btnClose, btnBuscar, btnEstadisticas;
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
	    scrollTabla.setPreferredSize(new Dimension(600, 400));

	    final JLabel lblFechas = new JLabel("Introduce un periodo de tiempo:");
	    StyleUtil.styleLabel(lblFechas, 14, false);

	    final JLabel lblFechaIni = new JLabel("Fecha de inicio:");
	    StyleUtil.styleLabel(lblFechaIni, 14, false);
	    datePickerInicio = crearDatePicker();

	    final JLabel lblFechaFin = new JLabel("Fecha de fin:");
	    StyleUtil.styleLabel(lblFechaFin, 14, false);
	    datePickerFin = crearDatePicker();
	    
	    btnBuscar = new JButton("Buscar");
	    styleCustomButton(btnBuscar);

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

	    final JLabel lblEst = new JLabel("Mostrar estadísticas:");
	    StyleUtil.styleLabel(lblEst, 14, false);

	    final JLabel lblFiltro = new JLabel("Selecciona un parámetro:");
	    StyleUtil.styleLabel(lblFiltro, 14, true);

	    cbFiltro = new JComboBox<>(new String[]{"Tipo", "Localización", "Estado"});
	    StyleUtil.styleComboBox(cbFiltro, 100, 35);

	    btnEstadisticas = new JButton("Mostrar");
	    styleCustomButton(btnEstadisticas);
	    
	    tablaEstadisticas = new JTable();
	    StyleUtil.styleTable(tablaEstadisticas);
	    scrollEstadisticas = new JScrollPane(tablaEstadisticas);
	    scrollEstadisticas.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
	    scrollEstadisticas.setPreferredSize(new Dimension(600, 200));

	    btnClose = new JButton();
	    StyleUtil.styleCloseButton(btnClose);

        // Creacion de las secciones de la pantalla
	    
	    JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
	    
	    JPanel informePanel = StyleUtil.createAbstractPanel();
	    informePanel.setLayout(new GridBagLayout()); 

	    estadisticasPanel = StyleUtil.createAbstractPanel();
	    estadisticasPanel.setLayout(new GridBagLayout());
	    
	    JPanel mainPanel = StyleUtil.createMainPanel();

        // Colocacion de los componentes de la pantalla
	    
	    // Panel de resumen de informe
	    GridBagConstraints gbcLblTotal = new GridBagConstraints();
	    gbcLblTotal.gridx = 0;
	    gbcLblTotal.gridy = 0;
	    gbcLblTotal.anchor = GridBagConstraints.WEST;
	    gbcLblTotal.insets = new Insets(5, 0, 5, 10);
	    informePanel.add(lblTotal, gbcLblTotal);

	    GridBagConstraints gbcLblComp = new GridBagConstraints();
	    gbcLblComp.gridx = 0;
	    gbcLblComp.gridy = 1;
	    gbcLblComp.anchor = GridBagConstraints.WEST;
	    gbcLblComp.insets = new Insets(5, 0, 5, 10);
	    informePanel.add(lblComp, gbcLblComp);

	    GridBagConstraints gbcLblRech = new GridBagConstraints();
	    gbcLblRech.gridx = 0;
	    gbcLblRech.gridy = 2;
	    gbcLblRech.anchor = GridBagConstraints.WEST;
	    gbcLblRech.insets = new Insets(5, 0, 5, 10);
	    informePanel.add(lblRech, gbcLblRech);

	    GridBagConstraints gbcValTotal = new GridBagConstraints();
	    gbcValTotal.gridx = 1;
	    gbcValTotal.gridy = 0;
	    gbcValTotal.anchor = GridBagConstraints.WEST;
	    gbcValTotal.insets = new Insets(5, 0, 0, 0);
	    informePanel.add(txtTotalVal, gbcValTotal);

	    GridBagConstraints gbcValComp = new GridBagConstraints();
	    gbcValComp.gridx = 1;
	    gbcValComp.gridy = 1;
	    gbcValComp.anchor = GridBagConstraints.WEST;
	    gbcValComp.insets = new Insets(5, 0, 0, 0);
	    informePanel.add(txtCompletadasVal, gbcValComp);

	    GridBagConstraints gbcValRech = new GridBagConstraints();
	    gbcValRech.gridx = 1;
	    gbcValRech.gridy = 2;
	    gbcValRech.anchor = GridBagConstraints.WEST;
	    gbcValRech.insets = new Insets(5, 0, 0, 0);
	    informePanel.add(txtRechazadasVal, gbcValRech);
	    
	    // Panel de estadisticas
	    GridBagConstraints gbcLblEst = new GridBagConstraints();
	    gbcLblEst.gridx = 0; 
	    gbcLblEst.gridy = 0;
	    gbcLblEst.gridwidth = 2; 
	    gbcLblEst.anchor = GridBagConstraints.WEST;
	    gbcLblEst.insets = new Insets(0, 0, 7, 0);
	    estadisticasPanel.add(lblEst, gbcLblEst);

	    GridBagConstraints gbcLblFiltro = new GridBagConstraints();
	    gbcLblFiltro.gridx = 0;
	    gbcLblFiltro.gridy = 1;
	    gbcLblFiltro.anchor = GridBagConstraints.WEST;
	    gbcLblFiltro.insets = new Insets(0, 0, 10, 10);
	    estadisticasPanel.add(lblFiltro, gbcLblFiltro);

	    GridBagConstraints gbcCbFiltro = new GridBagConstraints();
	    gbcCbFiltro.gridx = 1;
	    gbcCbFiltro.gridy = 1;
	    gbcCbFiltro.fill = GridBagConstraints.HORIZONTAL;
	    gbcCbFiltro.insets = new Insets(0, 0, 10, 10);
	    estadisticasPanel.add(cbFiltro, gbcCbFiltro);
	    
	    GridBagConstraints gbcBtnEst = new GridBagConstraints();
	    gbcBtnEst.gridx = 3;
	    gbcBtnEst.gridy = 1;
	    gbcBtnEst.fill = GridBagConstraints.HORIZONTAL;
	    gbcBtnEst.insets = new Insets(0, 0, 10, 0);
	    estadisticasPanel.add(btnEstadisticas, gbcBtnEst);
	    
	    // Panel principal
	    GridBagConstraints gbcTitle = new GridBagConstraints();
	    gbcTitle.gridx = 0;
	    gbcTitle.gridy = 0;
	    gbcTitle.gridwidth = 3;
	    gbcTitle.insets = new Insets(0, 0, 30, 0);
	    gbcTitle.fill = GridBagConstraints.HORIZONTAL;
	    mainPanel.add(lblTitle, gbcTitle);

	    GridBagConstraints gbcScrollTabla = new GridBagConstraints();
	    gbcScrollTabla.gridx = 0;
	    gbcScrollTabla.gridy = 1;
	    gbcScrollTabla.gridheight = 7;
	    gbcScrollTabla.weightx = 1.0;
	    gbcScrollTabla.weighty = 1.0;
	    gbcScrollTabla.fill = GridBagConstraints.BOTH;
	    gbcScrollTabla.insets = new Insets(0, 0, 10, 10);
	    mainPanel.add(scrollTabla, gbcScrollTabla);

	    GridBagConstraints gbcLblFechas = new GridBagConstraints();
	    gbcLblFechas.gridx = 1;
	    gbcLblFechas.gridy = 1;
	    gbcLblFechas.gridwidth = 2;
	    gbcLblFechas.anchor = GridBagConstraints.WEST;
	    gbcLblFechas.insets = new Insets(0, 0, 10, 0);
	    mainPanel.add(lblFechas, gbcLblFechas);

	    GridBagConstraints gbcLblIni = new GridBagConstraints();
	    gbcLblIni.gridx = 1;
	    gbcLblIni.gridy = 2;
	    gbcLblIni.anchor = GridBagConstraints.WEST;
	    gbcLblIni.insets = new Insets(0, 0, 5, 5);
	    mainPanel.add(lblFechaIni, gbcLblIni);

	    GridBagConstraints gbcDateInicio = new GridBagConstraints();
	    gbcDateInicio.gridx = 1;
	    gbcDateInicio.gridy = 3;
	    gbcDateInicio.anchor = GridBagConstraints.WEST;
	    gbcDateInicio.insets = new Insets(0, 0, 10, 10);
	    mainPanel.add(datePickerInicio, gbcDateInicio);

	    GridBagConstraints gbcLblFin = new GridBagConstraints();
	    gbcLblFin.gridx = 2;
	    gbcLblFin.gridy = 2;
	    gbcLblFin.anchor = GridBagConstraints.WEST;
	    gbcLblFin.insets = new Insets(0, 0, 5, 0);
	    mainPanel.add(lblFechaFin, gbcLblFin);

	    GridBagConstraints gbcDateFin = new GridBagConstraints();
	    gbcDateFin.gridx = 2;
	    gbcDateFin.gridy = 3;
	    gbcDateFin.anchor = GridBagConstraints.WEST;
	    gbcDateFin.insets = new Insets(0, 0, 10, 10);
	    mainPanel.add(datePickerFin, gbcDateFin);
	    
	    GridBagConstraints gbcBtnBuscar = new GridBagConstraints();
	    gbcBtnBuscar.gridx = 3;
	    gbcBtnBuscar.gridy = 3;
	    gbcBtnBuscar.anchor = GridBagConstraints.WEST;
	    gbcBtnBuscar.insets = new Insets(0, 0, 10, 0);
	    mainPanel.add(btnBuscar, gbcBtnBuscar);

	    GridBagConstraints gbcLblInforme = new GridBagConstraints();
	    gbcLblInforme.gridx = 1;
	    gbcLblInforme.gridy = 4;
	    gbcLblInforme.gridwidth = 2;
	    gbcLblInforme.anchor = GridBagConstraints.WEST;
	    gbcLblInforme.insets = new Insets(10, 0, 7, 0);
	    mainPanel.add(lblInforme, gbcLblInforme);

	    GridBagConstraints gbcPnlInforme = new GridBagConstraints();
	    gbcPnlInforme.gridx = 1;
	    gbcPnlInforme.gridy = 5;
	    gbcPnlInforme.gridwidth = 3;
	    gbcPnlInforme.fill = GridBagConstraints.HORIZONTAL;
	    gbcPnlInforme.anchor = GridBagConstraints.WEST;
	    gbcPnlInforme.insets = new Insets(0, 0, 15, 0);
	    mainPanel.add(informePanel, gbcPnlInforme);
	    
	    GridBagConstraints gbcPnlEst = new GridBagConstraints();
	    gbcPnlEst.gridx = 1;
	    gbcPnlEst.gridy = 6;
	    gbcPnlEst.gridwidth = 3;
	    gbcPnlEst.fill = GridBagConstraints.HORIZONTAL;
	    gbcPnlEst.anchor = GridBagConstraints.WEST;
	    gbcPnlEst.insets = new Insets(10, 0, 10, 0);
	    mainPanel.add(estadisticasPanel, gbcPnlEst);
	    
	    GridBagConstraints gbcScrollEst = new GridBagConstraints();
	    gbcScrollEst.gridx = 1;
	    gbcScrollEst.gridy = 7;
	    gbcScrollEst.gridwidth = 3;
	    gbcScrollEst.fill = GridBagConstraints.BOTH;
	    gbcScrollEst.insets = new Insets(0, 0, 10, 0);
	    mainPanel.add(scrollEstadisticas, gbcScrollEst);

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
	
	private void styleCustomButton(JButton btn) {
		btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(79, 70, 229));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(new Color(79, 70, 229).darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(new Color(79, 70, 229)); }
        });
        btn.setPreferredSize(new Dimension(100, 26));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        btn.setMinimumSize(new Dimension(100, 26));
	}

	public JButton getBtnClose() { return btnClose;	}
	public void setBtnClose(JButton btnClose) {	this.btnClose = btnClose; }
	
	public JButton getBtnBuscar() { return btnBuscar;	}
	public void setBtnBuscar(JButton btnBuscar) {	this.btnBuscar = btnBuscar; }
	
	public JButton getBtnEstadisticas() { return btnEstadisticas;	}
	public void setBtnEstadisticas(JButton btnEstadisticas) {	this.btnBuscar = btnEstadisticas; }

	public JTable getTablaIncidencias() { return tablaIncidencias; }
	public void setTablaIncidencias(JTable tablaIncidencias) { this.tablaIncidencias = tablaIncidencias; }
	
	public JTable getTablaEstadisticas() { return tablaEstadisticas; }
	public void setTablaEstadisticas(JTable tablaEstadisticas) { this.tablaEstadisticas = tablaEstadisticas; }
	
	public JScrollPane getScrollTabla() { return scrollTabla; }
	public void setScrollTabla(JScrollPane scrollTabla) { this.scrollTabla = scrollTabla; }
	
	public JScrollPane getScrollEstadisticas() { return scrollEstadisticas; }
	public void setScrollEstadisticas(JScrollPane scrollEstadisticas) { this.scrollEstadisticas = scrollEstadisticas; }

	public JDatePickerImpl getDatePickerInicio() { return datePickerInicio; }
	public void setDatePickerInicio(JDatePickerImpl datePickerInicio) {	this.datePickerInicio = datePickerInicio; }

	public JDatePickerImpl getDatePickerFin() {	return datePickerFin; }
	public void setDatePickerFin(JDatePickerImpl datePickerFin) { this.datePickerFin = datePickerFin; }

	public JLabel getTxtTotalVal() { return txtTotalVal; }
	public JLabel getTxtCompletadasVal() { return txtCompletadasVal; }
	public JLabel getTxtRechazadasVal() { return txtRechazadasVal; }
	
    public JComboBox<String> getListaFiltros() { return this.cbFiltro; }
    
    public JPanel getEstadisticasPanel() { return this.estadisticasPanel; }
}
