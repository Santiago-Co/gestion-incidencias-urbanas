package incidencias.controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import incidencias.model.InformesIncidenciasTecnicosModel;
import incidencias.model.DTOs.InformesIncidenciasTecnicosDTO;
import incidencias.ui.DashboardView;
import incidencias.ui.InformesIncidenciasTecnicosView;
import util.swing.SwingUtil;

public class InformesIncidenciasTecnicosController {
    
	private InformesIncidenciasTecnicosView view;
    private InformesIncidenciasTecnicosModel model;
	private DashboardView viewDsb;
	
	private static final String[] TITULOS_TABLA_INFORMES =
		{"ID", "Nombre", "Apellidos", "Incidencias asignadas", "Incidencias resueltas", "Horas totales empleadas"}; 
	
    private static final String[] TITULOS_TABLA_POJOS =
    	{"id", "nombre", "apellidos", "numAsignadas", "numResueltas", "horasReales"};
	
    public InformesIncidenciasTecnicosController(InformesIncidenciasTecnicosView view, InformesIncidenciasTecnicosModel model, DashboardView viewDsb) {
        this.view = view;
        this.model = model;
        this.viewDsb = viewDsb;
        
        initController();
        initView();
    }
    
    public void initView() {
    	generarInforme();
    	view.setVisible(true);
    }

    public void initController() {  
    	view.getBtnClose().addActionListener(e -> volverDashboard());    	
    }
    
    private void generarInforme() {
 
        SwingUtil.exceptionWrapper(() -> {
            
            List<InformesIncidenciasTecnicosDTO> lista = model.getInforme();
            
            TableModel modeloNuevo = SwingUtil.getTableModelFromPojos(lista, TITULOS_TABLA_POJOS);

            JTable tabla = view.getTablaIncidencias();
            tabla.setModel(modeloNuevo);
            
    		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INFORMES); 
            
    		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
        });
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
}