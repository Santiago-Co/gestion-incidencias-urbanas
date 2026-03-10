package incidencias.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import incidencias.model.AsignacionIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.UsuarioDTO;
import incidencias.ui.AsignacionIncidenciasView;
import incidencias.ui.DashboardView;
import util.Util;
import util.swing.SwingUtil;

public class AsignacionIncidenciasController {
    
	private AsignacionIncidenciasView view;
    private AsignacionIncidenciasModel model;
	private DashboardView viewDsb;
	private int idUsuario;
	
	private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Fecha/Hora"};
	private static final String[] TITULOS_TABLA_INCIDENCIAS_POJOS = new String[]{"id", "tipo", "descripcion", "fechaCambio"};
	
	private static final String[] TITULOS_TABLA_TECNICOS = {"ID", "Nombre", "Apellidos"};
	private static final String[] TITULOS_TABLA_TECNICOS_POJOS = new String[]{"id", "nombre", "apellidos"};	

    public AsignacionIncidenciasController(AsignacionIncidenciasView l, AsignacionIncidenciasModel m, int id, DashboardView viewDsb) {
        this.view = l;
        this.model = m;
        this.idUsuario = id;
        this.viewDsb = viewDsb;
        this.initView();
        this.initController();
    }
    
    public void initView() {
    	
    	SwingUtil.exceptionWrapper(() -> listarIncidenciasValidadas());
    	
    	view.setVisible(true);
    }

    public void initController() {
    	view.getTablaIncidencias().getSelectionModel().addListSelectionListener(e -> {
    	    if (!e.getValueIsAdjusting()) {
    	        int fila = view.getTablaIncidencias().getSelectedRow();
    	        if (fila != -1) {
    	        	SwingUtil.exceptionWrapper(() -> listarTecnicos());
    	        }
    	    }
    	});
    	view.getBtnAsignar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> asignarIncidencia()));    	
    	view.getBtnClose().addActionListener(e -> SwingUtil.exceptionWrapper(() -> volverDashboard()));
    }

    private void listarIncidenciasValidadas() {
    	List<IncidenciaDTO> incidencias = model.getListaIncidenciasValidadas();
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_INCIDENCIAS_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
    }
    
    private void listarTecnicos() {
    	List<UsuarioDTO> incidencias = model.getListaTecnicos();
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_TECNICOS_POJOS);
		JTable tabla = view.getTablaTecnicos();
		tabla.setModel(tmodel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_TECNICOS);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
    }
    
    private void asignarIncidencia() {
    	String idIncidencia = SwingUtil.getSelectedKey(view.getTablaIncidencias());
    	String idTecnico    = SwingUtil.getSelectedKey(view.getTablaTecnicos());
    	Util.validateCondition( !idIncidencia.equals("") && !idTecnico.equals("") ,
								"Debe seleccionar una incidencia y un técnico"  );
    	
    	int idIn  = Integer.parseInt(idIncidencia);
    	int idTec = Integer.parseInt(idTecnico);
    	String nombreTec = SwingUtil.getSelectedValue(view.getTablaTecnicos(), 1);
    	String apellidosTec = SwingUtil.getSelectedValue(view.getTablaTecnicos(), 2);

    	String mensajeConfirmacion = String.format("Desea asignar la incidencia %d al técnico %d (%s %s)?",
    												idIn, idTec, nombreTec, apellidosTec);
    	
    	if(SwingUtil.showQuestionMessage(view, mensajeConfirmacion)) {
    		model.asignarIncidencia(idIn, idTec, idUsuario);
    		JTable tablaIncidencias = view.getTablaIncidencias();
    		DefaultTableModel modelTabla = (DefaultTableModel) tablaIncidencias.getModel();
    		modelTabla.removeRow(tablaIncidencias.getSelectedRow());
    		view.getTablaTecnicos().setModel(new DefaultTableModel());
    		String mensajeExito = String.format("La incidencia %d se asignó correctamente al técnico %d (%s %s)",
												 idIn, idTec, nombreTec, apellidosTec);
    		SwingUtil.showSuccessMessage(view, mensajeExito);
    	}
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
    
}
