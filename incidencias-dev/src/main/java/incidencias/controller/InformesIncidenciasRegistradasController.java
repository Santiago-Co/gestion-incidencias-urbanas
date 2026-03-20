package incidencias.controller;

import java.text.SimpleDateFormat; 
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
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
		view.getBtnBuscar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> listarIncidenciasPorFecha()));
		view.getBtnClose().addActionListener(e -> volverDashboard());
	}
	
	private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }

	private void initView() {
		view.setVisible(true);
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
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFecha(sdf.format(fechaIni), sdf.format(fechaFin));
		
		actualizarResumenInforme(incidencias);
		
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollTabla());
	}
	
	private void actualizarResumenInforme(List<IncidenciaDTO> lista) {
		int completadas = 0;
	    int rechazadas = 0;
	    for (IncidenciaDTO i : lista) {
	        if ("Resuelta".equalsIgnoreCase(i.getEstado()) || "Cerrada".equalsIgnoreCase(i.getEstado())) {
	            completadas++;
	        } else if ("Rechazada".equalsIgnoreCase(i.getEstado())) {
	            rechazadas++;
	        }
	    }
	    
	    view.getTxtTotalVal().setText(String.valueOf(lista.size()));
	    view.getTxtCompletadasVal().setText(String.valueOf(completadas));
	    view.getTxtRechazadasVal().setText(String.valueOf(rechazadas));
	}
}
