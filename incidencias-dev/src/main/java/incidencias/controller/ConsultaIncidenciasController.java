package incidencias.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import incidencias.model.ConsultaIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.ui.ConsultaIncidenciasView;
import incidencias.ui.DashboardView;
import util.swing.SwingUtil;

public class ConsultaIncidenciasController {
    
	private ConsultaIncidenciasView view;
    private ConsultaIncidenciasModel model;
	private DashboardView viewDsb;
	private int idUsuario;
	
	private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Fecha/Hora", "Localización", "Estado" };
	private static final String[] TITULOS_TABLA_POJOS = new String[]{"id", "tipo", "descripcion", "fechaCambio", "localizacion", "estado"};
			
	
    public ConsultaIncidenciasController(ConsultaIncidenciasView l, ConsultaIncidenciasModel m, int id, DashboardView viewDsb) {
        this.view = l;
        this.model = m;
        this.idUsuario = id;
        this.viewDsb = viewDsb;
        
        initView();
        initController();
    }
    
    public void initView() {
    	
    	SwingUtil.exceptionWrapper(() -> listarIncidencias());
    	
    	view.setVisible(true);
    }

    public void initController() {    
    	view.getListaFiltros().addActionListener(e -> {
    	    String filtro = (String) view.getListaFiltros().getSelectedItem();
    		if(filtro.equals("Todos los estados")) SwingUtil.exceptionWrapper(() -> listarIncidencias());
    		else SwingUtil.exceptionWrapper(() -> filtrarIncidencias(filtro));
    	});
    	
    	view.getBtnClose().addActionListener(e -> SwingUtil.exceptionWrapper(() -> volverDashboard()));
    }

    private void listarIncidencias() {
    	List<IncidenciaDTO> incidencias = model.getListaIncidencias(idUsuario);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		mostrarTabla(tabla);
    }
    
    private void filtrarIncidencias(String estado) {
    	
    	List<IncidenciaDTO> incidencias = model.filtraListaIncidencias(idUsuario, estado);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		mostrarTabla(tabla);
    }
    
    private void mostrarTabla(JTable tabla) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, 2, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
    }
    
    private void volverDashboard() {
    	view.dispose(); 
        viewDsb.setVisible(true);
    }
    
}
