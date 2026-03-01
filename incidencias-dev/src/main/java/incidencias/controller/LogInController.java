package incidencias.controller;

import incidencias.model.DashboardModel;
import incidencias.model.LogInModel;
import incidencias.model.DTOs.UsuarioDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.LogInView;
import util.LogSystem;
import util.Util;
import util.swing.SwingUtil;

public class LogInController {
    private LogInView view;
    private LogInModel model;

    public LogInController(LogInView view, LogInModel model) {
        this.view = view;
        this.model = model;
        this.initController();
        this.view.setVisible(true);
    }

    public void initController() {
        
    	limpiarListeners();
    	
    	view.getBtnIdentificar().addActionListener(e -> SwingUtil.exceptionWrapper(() ->intentarLogin()));
          
        Util.addCloseAction(view.getBtnClose(), "Dashboard");
    }

    private void intentarLogin() {
    	
        String id = view.getIdentificador();
        
        LogSystem.logFlow("LOGIN_ATTEMPT", "Intento de acceso con: " + id);
        
        UsuarioDTO usuario = model.validarUsuario(id);
        
        LogSystem.logFlow("LOGIN_SUCCESS", "Usuario identificado con id: " + usuario.getId());
        
        abrirDashboard(usuario);
        
    }
    
    
    private void abrirDashboard(UsuarioDTO usuario) {
    	view.dispose();
    	/**
    	 * Cree el singleton para intentar solucionar el problema de duplicidad de ventanas lo solucione también
    	 * eliminando la asignación de las acciones a los botones no he probado quitando el singleton si funcionaria
    	 * TODO probar quitar singleton
    	 */
    	DashboardView appView = DashboardView.getInstance();//new DashboardView();
    	DashboardModel appModel = new DashboardModel();
    	new DashboardController(appView, appModel, usuario);
    }
    
    private void limpiarListeners() {
        for (java.awt.event.ActionListener al : view.getBtnIdentificar().getActionListeners()) 
            view.getBtnIdentificar().removeActionListener(al);
    }
}
