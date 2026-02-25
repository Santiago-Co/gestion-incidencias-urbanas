package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.HistoriaDTO;
import incidencias.model.DTOs.IncidenciaDTO;
import util.LogSystem;
import util.database.Database;


public class HistorialIncidenciaModel {

    private Database db = new Database();

    public static final String SQL_LISTA_INCIDENCIAS =
    	    "SELECT i.id, i.tipo, i.descripcion, h.fechaCambio as fechaCreacion, h.estado " +
    	    "FROM incidencia i " +
    	    "JOIN historia h ON h.idIncidencia = i.id " +
    	    "WHERE h.fechaCambio = (" +
    	    "   SELECT MAX(h2.fechaCambio) FROM historia h2 WHERE h2.idIncidencia = i.id" +
    	    ") " +
    	    "ORDER BY h.fechaCambio DESC";

    public static final String SQL_HISTORIAL_POR_INCIDENCIA = 
        "SELECT fechaCambio, descripcion, estado, idTecnico, idOperador " +
        "FROM historia " +
        "WHERE idIncidencia = ? " +
        "ORDER BY fechaCambio DESC";

    public List<IncidenciaDTO> getIncidenciasUsuario(int usuarioId) {
        LogSystem.logFlow("HistorialModel", "Listando incidencias (usuarioId=" + usuarioId + ")");
        List<IncidenciaDTO> lista = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTA_INCIDENCIAS);
        return lista;
    }

    public List<HistoriaDTO> getHistorialPorIncidencia(int idIncidencia) {
        LogSystem.logFlow("HistorialModel", "Obteniendo historial para incidencia " + idIncidencia);
        List<HistoriaDTO> historial = db.executeQueryPojo(HistoriaDTO.class, SQL_HISTORIAL_POR_INCIDENCIA, idIncidencia);
        return historial;
    }
}