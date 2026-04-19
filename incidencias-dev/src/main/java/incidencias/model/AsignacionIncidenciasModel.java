package incidencias.model;

import java.util.ArrayList;
import java.util.List;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.TecnicoDTO;
import incidencias.model.Entitiy.HistoriaEntity;
import util.LogSystem;
import util.database.Database;

public class AsignacionIncidenciasModel {
	    
    private Database db = new Database();
    
    /* 
     * Esta consulta devuelve todas las incidencias que se encuentren actualmente en estado 'Validada'
     * en el historial, ordenadas según la fecha en la que fueron registradas originalmente (las más
     * antiguas primero). De cada incidencia se extrae su ID, tipo, descripción, fecha de creación y
     * estado actual.
    */
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
    
    /* 
     * Esta consulta devuelve todos los técnicos del tipo especificado como parámetro, ordenados según el número de 
     * incidencias que tengan asignadas (los que menos tienen primero). De cada técnico se extrae su ID, nombre y apellidos, 
     * tipo y número de incidencias asignadas (se considera que una incidencia permanece asignada a un técnico mientras esté
     * en estado 'Asignada', 'En proceso' o 'Resuelta')
    */
    private static final String SQL_LISTA_TECNICOS = "SELECT u.id, u.nombre, u.apellidos, " +
    												 "ti.nombre AS tipo, " +
    												 "COUNT(DISTINCT hActual.idIncidencia) AS numAsignadas " +
    												 "FROM usuario u " +
    												 "JOIN tipoIncidenciaTecnico tit ON u.id = tit.idTecnico " +
    												 "JOIN tipoIncidencia ti ON tit.idTipo = ti.id " +
    												 "LEFT JOIN equipoTecnicos et ON u.id = et.idTecnico " +
            										 "LEFT JOIN historia hAsignacion ON et.idHistoria = hAsignacion.id " +
            										 "LEFT JOIN historia hActual ON hActual.id = " +
            										 "( " +
            										 "	SELECT MAX(h.id) FROM historia h " +
            										 "	WHERE h.idIncidencia = hAsignacion.idIncidencia " +
            										 ") " +
    												 "AND hActual.estado IN ('Asignada', 'En proceso', 'Resuelta') " +
    												 "WHERE rol = 'Tecnico' " +
    												 "AND ti.nombre = ? " +
    												 "GROUP BY u.id, u.nombre, u.apellidos, ti.nombre " +
    												 "ORDER BY numAsignadas ASC";
    
	private static final String SQL_INSERT_HISTORIAL = "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) " +
													   "VALUES (?, ?, 'Asignada', ?)";
	
	private static final String SQL_ID_HISTORIAL = "SELECT h.id FROM historia h " +
												   "WHERE h.idIncidencia = ? " +
												   "ORDER BY h.fechaCambio DESC " +
												   "LIMIT 1";
	
    private static final String SQL_INSERT_TECNICOS = "INSERT INTO equipoTecnicos (idTecnico, idHistoria) " +
			  										  "VALUES (?, ?)";
	
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
    
    public boolean asignarIncidencia(int idIncidencia, int[] idTecnicos, int idOperador) {
    	    	
    	String descripcion = String.format("Incidencia asignada por el operador %d a los técnicos: ", idOperador);
    	
    	StringBuilder formatter = new StringBuilder(descripcion);
    	for(int i=0; i<idTecnicos.length; i++) {
    		if(i < idTecnicos.length-1) {
        		formatter.append(String.format("%d", idTecnicos[i])).append(",");
    		} else {
        		formatter.append(String.format("%d", idTecnicos[i]));
    		}
    	}
    	
    	LogSystem.logFlow("Asignacion incidencia en historial", "ejecución query");

    	db.executeUpdate(SQL_INSERT_HISTORIAL, idIncidencia, idOperador, formatter.toString());
    	
    	List<HistoriaEntity> historias = db.executeQueryPojo(HistoriaEntity.class, SQL_ID_HISTORIAL, idIncidencia);
    	if(historias.isEmpty()) {
    		return false;
    	}
    	int idHistoria = historias.get(0).getId();
    	
    	List<Object[]> listaParams = new ArrayList<>();
    	
    	for(int idTec : idTecnicos) {
    		listaParams.add(new Object[] {idTec, idHistoria});
    	}
    	
    	LogSystem.logFlow("Asignacion incidencia en equipoTecnicos", "ejecución query");
		db.executeBatchParams(SQL_INSERT_TECNICOS, listaParams);
    	
    	return true;
    }
}