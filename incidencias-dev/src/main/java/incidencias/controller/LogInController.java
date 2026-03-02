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
    	DashboardView appView = new DashboardView();
    	DashboardModel appModel = new DashboardModel();
    	new DashboardController(appView, appModel, usuario);
    }
}
