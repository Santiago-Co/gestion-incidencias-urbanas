package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.LogSystem;
import util.database.Database;

public class ConsultaIncidenciasModel {
	    
    private Database db = new Database();
    
    public static final String SQL_LISTA_INCIDENCIAS = "SELECT i.id, tipo, descripcion, fechaHora, estado "
    											     + "FROM usuario u JOIN incidencia i "
    											     + "ON u.id = i.idUsuario "
    											     + "WHERE u.id = ? "
    											     + "ORDER BY fechaHora DESC";
    
    public static final String SQL_FILTRA_INCIDENCIAS = "SELECT i.id, tipo, descripcion, fechaHora, estado "
												      + "FROM usuario u JOIN incidencia i "
												      + "ON u.id = i.idUsuario "
												      + "WHERE u.id = ? and estado = ?"
												      + "ORDER BY fechaHora DESC";

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