package incidencias.controller;

import incidencias.model.CerrarIncidenciasEspecialidadModel;
import incidencias.ui.CerrarIncidenciasEspecialidadView;
import incidencias.ui.DashboardView;
import util.swing.SwingUtil;

public class CerrarIncidenciasEspecialidadController {

	CerrarIncidenciasEspecialidadView view;
	CerrarIncidenciasEspecialidadModel model;
	DashboardView viewDsb;
	int idTecnico;
	
	public CerrarIncidenciasEspecialidadController(CerrarIncidenciasEspecialidadView view, CerrarIncidenciasEspecialidadModel model, DashboardView viewDsb, int idTecnico) {
		this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;
        this.idTecnico = idTecnico;

        initView();
        initController();
	}
	
	private void initView() {
        listarIncidencias();
        view.setVisible(true);
    }

    private void listarIncidencias() {
		// TODO Auto-generated method stub
		
	}

	private void initController() {
        view.getBtnCerrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> confirmarYCerrar()));
        SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
    }

	private Object confirmarYCerrar() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
