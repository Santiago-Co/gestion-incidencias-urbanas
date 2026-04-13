package incidencias.model;

import java.util.*;

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class PlanificacionIncidenciasModel {
	
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
			   											 "WHERE et.idTecnico = ? AND hActual.estado = 'Asignada' " +
			   											 "ORDER BY hCreacion.fechaCambio";
	
	private static final String SQL_NOTAS_INCIDENCIA = "UPDATE incidencia SET horasPrevistas = ?, descripcionPreviaTrabajo = ? WHERE id = ?";
	
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) " +
			   										   "VALUES (?, NULL, ?, ?)";
    
	private static final String SQL_GET_LAST_HISTORIA_ID = "SELECT MAX(id) FROM historia WHERE idIncidencia = ?";
	
    private static final String SQL_INSERT_EQUIPO_TECNICO = "INSERT INTO equipoTecnicos (idTecnico, idHistoria) VALUES (?, ?)";
    
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
	    
	    db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, "En proceso", comentario);
	    
	    // Obtenemos el id de la nueva historia
        int idHistoria = (int) db.executeQueryArray(SQL_GET_LAST_HISTORIA_ID, idIncidencia).get(0)[0];

        db.executeUpdate(SQL_INSERT_EQUIPO_TECNICO, idTecnico, idHistoria);
	}

    public void actualizarEstadoIncidenciaEliminar(int idIncidencia, int idTecnico, String detalleEliminar) {
    	db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, "Rechazado", detalleEliminar);
        int idHistoria = (int) db.executeQueryArray(SQL_GET_LAST_HISTORIA_ID, idIncidencia).get(0)[0];
        db.executeUpdate(SQL_INSERT_EQUIPO_TECNICO, idTecnico, idHistoria);
    }
}
