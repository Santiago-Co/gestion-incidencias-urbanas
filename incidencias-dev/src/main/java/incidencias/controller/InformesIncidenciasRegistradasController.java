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

	@SuppressWarnings("deprecation")
	private void initController() {
		view.getListaFiltros().addActionListener(e -> {
			SwingUtil.exceptionWrapper(() -> seleccionarFiltro());
			if(view.getInformePanel().isShowing()) view.getInformePanel().hide();
			if(view.getEstadisticasPanel().isShowing()) view.getEstadisticasPanel().hide();
			view.getTablaIncidencias().setModel(new DefaultTableModel());
			view.setTxtFiltro("");
			actualizarPantalla();
		});
		view.getBtnBuscar1().addActionListener(e -> {
			SwingUtil.exceptionWrapper(() -> listarIncidenciasPorParametro());
			if(!incidencias.isEmpty()) {
				SwingUtil.exceptionWrapper(() -> actualizarResumenInforme());
				view.getInformePanel().show();
				SwingUtil.exceptionWrapper(() -> mostrarEstadisticas());
				view.getEstadisticasPanel().show();
				actualizarPantalla();
			} else {
				view.getInformePanel().hide();
				view.getEstadisticasPanel().hide();
			}
		});
		view.getBtnBuscar2().addActionListener(e -> {
			SwingUtil.exceptionWrapper(() -> listarIncidenciasPorFecha());
			if(!incidencias.isEmpty()) {
				SwingUtil.exceptionWrapper(() -> actualizarResumenInforme());
				view.getInformePanel().show();
				SwingUtil.exceptionWrapper(() -> mostrarEstadisticas());
				view.getEstadisticasPanel().show();
				actualizarPantalla();
			}
			else {
				view.getInformePanel().hide();
				view.getEstadisticasPanel().hide();
			}
		});
		SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		view.setVisible(true);
		view.getTxtPanel().hide();
		view.getFechasPanel().hide();
		view.getInformePanel().hide();
		view.getEstadisticasPanel().hide();
		actualizarPantalla();
	}
	
	@SuppressWarnings("deprecation")
	public void seleccionarFiltro() {
		String filtro = (String) view.getListaFiltros().getSelectedItem();
		if(filtro.equals("Fecha")) {
			if(view.getTxtPanel().isShowing()) view.getTxtPanel().hide();
			view.getFechasPanel().show();
		}
		else {
			if(view.getFechasPanel().isShowing()) view.getFechasPanel().hide();
			view.getTxtPanel().show();
		}
	}
	
	public void listarIncidenciasPorParametro() {
		String parametro = view.getTxtFiltro();
		if(parametro.isBlank()) {
			SwingUtil.showErrorMessage(view, "Por favor, introduzca un valor para el parámetro seleccionado.");
	        return;
		}
		
		String filtro = (String) view.getListaFiltros().getSelectedItem();
		incidencias = model.getIncidenciasPorFiltro(filtro, parametro);
		
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollTabla());
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
		incidencias = model.getIncidenciasPorFiltro("Fecha", sdf.format(fechaIni), sdf.format(fechaFin));
		
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
		String[] filtros = { "Tipo", "Localización", "Estado" };
		
		JTable tablaEstTipos = view.getEstadisticasTipo();
		String[] titulosTipo = {filtros[0], "Porcentaje de aparición"};
		DefaultTableModel modelTipo = new DefaultTableModel(titulosTipo, 0);
	    
		JTable tablaEstLocalizacion = view.getEstadisticasLocalizacion();
		String[] titulosLoc = {filtros[1], "Porcentaje de aparición"};
		DefaultTableModel modelLoc = new DefaultTableModel(titulosLoc, 0);
		
		JTable tablaEstEstado = view.getEstadisticasEstado();
		String[] titulosEstado = {filtros[2], "Porcentaje de aparición"};
		DefaultTableModel modelEstado = new DefaultTableModel(titulosEstado, 0);
		
		DefaultTableModel[] models = { modelTipo, modelLoc, modelEstado };
		
		List<String> tipos = new ArrayList<>();
		List<Integer> cuantos = new ArrayList<>();
		
		for(int i=0; i<filtros.length; i++) {
			contarCategorias(tipos, cuantos, filtros[i]);
			porcentajes(tipos, cuantos, models[i]);
			tipos.clear();
			cuantos.clear();
		}
		
		tablaEstTipos.setModel(modelTipo);
		DefaultTableCellRenderer rendererTipo = new DefaultTableCellRenderer();
		rendererTipo.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tablaEstTipos, rendererTipo, titulosTipo);
		SwingUtil.autoAdjustColumns(tablaEstTipos, view.getScrollEstTipo());
		
		tablaEstLocalizacion.setModel(modelLoc);
		DefaultTableCellRenderer rendererLoc = new DefaultTableCellRenderer();
		rendererLoc.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tablaEstLocalizacion, rendererLoc, titulosLoc);
		SwingUtil.autoAdjustColumns(tablaEstLocalizacion, view.getScrollEstLocalizacion());
		
		tablaEstEstado.setModel(modelEstado);
		DefaultTableCellRenderer rendererEstado = new DefaultTableCellRenderer();
		rendererEstado.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tablaEstEstado, rendererEstado, titulosEstado);
		SwingUtil.autoAdjustColumns(tablaEstEstado, view.getScrollEstEstado());
	}
	
	private void contarCategorias(List<String> tipos, List<Integer> cuantos, String filtro) {
		JTable tablaIncidencias = view.getTablaIncidencias();
		DefaultTableModel modelIncidencias = (DefaultTableModel)tablaIncidencias.getModel();
		
		for (int i=0; i < modelIncidencias.getRowCount(); i++) {
			int col = tablaIncidencias.getColumnModel().getColumnIndex(filtro);
			String tipo = modelIncidencias.getValueAt(i, col).toString();
			if(!tipos.contains(tipo)) {
				tipos.add(tipo);
				cuantos.add(1);
			} else {
				int anterior = cuantos.get(tipos.indexOf(tipo));
				cuantos.set(tipos.indexOf(tipo), anterior+1); 
			}
		}
	}
	
	private void porcentajes(List<String> tipos, List<Integer> cuantos, DefaultTableModel modelEstadisticas) {
		int total = Integer.parseInt(view.getTxtTotalVal().getText());
		
		for(int i=0; i < tipos.size(); i++) {
			double porcentaje = cuantos.get(i).doubleValue()/total*100;
			modelEstadisticas.addRow(new Object[]{tipos.get(i), String.valueOf(porcentaje)});
		}
	}
	
	private void actualizarPantalla() {
		view.revalidate();
		view.repaint();
	}
}
