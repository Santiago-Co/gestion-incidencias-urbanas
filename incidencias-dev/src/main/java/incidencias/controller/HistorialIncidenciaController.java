package incidencias.controller;

import java.util.List; 

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import incidencias.model.HistorialIncidenciaModel;
import incidencias.model.DTOs.HistoriaDTO;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.HistorialIncidenciaView;
import util.LogSystem;
import util.swing.SwingUtil;

/**
 * Controlador para la funcionalidad de visualización del historial de las
 * incidencias asociadas a un técnico/operador.
 * 
 * Se encarga de:
 * <br/> - Inicializar la vista y el modelo.
 * <br/> - Cargar las incidencias del usuario autenticado.
 * <br/> - Gestionar la selección de una incidencia.
 * <br/> - Mostrar el historial de cambios asociado.
 * <br/> - Gestionar la navegación de vuelta al Dashboard.
 */
public class HistorialIncidenciaController {

    private HistorialIncidenciaView view;
    private HistorialIncidenciaModel model;
    private DashboardView viewDsb;

    private List<IncidenciaDTO> incidenciaList;
    private static final String COLUMN_ID ="Id";
    private static final String DESC_ID ="Descripción";
    
    private static final String[] TITULOS_TABLA_INCIDENCIAS_POJOS = 
            new String[] { "id", "tipo", "descripcion", "fechaCambio", "estado" };
    
    private static final String[] TITULOS_TABLA_INCIDENCIAS_VISTA = 
            new String[] { "Id", "Tipo", "Descripción", "Último cambio", "Estado" };

    private static final String[] TITULOS_TABLA_HISTORIAL_POJOS = 
            new String[] { "id", "fechaCambio", "descripcion", "estado" };
    
    private static final String[] TITULOS_TABLA_HISTORIAL_VISTA = 
            new String[] { "Id", "Fecha de cambio", "Descripción", "Estado" };

    public HistorialIncidenciaController(HistorialIncidenciaView view, HistorialIncidenciaModel model, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;

        initView();
        initController();
    }

    private void initView() {
        SwingUtil.exceptionWrapper(() -> listarIncidencias());
        view.setVisible(true);
    }

    private void initController() {
        // Listener de selección en la tabla de incidencias
        view.getTablaIncidencias().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Ignorar eventos intermedios
                if (e.getValueIsAdjusting()) return;
                SwingUtil.exceptionWrapper(() -> onIncidenciaSelected());
            }
        });

        view.getBtnClose().addActionListener(e -> volverDashboard());
    }

    private void listarIncidencias() {
        LogSystem.logFlow("Historial", "Listando incidencias ");
        incidenciaList = model.getIncidencias();

        TableModel tmodel = SwingUtil.getTableModelFromPojos(incidenciaList, TITULOS_TABLA_INCIDENCIAS_POJOS);
        JTable tabla = view.getTablaIncidencias();
        tabla.setModel(tmodel);
        mostrarTabla(tabla, TITULOS_TABLA_INCIDENCIAS_VISTA);
    }

    private void onIncidenciaSelected() {
        JTable tabla = view.getTablaIncidencias();
        SwingUtil.exceptionWrapper(() -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                // Limpiar historial si no hay selección
                view.getTablaHistorial().setModel(SwingUtil.getTableModelFromPojos(null, TITULOS_TABLA_HISTORIAL_POJOS));
                return;
            }

            int columnIndex = tabla.getColumnModel().getColumnIndex(COLUMN_ID);
            int idIncidencia = (int) tabla.getValueAt(row, columnIndex);

            cargarHistorial(idIncidencia);
        });
    }

    private void cargarHistorial(int idIncidencia) {
        LogSystem.logFlow("Historial", "Cargando historial para incidencia " + idIncidencia);
        List<HistoriaDTO> historial = model.getHistorialPorIncidencia(idIncidencia);

        TableModel tmodel = SwingUtil.getTableModelFromPojos(historial, TITULOS_TABLA_HISTORIAL_POJOS);
        JTable tabla = view.getTablaHistorial();
        tabla.setModel(tmodel);
        mostrarTabla(tabla, TITULOS_TABLA_HISTORIAL_VISTA);
    }
    
    private void mostrarTabla(JTable tabla, String[] titulos) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, titulos);
		SwingUtil.setDescriptionColumnStyle(tabla, tabla.getColumnModel().getColumnIndex(DESC_ID), 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollHist());
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}