package incidencias.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

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
		SwingUtil.setDescColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla);
    }
	
	private void guardarPlanificacion() {
	    JTable tabla = view.getTablaIncidencias();
	    int fila = tabla.getSelectedRow();

	    if (fila == -1) {
	        JOptionPane.showMessageDialog(view, "Seleccione una incidencia de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    Object value = tabla.getModel().getValueAt(fila, 0);
	    if (!(value instanceof Number)) {
	        JOptionPane.showMessageDialog(view, "Id de incidencia inválido.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    int idIncidencia = ((Number) value).intValue();

	    String horasTxt = view.getTextFieldHorasPrevistas().getText().trim();
        Integer horas;
        if (horasTxt.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Las horas previstas son obligatorias.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            horas = Integer.parseInt(horasTxt);
            if (horas <= 0) {
                JOptionPane.showMessageDialog(view, "Introduce un número de horas válido (> 0).", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Horas previstas debe ser un número entero.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String descripcionTrabajo = view.getTextAreaDescripcion().getText().trim();
        if (descripcionTrabajo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "La descripción del trabajo es obligatoria.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.planificarIncidencia(idIncidencia, idUsuario, horas, descripcionTrabajo);

        JOptionPane.showMessageDialog(view, "Incidencia planificada correctamente. Estado cambiado a 'En proceso'.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        listarIncidenciasTecnico();
	}
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}
