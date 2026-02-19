package incidencias.controller;

import incidencias.model.ClasificarIncidenciasModel;
import incidencias.model.ConsultaIncidenciasModel;
import incidencias.model.CrearIncidenciaModel;
import incidencias.model.DashboardModel;
import incidencias.model.LogInModel;
import incidencias.model.DTOs.UsuarioDTO;
import incidencias.ui.ClasificarIncidenciasView;
import incidencias.ui.ConsultaIncidenciasView;
import incidencias.ui.CrearIncidenciaView;
import incidencias.ui.DashboardView; // O el paquete correcto view.DashboardView
import incidencias.ui.LogInView;
import util.Util;

public class DashboardController {

    private DashboardView view;
    private DashboardModel model;

    public DashboardController(DashboardView view, DashboardModel model, UsuarioDTO usuarioLogueado) {
        this.view = view;
        this.model = model;
        
        this.model.setUsuarioActual(usuarioLogueado);
        
        initController();
        initView();
    }

    private void initView() {
    	
        UsuarioDTO user = model.getUsuarioActual();
        System.out.println(user.getRol());
        view.configurarVistaPorRol(user.getRol(), user.getCorreo());
        
        view.setVisible(true);
    }

    private void initController() {
    	
        // --- Botones Comunes ---
        view.getBtnLogout().addActionListener(e -> cerrarSesion());
        Util.addCloseAction(view.getBtnClose(), "Dashboard");
        view.getBtnConsultarMisIncidencias().addActionListener(e -> abrirConsultarMisIncidencias());
        view.getBtnCrearIncidencia().addActionListener(e -> abrirCrearIncidencia());
        // --- Botones de CIUDADANO ---
        

        // --- Botones de OPERADOR ---
        
        view.getBtnClasificarIncidencia().addActionListener(e -> abrirValidarIncidencias());

        // --- Botones de TÉCNICO ---
        
    }

    private void cerrarSesion() {
        
        model.cerrarSesion();
        view.dispose();
        LogInView loginView = new LogInView();
        LogInModel loginModel = new LogInModel();
        new LogInController(loginView, loginModel);
    }

    // --- ACCIONES DE OPERADOR ---
    
    private void abrirConsultarMisIncidencias() {
    	
    	ConsultaIncidenciasView consultarView = new ConsultaIncidenciasView(); 
    	ConsultaIncidenciasModel consultarModel = new ConsultaIncidenciasModel();
    	new ConsultaIncidenciasController(consultarView, consultarModel, this.model.getUsuarioActual().getId());
    	view.setVisible(false);
    }
    
    private void abrirValidarIncidencias() {
        ClasificarIncidenciasView clasificarView = new ClasificarIncidenciasView();
        ClasificarIncidenciasModel clasificarModel = new ClasificarIncidenciasModel();
        new ClasificarIncidenciasController(clasificarView, clasificarModel);
        view.setVisible(false); 
    }
    
    private void abrirCrearIncidencia() {
    	CrearIncidenciaView crearIncidenciaView = new CrearIncidenciaView();
    	CrearIncidenciaModel crearIncidenciaModel = new CrearIncidenciaModel();
    	new CrearIncidenciasController(crearIncidenciaView, crearIncidenciaModel, model.getUsuarioActual().getId());
    	view.setVisible(false);
    }
}