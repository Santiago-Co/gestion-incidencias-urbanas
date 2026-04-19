package incidencias.controller;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import incidencias.model.ExportarHistorialModel;
import incidencias.model.DTOs.IncidenciaExportDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.ExportarHistorialView;
import util.Util;
import util.swing.SwingUtil;

public class ExportarHistorialController {

    private ExportarHistorialView view;
    private ExportarHistorialModel model;
    private DashboardView viewDsb;
    
    public ExportarHistorialController(ExportarHistorialView view, ExportarHistorialModel model, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;
        this.initController();
    }

    private void initController() {
        view.getBtnDescargar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> procesarDescargaJson()));
        SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
        SwingUtil.volverDashboard(view.getBtnCancelar(), view, viewDsb);
        view.setVisible(true);
    }

    private void procesarDescargaJson() {
        String fechaInicio = obtenerFechaFormateada(view.getDatePickerInicio().getModel().getValue());
        String fechaFin = obtenerFechaFormateada(view.getDatePickerFin().getModel().getValue());
        
        List<String> tiposSeleccionados = view.getCbTipoIncidencia().getSelectedItems();
        List<String> zonasSeleccionadas = view.getCbZona().getSelectedItems();

        List<IncidenciaExportDTO> datos = model.obtenerDatosExportacion(fechaInicio, fechaFin, tiposSeleccionados, zonasSeleccionadas);

        Util.validateCondition(datos != null && !datos.isEmpty(), "No se encontraron registros con los filtros aplicados.");

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Historial JSON");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON (*.json)", "json"));

        int seleccion = fileChooser.showSaveDialog(view);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoDestino = fileChooser.getSelectedFile();
            String ruta = archivoDestino.getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".json")) {
                ruta += ".json";
            }

            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(generarJson(datos));
                SwingUtil.showSuccessMessage(view, "El archivo JSON se ha exportado correctamente.");
            } catch (Exception ex) {
                throw new RuntimeException("Error al guardar el archivo JSON: " + ex.getMessage(), ex);
            }
        }
    }

    private String obtenerFechaFormateada(Object valorDate) {
        if (valorDate instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format((Date) valorDate);
        }
        return null;
    }

    private String generarJson(List<IncidenciaExportDTO> datos) {
        StringBuilder jsonBuilder = new StringBuilder("[\n");
        for (int i = 0; i < datos.size(); i++) {
            jsonBuilder.append(datos.get(i).toJson());
            if (i < datos.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }
}