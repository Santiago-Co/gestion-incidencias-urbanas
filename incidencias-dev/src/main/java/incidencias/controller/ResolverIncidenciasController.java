package incidencias.controller;

import java.util.List; 

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import exceptions.ApplicationException;
import incidencias.model.ResolverIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.ResolverIncidenciasView;
import util.swing.SwingUtil;

public class ResolverIncidenciasController {

	private ResolverIncidenciasView view;
	private ResolverIncidenciasModel model;
    private DashboardView viewDsb;
	private int usuarioId;
	
    private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Localización", "Fecha/Hora"};
	private static final String[] TITULOS_TABLA_POJOS = new String[]{"id", "tipo", "descripcion", "localizacion", "fechaCambio"};
	private static final Object COLUMN_ID = "ID";
    
	private static final String MENSAJE_EXITO_ELIMINAR = "Incidencia eliminada correctamente";
	private static final String MENSAJE_FALLO_ELIMINAR = "Debe seleccionar una incidencia para eliminarla"; 
	private static final String MENSAJE_CONFIRMAR_ELIMINAR = "¿Desea eliminar la incidencia: %s?";
	private static final String MENSAJE_DETALLE_ELIMINAR = "¿Por qué quiere eliminar la incidencia: %s?";
	
	
	
	public ResolverIncidenciasController(ResolverIncidenciasView view, ResolverIncidenciasModel model, int usuarioId, DashboardView viewDsb) {
		this.view = view;
        this.model = model;
        this.usuarioId = usuarioId;
        this.viewDsb = viewDsb;

        initView();
        initController();
	}
	
	private void initView() {
		SwingUtil.exceptionWrapper(() -> listarIncidenciasTecnico());
        view.setVisible(true);
	}

	private void initController() {
		view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarResolucion()));
		SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
    	view.getBtnEliminar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> eliminarIncidencia()));
	}

	private void guardarResolucion() {
		JTable tabla = view.getTablaIncidencias();
	    int fila = tabla.getSelectedRow();

	    if (fila == -1) {
	        throw new ApplicationException("Seleccione una incidencia de la tabla");
	    }

	    int columnIndex = tabla.getColumnModel().getColumnIndex(COLUMN_ID);
        int idIncidencia = (int) tabla.getValueAt(fila, columnIndex);
        String tipo = (String) tabla.getValueAt(fila, tabla.getColumnModel().getColumnIndex("Tipo"));
        
        // Validar horas
	    String horasTxt = view.getTextFieldHorasReales().getText().trim();
        Integer horas;
        if (horasTxt.isEmpty()) {
	        throw new ApplicationException("Las horas de trabajo finales son obligatorias");
        }
        try {
            horas = Integer.parseInt(horasTxt);
            if (horas <= 0) {
    	        throw new ApplicationException("Introduce un número de horas válido (>0)");
            }
        } catch (NumberFormatException ex) {
	        throw new ApplicationException("Las horas de trabajo finales deben ser un número entero");
        }

        // Validar descripción
        String descripcionTrabajo = view.getTextAreaDescripcion().getText().trim();
        if (descripcionTrabajo.isEmpty()) {
	        throw new ApplicationException("La descripción de los trabajos realizados es obligatoria");
        }
        
        // Validar coste
        String costeTxt = view.getTextFieldCoste().getText().trim();
        double costeMateriales;
        if (costeTxt.isEmpty()) throw new ApplicationException("El coste de materiales es obligatorio");
        try {
            costeMateriales = Double.parseDouble(costeTxt.replace(",", "."));
            if (costeMateriales < 0) throw new ApplicationException("El coste no puede ser negativo");
        } catch (NumberFormatException e) {
            throw new ApplicationException("El coste de materiales debe ser un número");
        }
        
        double precioHora = model.getPrecioHoraSistema();
        double costeTotal = (horas * precioHora) + costeMateriales;

        String msg = String.format("Resumen de costes:\n- Mano de obra (%dh): %.2f€\n- Materiales: %.2f€\n- TOTAL: %.2f€\n\n¿Confirmar resolución de la incidencia %d?", 
                                   horas, (horas * precioHora), costeMateriales, costeTotal, idIncidencia);
        if(SwingUtil.showQuestionMessage(view, msg)) {
    		model.resolverIncidencia(idIncidencia, usuarioId, horas, descripcionTrabajo, costeMateriales, costeTotal);
    		String fechaResolucion = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());
    		
    		int longitudMaxima = 30;
            String resumenConfirmacion = descripcionTrabajo;
            if (descripcionTrabajo.length() > longitudMaxima) {
                resumenConfirmacion = descripcionTrabajo.substring(0, longitudMaxima) + "...";
            }
            
    		String mensajeResumen = "Incidencia resuelta correctamente. Estado cambiado a 'Resuelta'\n\n"
    		+ "ID: " + idIncidencia + "\n"
    		+ "Tipo: " + tipo + "\n"
    		+ "Fecha Resolución: " + fechaResolucion + "\n"
    		+ "Horas dedicadas: " + horas + "\n"
    		+ "Descripción Trabajos: " + resumenConfirmacion + "\n"
    		+ "Coste Total: " + costeTotal + " euros";
            SwingUtil.showSuccessMessage(view, mensajeResumen);
            listarIncidenciasTecnico();
    	}
	}
	
	private void listarIncidenciasTecnico() {
		List<IncidenciaDTO> incidencias = model.getIncidenciasTecnico(usuarioId);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollTabla());
	}
	
	private void eliminarIncidencia() {

		String idStr = SwingUtil.getSelectedKey(view.getTablaIncidencias());
		
		if (idStr.isEmpty() || idStr == null) {
			SwingUtil.showErrorMessage(view, MENSAJE_FALLO_ELIMINAR);
			return;
		}

		// solucionar
		String mensajeConfirmacion = String.format(MENSAJE_CONFIRMAR_ELIMINAR, idStr);

		String detalleEliminar = SwingUtil.showInputMessage(view, String.format(MENSAJE_DETALLE_ELIMINAR, idStr));
		if (detalleEliminar == null) {
			return;
		}

		if (!SwingUtil.showQuestionMessage(view, mensajeConfirmacion)) {
			return;
		}

		SwingUtil.exceptionWrapper(() -> {
			int id = Integer.parseInt(idStr);
			
			model.actualizarEstadoIncidenciaEliminar(id, usuarioId, detalleEliminar);
			SwingUtil.showSuccessMessage(view, MENSAJE_EXITO_ELIMINAR);

			listarIncidenciasTecnico();
		});

	}
}
