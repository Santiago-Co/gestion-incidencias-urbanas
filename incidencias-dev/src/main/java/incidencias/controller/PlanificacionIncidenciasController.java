package incidencias.controller;

import incidencias.model.PlanificacionIncidenciasModel;
import incidencias.ui.PlanificacionIncidenciasView;

public class PlanificacionIncidenciasController {

	private PlanificacionIncidenciasView view;
    private PlanificacionIncidenciasModel model;
    
    private int idUsuario;

	public PlanificacionIncidenciasController(PlanificacionIncidenciasView view, PlanificacionIncidenciasModel model, int id) {
		super();
		this.view = view;
		this.model = model;
		this.idUsuario = id;
		this.initView();
	}

	private void initView() {
		view.setVisible(true);
	}

	public void initController() {
		// TODO Auto-generated method stub
		
	}
    
    
	
}
