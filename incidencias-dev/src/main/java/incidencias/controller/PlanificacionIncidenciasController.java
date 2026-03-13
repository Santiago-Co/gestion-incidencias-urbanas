package incidencias.controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import exceptions.ApplicationException;
import incidencias.model.PlanificacionIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.PlanificacionIncidenciasView;
import util.swing.SwingUtil;

public class PlanificacionIncidenciasController {

	private PlanificacionIncidenciasView view;
    private PlanificacionIncidenciasModel model;
    private DashboardView viewDsb;
    private int idUsuario;
    
    private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Localización", "Fecha/Hora"};
	private static final String[] TITULOS_TABLA_POJOS = new String[]{"id", "tipo", "descripcion", "localizacion", "fechaCambio"};
	private static final Object COLUMN_ID = "ID";
	
	public PlanificacionIncidenciasController(PlanificacionIncidenciasView view, PlanificacionIncidenciasModel model, int id, DashboardView viewDsb) {
		this.view = view;
		this.model = model;
		this.viewDsb = viewDsb;
		this.idUsuario = id;
		
		initView();
		initController();
	}

	private void initView() {
		SwingUtil.exceptionWrapper(() -> listarIncidenciasTecnico());
		view.setVisible(true);
	}

	public void initController() {
		view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarPlanificacion()));
    	view.getBtnClose().addActionListener(e -> volverDashboard());
	}
	
	private void listarIncidenciasTecnico() {
    	List<IncidenciaDTO> incidencias = model.getIncidenciasTecnico(idUsuario);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollTabla());
    }
	
	private void guardarPlanificacion() {
	    JTable tabla = view.getTablaIncidencias();
	    int fila = tabla.getSelectedRow();

	    if (fila == -1) {
	        throw new ApplicationException("Seleccione una incidencia de la tabla");
	    }

	    int columnIndex = tabla.getColumnModel().getColumnIndex(COLUMN_ID);
        int idIncidencia = (int) tabla.getValueAt(fila, columnIndex);
        String tipo = (String) tabla.getValueAt(fila, tabla.getColumnModel().getColumnIndex("Tipo"));

	    String horasTxt = view.getTextFieldHorasPrevistas().getText().trim();
        Integer horas;
        if (horasTxt.isEmpty()) {
	        throw new ApplicationException("Las horas de trabajo previstas son obligatorias");
        }
        try {
            horas = Integer.parseInt(horasTxt);
            if (horas <= 0) {
    	        throw new ApplicationException("Introduce un número de horas válido (>0)");
            }
        } catch (NumberFormatException ex) {
	        throw new ApplicationException("Las horas de trabajo previstas deben ser un número entero");
        }

        String descripcionTrabajo = view.getTextAreaDescripcion().getText().trim();
        if (descripcionTrabajo.isEmpty()) {
	        throw new ApplicationException("La descripción de los trabajos a realizar es obligatoria");
        }

        String mensajeConfirmacion = String.format("¿Desea marcar como planificada la incidencia %d?", idIncidencia);
        if(SwingUtil.showQuestionMessage(view, mensajeConfirmacion)) {
        	model.planificarIncidencia(idIncidencia, idUsuario, horas, descripcionTrabajo);
        	String fechaPlanificacion = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
        	
        	int longitudMaxima = 30;
            String resumenConfirmacion = descripcionTrabajo;
            if (descripcionTrabajo.length() > longitudMaxima) {
                resumenConfirmacion = descripcionTrabajo.substring(0, longitudMaxima) + "...";
            }
        	
    		String mensajeResumen = "Incidencia planificada correctamente. Estado cambiado a 'En proceso'\n\n"
    		+ "ID: " + idIncidencia + "\n"
    		+ "Tipo: " + tipo + "\n"
    		+ "Fecha Planificación: " + fechaPlanificacion + "\n"
    		+ "Horas previstas: " + horas + "\n"
    		+ "Descripción Trabajos: " + resumenConfirmacion;
            SwingUtil.showSuccessMessage(view, mensajeResumen);
            listarIncidenciasTecnico();
    	}
	}
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}
