package incidencias.controller;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import incidencias.model.ClasificarIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.ClasificarIncidenciasView;
import incidencias.ui.DashboardView;
import util.swing.SwingUtil;

public class ClasificarIncidenciasController {
    
	private ClasificarIncidenciasView view;
    private ClasificarIncidenciasModel model;
	private DashboardView viewDsb;
	private int idUsuario;
	
    private static final String[] TITULOS_TABLA_INCIDENCIAS_DTO = {"id", "tipo", "descripcion", "fechaCambio", "estado"};
	private static final String[] TITULOS_VISUALES = {"ID", "Tipo", "Descripción", "Fecha/Hora", "Estado"};
	
	private static final String[] OPCIONES_INCIDENCIAS= {"Alumbrado", "Limpieza", "Mobiliario urbano", 
														"Zonas verdes", "Señalización y calzada", "Otro"};
	
	private static final String MENSAJE_EXITO_VALIDAR = "Incidencia validada correctamente";
	private static final String MENSAJE_EXITO_ELIMINAR = "Incidencia eliminada correctamente";
	private static final String MENSAJE_FALLO_VALIDACION = "Debe seleccionar una incidencia para validarla"; 
	private static final String MENSAJE_FALLO_ELIMINAR = "Debe seleccionar una incidencia para eliminarla"; 
	
	
    public ClasificarIncidenciasController(ClasificarIncidenciasView view, ClasificarIncidenciasModel model, int id, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;
        this.idUsuario = id;
        
        initController();
        initView();
    }
    
    public void initView() {
    	listarIncidencias();
    	view.setVisible(true);
    }

    public void initController() {  
    	view.getBtnValidar().addActionListener(e -> validarIncidencia());
    	view.getBtnClose().addActionListener(e -> volverDashboard());
    	view.getBtnEliminar().addActionListener(e -> eliminarIncidencia());
    	
    }
    
    private void listarIncidencias() {
 
        SwingUtil.exceptionWrapper(() -> {
            
            List<IncidenciaDTO> lista = model.getListaIncidencias();
            
            TableModel modeloNuevo = SwingUtil.getTableModelFromPojos(lista, TITULOS_TABLA_INCIDENCIAS_DTO);

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
        String idStr = SwingUtil.getSelectedKey(view.getTablaIncidencias());
        String tipoIncidencia = SwingUtil.getSelectedValue(view.getTablaIncidencias(),1);
        System.out.println(tipoIncidencia);
        if (idStr.isEmpty()) {
        	SwingUtil.showErrorMessage(view, MENSAJE_FALLO_VALIDACION);
            return;
        }

        String mensajeConfirmacion = String.format("¿Desea validar la incidencia: %s?", idStr);
        
        if(!SwingUtil.showQuestionMessage(view, mensajeConfirmacion)) { return; }
        
        SwingUtil.exceptionWrapper(() -> {
            int id = Integer.parseInt(idStr);
            
            model.actualizarEstadoIncidenciaValidar(id, idUsuario, tipoIncidencia);
        	SwingUtil.showSuccessMessage(view, MENSAJE_EXITO_VALIDAR);
            
            listarIncidencias();
        });
    }
    
    private void eliminarIncidencia() {
    	String idStr = SwingUtil.getSelectedKey(view.getTablaIncidencias());
    	String tipoIncidencia = SwingUtil.getSelectedValue(view.getTablaIncidencias(),1);
    	
    	if (idStr.isEmpty()) {
        	SwingUtil.showErrorMessage(view, MENSAJE_FALLO_ELIMINAR);
            return;
        }
    	
    	
        String mensajeConfirmacion = String.format("¿Desea eliminar la incidencia: %s?", idStr);
        
        String detalleEliminar = SwingUtil.showInputMessage(view, "¿Por qué quiere eliminar la incidencia: %s?");
        
        if(!SwingUtil.showQuestionMessage(view, mensajeConfirmacion)) { return; }
    	
        SwingUtil.exceptionWrapper(() -> {
            int id = Integer.parseInt(idStr);
            
            model.actualizarEstadoIncidenciaEliminar(id, idUsuario, tipoIncidencia, detalleEliminar);
        	SwingUtil.showSuccessMessage(view, MENSAJE_EXITO_ELIMINAR);
            
            listarIncidencias();
        });
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}