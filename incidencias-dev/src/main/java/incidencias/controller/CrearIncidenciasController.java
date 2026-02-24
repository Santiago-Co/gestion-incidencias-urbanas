package incidencias.controller;

import incidencias.model.CrearIncidenciaModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.Entitiy.IncidenciaEntity;
import incidencias.ui.CrearIncidenciaView;
import incidencias.ui.DashboardView;
import util.LogSystem;
import util.Util;

public class CrearIncidenciasController {
    private CrearIncidenciaView view; 
    private CrearIncidenciaModel model;
    private DashboardView viewDsb;
    private int ciudadanoId; 

    public CrearIncidenciasController(CrearIncidenciaView view, CrearIncidenciaModel model, int ciudadanoId, DashboardView viewDsb) {
        this.view = view; 
        this.model = model; 
        this.ciudadanoId = ciudadanoId;
        this.viewDsb = viewDsb; 
        this.initController();
        view.setVisible(true);
    }
    
    public void initController() {
        view.getBtnEnviar().addActionListener(e -> registrarIncidencia());

        view.getBtnClose().addActionListener(e -> volverDashboard());
    }
    
    private void registrarIncidencia() {

        String tipo = (String) view.getCmbTipo().getSelectedItem();
        String localizacion = view.getTxtLocalizacion().getText().trim();
        String descripcion = view.getTxtDescripcion().getText().trim();

        LogSystem.logFlow("ACTION", "Intento de registro: " + tipo);

        
        if (descripcion.isEmpty() || localizacion.isEmpty()) {
            LogSystem.logFlow("VALIDATION_FAIL", "Descripción o localización vacías");
            view.mostrarError("Por favor, rellene la descripción y la localización.");
            return;
        }

        
        IncidenciaEntity nuevaIncidencia = new IncidenciaEntity();
        nuevaIncidencia.setCiudadanoId(this.ciudadanoId);
        nuevaIncidencia.setTipo(tipo);
        nuevaIncidencia.setLocalizacion(localizacion);
        nuevaIncidencia.setDescripcion(descripcion);
        nuevaIncidencia.setEstado("Nueva");

        
        try {
            IncidenciaDTO incidenciaGenerada = model.registrarYRecuperarIncidencia(nuevaIncidencia);
            
            String mensajeResumen = "¡Registro Exitoso!\n" +
                    "Ticket ID: " + incidenciaGenerada.getId() + "\n" +
                    "Fecha: " + incidenciaGenerada.getFechaRegistro() + "\n" +
                    "Estado: " + incidenciaGenerada.getEstado() + "\n" +
                    "Localización: " + incidenciaGenerada.getLocalizacion() + "\n" +
            		"Descripción: " + incidenciaGenerada.getDescripcion();
            
            String resumenDetalle = "Incidencia guardada con ID: " + incidenciaGenerada.getId();
            view.mostrarResumen(mensajeResumen, resumenDetalle);
            limpiarFormulario();
            
        } catch (Exception ex) {
            LogSystem.logFlow("ERROR", "Fallo al insertar en DB: " + ex.getMessage());
            view.mostrarError("Hubo un error al guardar la incidencia en la base de datos.");
        }
    }
    
    private void volverDashboard() {
        view.dispose(); 
        viewDsb.setVisible(true);
    }
    
    private void limpiarFormulario() {
        view.getTxtLocalizacion().setText("");
        view.getTxtDescripcion().setText("");
        view.getCmbTipo().setSelectedIndex(0);
    }
}