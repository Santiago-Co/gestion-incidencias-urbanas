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
        view.configurarVistaPorRol(user.getRol(), user.getCorreo());
        
        view.setVisible(true);
    }

    private void initController() {
        // 1. Limpiamos los listeners previos para evitar duplicidad por el Singleton
        limpiarListeners();

        // 2. Asignamos los listeners normalmente
        // --- Botones Comunes ---
        view.getBtnLogout().addActionListener(e -> cerrarSesion());
        Util.addCloseAction(view.getBtnClose(), "Dashboard");
        view.getBtnConsultarMisIncidencias().addActionListener(e -> abrirConsultarMisIncidencias());
        view.getBtnCrearIncidencia().addActionListener(e -> abrirCrearIncidencia());

        // --- Botones de OPERADOR ---
        view.getBtnClasificarIncidencia().addActionListener(e -> abrirValidarIncidencias());
    }

    /**
     * Elimina todos los ActionListeners de los botones de la vista
     * para evitar que se ejecuten varias veces debido al uso de Singleton.
     */
    private void limpiarListeners() {
        for (java.awt.event.ActionListener al : view.getBtnLogout().getActionListeners()) 
            view.getBtnLogout().removeActionListener(al);
            
        for (java.awt.event.ActionListener al : view.getBtnConsultarMisIncidencias().getActionListeners()) 
            view.getBtnConsultarMisIncidencias().removeActionListener(al);
            
        for (java.awt.event.ActionListener al : view.getBtnCrearIncidencia().getActionListeners()) 
            view.getBtnCrearIncidencia().removeActionListener(al);
            
        for (java.awt.event.ActionListener al : view.getBtnClasificarIncidencia().getActionListeners()) 
            view.getBtnClasificarIncidencia().removeActionListener(al);
        
        for (java.awt.event.ActionListener al : view.getBtnClose().getActionListeners()) 
            view.getBtnClose().removeActionListener(al);
        // Nota: El botón Close suele estar gestionado por Util, 
        // pero si también se duplica, límpialo aquí.
    }

    private void cerrarSesion() {
        
        model.cerrarSesion();
        view.setVisible(false);
        view.dispose();
        LogInView loginView = new LogInView();
        LogInModel loginModel = new LogInModel();
        new LogInController(loginView, loginModel);
    }

    private void abrirConsultarMisIncidencias() {
    	
    	ConsultaIncidenciasView consultarView = new ConsultaIncidenciasView(); 
    	ConsultaIncidenciasModel consultarModel = new ConsultaIncidenciasModel();
    	new ConsultaIncidenciasController(consultarView, consultarModel, this.model.getUsuarioActual().getId(), this.view);
    	view.setVisible(false);
    }
    
    private void abrirValidarIncidencias() {
        ClasificarIncidenciasView clasificarView = new ClasificarIncidenciasView();
        ClasificarIncidenciasModel clasificarModel = new ClasificarIncidenciasModel();
        new ClasificarIncidenciasController(clasificarView, clasificarModel, this.view);
        view.setVisible(false); 
    }
    
    private void abrirCrearIncidencia() {
    	CrearIncidenciaView crearIncidenciaView = new CrearIncidenciaView();
    	CrearIncidenciaModel crearIncidenciaModel = new CrearIncidenciaModel();
    	new CrearIncidenciasController(crearIncidenciaView, crearIncidenciaModel, model.getUsuarioActual().getId(), this.view);
    	view.setVisible(false);
    }
}