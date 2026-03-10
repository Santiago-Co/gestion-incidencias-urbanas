package incidencias.controller;

import incidencias.model.AsignacionIncidenciasModel;
import incidencias.model.ClasificarIncidenciasModel;
import incidencias.model.ConsultaIncidenciasModel;
import incidencias.model.CrearIncidenciaModel;
import incidencias.model.DashboardModel;
import incidencias.model.HistorialIncidenciaModel;
import incidencias.model.LogInModel;
import incidencias.model.PlanificacionIncidenciasModel;
import incidencias.model.ResolverIncidenciasModel;
import incidencias.model.DTOs.UsuarioDTO;
import incidencias.ui.AsignacionIncidenciasView;
import incidencias.ui.ClasificarIncidenciasView;
import incidencias.ui.ConsultaIncidenciasView;
import incidencias.ui.CrearIncidenciaView;
import incidencias.ui.DashboardView;
import incidencias.ui.HistorialIncidenciaView;
import incidencias.ui.LogInView;
import incidencias.ui.PlanificacionIncidenciasView;
import incidencias.ui.ResolverIncidenciasView;
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
        // --- Botones Comunes ---
        view.getBtnLogout().addActionListener(e -> cerrarSesion());
        Util.addCloseAction(view.getBtnClose(), "Dashboard");
        view.getBtnConsultarMisIncidencias().addActionListener(e -> abrirConsultarMisIncidencias());
        view.getBtnCrearIncidencia().addActionListener(e -> abrirCrearIncidencia());

        // --- Botones de OPERADOR ---
        view.getBtnClasificarIncidencia().addActionListener(e -> abrirValidarIncidencias());
        view.getBtnAsignarIncidencia().addActionListener(e -> abrirAsignarIncidencia());
        
        // --- Botones de TECNICO ---
        view.getBtnPlanificarIncidencia().addActionListener(e -> abrirPlanificarIncidencias());
        view.getBtnResolverIncidencia().addActionListener(e -> abrirResolverIncidencia());
        
        // --- Botones de OPERADOR Y TECNICO ---
        view.getBtnHistorialIncidencia().addActionListener(e -> abrirHistorialIncidencia());
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
        new ClasificarIncidenciasController(clasificarView, clasificarModel, model.getUsuarioActual().getId(), this.view);
        view.setVisible(false); 
    }
    
    private void abrirCrearIncidencia() {
    	CrearIncidenciaView crearIncidenciaView = new CrearIncidenciaView();
    	CrearIncidenciaModel crearIncidenciaModel = new CrearIncidenciaModel();
    	new CrearIncidenciasController(crearIncidenciaView, crearIncidenciaModel, model.getUsuarioActual().getId(), this.view);
    	view.setVisible(false);
    }
    
    private void abrirAsignarIncidencia() {
    	AsignacionIncidenciasView asignarIncidenciaView = new AsignacionIncidenciasView();
    	AsignacionIncidenciasModel asignarIncidenciaModel = new AsignacionIncidenciasModel();
    	new AsignacionIncidenciasController(asignarIncidenciaView, asignarIncidenciaModel, model.getUsuarioActual().getId(), this.view);
    	view.setVisible(false);
    }

    private void abrirPlanificarIncidencias() {
    	PlanificacionIncidenciasView planView = new PlanificacionIncidenciasView();
        PlanificacionIncidenciasModel planModel = new PlanificacionIncidenciasModel();
        new PlanificacionIncidenciasController(planView, planModel, model.getUsuarioActual().getId(), this.view);
        view.setVisible(false);
    }
    
    private void abrirHistorialIncidencia() {
    	HistorialIncidenciaView histView = new HistorialIncidenciaView();
        HistorialIncidenciaModel histModel = new HistorialIncidenciaModel();
        new HistorialIncidenciaController(histView, histModel, model.getUsuarioActual().getId(), this.view);
        view.setVisible(false);
    }
    
    private void abrirResolverIncidencia() {
    	ResolverIncidenciasView histView = new ResolverIncidenciasView();
        ResolverIncidenciasModel histModel = new ResolverIncidenciasModel();
        new ResolverIncidenciasController(histView, histModel, model.getUsuarioActual().getId(), this.view);
        view.setVisible(false);
    }
}