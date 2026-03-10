package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.UsuarioDTO;
import util.LogSystem;
import util.database.Database;

public class AsignacionIncidenciasModel {
	    
    private Database db = new Database();

    private static final String SQL_INCIDENCIAS_VALIDADAS = "SELECT i.id, tipo, i.descripcion, " +
														   "hCreacion.fechaCambio AS fechaCreacion, " +
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
														   "WHERE hActual.estado = 'Validada' " +
														   "ORDER BY hCreacion.fechaCambio";
    
    private static final String SQL_LISTA_TECNICOS = "SELECT id, nombre, apellidos " +
		      									     "FROM usuario " +
		      									     "WHERE rol = 'Tecnico'";
    
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
													   "VALUES (?, ?, ?, 'Asignada', ?)";
    	
    public List<IncidenciaDTO> getListaIncidenciasValidadas() {
    	
    	LogSystem.logFlow("Incidencias validadas", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_INCIDENCIAS_VALIDADAS);
         	
    	return incidencias;
    }
    
    public List<UsuarioDTO> getListaTecnicos() {
    	
    	LogSystem.logFlow("Consulta tecnicos", "ejecución query");
    	
        List<UsuarioDTO> incidencias = db.executeQueryPojo(UsuarioDTO.class, SQL_LISTA_TECNICOS);
    	
    	return incidencias;
    }
    
    public void asignarIncidencia(int idIncidencia, int idTecnico, int idOperador) {
    	
    	LogSystem.logFlow("Asignacion incidencia", "ejecución query");
    	
    	db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, idOperador,
    					 String.format("Incidencia asignada por el operador %d al técnico %d", idOperador, idTecnico));
    }
}