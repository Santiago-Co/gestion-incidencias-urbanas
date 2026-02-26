package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.Entitiy.IncidenciaEntity;
import util.LogSystem;
import util.Util;
import util.database.Database;

public class CrearIncidenciaModel {

	private final static String INCIDENCIA_NO_NULA = "El identificador no puede ser nulo";
	
	private Database db = new Database();
    
	private static final String SQL_INSERT_INCIDENCIA = "INSERT INTO incidencia (tipo, idCiudadano, descripcion, localizacion) " +
													    "VALUES (?, ?, ?, ?)";
	
	private static final String SQL_INSERT_HISTORIAL  = "INSERT INTO historia (idIncidencia, idTecnico, idOperador, estado, descripcion) " +
													    "VALUES (?, NULL, NULL, ?, ?)";
	
	private static final String SQL_SELECT_INCIDENCIA = "SELECT id, tipo, idCiudadano, descripcion, localizacion " +
													    "FROM incidencia ORDER BY id DESC LIMIT 1";
			
	public IncidenciaDTO registrarYRecuperarIncidencia(IncidenciaEntity inc) {
	    Util.validateNotNull(inc, INCIDENCIA_NO_NULA);
	    
	    db.executeUpdate(SQL_INSERT_INCIDENCIA,
	    		inc.getTipo(),
	    		inc.getIdCiudadano(),
	    		inc.getDescripcion(), 
	    		inc.getLocalizacion());
	    ;
	    List<IncidenciaDTO> lista = db.executeQueryPojo(IncidenciaDTO.class, SQL_SELECT_INCIDENCIA);
	    
	    db.executeUpdate(SQL_INSERT_HISTORIAL,
	    		lista.get(0).getId(),
	    		"Nueva",
	    		String.format("Incidencia registrada por el usuario %d", inc.getIdCiudadano()));
	    
	    return lista.get(0); 
	}
	
	/**
	 * Este código no lo estoy utilizando pero creo que sería mejor separar esta función en dos
	 */
	
	public void registrarIncidencia(IncidenciaEntity inc) {
		db.executeUpdate(SQL_INSERT_INCIDENCIA,
				inc.getTipo(),
	    		inc.getIdCiudadano(),
	    		inc.getDescripcion(), 
	    		inc.getLocalizacion());
	}
	
	public IncidenciaDTO recuperarUltimaIncidencia() {
	    List<IncidenciaDTO> lista = db.executeQueryPojo(IncidenciaDTO.class, SQL_SELECT_INCIDENCIA);
	    
	    if (lista.isEmpty()) {
	        LogSystem.logFlow("Error", "No hay datos en la sql de incidencia");
	    	return null; 
	    }
	    
	    return lista.get(0);
	}
}