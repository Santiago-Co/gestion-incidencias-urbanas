package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.Entitiy.IncidenciaEntity;
import util.Database;
import util.LogSystem;
import util.Util;

public class CrearIncidenciaModel {

	private final static String INCIDENCIA_NO_NULA = "El identificador no puede ser nulo";
	
	private Database db = new Database();
    
	private static final String SQL_INSERT_INCIDENCIA = 
			"INSERT INTO incidencia (ciudadanoId, tipo, descripcion, localizacion, estado) " 
			+ "VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_SELECT_INCIDENCIA = 
			"SELECT id, ciudadanoId, tipo, descripcion, localizacion, estado," 
			+ "fechaRegistro FROM incidencia ORDER BY id DESC LIMIT 1";
			
	public IncidenciaDTO registrarYRecuperarIncidencia(IncidenciaEntity inc) {
	    Util.validateNotNull(inc, INCIDENCIA_NO_NULA);
	    db.executeUpdate(SQL_INSERT_INCIDENCIA,
	    		inc.getCiudadanoId(),
	    		inc.getTipo(),
	    		inc.getDescripcion(), 
	    		inc.getLocalizacion(), 
	    		inc.getEstado());
	    List<IncidenciaDTO> lista = db.executeQueryPojo(IncidenciaDTO.class, SQL_SELECT_INCIDENCIA);
	    return lista.get(0); 
	}
	
	/**
	 * Este código no lo estoy utilizando pero creo que sería mejor separar esta función en dos
	 */
	
	public void registrarIncidencia(IncidenciaEntity inc) {
		db.executeUpdate(SQL_INSERT_INCIDENCIA,
	    		inc.getCiudadanoId(),
	    		inc.getTipo(),
	    		inc.getDescripcion(), 
	    		inc.getLocalizacion(), 
	    		inc.getEstado());
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