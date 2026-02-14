package incidencias.controller;

import incidencias.model.UsuarioDTO;
import incidencias.model.ConsultaIncidenciasModel;
import incidencias.model.TestLoginModel;
import util.LogSystem;
import util.SwingUtil;
import incidencias.ui.ConsultaIncidenciasView;
import incidencias.ui.TestLoginView;

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
        
    }

    private void abrirConsultaIncidencias(int usuarioId) {
    	
        loginView.dispose(); 
        ConsultaIncidenciasView appView = new ConsultaIncidenciasView();
        ConsultaIncidenciasModel appModel = new ConsultaIncidenciasModel();
        new ConsultaIncidenciasController(appView, appModel, usuarioId).initController();;
    }
}
