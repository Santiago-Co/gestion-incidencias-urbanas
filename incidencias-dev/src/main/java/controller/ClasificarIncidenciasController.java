package controller;

import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import dto.IncidenciaDTO;
import model.ClasificarIncidenciasModel;
import util.Util;
import view.ClasificarIncidenciasView;

public class ClasificarIncidenciasController {
    
	private ClasificarIncidenciasView view;
    private ClasificarIncidenciasModel model;
	
    private static final String[] TITULOS_TABLA_INCIDENCIAS_DTO = {"id", "tipo", "descripcion", "fechaRegistro"};
	private static final String[] TITULOS_VISUALES = {"ID", "Tipo", "Descripción", "Fecha/Hora"};
	private static final String[] OPCIONES_INCIDENCIAS= {"Alumbrado", "Limpieza", "Mobiliario urbano", 
														"Zonas verdes", "Señalización y calzada", "Otro"};
	private static final String MENSAJE_EXITO = "Incidencia validada correctamente";
	private static final String MENSAJE_FALLO = "Debe seleccionar una incidencia para validarla"; 
	
    public ClasificarIncidenciasController(ClasificarIncidenciasView view, ClasificarIncidenciasModel model) {
        this.view = view;
        this.model = model;
        this.initController();
        this.initView();
        
    }
    
    public void initView() {
    	listarIncidencias();
    	view.setVisible(true);
    }

    public void initController() {  
    	view.getBtnValidar().addActionListener(e -> validarIncidencia());
    	
    	Util.addCloseAction(view.getBtnClose(), "ClasificarIncidencias");
    }
    
    private void listarIncidencias() {
 
        Util.exceptionWrapper(() -> {
            
            List<IncidenciaDTO> lista = model.getListaIncidencias();
            
            TableModel modeloNuevo = Util.getTableModelFromPojos(lista, TITULOS_TABLA_INCIDENCIAS_DTO);

            view.getTablaIncidencias().setModel(modeloNuevo);

            if (modeloNuevo instanceof DefaultTableModel) {
                ((DefaultTableModel) modeloNuevo).setColumnIdentifiers(TITULOS_VISUALES);
            }
            
            JComboBox<String> combo = new JComboBox<>(OPCIONES_INCIDENCIAS);
            TableColumn columnaTipo = view.getTablaIncidencias().getColumnModel().getColumn(1);
            columnaTipo.setCellEditor(new DefaultCellEditor(combo));       
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
}