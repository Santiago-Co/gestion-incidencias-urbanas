package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class ResolverIncidenciasModel {

	private Database db = new Database();
	
	private static final String SQL_LISTAR_INCIDENCIAS = "SELECT i.id, tipo, i.descripcion, localizacion, " +
				 "hCreacion.fechaCambio " +
				 "FROM incidencia i " +
				 "JOIN historia hCreacion ON hCreacion.id = " +
				 "( " +
				 "   SELECT MIN(h1.id) FROM historia h1 " +
				 "   WHERE h1.idIncidencia = i.id " +
				 ") " +
				 "JOIN historia hActual ON hActual.id = " +
				 "( " +
				 "   SELECT MAX(h2.id) FROM historia h2 " +
				 "   WHERE h2.idIncidencia = i.id " +
				 ") " +
				 "WHERE hActual.idTecnico = ? AND hActual.estado = 'En proceso' " +
				 "ORDER BY hCreacion.fechaCambio";
	
    private static final String SQL_NOTAS_INCIDENCIA = "UPDATE incidencia SET horasReales = ?, descripcionFinalTrabajo = ? WHERE id = ?";
	
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
			   										   "VALUES (?, ?, NULL, 'Resuelta', ?)";
	
	// Recoge el valor del id del último operador que trabajo en la indidencia 
	private static final String SQL_OBTENER_OPERADOR = "SELECT idOperador "+
															"FROM historia " + 
															"WHERE idIncidencia = ? "+
															"AND idOperador IS NOT NULL " +
															"ORDER BY fechaCambio DESC " +
															"LIMIT 1";
	
	private static final String SQL_INSERT_HISTORIAL_ELIMINAR = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
				   "VALUES (?, ?, ?, ?, ?)";
	private static final String ELIMINAR_INCIDENCIA = "Rechazado";
	
	public List<IncidenciaDTO> getIncidenciasTecnico(int id) {
		List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS, id);

		return incidenciasTecnico;
	}

	public void resolverIncidencia(int idIncidencia, int idTecnico, Integer horasReales, String descripcionFinalTrabajo) {
        db.executeUpdate(SQL_NOTAS_INCIDENCIA, horasReales, descripcionFinalTrabajo, idIncidencia);
	    
	    String comentario = "Resolución: horasReales=" + (horasReales == null ? "N/A" : horasReales)
	                       + (descripcionFinalTrabajo == null || descripcionFinalTrabajo.isEmpty() ? "" : " - " + descripcionFinalTrabajo);
	    
	    db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, comentario);
	}
	
	public void actualizarEstadoIncidenciaEliminar(int idIncidencia, int idTecnico, String nuevoTipo, String detalleEliminar) {
    	
        List<Object[]> result = db.executeQueryArray(SQL_OBTENER_OPERADOR, idIncidencia);
        
        if (result.isEmpty()) {
            // Puede que genere errores 
        }

        Integer idOperador = (Integer) result.get(0)[0];
        db.executeUpdate(SQL_INSERT_HISTORIAL_ELIMINAR, idIncidencia, idTecnico, idOperador, ELIMINAR_INCIDENCIA,
        		detalleEliminar);
    }

}
