package incidencias.model;

import java.util.List;

import exceptions.ApplicationException;
import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class InformesIncidenciasRegistradasModel {

	Database db = new Database();
	
	private static final String SQL_LISTAR_INCIDENCIAS_POR_FECHA = "SELECT i.id, ti.nombre AS tipo, " +
			   													   "i.descripcion, " +
			   													   "i.localizacion, " +
			   													   "hCreacion.fechaCambio, " +
			   													   "hActual.estado " +
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
			   													   "WHERE date(hCreacion.fechaCambio) >= ? " +
			   													   "AND date(hCreacion.fechaCambio) <= ? " +
			   													   "ORDER BY hCreacion.fechaCambio DESC";
	
	private static final String SQL_LISTAR_INCIDENCIAS_POR_TIPO = "SELECT i.id, ti.nombre AS tipo, " +
			   													  "i.descripcion, " +
			   													  "i.localizacion, " +
			   													  "hCreacion.fechaCambio, " +
			   											   	      "hActual.estado " +
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
			   													  "WHERE ti.nombre == ? " +
			   													  "ORDER BY hCreacion.fechaCambio DESC";
	
	private static final String SQL_LISTAR_INCIDENCIAS_POR_LOCALIZACION = "SELECT i.id, ti.nombre AS tipo, " +
				  												   		  "i.descripcion, " +
				  												   		  "i.localizacion, " +
				  												   		  "hCreacion.fechaCambio, " +
				  												   		  "hActual.estado " +
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
				  												   		  "WHERE i.localizacion == ? " +
				  												   		  "ORDER BY hCreacion.fechaCambio DESC";
	
	private static final String SQL_LISTAR_INCIDENCIAS_POR_ESTADO = "SELECT i.id, ti.nombre AS tipo, " +
				  													"i.descripcion, " +
				  													"i.localizacion, " +
				  													"hCreacion.fechaCambio, " +
				  													"hActual.estado " +
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
				  													"WHERE hActual.estado == ? " +
				  													"ORDER BY hCreacion.fechaCambio DESC";
	
	public List<IncidenciaDTO> getIncidenciasPorFiltro(String filtro, String... args) {
		List<IncidenciaDTO> incidencias;
		switch(filtro) {
			case "Fecha":
				incidencias = getIncidenciasPorFecha(args[0], args[1]);
				break;
			case "Tipo":
				incidencias = getIncidenciasPorTipo(args[0]);
				break;
			case "Localización":
				incidencias = getIncidenciasPorLoc(args[0]);
				break;
			case "Estado":
				incidencias = getIncidenciasPorEstado(args[0]);
				break;
			default:
				throw new ApplicationException("Filtro inválido");
		}
		return incidencias;
	}
	
	/**
	 * Selecciona las incidencias que han sido registradas después de "fechaIni" y antes de
	 * "fechaFin". Muestra de cada una de ellas sus datos principales.
	 */
	private List<IncidenciaDTO> getIncidenciasPorFecha(String fechaIni, String fechaFin) {
		List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS_POR_FECHA, fechaIni, fechaFin);
		return incidencias;
	}
	
	private List<IncidenciaDTO> getIncidenciasPorTipo(String tipo) {
		List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS_POR_TIPO, tipo);
		return incidencias;
	}
	
	private List<IncidenciaDTO> getIncidenciasPorLoc(String localizacion) {
		List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS_POR_LOCALIZACION, localizacion);
		return incidencias;
	}
	
	private List<IncidenciaDTO> getIncidenciasPorEstado(String estado) {
		List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_INCIDENCIAS_POR_ESTADO, estado);
		return incidencias;
	}
}
