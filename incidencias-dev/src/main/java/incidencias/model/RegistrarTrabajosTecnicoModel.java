package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TrabajoDTO;
import util.database.Database;

public class RegistrarTrabajosTecnicoModel {

	Database db = new Database();
	
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
			 "ORDER BY hCreacion.fechaCambio DESC";
	
	private static final String SQL_LISTAR_TRABAJOS = "SELECT t.id, t.fecha, t.descripcion "
			+ "FROM trabajo t "
			+ "WHERE ? = t.idIncidencia "
			+ "ORDER BY t.fecha DESC, t.id DESC";
	
	private static final String SQL_INSERT_COMENTARIO =
		    "INSERT INTO trabajo (idIncidencia, fecha, descripcion) VALUES (?, datetime('now','localtime'), ?)";
	
	public List<IncidenciaDTO> getIncidenciasTecnico(int id) {
		return db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS, id);
	}

	public List<TrabajoDTO> getTrabajosIncidencia(int idIncidencia) {
	    return db.executeQueryPojo(TrabajoDTO.class, SQL_LISTAR_TRABAJOS, idIncidencia);
	}

	public void registrarTrabajo(int idIncidencia, String comentario) {
	    db.executeUpdate(SQL_INSERT_COMENTARIO, idIncidencia, comentario.trim());
	}
}
