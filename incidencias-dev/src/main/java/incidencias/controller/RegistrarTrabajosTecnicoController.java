package incidencias.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import exceptions.ApplicationException;
import incidencias.model.RegistrarTrabajosTecnicoModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TrabajoDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.RegistrarTrabajosTecnicoView;
import util.swing.SwingUtil;

public class RegistrarTrabajosTecnicoController {

	private RegistrarTrabajosTecnicoView view;
    private RegistrarTrabajosTecnicoModel model;
    private DashboardView viewDsb;
    private int idUsuario;
    private static final String COLUMN_ID ="ID";
    
    private static final String[] TITULOS_TABLA_INCIDENCIAS_VISTA = {"ID", "Tipo", "Descripción", "Localización", "Fecha/Hora"};
	private static final String[] TITULOS_TABLA_INCIDENCIAS_POJOS = {"id", "tipo", "descripcion", "localizacion", "fechaCambio"};
	
	private static final String[] TITULOS_TABLA_COMENTARIOS_VISTA = {"ID", "Fecha", "Comentario"};
	private static final String[] TITULOS_TABLA_COMENTARIOS_POJOS = {"id", "fecha", "descripcion"};
    
    public RegistrarTrabajosTecnicoController(RegistrarTrabajosTecnicoView view, RegistrarTrabajosTecnicoModel model, DashboardView viewDsb, int idUsuario) {
    	this.view = view;
		this.model = model;
		this.viewDsb = viewDsb;
		this.idUsuario = idUsuario;
		
		initController();
		initView();
    }

	private void initController() {
		SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
		view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarComentario()));
		view.getTablaIncidencias().getSelectionModel().addListSelectionListener(e -> {
	        if (!e.getValueIsAdjusting()) onIncidenciaSelected();
	    });
	}

	private void initView() {
		SwingUtil.exceptionWrapper(() -> listarIncidenciasTecnico());
        view.setVisible(true);
        setComentariosEnabled(false);
	}
	
	private void listarIncidenciasTecnico() {
		List<IncidenciaDTO> incidencias = model.getIncidenciasTecnico(idUsuario);
		
		JTable tabla = view.getTablaIncidencias();
	    tabla.setModel(SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_INCIDENCIAS_POJOS));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS_VISTA);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
	}
	
	private void onIncidenciaSelected() {
        JTable tabla = view.getTablaIncidencias();
        SwingUtil.exceptionWrapper(() -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                // Limpiar historial si no hay selección
                view.getTablaTrabajos().setModel(SwingUtil.getTableModelFromPojos(null, TITULOS_TABLA_COMENTARIOS_POJOS));
                // Deshabilitar sección inferior
                setComentariosEnabled(false);
                return;
            }

            int columnIndex = tabla.getColumnModel().getColumnIndex(COLUMN_ID);
            int idIncidencia = (int) tabla.getValueAt(row, columnIndex);

            setComentariosEnabled(true);
            
            listarComentariosIncidencia(idIncidencia);
        });
    }
	
	private void listarComentariosIncidencia(int idIncidencia) {
		List<TrabajoDTO> comentarios = model.getTrabajosIncidencia(idIncidencia);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(comentarios, TITULOS_TABLA_COMENTARIOS_POJOS);
		JTable tabla = view.getTablaTrabajos();
		tabla.setModel(tmodel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_COMENTARIOS_VISTA);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollTrabajos());
	}

	private void guardarComentario() {
		JTable tabla = view.getTablaIncidencias();
	    int fila = tabla.getSelectedRow();

	    if (fila == -1) {
	        throw new ApplicationException("Seleccione una incidencia de la tabla");
	    }

	    int columnIndex = tabla.getColumnModel().getColumnIndex(COLUMN_ID);
        int idIncidencia = (int) tabla.getValueAt(fila, columnIndex);
        String tipo = (String) tabla.getValueAt(fila, tabla.getColumnModel().getColumnIndex("Tipo"));

        String comentariosTxt = view.getTextAreaComentarios().getText().trim();
        if (comentariosTxt.isEmpty()) {
	        throw new ApplicationException("Los comentarios de los trabajos del día son obligatorios");
        }
        
        Object valFecha = view.getDatePickerFecha().getModel().getValue();
        if (valFecha == null) {
        	throw new ApplicationException("La fecha en que se realizaron los comentarios es obligatoria");
        }
        
        java.util.Date dateFecha = (java.util.Date) valFecha;
        
        if (dateFecha.after(new java.util.Date())) {
        	throw new ApplicationException("La fecha en que se realizaron los comentarios debe ser la de hoy o una anterior");
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String mensajeConfirmacion = String.format("¿Desea añadir los trabajos del día a la incidencia %d?", idIncidencia);
        if(SwingUtil.showQuestionMessage(view, mensajeConfirmacion)) {
        	String txtFecha = sdf.format(dateFecha);
    		model.registrarTrabajo(idIncidencia, txtFecha, comentariosTxt);
    		
    		int longitudMaxima = 30;
            String resumenTrabajos = comentariosTxt;
            if (comentariosTxt.length() > longitudMaxima) {
                resumenTrabajos = comentariosTxt.substring(0, longitudMaxima) + "...";
            }
            
    		String mensajeResumen = "Comentarios añadidos correctamente. \n\n"
    		+ "ID incidencia: " + idIncidencia + "\n"
    		+ "Tipo: " + tipo + "\n"
    		+ "Fecha de trabajos: " + txtFecha + "\n"
    		+ "Comentarios del día: " + resumenTrabajos;
            SwingUtil.showSuccessMessage(view, mensajeResumen);
            listarComentariosIncidencia(idIncidencia);
            view.getTextAreaComentarios().setText("");
    	}
	}
	
	private void setComentariosEnabled(boolean enabled) {
	    view.getTextAreaComentarios().setEnabled(enabled);
	    view.getDatePickerFecha().setEnabled(enabled);
	    view.getBtnGuardar().setEnabled(enabled);
	    if (!enabled) {
	        view.getTextAreaComentarios().setText("");
	    }
	}
}
