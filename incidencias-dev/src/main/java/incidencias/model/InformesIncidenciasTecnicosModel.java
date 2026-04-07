package incidencias.model;

import java.util.List;
import incidencias.model.DTOs.InformesIncidenciasTecnicosDTO;
import util.LogSystem;
import util.database.Database;

public class InformesIncidenciasTecnicosModel {
	    
    private Database db = new Database();
    
    private static final String SQL_LISTA_INCIDENCIAS = "SELECT u.id, u.nombre, u.apellidos, " +
    													"COUNT(DISTINCT CASE " +
    														"WHEN h.estado = 'Asignada' THEN h.idIncidencia " + 
    													"END) AS numAsignadas, " +
    													"COUNT(DISTINCT CASE " + 
    														"WHEN h.estado = 'Resuelta' THEN h.idIncidencia " + 
    													"END) AS numResueltas, " +
    													"SUM(CASE " + 
    														"WHEN h.estado = 'Resuelta' THEN i.horasReales " + 
    														"ELSE 0 " +
    													"END) AS horasReales " +
    													"FROM usuario u " +
    													"LEFT JOIN equipoTecnicos et ON u.id = et.idTecnico " +
    													"LEFT JOIN historia h ON " +
    													"( " +
    													"   et.idHistoria = h.id " +
    													"   AND h.fechaCambio >= date('now','start of month','-1 month') " +
    													"   AND h.fechaCambio <  date('now','start of month') " +
    													")" +
    													"LEFT JOIN incidencia i ON i.id = h.idIncidencia " +
    													"WHERE u.rol = 'Tecnico' " +
    													"GROUP BY u.id"; 

    public List<InformesIncidenciasTecnicosDTO> getInforme() {
    	
    	LogSystem.logFlow("Informe tecnicos", "ejecución query");
    	
        List<InformesIncidenciasTecnicosDTO> informe = db.executeQueryPojo(InformesIncidenciasTecnicosDTO.class, SQL_LISTA_INCIDENCIAS);
    	
    	return informe;
    }
}