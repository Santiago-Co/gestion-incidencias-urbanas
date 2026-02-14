package incidencias.controller;

import java.util.List;

import javax.swing.table.TableModel;

import incidencias.model.ConsultaIncidenciasModel;
import incidencias.model.IncidenciaDTO;
import incidencias.ui.ConsultaIncidenciasView;
import util.SwingUtil;

public class ConsultaIncidenciasController {
    
	private ConsultaIncidenciasView view;
    private ConsultaIncidenciasModel model;
	
	private int idUsuario;

    public ConsultaIncidenciasController(ConsultaIncidenciasView l, ConsultaIncidenciasModel m, int id) {
        this.view = l;
        this.model = m;
        this.idUsuario = id;
        this.initView();
    }
    
    public void initView() {
    	
    	listarIncidencias();
    	
    	view.setVisible(true);
    }

    public void initController() {    
    	view.getListaFiltros().addActionListener(e -> {
    	    String filtro = (String) view.getListaFiltros().getSelectedItem();
    		SwingUtil.exceptionWrapper(() -> filtrarIncidencias(filtro));
    	});
    }

    private void listarIncidencias() {
    	List<IncidenciaDTO> incidencias = model.getListaIncidencias(idUsuario);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, new String[]{"id", "tipo", "descripcion", "fechaHora", "estado"});
		view.getTablaIncidencias().setModel(tmodel);
		SwingUtil.autoAdjustColumns(view.getTablaIncidencias());
    }
    
    private void filtrarIncidencias(String estado) {
    	
    	List<IncidenciaDTO> incidencias = model.filtraListaIncidencias(idUsuario, estado);
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, new String[]{"id", "tipo", "descripcion", "fechaHora", "estado"});
		view.getTablaIncidencias().setModel(tmodel);
		SwingUtil.autoAdjustColumns(view.getTablaIncidencias());
    }
    
}
