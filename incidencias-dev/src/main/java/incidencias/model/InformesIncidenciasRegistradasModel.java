package incidencias.model;

import java.util.List; 

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class InformesIncidenciasRegistradasModel {

	Database db = new Database();
	
	private static final String SQL_LISTAR_INCIDENCIAS_POR_FECHA = "SELECT i.id, tipo, i.descripcion, " +
			   "i.localizacion, " +
			   "hCreacion.fechaCambio, " +
			   "hActual.estado " +
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
			   "WHERE date(hCreacion.fechaCambio) >= ? " +
			   "AND date(hCreacion.fechaCambio) <= ? " +
			   "ORDER BY hCreacion.fechaCambio DESC";
	
	/**
	 * Selecciona las incidencias que han sido registradas después de "fechaIni" y antes de
	 * "fechaFin". Muestra de cada una de ellas sus datos principales.
	 */
	public List<IncidenciaDTO> getIncidenciasPorFecha(String fechaIni, String fechaFin) {
		List<IncidenciaDTO> incidenciasTecnico = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS_POR_FECHA, fechaIni, fechaFin);
		return incidenciasTecnico;
	}
	
	
}
