package model;

import java.util.List;

import dto.IncidenciaDTO;
import util.Database;
import util.LogSystem;

public class ClasificarIncidenciasModel {
	    
    private Database db = new Database();
    
    public static final String SQL_LISTA_INCIDENCIAS = "SELECT i.id, tipo, descripcion, fechaHora, estado "
    											     + "FROM usuario u JOIN incidencia i "
    											     + "ON u.id = i.idUsuario "
    											     + "WHERE estado = Nueva"
    											     + "ORDER BY fechaHora DESC";
    
    public static final String SQL_FILTRA_INCIDENCIAS = "SELECT i.id, tipo, descripcion, fechaHora, estado "
												      + "FROM usuario u JOIN incidencia i "
												      + "ON u.id = i.idUsuario "
												      + "WHERE u.id = ? and estado = ?"
												      + "ORDER BY fechaHora DESC";

    public List<IncidenciaDTO> getListaIncidencias() {
    	
    	LogSystem.logFlow("Consulta incidencias", "ejecución query");
    	
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTA_INCIDENCIAS);
    	
    	return incidencias;
    }
}