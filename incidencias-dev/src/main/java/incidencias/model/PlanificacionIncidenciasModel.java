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
		
		String sql = "SELECT i.id, i.tipo, i.descripcion, i.fechaHora, i.estado "
			     + "FROM usuario u JOIN incidencia i "
			     + "ON u.id = i.idUsuario "
			     + "WHERE u.id = ? AND i.estado = 'Asignada' "
			     + "ORDER BY fechaHora DESC";
		
        List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, sql, id);

		return incidenciasTecnico;
	}
}
