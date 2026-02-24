package incidencias.model;

import java.util.*;

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class PlanificacionIncidenciasModel {
	
	private Database db = new Database();
	
	/*
	 * Obtiene las incidencias asociadas a un tecnico
	 */
	public List<IncidenciaDTO> getIncidenciasTecnico(int id) {
		String sql = "SELECT id, tipo, descripcion, fechaRegistro, estado "
			     + "FROM incidencia "
			     + "WHERE tecnicoId = ? AND estado = 'Asignada' "
			     + "ORDER BY fechaRegistro DESC";
		
        List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, sql, id);

		return incidenciasTecnico;
	}
	
	public void planificarIncidencia(int idIncidencia, Integer horasPrevistas, String descripcionTrabajo) {
	    String sqlUpdate = "UPDATE incidencia SET horasPrevistas = ?, descripcionTrabajo = ?, estado = 'En proceso' WHERE id = ?";
	    db.executeUpdate(sqlUpdate, horasPrevistas, descripcionTrabajo, idIncidencia);
	    
	    String comentario = "Planificación: horasPrevistas=" + (horasPrevistas == null ? "N/A" : horasPrevistas)
	                       + (descripcionTrabajo == null || descripcionTrabajo.isEmpty() ? "" : " - " + descripcionTrabajo);
	    String sqlHist = "INSERT INTO historial(idIncidencia, fechaCambio, estado, descripcion) VALUES (?, datetime('now'), 'En proceso', ?)";
	    db.executeUpdate(sqlHist, idIncidencia, comentario);
	}
}
