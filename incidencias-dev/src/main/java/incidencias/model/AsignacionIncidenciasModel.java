package incidencias.model;

import java.util.List;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TecnicoDTO;
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
    												 "ti.nombre AS tipo " +
		      									     "FROM usuario u " +
		      									     "JOIN tipoIncidenciaTecnico tit " +
		      									     "ON u.id = tit.idTecnico " +
		      									     "JOIN tipoIncidencia ti " +
		      									     "ON tit.idTipo = ti.id " +
		      									     "WHERE rol = 'Tecnico' " +
		      									     "AND ti.nombre = ?";
    
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
													   "VALUES (?, ?, ?, 'Asignada', ?)";
    	
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
    	
    	db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, idOperador,
    					 String.format("Incidencia asignada por el operador %d al técnico %d", idOperador, idTecnico));
    }
}