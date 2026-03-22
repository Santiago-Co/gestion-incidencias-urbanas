package incidencias.controller;

import incidencias.model.RegistrarTrabajosTecnicoModel;
import incidencias.ui.DashboardView;
import incidencias.ui.RegistrarTrabajosTecnicoView;
import util.swing.SwingUtil;

public class RegistrarTrabajosTecnicoController {

	private RegistrarTrabajosTecnicoView view;
    private RegistrarTrabajosTecnicoModel model;
    private DashboardView viewDsb;
    private int idUsuario;
    
    public RegistrarTrabajosTecnicoController(RegistrarTrabajosTecnicoView view, RegistrarTrabajosTecnicoModel model, DashboardView viewDsb, int idUsuario) {
    	this.view = view;
		this.model = model;
		this.viewDsb = viewDsb;
		this.idUsuario = idUsuario;
		
		initView();
		initController();
    }

	private void initController() {
		SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
	}
	
	private void initView() {
		//SwingUtil.exceptionWrapper(() -> listarIncidenciasTecnico());
		view.setVisible(true);
	}
}
