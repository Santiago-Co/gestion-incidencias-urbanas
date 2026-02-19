package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.Database;
import util.LogSystem;

public class ClasificarIncidenciasModel {
	    
    private Database db = new Database();
    
    /** Utilizada para los test de la historia
    private String SQL_LISTA_INCIDENCIAS_TODO = "select * from incidencia";
    */
    /**Si no se pone as fechaRegistro para coincidir {@IncidenciaDTO} al mapearlo no se podria conseguir su 
    getter y saldria con una hora null */
    private String SQL_LISTA_INCIDENCIAS = "select id, tipo, descripcion, fechaRegistro " 
    		+ "from incidencia where estado = 'Nueva' order by fechaRegistro";
    
    private String SQL_ACTUALIZAR_ESTADO = "UPDATE incidencia SET estado = ?, tipo = ? WHERE id = ?";
    private static final String NUEVO_ESTADO_INCIDENCIA = "Validada";
    
    public List<IncidenciaDTO> getListaIncidencias() {
    	
    	LogSystem.logFlow("Consulta incidencias", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTA_INCIDENCIAS);
    	
    	return incidencias;
    }
        
    public void actualizarEstadoIncidencia(int id, String nuevoTipo) {
        db.executeUpdate(SQL_ACTUALIZAR_ESTADO,NUEVO_ESTADO_INCIDENCIA, nuevoTipo, id);
    }
}