package incidencias.controller;

import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import incidencias.model.AsignacionIncidenciasModel;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TecnicoDTO;
import incidencias.ui.AsignacionIncidenciasView;
import incidencias.ui.DashboardView;
import util.Util;
import util.swing.SwingUtil;
import util.swing.TableCellRendererAsignacion;

public class AsignacionIncidenciasController {
    
	private AsignacionIncidenciasView view;
    private AsignacionIncidenciasModel model;
	private DashboardView viewDsb;
	private int idUsuario;
	
	private static final String[] TITULOS_TABLA_INCIDENCIAS = {"ID", "Tipo", "Descripción", "Fecha/Hora", "Estado"};
	private static final String[] TITULOS_TABLA_INCIDENCIAS_POJOS = new String[]{"id", "tipo", "descripcion", "fechaCambio", "estado"};
	
	private static final String[] TITULOS_TABLA_TECNICOS = {"ID", "Nombre", "Apellidos", "Especialidad", "Incidencias asignadas"};
	private static final String[] TITULOS_TABLA_TECNICOS_POJOS = new String[]{"id", "nombre", "apellidos", "tipo", "numAsignadas"};
	
	private static final int MAX_INCIDENCIAS_ASIGNADAS = 3;
	
	// Indices tabla incidencias
	private static final int COLUMNA_TIPO = 1;
	private static final int COLUMNA_DESCRIPCION = 2;
	
	// Indices tabla tecnicos
	private static final int COLUMNA_ID = 0;
	private static final int COLUMNA_NOMBRE = 1;
	private static final int COLUMNA_APELLIDOS = 2;
	private static final int COLUMNA_ASIGNADAS = 4;
	
	//Mensajes de dialogos en pantalla
	private static final String CONFIRMACION = "¿Desea asignar la incidencia %d a estos técnicos?:";
	private static final String EXITO = "La incidencia %d se asignó correctamente a los técnicos:";
	
    public AsignacionIncidenciasController(AsignacionIncidenciasView l, AsignacionIncidenciasModel m, int id, DashboardView viewDsb) {
        this.view = l;
        this.model = m;
        this.idUsuario = id;
        this.viewDsb = viewDsb;
        this.initView();
        this.initController();
    }
    
    public void initView() {
    	
    	SwingUtil.exceptionWrapper(() -> listarIncidenciasValidadas());
    	
    	view.setVisible(true);
    }

    public void initController() {
    	view.getTablaIncidencias().getSelectionModel().addListSelectionListener(e -> {
    	    if (!e.getValueIsAdjusting()) {
    	        int fila = view.getTablaIncidencias().getSelectedRow();
    	        if (fila != -1) {
    	        	SwingUtil.exceptionWrapper(() -> listarTecnicos());
    	        }
    	    }
    	});
    	view.getBtnAsignar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> asignarIncidencia()));    	
    	SwingUtil.volverDashboard(view.getBtnClose(), view, viewDsb);
    }

    private void listarIncidenciasValidadas() {
    	List<IncidenciaDTO> incidencias = model.getListaIncidenciasValidadas();
		TableModel tmodel = SwingUtil.getTableModelFromPojos(incidencias, TITULOS_TABLA_INCIDENCIAS_POJOS);
		JTable tabla = view.getTablaIncidencias();
		tabla.setModel(tmodel);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		SwingUtil.setTableStyle(tabla, centerRenderer, TITULOS_TABLA_INCIDENCIAS);
		SwingUtil.setDescriptionColumnStyle(tabla, COLUMNA_DESCRIPCION, 250);
		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
    }
    
    private void listarTecnicos() {
    	List<TecnicoDTO> tecnicos = model.getListaTecnicos(SwingUtil.getSelectedValue(view.getTablaIncidencias(), COLUMNA_TIPO));
		TableModel tmodel = SwingUtil.getTableModelFromPojos(tecnicos, TITULOS_TABLA_TECNICOS_POJOS);
		JTable tabla = view.getTablaTecnicos();
		tabla.setModel(tmodel);
		TableCellRendererAsignacion renderer = new TableCellRendererAsignacion(MAX_INCIDENCIAS_ASIGNADAS, COLUMNA_ASIGNADAS); 
		SwingUtil.setTableStyle(tabla, renderer, TITULOS_TABLA_TECNICOS);
		
		// Para mostrar en gris las filas de tecnicos que ya no puedan ser asignados a mas incidencias
		tabla.setSelectionModel(new DefaultListSelectionModel() {
		    private boolean esFilaBloqueada(int row) {
		    	return (int)tmodel.getValueAt(row, COLUMNA_ASIGNADAS) >= MAX_INCIDENCIAS_ASIGNADAS;
		    }
		    @Override
		    public void setSelectionInterval(int index0, int index1) {
		        if (!esFilaBloqueada(index0)) {
		            super.setSelectionInterval(index0, index1);
		        }
		    }
		    @Override
		    public void addSelectionInterval(int index0, int index1) {
		        if (!esFilaBloqueada(index0)) {
		            super.addSelectionInterval(index0, index1);
		        }
		    }
		});
		
		SwingUtil.autoAdjustColumns(tabla, view.getScrollIncidencias());
    }
    
    private void asignarIncidencia() {
    	String idIncidencia = SwingUtil.getSelectedKey(view.getTablaIncidencias());
    	int[] idFilasTecnicos = view.getTablaTecnicos().getSelectedRows();
    	Util.validateCondition( !idIncidencia.equals("") && !(idFilasTecnicos.length==0) ,
								"Debe seleccionar una incidencia y al menos un técnico"  );
    	
    	int idIn  = Integer.parseInt(idIncidencia);
    	int[] idTecnicos = new int[idFilasTecnicos.length];
    	
    	String mensajeConfirmacion = String.format(CONFIRMACION, idIn);;
    	
    	int i = 0;
    	StringBuilder formatter1 = new StringBuilder(mensajeConfirmacion);
    	for(int id : idFilasTecnicos) {    		
    		int idTec = (int)view.getTablaTecnicos().getValueAt(id, COLUMNA_ID);
    		idTecnicos[i++] = idTec;
    		String nombreTec = view.getTablaTecnicos().getValueAt(id, COLUMNA_NOMBRE).toString();
    		String apellidosTec = view.getTablaTecnicos().getValueAt(id, COLUMNA_APELLIDOS).toString();
    		
    		formatter1.append("\n").append(String.format("%s %s (ID = %d)", nombreTec, apellidosTec, idTec));
    	}
    	
    	if(SwingUtil.showQuestionMessage(view, formatter1.toString())) {
    		if(!model.asignarIncidencia(idIn, idTecnicos, idUsuario)) {
    			SwingUtil.showErrorMessage(view, "Ha ocurrido un error al asignar la incidencia");
    		}
    		JTable tablaIncidencias = view.getTablaIncidencias();
    		DefaultTableModel modelTabla = (DefaultTableModel) tablaIncidencias.getModel();
    		modelTabla.removeRow(tablaIncidencias.getSelectedRow());
    		view.getTablaTecnicos().setModel(new DefaultTableModel());
    		
    		String mensajeExito = String.format(EXITO, idIn);
    		StringBuilder formatter2 = new StringBuilder(mensajeExito);
    		formatter2.append(" ").append(formatter1.toString().split(":")[1]);
    		SwingUtil.showSuccessMessage(view, formatter2.toString());
    	}
    }
}
