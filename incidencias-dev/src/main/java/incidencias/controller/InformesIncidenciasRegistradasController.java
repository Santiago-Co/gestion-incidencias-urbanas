package incidencias.controller;

import incidencias.model.InformesIncidenciasRegistradasModel;
import incidencias.ui.DashboardView;
import incidencias.ui.InformesIncidenciasRegistradasView;

public class InformesIncidenciasRegistradasController {

	private InformesIncidenciasRegistradasView view;
	private InformesIncidenciasRegistradasModel model;
	private DashboardView viewDsb;
	
	public InformesIncidenciasRegistradasController(InformesIncidenciasRegistradasView view, InformesIncidenciasRegistradasModel model, DashboardView viewDsb) {
		this.view = view;
		this.model = model;
		this.viewDsb = viewDsb;
		
		initController();
        initView();
	}

	private void initController() {
		view.getBtnClose().addActionListener(e -> volverDashboard());    
	}
	
	private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }

	private void initView() {
		view.setVisible(true);
	}
	
	
}
