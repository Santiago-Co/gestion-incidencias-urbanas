package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.IncidenciaDTO;
import util.LogSystem;
import util.database.Database;

public class ReabrirIncidenciaCiudadanoModel {
    
    private Database db = new Database();
    
    private static final String SQL_INCIDENCIAS_CERRADAS_RECHAZADAS = 
            "SELECT i.id, " +
            "ti.nombre AS tipo, " +
            "i.descripcion, " +
            "i.localizacion, " +
            "hActual.estado " +
            "FROM incidencia i " +
            "JOIN tipoIncidencia ti ON i.tipo = ti.id " +
            "JOIN historia hActual ON hActual.id = " +
            "( " +
            "   SELECT MAX(h2.id) FROM historia h2 " +
            "   WHERE h2.idIncidencia = i.id " +
            ") " +
            "WHERE i.idCiudadano = ? AND hActual.estado IN ('Cerrada', 'Rechazado') " +
            "ORDER BY i.id DESC";

    private static final String SQL_REABRIR_INCIDENCIA = 
            "INSERT INTO historia (idIncidencia, estado, descripcion) VALUES (?, 'Nueva', ?)";

    public List<IncidenciaDTO> getIncidenciasCerradasORechazadas(int idCiudadano) {
        
        LogSystem.logFlow("Consulta reapertura", "Obteniendo incidencias cerradas o rechazadas del ciudadano");
        
        List<IncidenciaDTO> incidencias = db.executeQueryPojo(IncidenciaDTO.class, SQL_INCIDENCIAS_CERRADAS_RECHAZADAS, idCiudadano);
        
        return incidencias;
    }
    
    public void reabrirIncidencia(int idIncidencia, String motivo) {
        
        LogSystem.logFlow("Reabrir incidencia", "Insertando nuevo estado 'Nueva' para la incidencia ID: " + idIncidencia);
        
        db.executeUpdate(SQL_REABRIR_INCIDENCIA, idIncidencia, motivo);
    }
}