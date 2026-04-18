package incidencias.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import incidencias.model.CrearPresupuestosModel;
import incidencias.ui.CrearPresupuestosView;
import incidencias.ui.DashboardView;
import util.Util;
import util.swing.SwingUtil;

public class CrearPresupuestosController {

    private CrearPresupuestosView view;
    private CrearPresupuestosModel model;
    private DashboardView viewDsb;

    public CrearPresupuestosController(
            CrearPresupuestosView view,
            CrearPresupuestosModel model,
            DashboardView viewDsb) {

        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;

        initView();
        initController();
    }

    private void initView() {
        view.setVisible(true);
    }

    private void initController() {
        
        SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);

        // Eventos
        view.getCbFiltroTipo().addActionListener(e -> actualizarTarjetaPresupuesto());
        
        view.getBtnNuevoPresupuesto().addActionListener(e -> 
            view.abrirModalLimpio(view.getCbFiltroTipo().getSelectedIndex())
        );

        view.getBtnCloseModal().addActionListener(e -> view.cerrarModal());
        view.getBtnCancelarModal().addActionListener(e -> view.cerrarModal());

        view.getBtnGenerar().addActionListener(
            e -> SwingUtil.exceptionWrapper(() -> guardarPresupuesto())
        );
    }

    private void actualizarTarjetaPresupuesto() {

        int index = view.getCbFiltroTipo().getSelectedIndex();

        if (index < 0) {
            view.ocultarTarjeta(); // Delegado a la vista
            return;
        }

        int idTipo = index + 1;
        Object[] datos = model.obtenerPresupuestoActivo(idTipo);

        if (datos != null) {
            // Extraer datos matemáticos
            double importe = (double) datos[0];
            String concepto = (String) datos[1];
            String vigencia = (String) datos[2];
            double consumido = (double) datos[3];
            
            double disponible = importe - consumido;
            int porcentaje = (importe > 0) ? (int) ((consumido / importe) * 100) : 0;

            // Delegamos todo el pintado (textos y colores) a la vista
            view.mostrarTarjetaActiva(concepto, vigencia, importe, consumido, disponible, porcentaje);

        } else {
            // Delegamos el pintado del estado inactivo a la vista
            view.mostrarTarjetaInactiva();
        }
    }

    private void guardarPresupuesto() {

        int index = view.getCbModalTipo().getSelectedIndex();
        int idTipo = index + 1;

        String concepto = view.getTxtConcepto().getText();

        double importe;
        try {
            importe = Double.parseDouble(
                view.getTxtImporte().getText().replace(",", ".")
            );
        } catch (NumberFormatException ex) {
            throw new RuntimeException("El importe no es válido.");
        }

        String vigencia = determinarVigencia();

        model.registrarPresupuesto(idTipo, importe, concepto, vigencia);

        view.cerrarModal(); // Delegado a la vista

        view.getCbFiltroTipo().setSelectedIndex(index);
        actualizarTarjetaPresupuesto();

        SwingUtil.showSuccessMessage(
            view,
            "Partida presupuestaria generada con éxito."
        );
    }

    private String determinarVigencia() {

        if (view.getRbAnual().isSelected()) {
            return "Año " + Calendar.getInstance().get(Calendar.YEAR);
        }

        Object ini = view.getDateInicio().getModel().getValue();
        Object fin = view.getDateFin().getModel().getValue();

        Util.validateCondition(ini != null && fin != null,
            "Debe seleccionar ambas fechas.");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return sdf.format((Date) ini) + " - " + sdf.format((Date) fin);
    }
}