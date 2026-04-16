package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.database.Database;

public class CerrarIncidenciasEspecialidadModel {

	Database db = new Database();
	
	private static final String SQL_LISTAR_RESUELTAS = 
		    "SELECT i.id, ti.nombre AS tipo, i.descripcion, i.localizacion, " +
		    "i.horasReales, i.costeTotal, i.descripcionFinalTrabajo, hActual.fechaCambio " +
		    "FROM incidencia i " +
		    "JOIN tipoIncidencia ti ON i.tipo = ti.id " +
		    "JOIN tipoIncidenciaTecnico tit ON tit.idTipo = i.tipo " +
		    "JOIN historia hActual ON hActual.id = (" +
		    "   SELECT MAX(h.id) FROM historia h WHERE h.idIncidencia = i.id" +
		    ") " +
		    "WHERE tit.idTecnico = ? " +
		    "AND hActual.estado = 'Resuelta' " +
		    "ORDER BY hActual.fechaCambio DESC";

    private static final String SQL_INSERT_HISTORIA = 
	        "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (?, NULL, 'Cerrada', ?)";

    private static final String SQL_GET_LAST_HISTORIA_ID = "SELECT MAX(id) FROM historia WHERE idIncidencia = ?";

    private static final String SQL_INSERT_EQUIPO_TECNICO = 
	        "INSERT INTO equipoTecnicos (idTecnico, idHistoria) VALUES (?, ?)";
	    
    private static final String SQL_GET_NOMBRE_ESPECIALIDAD =
	    	"SELECT ti.nombre FROM tipoIncidencia ti " +
		    "JOIN tipoIncidenciaTecnico tit ON ti.id = tit.idTipo " +
		    "WHERE tit.idTecnico = ? LIMIT 1";
	    
    private static final String SQL_GET_PRESUPUESTO_DISPONIBLE = 
    	    "SELECT p.importe - " +
    	    "  COALESCE((SELECT SUM(i.costeTotal) FROM incidencia i " +
    	    "            JOIN historia h ON i.id = h.idIncidencia " +
    	    "            WHERE i.tipo = p.idTipoIncidencia " +
    	    "            AND h.id = (SELECT MAX(id) FROM historia WHERE idIncidencia = i.id) " +
    	    "            AND h.estado = 'Cerrada'), 0) " +
    	    "FROM presupuesto p WHERE p.idTipoIncidencia = ?";

    private static final String SQL_GET_INFO_CIERRE = 
    	    "SELECT tipo, costeTotal FROM incidencia WHERE id = ?";
   
    public List<IncidenciaDTO> getIncidenciasResueltasEspecialidad(int idTecnicoResponsable) {
        return db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTAR_RESUELTAS, idTecnicoResponsable);
    }

    public void cerrarIncidencia(int idIncidencia, int idTecnico, String comentario) {
        db.executeUpdate(SQL_INSERT_HISTORIA, idIncidencia, comentario);
        
        int idHistoria = (int) db.executeQueryArray(SQL_GET_LAST_HISTORIA_ID, idIncidencia).get(0)[0];
        db.executeUpdate(SQL_INSERT_EQUIPO_TECNICO, idTecnico, idHistoria);
    }

    public String getNombreEspecialidad(int idTecnico) {
		List<Object[]> res = db.executeQueryArray(SQL_GET_NOMBRE_ESPECIALIDAD, idTecnico);
		if (res.isEmpty()) return "General";
		return res.get(0)[0].toString();
	}

  	public Double getPresupuestoRestante(int idTipo) {
  	    List<Object[]> res = db.executeQueryArray(SQL_GET_PRESUPUESTO_DISPONIBLE, idTipo);
   	    if (res.isEmpty()) return null; // No hay presupuesto definido
   	    return Double.parseDouble(res.get(0)[0].toString());
   	}

  	public Object[] getInfoCierreIncidencia(int idIncidencia) {
        List<Object[]> res = db.executeQueryArray(SQL_GET_INFO_CIERRE, idIncidencia);
        return res.isEmpty() ? null : res.get(0);
    }
}
