package test_login.controller;

import incidencias.controller.ConsultaIncidenciasController;
import incidencias.controller.PlanificacionIncidenciasController;
import incidencias.model.ConsultaIncidenciasModel;
import util.LogSystem;
import util.swing.SwingUtil;
import incidencias.ui.ConsultaIncidenciasView;
import test_login.model.TestLoginModel;
import test_login.model.DTOs.UsuarioDTO;
import test_login.ui.TestLoginView;
import incidencias.ui.PlanificacionIncidenciasView;
import incidencias.model.PlanificacionIncidenciasModel;

public class TestLoginController {
    
	private TestLoginView loginView;
    private TestLoginModel loginModel;

    public TestLoginController(TestLoginView l, TestLoginModel m) {
        this.loginView = l;
        this.loginModel = m;
        this.initView();
    }
    
    public void initView() { loginView.setVisible(true); }

    public void initController() {        
    	loginView.getBtnIdentificar().addActionListener(e -> SwingUtil.exceptionWrapper(() ->intentarLogin()));
    }

    private void intentarLogin() {
    	
        String id = loginView.getIdentificador();
        
        LogSystem.logFlow("LOGIN_ATTEMPT", "Intento de acceso con: " + id);
        
        UsuarioDTO usuario = loginModel.validarUsuario(id);
        
        LogSystem.logFlow("LOGIN_SUCCESS", "Usuario identificado con id: " + usuario.getId());
        
        this.abrirConsultaIncidencias(usuario.getId());
        this.abrirPlanificacionIncidencias(usuario.getId());
    }

    private void abrirConsultaIncidencias(int usuarioId) {
    	
        loginView.dispose(); 
        ConsultaIncidenciasView appView = new ConsultaIncidenciasView();
        ConsultaIncidenciasModel appModel = new ConsultaIncidenciasModel();
        new ConsultaIncidenciasController(appView, appModel, usuarioId).initController();;
    }
    
    private void abrirPlanificacionIncidencias(int usuarioId) {
    	
        loginView.dispose(); 
        PlanificacionIncidenciasView appView = new PlanificacionIncidenciasView();
        PlanificacionIncidenciasModel appModel = new PlanificacionIncidenciasModel();
        new PlanificacionIncidenciasController(appView, appModel, usuarioId).initController();
    }
}
