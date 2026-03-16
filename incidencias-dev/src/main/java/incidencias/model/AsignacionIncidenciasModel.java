package incidencias.model;

import java.util.List;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TecnicoDTO;
import util.LogSystem;
import util.MapEspecialidades;
import util.database.Database;

public class AsignacionIncidenciasModel {
	    
    private Database db = new Database();

    private static final String SQL_INCIDENCIAS_VALIDADAS = "SELECT i.id, tipo, i.descripcion, " +
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
														   "WHERE hActual.estado = 'Validada' " +
														   "ORDER BY hCreacion.fechaCambio";
    
    private static final String SQL_LISTA_TECNICOS = "SELECT id, nombre, apellidos, especialidad " +
		      									     "FROM usuario " +
		      									     "WHERE rol = 'Tecnico' " +
		      									     "AND especialidad = ?";
    
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
													   "VALUES (?, ?, ?, 'Asignada', ?)";
    	
    public List<IncidenciaDTO> getListaIncidenciasValidadas() {
    	
    	LogSystem.logFlow("Incidencias validadas", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_INCIDENCIAS_VALIDADAS);
         	
    	return incidencias;
    }
    
    public List<TecnicoDTO> getListaTecnicos(String tipoIncidencia) {
    	
    	LogSystem.logFlow("Consulta tecnicos", "ejecución query");
    	
        List<TecnicoDTO> incidencias = db.executeQueryPojo(TecnicoDTO.class, SQL_LISTA_TECNICOS, MapEspecialidades.getEspecialidad(tipoIncidencia));
    	
    	return incidencias;
    }
    
    public void asignarIncidencia(int idIncidencia, int idTecnico, int idOperador) {
    	
    	LogSystem.logFlow("Asignacion incidencia", "ejecución query");
    	
    	db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idTecnico, idOperador,
    					 String.format("Incidencia asignada por el operador %d al técnico %d", idOperador, idTecnico));
    }
}