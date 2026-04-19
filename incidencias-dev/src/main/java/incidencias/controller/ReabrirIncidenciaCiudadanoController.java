package incidencias.controller;

import java.util.List;
import javax.swing.table.DefaultTableModel;

import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.ReabrirIncidenciaCiudadanoModel;
import incidencias.ui.DashboardView;
import incidencias.ui.ReabrirIncidenciaCiudadanoView;
import util.LogSystem;
import util.swing.SwingUtil;

public class ReabrirIncidenciaCiudadanoController {
    
    private ReabrirIncidenciaCiudadanoView view; 
    private ReabrirIncidenciaCiudadanoModel model;
    private DashboardView viewDsb;
    private int ciudadanoId; 

    // Constantes
    private static final int COLUMN_ID = 0;

    private static final String LOG_ACTION = "ACTION";
    private static final String LOG_ERROR = "ERROR";

    private static final String LOG_REAPERTURA_INTENTO = 
        "Intento de reapertura para incidencia ID: %d";

    private static final String INPUT_MOTIVO_REAPERTURA = 
        "Por favor, detalla el motivo por el que solicitas reabrir esta incidencia.";

    private static final String MENSAJE_REAPERTURA_TITULO = "¡Incidencia Reabierta!";
    private static final String MENSAJE_ID = "ID incidencia: ";
    private static final String MENSAJE_ESTADO = "Estado actual: ";
    private static final String ESTADO_NUEVO = "Nueva";

    private static final String RESUMEN_DETALLE = 
        "Se ha registrado el motivo y la incidencia volverá a ser evaluada.";

    private static final String ERROR_REAPERTURA = 
        "Hubo un error al intentar reabrir la incidencia.";

    private static final String LOG_ERROR_REAPERTURA = 
        "Fallo al registrar la reapertura en DB: ";

    public ReabrirIncidenciaCiudadanoController(
            ReabrirIncidenciaCiudadanoView view, 
            ReabrirIncidenciaCiudadanoModel model, 
            int ciudadanoId, 
            DashboardView viewDsb) {

        this.view = view; 
        this.model = model; 
        this.ciudadanoId = ciudadanoId;
        this.viewDsb = viewDsb; 
        this.initController();
        view.setVisible(true);
    }
    
    public void initController() {
        cargarTablaIncidencias();

        view.getBtnReabrir().addActionListener(e -> solicitarReapertura());

        SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
    }
    
    private void cargarTablaIncidencias() {
        List<IncidenciaDTO> incidencias = 
            model.getIncidenciasCerradasORechazadas(this.ciudadanoId);

        DefaultTableModel tableModel = 
            (DefaultTableModel) view.getTablaIncidencias().getModel();
        
        tableModel.setRowCount(0); 
        
        for (IncidenciaDTO incidencia : incidencias) {
            Object[] row = {
                incidencia.getId(),
                incidencia.getTipo(),
                incidencia.getDescripcion(),
                incidencia.getLocalizacion(),
                incidencia.getEstado()
            };
            tableModel.addRow(row);
        }
    }
    
    private void solicitarReapertura() {
        int selectedRow = view.getTablaIncidencias().getSelectedRow();
        
        if (selectedRow == -1) {
            return;
        }

        int idIncidencia = (int) view.getTablaIncidencias()
                                    .getValueAt(selectedRow, COLUMN_ID);

        LogSystem.logFlow(LOG_ACTION, 
            String.format(LOG_REAPERTURA_INTENTO, idIncidencia));

        String motivo = SwingUtil.showInputMessage(
            view, 
            INPUT_MOTIVO_REAPERTURA
        );

        if (motivo != null && !motivo.trim().isEmpty()) {
            try {
                model.reabrirIncidencia(idIncidencia, motivo.trim());
                
                String mensajeResumen = MENSAJE_REAPERTURA_TITULO + "\n" +
                                        MENSAJE_ID + idIncidencia + "\n" +
                                        MENSAJE_ESTADO + ESTADO_NUEVO;
                
                SwingUtil.showSummaryMessage(
                    view, 
                    mensajeResumen, 
                    RESUMEN_DETALLE
                );
                
                cargarTablaIncidencias();
                view.getBtnReabrir().setEnabled(false);
                
            } catch (Exception ex) {
                LogSystem.logFlow(LOG_ERROR, 
                    LOG_ERROR_REAPERTURA + ex.getMessage());

                SwingUtil.showErrorMessage(view, ERROR_REAPERTURA);
            }
        }
    }
}