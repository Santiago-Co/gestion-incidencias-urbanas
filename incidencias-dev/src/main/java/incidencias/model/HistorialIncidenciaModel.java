package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.HistoriaDTO;
import incidencias.model.DTOs.IncidenciaDTO;
import util.LogSystem;
import util.database.Database;

/**
 * Acceso a los datos relacionados con el historial de incidencias.
 * 
 * Contiene dos consultas SQL:
 * - Obtener las incidencias de un técnico/operador.
 * - Obtener el historial completo de una incidencia.
 */
public class HistorialIncidenciaModel {

    private Database db = new Database();

    public static final String SQL_LISTA_INCIDENCIAS =
    	    "SELECT i.id, " +
    	    "ti.nombre AS tipo, " +
    	    "i.descripcion, h.fechaCambio, h.estado " +
    	    "FROM incidencia i " +
			"INNER JOIN tipoIncidencia ti ON i.tipo = ti.id " +
    	    "INNER JOIN historia h ON h.idIncidencia = i.id " +
    	    "WHERE h.fechaCambio = (" +
    	    "   SELECT MAX(h2.fechaCambio) FROM historia h2 WHERE h2.idIncidencia = i.id" +
    	    ") " +
    	    "ORDER BY h.fechaCambio DESC";

    public static final String SQL_HISTORIAL_POR_INCIDENCIA = 
        "SELECT id, fechaCambio, descripcion, estado " +
        "FROM historia " +
        "WHERE idIncidencia = ? " +
        "ORDER BY fechaCambio DESC";

    /*
     * Selecciona las incidencias cuyo id de operador o técnico se corresponde con el id del usuario
     * que desea realizar la consulta. Se recoge de la tabla "Historia" el último estado al que ha
     * pasado la incidencia.
     */
    public List<IncidenciaDTO> getIncidencias() {
        LogSystem.logFlow("HistorialModel", "Listando incidencias ");
        List<IncidenciaDTO> lista = db.executeQueryPojo(IncidenciaDTO.class, SQL_LISTA_INCIDENCIAS);
        return lista;
    }

    /*
     * Selecciona los datos principales de las historias asociadas a una incidencia concreta.
     */
    public List<HistoriaDTO> getHistorialPorIncidencia(int idIncidencia) {
        LogSystem.logFlow("HistorialModel", "Obteniendo historial para incidencia " + idIncidencia);
        List<HistoriaDTO> historial = db.executeQueryPojo(HistoriaDTO.class, SQL_HISTORIAL_POR_INCIDENCIA, idIncidencia);
        return historial;
    }
}