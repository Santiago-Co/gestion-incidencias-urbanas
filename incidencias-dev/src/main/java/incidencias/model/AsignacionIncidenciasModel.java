package incidencias.model;

import java.util.List;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TecnicoDTO;
import incidencias.model.Entitiy.TipoIncidenciaTecnicoEntity;
import util.LogSystem;
import util.database.Database;

public class AsignacionIncidenciasModel {
	    
    private Database db = new Database();

    private static final String SQL_INCIDENCIAS_VALIDADAS = "SELECT i.id, " +
    													   "ti.nombre AS tipo, " +
    													   "i.descripcion, " +
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
														   "WHERE hActual.estado = 'Validada' " +
														   "ORDER BY hCreacion.fechaCambio";
    

    private static final String SQL_LISTA_TECNICOS = "SELECT u.id, u.nombre, u.apellidos, " +
            										"ti.nombre AS tipo, " +
                                                    "COUNT(DISTINCT h.idIncidencia) AS numAsignadas " +
                                                    "FROM usuario u " +
            										"JOIN tipoIncidencia ti ON tit.idTipo = ti.id " +
            										"JOIN tipoIncidenciaTecnico tit ON u.id = tit.idTecnico " +
            										"LEFT JOIN equipoTecnicos et ON u.id = et.idTecnico " +
           										    "LEFT JOIN historia h ON et.idHistoria = h.id " +
            										"  AND h.id = (SELECT MAX(id) FROM historia WHERE idIncidencia = h.idIncidencia) " +
           										    "  AND h.estado IN ('Asignada', 'En proceso') " +
            										"WHERE u.rol = 'Tecnico' AND ti.nombre = ? " +
            										"GROUP BY u.id, u.nombre, u.apellidos, ti.nombre " +
            										"ORDER BY numAsignadas ASC";
    
    
    private static final String SQL_ID_ASIGNACION = "SELECT tit.id, tit.idTipo, tit.idTecnico " +
    												"FROM incidencia i " +
    												"JOIN tipoIncidencia ti " +
    												"ON i.tipo = ti.id " +
    												"JOIN tipoIncidenciaTecnico tit " +
    												"ON tit.idTipo = ti.id " +
    												"WHERE i.id = ? " +
    												"AND tit.idTecnico = ?";
    
    private static final String SQL_ASIGNACION_INCIDENCIA = "UPDATE incidencia SET idAsignacion = ? WHERE id = ?";
    
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
													   "VALUES (?, ?, ?, 'Asignada', ?)";
    
	private int getIdAsignacion(int idIncidencia, int idTecnico) {
		List<TipoIncidenciaTecnicoEntity> asignacion = db.executeQueryPojo(TipoIncidenciaTecnicoEntity.class, SQL_ID_ASIGNACION,
																	 	   idIncidencia, idTecnico);
		return asignacion.get(0).getId();
	}
	
    public List<IncidenciaDTO> getListaIncidenciasValidadas() {
    	
    	LogSystem.logFlow("Incidencias validadas", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_INCIDENCIAS_VALIDADAS);
         	
    	return incidencias;
    }
    
    public List<TecnicoDTO> getListaTecnicos(String tipoIncidencia) {
    	
    	LogSystem.logFlow("Consulta tecnicos", "ejecución query");
    	
        List<TecnicoDTO> incidencias = db.executeQueryPojo(TecnicoDTO.class, SQL_LISTA_TECNICOS, tipoIncidencia);
    	
    	return incidencias;
    }
    
    public void asignarIncidencia(int idIncidencia, int idTecnico, int idOperador) {
    	
    	LogSystem.logFlow("Asignacion incidencia", "ejecución query");
    	
    	db.executeUpdate(SQL_ASIGNACION_INCIDENCIA, getIdAsignacion(idIncidencia, idTecnico), idIncidencia);
    	
    	db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, idOperador,
    					 String.format("Incidencia asignada por el operador %d al técnico %d", idOperador, idTecnico));
    }
}