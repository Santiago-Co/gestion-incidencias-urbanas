package incidencias.controller;

import incidencias.model.CrearPresupuestosModel;
import incidencias.ui.CrearPresupuestosView;
import incidencias.ui.DashboardView;
import util.swing.SwingUtil;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearPresupuestosController {

    private final CrearPresupuestosView view;
    private final CrearPresupuestosModel model;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DashboardView viewDsb; 
    
    public CrearPresupuestosController(CrearPresupuestosView view, CrearPresupuestosModel model, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb; 
        initEvents();
        view.setVisible(true);
    }

    private void initEvents() {
        
    	SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);

        view.getCbFiltroTipo().addActionListener(e -> cargarDatosTarjeta());

        view.getBtnNuevoPresupuesto().addActionListener(e -> {
            int selectedIndex = view.getCbFiltroTipo().getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(view, "Seleccione un tipo de incidencia en el filtro primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            view.abrirModalLimpio(selectedIndex);
        });

        
        view.getBtnGenerar().addActionListener(e -> procesarNuevoPresupuesto());
    }

    private void cargarDatosTarjeta() {
        int selectedIndex = view.getCbFiltroTipo().getSelectedIndex();
        if (selectedIndex == -1) return;

        int idTipoIncidencia = selectedIndex + 1; 
        Object[] datos = model.obtenerPresupuestoActivo(idTipoIncidencia);

        view.getPnlTarjeta().setVisible(true);

        if (datos != null) {
            double importe = (double) datos[0];
            String concepto = (String) datos[1];
            String vigencia = (String) datos[2];
            double consumido = (double) datos[3];
            double disponible = importe - consumido;

            int porcentaje = (importe > 0) ? (int) ((consumido / importe) * 100) : 0;
            
            view.getLblTarjetaConcepto().setText(concepto);
            view.getLblTarjetaEstado().setText("• ACTIVO");
            view.getLblTarjetaEstado().setForeground(new Color(34, 197, 94)); // Verde
            view.getLblTarjetaVigencia().setText("Vigencia: " + vigencia);
            
            view.getProgressBarFondos().setValue(porcentaje);
            view.getProgressBarFondos().setValue(porcentaje);
            view.getProgressBarFondos().setForeground(new Color(239, 68, 68)); 
            view.getProgressBarFondos().setBackground(new Color(34, 197, 94));
            view.getLblValTotal().setText(String.format("%.2f €", importe));
            view.getLblValConsumido().setText(String.format("%.2f €", consumido));
            
            view.getLblValDisponible().setText(String.format("%.2f €", disponible));
            
            if (disponible > 0) {
                view.getLblValDisponible().setForeground(new Color(34, 197, 94)); // Verde
            } else {
                view.getLblValDisponible().setForeground(new Color(239, 68, 68)); // Rojo
                view.getProgressBarFondos().setValue(0);
                
                view.getProgressBarFondos().setForeground(new Color(156, 163, 175)); 
                view.getProgressBarFondos().setBackground(new Color(229, 231, 235));
            }
            
        } else {
            view.getLblTarjetaConcepto().setText("No hay datos disponibles");
            view.getLblTarjetaEstado().setText("• INACTIVO");
            view.getLblTarjetaEstado().setForeground(new Color(156, 163, 175)); // Gris
            view.getLblTarjetaVigencia().setText("Vigencia: -");
            
            view.getProgressBarFondos().setValue(0);
            view.getLblValTotal().setText("0.00 €");
            view.getLblValConsumido().setText("0.00 €");
            
            view.getLblValDisponible().setText("0.00 €");
            view.getLblValDisponible().setForeground(new Color(239, 68, 68)); // Rojo por defecto si no hay fondos
        }
    }

    private void procesarNuevoPresupuesto() {
        try {
          
            int selectedIndex = view.getCbModalTipo().getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(view, "Seleccione un tipo de incidencia.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int idTipoIncidencia = selectedIndex + 1;
            String concepto = view.getTxtConcepto().getText();
            double importe = Double.parseDouble(view.getTxtImporte().getText().replace(",", "."));
            
            
            Date fechaInicio = (Date) view.getDateInicio().getModel().getValue();
            Date fechaFin = (Date) view.getDateFin().getModel().getValue();
            
            if (fechaInicio == null || fechaFin == null || fechaInicio.after(fechaFin)) {
                JOptionPane.showMessageDialog(view, "Verifique que las fechas sean válidas y el inicio sea anterior al fin.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String vigencia = sdf.format(fechaInicio) + " - " + sdf.format(fechaFin);

            
            model.registrarPresupuesto(idTipoIncidencia, importe, concepto, vigencia);
            
            JOptionPane.showMessageDialog(view, "Presupuesto registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.cerrarModal();
            
            
            if (view.getCbFiltroTipo().getSelectedIndex() == selectedIndex) {
                cargarDatosTarjeta();
            } else {
                view.getCbFiltroTipo().setSelectedIndex(selectedIndex);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "El importe debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error al registrar", JOptionPane.ERROR_MESSAGE);
        }
    }
}