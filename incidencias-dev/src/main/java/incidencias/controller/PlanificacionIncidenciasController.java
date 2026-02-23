package incidencias.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import incidencias.model.PlanificacionIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.PlanificacionIncidenciasView;
import util.swing.SwingUtil;

public class PlanificacionIncidenciasController {

	private PlanificacionIncidenciasView view;
    private PlanificacionIncidenciasModel model;
    
    private int idUsuario;
    
	private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Fecha/Hora", "Estado"};
	private static final String[] TITULOS_TABLA_POJOS = new String[]{"id", "tipo", "descripcion", "fechaHora", "estado"};


	public PlanificacionIncidenciasController(PlanificacionIncidenciasView view, PlanificacionIncidenciasModel model, int id) {
		super();
		this.view = view;
		this.model = model;
		this.idUsuario = id;
		this.initView();
	}

	private void initView() {
		SwingUtil.exceptionWrapper(() -> listarIncidenciasTecnico());
		view.setVisible(true);
	}

	public void initController() {
		// TODO Auto-generated method stub
		
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
    
    
	
}
