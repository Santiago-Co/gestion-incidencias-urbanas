package controller;

import java.util.List;

import dto.UsuarioDTO;
import model.ClasificarIncidenciasModel;
import model.LoginModel;
import util.LogSystem;
import util.Util;
import view.ClasificarIncidenciasView;
import view.LogIn;

public class LoginController {
    private LogIn view;
    private LoginModel model;

    public LoginController(LogIn view, LoginModel model) {
        this.view = view;
        this.model = model;
        this.initController();
    }

    public void initController() {
        
        view.getBtnIdentificar().addActionListener(e -> intentarLogin());
          
        Util.addCloseAction(view.getBtnClose(), "Login");
    }

    private void intentarLogin() {
        String id = view.getIdentificador();
        
        LogSystem.logFlow("LOGIN_ATTEMPT", "Intento de acceso con: " + id);

        if (id.isEmpty()) {
            view.mostrarError("Debe introducir un DNI o Correo");
            return;
        }
        
        if (!Util.esDNIValido(id) && !Util.esCorreoValido(id)) {
        	LogSystem.logFlow("VALIDATION_ERROR", "Formato inválido intentado: " + id);
            view.mostrarError("Formato no válido, debe introducir un DNI o un correo");
            return;
        }
        
        List<UsuarioDTO> resultados = model.validarUsuario(id);

        if (!resultados.isEmpty()) {
            LogSystem.logFlow("LOGIN_SUCCESS", "Usuario identificado correctamente");
            UsuarioDTO usuarioLogueado = resultados.get(0);
            this.abrirClasificarIncidencias();
            //this.abrirRegistroIncidencias(usuarioLogueado.getId()); Comentado al no usarse en esta historia 
        } else {
            LogSystem.logFlow("LOGIN_FAIL", "Credenciales no encontradas en DB");
            view.mostrarError("El usuario no está registrado en el sistema");
        }
    }
    private void abrirClasificarIncidencias() {
    	view.dispose();
    	ClasificarIncidenciasView nextView = new ClasificarIncidenciasView();
    	ClasificarIncidenciasModel nextModel = new ClasificarIncidenciasModel(); 
    	new ClasificarIncidenciasController(nextView, nextModel);
    }
/* Comentado al no utilizarse en esta historia
    private void abrirRegistroIncidencias(int usuarioId) {
        view.dispose(); 
        RegistroIncidencias nextView = new RegistroIncidencias();
        IncidenciaModel nextModel = new IncidenciaModel();
        new IncidenciasController(nextView, nextModel, usuarioId);
        
        nextView.setVisible(true);
    }
*/
}