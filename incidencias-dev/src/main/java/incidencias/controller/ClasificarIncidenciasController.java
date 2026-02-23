package incidencias.controller;

import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import incidencias.model.ClasificarIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.ClasificarIncidenciasView;
import incidencias.ui.DashboardView;
import util.Util;
import util.swing.SwingUtil;

public class ClasificarIncidenciasController {
    
	private ClasificarIncidenciasView view;
    private ClasificarIncidenciasModel model;
	private DashboardView viewDsb;
	
    private static final String[] TITULOS_TABLA_INCIDENCIAS_DTO = {"id", "tipo", "descripcion", "fechaRegistro"};
	private static final String[] TITULOS_VISUALES = {"ID", "Tipo", "Descripción", "Fecha/Hora"};
	private static final String[] OPCIONES_INCIDENCIAS= {"Alumbrado", "Limpieza", "Mobiliario urbano", 
														"Zonas verdes", "Señalización y calzada", "Otro"};
	private static final String MENSAJE_EXITO = "Incidencia validada correctamente";
	private static final String MENSAJE_FALLO = "Debe seleccionar una incidencia para validarla"; 
	
    public ClasificarIncidenciasController(ClasificarIncidenciasView view, ClasificarIncidenciasModel model, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb; 
        this.initController();
        this.initView();
        
    }
    
    public void initView() {
    	listarIncidencias();
    	view.setVisible(true);
    }

    public void initController() {  
    	view.getBtnValidar().addActionListener(e -> validarIncidencia());
    	view.getBtnClose().addActionListener(e -> volverDashboard());
    	
    }
    
    private void listarIncidencias() {
 
        Util.exceptionWrapper(() -> {
            
            List<IncidenciaDTO> lista = model.getListaIncidencias();
            
            TableModel modeloNuevo = Util.getTableModelFromPojos(lista, TITULOS_TABLA_INCIDENCIAS_DTO);

            JTable tabla = view.getTablaIncidencias();
            tabla.setModel(modeloNuevo);
            
    		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_VISUALES);
    		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
            
            JComboBox<String> combo = new JComboBox<>(OPCIONES_INCIDENCIAS);
            SwingUtil.setComboBoxColumnStyle(tabla, combo, 1, 150);   
            
    		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
        });
    }

    
    private void validarIncidencia() {
        String idStr = Util.getSelectedKey(view.getTablaIncidencias());
        String tipoIncidencia = Util.getSelectedValue(view.getTablaIncidencias(),1);
        
        if (idStr.isEmpty()) {
        	view.mostrarError(MENSAJE_FALLO);
            return;
        }

        if(!view.confirmacionIncidencia(idStr)) { return; }
        
        Util.exceptionWrapper(() -> {
            int id = Integer.parseInt(idStr);
            
            model.actualizarEstadoIncidencia(id, tipoIncidencia);
            this.view.mostrarExito(MENSAJE_EXITO);
            
            listarIncidencias();
        });
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}