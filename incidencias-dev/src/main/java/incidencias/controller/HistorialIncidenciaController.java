package incidencias.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import incidencias.model.HistorialIncidenciaModel;
import incidencias.model.DTOs.HistoriaDTO;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.HistorialIncidenciaView;
import util.LogSystem;
import util.swing.SwingUtil;

import javax.swing.JOptionPane;


public class HistorialIncidenciaController {

    private HistorialIncidenciaView view;
    private HistorialIncidenciaModel model;
    private int usuarioId;
    private DashboardView viewDsb;

    private List<IncidenciaDTO> incidenciaList;

    private static final String[] TITULOS_TABLA_INCIDENCIAS_POJOS = 
            new String[] { "id", "tipo", "descripcion", "fechaCambio", "estado" };

    private static final String[] TITULOS_TABLA_HISTORIAL_POJOS = 
            new String[] { "fechaCambio", "descripcion", "estado" };

    public HistorialIncidenciaController(HistorialIncidenciaView view, HistorialIncidenciaModel model, int usuarioId, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.usuarioId = usuarioId;
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

        // Botones
        view.getBtnClose().addActionListener(e -> volverDashboard());
    }

    private void listarIncidencias() {
        LogSystem.logFlow("Historial", "Listando incidencias para usuario " + usuarioId);
        incidenciaList = model.getIncidenciasUsuario(usuarioId);

        TableModel tmodel = SwingUtil.getTableModelFromPojos(incidenciaList, TITULOS_TABLA_INCIDENCIAS_POJOS);
        JTable tabla = view.getTablaIncidencias();
        tabla.setModel(tmodel);

        SwingUtil.autoAdjustColumns(tabla);
    }

    private void onIncidenciaSelected() {
        JTable tabla = view.getTablaIncidencias();
        int row = tabla.getSelectedRow();
        if (row < 0) {
            // limpiar historial si no hay selección
            view.getTablaHistorial().setModel(new javax.swing.table.DefaultTableModel(TITULOS_TABLA_HISTORIAL_POJOS, 0));
            return;
        }

        Object idObj = tabla.getModel().getValueAt(row, 0);
        if (idObj == null) return;

        int idIncidencia;
        if (idObj instanceof Number) {
            idIncidencia = ((Number) idObj).intValue();
        } else {
            try {
                idIncidencia = Integer.parseInt(idObj.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Id de incidencia inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        cargarHistorial(idIncidencia);
    }

    private void cargarHistorial(int idIncidencia) {
        LogSystem.logFlow("Historial", "Cargando historial para incidencia " + idIncidencia);
        List<HistoriaDTO> historial = model.getHistorialPorIncidencia(idIncidencia);

        TableModel tmodel = SwingUtil.getTableModelFromPojos(historial, TITULOS_TABLA_HISTORIAL_POJOS);
        view.getTablaHistorial().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTablaHistorial());
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}