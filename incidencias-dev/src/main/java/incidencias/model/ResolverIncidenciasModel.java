package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class ResolverIncidenciasModel {

	private Database db = new Database();
	
	private static final String SQL_LISTAR_INCIDENCIAS = "SELECT i.id, " +
				 "ti.nombre AS tipo, " +
				 "i.descripcion, localizacion, " +
				 "hCreacion.fechaCambio " +
				 "FROM incidencia i " +
				 "JOIN tipoIncidencia ti ON i.tipo = ti.id " +
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
				 "JOIN equipoTecnicos et ON et.idHistoria = hActual.id " +
				 "WHERE et.idTecnico = ? AND hActual.estado = 'En proceso' " +
				 "ORDER BY hCreacion.fechaCambio";
	
    private static final String SQL_NOTAS_INCIDENCIA = "UPDATE incidencia SET horasReales = ?, descripcionFinalTrabajo = ? WHERE id = ?";
	
    private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) " +
    		 										   "VALUES (?, NULL, ?, ?)";
    
    private static final String SQL_GET_LAST_HISTORIA_ID = "SELECT MAX(id) FROM historia WHERE idIncidencia = ?";
    
    private static final String SQL_INSERT_EQUIPO_TECNICO = "INSERT INTO equipoTecnicos (idTecnico, idHistoria) " +
    													    "VALUES (?, ?)";
	
	public List<IncidenciaDTO> getIncidenciasTecnico(int id) {
		List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS, id);
		return incidenciasTecnico;
	}

	public void resolverIncidencia(int idIncidencia, int idTecnico, Integer horasReales, String descripcionFinalTrabajo) {
        db.executeUpdate(SQL_NOTAS_INCIDENCIA, horasReales, descripcionFinalTrabajo, idIncidencia);
	    
	    String comentario = "Resolución: horasReales=" + (horasReales == null ? "N/A" : horasReales)
	                       + (descripcionFinalTrabajo == null || descripcionFinalTrabajo.isEmpty() ? "" : " - " + descripcionFinalTrabajo);
	    
	    db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, "Resuelta", comentario);
        int idHistoria = (int) db.executeQueryArray(SQL_GET_LAST_HISTORIA_ID, idIncidencia).get(0)[0];
        db.executeUpdate(SQL_INSERT_EQUIPO_TECNICO, idTecnico, idHistoria);	}
	
	public void actualizarEstadoIncidenciaEliminar(int idIncidencia, int idTecnico, String detalleEliminar) {
		db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, "Rechazado", detalleEliminar);
        int idHistoria = (int) db.executeQueryArray(SQL_GET_LAST_HISTORIA_ID, idIncidencia).get(0)[0];
        db.executeUpdate(SQL_INSERT_EQUIPO_TECNICO, idTecnico, idHistoria);    }

}
