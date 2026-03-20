package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.LogSystem;
import util.database.Database;

public class ConsultaIncidenciasModel {
	    
    private Database db = new Database();
    
    private static final String SQL_LISTA_INCIDENCIAS = "SELECT i.id, tipo, i.descripcion, " +
    												   "hCreacion.fechaCambio AS fechaCreacion, " +
    												   "hActual.estado, localizacion " +
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
    	    										   "WHERE idCiudadano = ? " +
    	    										   "ORDER BY hCreacion.fechaCambio DESC";
	
	private static final String SQL_FILTRA_INCIDENCIAS = "SELECT i.id, tipo, i.descripcion, " +
														"hCreacion.fechaCambio AS fechaCreacion, " +
														"hActual.estado, localizacion " +
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
			   											"WHERE idCiudadano = ? AND hActual.estado = ?" +
			   											"ORDER BY hCreacion.fechaCambio DESC";
    	
    public List<IncidenciaDTO> getListaIncidencias(int id) {
    	
    	LogSystem.logFlow("Consulta incidencias", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTA_INCIDENCIAS, id);
    	
    	return incidencias;
    }
    
    public List<IncidenciaDTO> filtraListaIncidencias(int id, String estado) {
    	
    	LogSystem.logFlow("Filtrado incidencias", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_FILTRA_INCIDENCIAS, id, estado);
    	
    	return incidencias;
    }
}