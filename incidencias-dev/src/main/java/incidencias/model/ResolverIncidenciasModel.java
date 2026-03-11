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
	
	public List<IncidenciaDTO> getIncidenciasTecnico(int id) {
		List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS, id);

		return incidenciasTecnico;
	}

	public void resolverIncidencia(int idIncidencia, int idTecnico, Integer horasReales, String descripcionPreviaTrabajo) {
        db.executeUpdate(SQL_NOTAS_INCIDENCIA, horasReales, descripcionPreviaTrabajo, idIncidencia);
	    
	    String comentario = "Resolución: horasReales=" + (horasReales == null ? "N/A" : horasReales)
	                       + (descripcionPreviaTrabajo == null || descripcionPreviaTrabajo.isEmpty() ? "" : " - " + descripcionPreviaTrabajo);
	    
	    db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, comentario);
	}

}
