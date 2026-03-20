package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.LogSystem;
import util.database.Database;

public class ClasificarIncidenciasModel {
	    
    private Database db = new Database();
    
    /** Utilizada para los test de la historia
    private String SQL_LISTA_INCIDENCIAS_TODO = "select * from incidencia";
    */

    private static final String SQL_LISTA_INCIDENCIAS = "SELECT i.id, i.tipo, i.descripcion, h.fechaCambio, h.estado " +
    													"FROM incidencia i JOIN historia h ON h.idIncidencia = i.id " +
    													"WHERE h.estado = 'Nueva' " +
    													"AND h.id = (SELECT MIN(h1.id) FROM historia h1 WHERE h1.idIncidencia = i.id) " +
    													"AND h.id = (SELECT MAX(h2.id) FROM historia h2 WHERE h2.idIncidencia = i.id) " +
    													"ORDER BY h.fechaCambio";

    private static final String SQL_ACTUALIZAR_TIPO = "UPDATE incidencia SET tipo = ? WHERE id = ?";
    
    private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
		    										   "VALUES (?, NULL, ?, ?, ?)";
    
    private static final String VALIDAR_INCIDENCIA = "Validada";
    private static final String ELIMINAR_INCIDENCIA = "Rechazado";
    
    public List<IncidenciaDTO> getListaIncidencias() {
    	
    	LogSystem.logFlow("Consulta incidencias validadas", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTA_INCIDENCIAS);
        
    	if(!incidencias.isEmpty()) {
    		LogSystem.logFlow("Resultados", String.format("Fecha incidencia: %s", incidencias.get(0).getFechaCambio()));
    	}else {
    		LogSystem.logFlow("Resultados", "No quedan incidencias");
    	}
    	
    	return incidencias;
    }
        
    public void actualizarEstadoIncidenciaValidar(int idIncidencia, int idOperador, String nuevoTipo) {
    	
        db.executeUpdate(SQL_ACTUALIZAR_TIPO, nuevoTipo, idIncidencia);
        db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idOperador, VALIDAR_INCIDENCIA,
        				 String.format("Incidencia validada por el operador %d", idOperador));
    }
    
    public void actualizarEstadoIncidenciaEliminar(int idIncidencia, int idOperador, String nuevoTipo, String detalleEliminar) {
    	
        db.executeUpdate(SQL_ACTUALIZAR_TIPO, nuevoTipo, idIncidencia);
        db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idOperador, ELIMINAR_INCIDENCIA,
        		detalleEliminar);
    }
}