package incidencias.controller;

import incidencias.model.ResolverIncidenciasModel;
import incidencias.ui.DashboardView;
import incidencias.ui.ResolverIncidenciasView;
import util.swing.SwingUtil;

public class ResolverIncidenciasController {

	private ResolverIncidenciasView view;
	private ResolverIncidenciasModel model;
	private int usuarioId;
    private DashboardView viewDsb;
	
	public ResolverIncidenciasController(ResolverIncidenciasView view, ResolverIncidenciasModel model, int usuarioId, DashboardView viewDsb) {
		this.view = view;
        this.model = model;
        this.usuarioId = usuarioId;
        this.viewDsb = viewDsb;

        initView();
        initController();
	}

	private void initController() {
		//view.getBtnGuardar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> guardarResolucion()));
    	view.getBtnClose().addActionListener(e -> volverDashboard());
	}

	private Object guardarResolucion() {
		// TODO Auto-generated method stub
		return null;
	}

	private void initView() {
		//SwingUtil.exceptionWrapper(() -> listarIncidencias());
        view.setVisible(true);
	}
	
	private void volverDashboard() {
        view.dispose(); 
        viewDsb.setVisible(true);
    }
}
