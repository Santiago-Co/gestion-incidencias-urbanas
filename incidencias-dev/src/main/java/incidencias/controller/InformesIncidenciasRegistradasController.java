package incidencias.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import incidencias.model.InformesIncidenciasRegistradasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.InformesIncidenciasRegistradasView;
import util.swing.SwingUtil;

public class InformesIncidenciasRegistradasController {

	private InformesIncidenciasRegistradasView view;
	private InformesIncidenciasRegistradasModel model;
	private DashboardView viewDsb;
	private List<IncidenciaDTO> incidencias;
	
	private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Localización", "Fecha Creacion", "Estado"};
	private static final String[] TITULOS_TABLA_POJOS = new String[]{"id", "tipo", "descripcion", "localizacion", "fechaCambio", "estado"};
	
	public InformesIncidenciasRegistradasController(InformesIncidenciasRegistradasView view, InformesIncidenciasRegistradasModel model, DashboardView viewDsb) {
		this.view = view;
		this.model = model;
		this.viewDsb = viewDsb;
		
		initController();
        initView();
	}

	private void initController() {
		view.getBtnBuscar().addActionListener(e -> {
			SwingUtil.exceptionWrapper(() -> listarIncidenciasPorFecha());
			if(!incidencias.isEmpty()) {
				SwingUtil.exceptionWrapper(() -> actualizarResumenInforme());
				view.getEstadisticasPanel().setVisible(true);
				view.getTablaEstadisticas().setVisible(true);
			}
		});
		view.getBtnEstadisticas().addActionListener(e -> SwingUtil.exceptionWrapper(() -> mostrarEstadisticas()));
		SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
	}

	private void initView() {
		view.setVisible(true);
		view.getEstadisticasPanel().setVisible(false);
		view.getTablaEstadisticas().setVisible(false);
	}
	
	public void listarIncidenciasPorFecha() {
		Object valIni = view.getDatePickerInicio().getModel().getValue();
		Object valFin = view.getDatePickerFin().getModel().getValue();
		
		if (valIni == null || valFin == null) {
            SwingUtil.showErrorMessage(view, "Por favor, selecciona ambas fechas.");
            return;
        }
		
	    java.util.Date fechaIni = (java.util.Date) valIni;
	    java.util.Date fechaFin = (java.util.Date) valFin;
	    
	    if (fechaIni.after(fechaFin)) {
	        SwingUtil.showErrorMessage(view, "La fecha de inicio no puede ser posterior a la de fin.");
	        return;
	    }
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		incidencias = model.getIncidenciasPorFecha(sdf.format(fechaIni), sdf.format(fechaFin));
		
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollTabla());
	}
	
	private void actualizarResumenInforme() {
		int completadas = 0;
	    int rechazadas = 0;
	    for (IncidenciaDTO i : incidencias) {
	        if ("Resuelta".equalsIgnoreCase(i.getEstado()) || "Cerrada".equalsIgnoreCase(i.getEstado())) {
	            completadas++;
	        } else if ("Rechazada".equalsIgnoreCase(i.getEstado())) {
	            rechazadas++;
	        }
	    }
	    
	    view.getTxtTotalVal().setText(String.valueOf(incidencias.size()));
	    view.getTxtCompletadasVal().setText(String.valueOf(completadas));
	    view.getTxtRechazadasVal().setText(String.valueOf(rechazadas));
	}
	
	private void mostrarEstadisticas() {
		JTable tablaIncidencias = view.getTablaIncidencias();
		DefaultTableModel modelIncidencias = (DefaultTableModel)tablaIncidencias.getModel();
		
		String param = (String) view.getListaFiltros().getSelectedItem();
		
		JTable tablaEstadisticas = view.getTablaEstadisticas();
		String[] titulos = {param, "Porcentaje de aparición"};
		DefaultTableModel modelEstadisticas = new DefaultTableModel(titulos, 0);
	    
		List<String> tipos = new ArrayList<>();
		List<Integer> cuantos = new ArrayList<>();
		for (int i=0; i < modelIncidencias.getRowCount(); i++) {
			int col = tablaIncidencias.getColumnModel().getColumnIndex(param);
			String tipo = modelIncidencias.getValueAt(i, col).toString();
			if(!tipos.contains(tipo)) {
				tipos.add(tipo);
				cuantos.add(1);
			} else {
				int anterior = cuantos.get(tipos.indexOf(tipo));
				cuantos.set(tipos.indexOf(tipo), anterior+1); 
			}
		}
		
		int total = Integer.parseInt(view.getTxtTotalVal().getText());
		
		for(int i=0; i < tipos.size(); i++) {
			double porcentaje = cuantos.get(i).doubleValue()/total*100;
			modelEstadisticas.addRow(new Object[]{tipos.get(i), String.valueOf(porcentaje)});
		}
		
		tablaEstadisticas.setModel(modelEstadisticas);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tablaEstadisticas, centerRenderer, titulos);
		SwingUtil.autoAdjustColumns(tablaEstadisticas, view.getScrollEstadisticas());
	}
}
