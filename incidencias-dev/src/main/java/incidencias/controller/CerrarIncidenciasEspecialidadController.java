package incidencias.controller;

import java.util.List;

import javax.swing.JTable;

import exceptions.ApplicationException;
import incidencias.model.CerrarIncidenciasEspecialidadModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.CerrarIncidenciasEspecialidadView;
import incidencias.ui.DashboardView;
import util.swing.SwingUtil;

public class CerrarIncidenciasEspecialidadController {

	CerrarIncidenciasEspecialidadView view;
	CerrarIncidenciasEspecialidadModel model;
	DashboardView viewDsb;
	int idTecnico;
	
	private static final String[] TITULOS_TABLA = {"ID", "Tipo", "Localización", "Horas", "Coste (€)", "Trabajo Realizado", "Fecha Res."};

		private static final String[] POJO_FIELDS = {"id", "tipo", "localizacion", "horasReales", "costeTotal", "descripcionFinalTrabajo", "fechaCambio"};
	
	public CerrarIncidenciasEspecialidadController(CerrarIncidenciasEspecialidadView view, CerrarIncidenciasEspecialidadModel model, DashboardView viewDsb, int idTecnico) {
		this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;
        this.idTecnico = idTecnico;

        initView();
        initController();
	}
	
	private void initView() {
		String especialidad = model.getNombreEspecialidad(idTecnico);
	    view.getLblTitle().setText("Incidencias resueltas en la especialidad: " + especialidad);
	    
	    listarIncidencias();
	    view.setVisible(true);
    }

    private void listarIncidencias() {
    	List<IncidenciaDTO> lista = model.getIncidenciasResueltasEspecialidad(idTecnico);
        view.getTablaIncidencias().setModel(SwingUtil.getTableModelFromPojos(lista, POJO_FIELDS));
        
        JTable tabla = view.getTablaIncidencias();
        SwingUtil.setTableStyle(tabla, null, TITULOS_TABLA);
        SwingUtil.setDescriptionColumnStyle(tabla, 5, 230);
        SwingUtil.autoAdjustColumns(tabla, view.getScrollTabla());
	}

	private void initController() {
        view.getBtnCerrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> confirmarYCerrar()));
        SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
    }

	private void confirmarYCerrar() {
		JTable tabla = view.getTablaIncidencias();
		int[] filas = tabla.getSelectedRows();
		if (filas.length == 0) {
	        throw new ApplicationException("Debe seleccionar al menos una incidencia de la lista.");
	    }
		
		String mensaje;
	    if (filas.length == 1) {
	        int idIncidencia = (int) tabla.getValueAt(filas[0], 0);
	        mensaje = "¿Está seguro de que desea cerrar la incidencia " + idIncidencia + "?";
	    } else {
	        mensaje = String.format("¿Está seguro de que desea cerrar las %d incidencias seleccionadas?", filas.length);
	    }

	    if (SwingUtil.showQuestionMessage(view, mensaje)) {
	        SwingUtil.exceptionWrapper(() -> {
	            int exitos = 0;
	            for (int fila : filas) {
	                int id = (int) tabla.getValueAt(fila, 0);
	                
	                model.cerrarIncidencia(id, idTecnico, "Cierre masivo verificado por responsable.");
	                exitos++;
	            }
	            
	            SwingUtil.showSuccessMessage(view, exitos + " incidencia(s) cerrada(s) con éxito.");
	            listarIncidencias();
	        });
	    }
	}
    
}
