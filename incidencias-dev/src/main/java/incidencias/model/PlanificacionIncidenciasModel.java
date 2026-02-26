package incidencias.model;

import java.util.*;

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class PlanificacionIncidenciasModel {
	
	private Database db = new Database();
	
	private static final String SQL_LISTAR_INCIDENCIAS = "SELECT i.id, tipo, i.descripcion, localizacion, " +
			   											 "hCreacion.fechaCambio AS fechaCreacion " +
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
			   											 "WHERE hActual.idTecnico = ? AND hActual.estado = 'Asignada' " +
			   											 "ORDER BY hCreacion.fechaCambio";
	
	private static final String SQL_NOTAS_INCIDENCIA = "UPDATE incidencia SET horasPrevistas = ?, descripcionTrabajo = ? WHERE id = ?";
	
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
			   										   "VALUES (?, ?, NULL, 'En proceso', ?)";
	
	/*
	 * Obtiene las incidencias asociadas a un tecnico
	 */
	public List<IncidenciaDTO> getIncidenciasTecnico(int id) {
		
        List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS, id);

		return incidenciasTecnico;
	}
	
	public void planificarIncidencia(int idIncidencia, int idTecnico, Integer horasPrevistas, String descripcionTrabajo) {
	    
		db.executeUpdate(SQL_NOTAS_INCIDENCIA, horasPrevistas, descripcionTrabajo, idIncidencia);
	    
	    String comentario = "Planificación: horasPrevistas=" + (horasPrevistas == null ? "N/A" : horasPrevistas)
	                       + (descripcionTrabajo == null || descripcionTrabajo.isEmpty() ? "" : " - " + descripcionTrabajo);
	    
	    db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, comentario);
	}
}
