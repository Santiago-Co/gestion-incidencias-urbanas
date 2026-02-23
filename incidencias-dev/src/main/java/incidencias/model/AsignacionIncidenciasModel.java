package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.DTOs.UsuarioDTO;
import util.LogSystem;
import util.Util;
import util.database.Database;

public class AsignacionIncidenciasModel {
	    
    private Database db = new Database();

    public static final String SQL_INCIDENCIAS_VALIDADAS = "SELECT id, tipo, descripcion, fechaRegistro "
		     											 + "FROM incidencia "
		     											 + "WHERE estado = 'Validada' "
		     											 + "ORDER BY fechaRegistro";
    
    public static final String SQL_LISTA_TECNICOS = "SELECT id, correo "
		      									  + "FROM usuarios "
		      									  + "WHERE rol = 'Tecnico'";
    
    public static final String SQL_ACTUALIZA_ESTADO = "UPDATE incidencia SET estado = 'Asignada' WHERE id = ?";
    
    public static final String SQL_ASIGNA_TECNICO = "INSERT INTO asignacion(tecnicoId, incidenciaId) VALUES(?, ?)";
    	
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
    
    public void asignarIncidencia(int idIncidencia, int idTecnico) {
    	
    	LogSystem.logFlow("Asignacion incidencia", "ejecución query");
    	
    	db.executeUpdate(SQL_ASIGNA_TECNICO, idTecnico, idIncidencia);
    	
    	LogSystem.logFlow("Actualizacion estado incidencia", "ejecución query");
    	
    	db.executeUpdate(SQL_ACTUALIZA_ESTADO, idIncidencia);
    }
}