package incidencias.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import incidencias.model.DTOs.HistoriaExportDTO;
import incidencias.model.DTOs.IncidenciaExportDTO;
import incidencias.model.DTOs.IncidenciaFlatDTO;
import util.database.Database;

public class ExportarHistorialModel {

    private Database db = new Database();

    public List<IncidenciaExportDTO> obtenerDatosExportacion(String fechaInicio, String fechaFin, List<String> tipos, List<String> zonas) {
        StringBuilder sql = new StringBuilder(
            "SELECT i.id, ti.nombre AS tipo, i.idCiudadano, i.descripcion, i.localizacion, " +
            "h.fechaCambio, h.estado, h.descripcion AS historiaDescripcion " +
            "FROM incidencia i " +
            "JOIN tipoIncidencia ti ON i.tipo = ti.id " +
            "JOIN historia h ON h.idIncidencia = i.id " +
            "WHERE 1=1 "
        );
        
        List<Object> params = new ArrayList<>();

        if (fechaInicio != null && !fechaInicio.isEmpty()) {
            sql.append(" AND date(h.fechaCambio) >= date(?) ");
            params.add(fechaInicio);
        }
        
        if (fechaFin != null && !fechaFin.isEmpty()) {
            sql.append(" AND date(h.fechaCambio) <= date(?) ");
            params.add(fechaFin);
        }

        if (tipos != null && !tipos.isEmpty() && !tipos.contains("Todos")) {
            sql.append(" AND ti.nombre IN (");
            for (int i = 0; i < tipos.size(); i++) {
                sql.append("?");
                if (i < tipos.size() - 1) sql.append(", ");
                params.add(tipos.get(i));
            }
            sql.append(") ");
        }

        if (zonas != null && !zonas.isEmpty() && !zonas.contains("Todas las zonas")) {
            sql.append(" AND i.localizacion IN (");
            for (int i = 0; i < zonas.size(); i++) {
                sql.append("?");
                if (i < zonas.size() - 1) sql.append(", ");
                params.add(zonas.get(i));
            }
            sql.append(") ");
        }
        
        sql.append(" ORDER BY i.id DESC, h.fechaCambio DESC");

        List<IncidenciaFlatDTO> flatResults = db.executeQueryPojo(IncidenciaFlatDTO.class, sql.toString(), params.toArray());
        
        Map<Integer, IncidenciaExportDTO> groupedMap = new LinkedHashMap<>();
        
        for (IncidenciaFlatDTO flat : flatResults) {
            IncidenciaExportDTO incidencia = groupedMap.get(flat.getId());
            
            if (incidencia == null) {
                incidencia = new IncidenciaExportDTO();
                incidencia.setId(flat.getId());
                incidencia.setTipo(flat.getTipo());
                incidencia.setIdCiudadano(flat.getIdCiudadano());
                incidencia.setDescripcion(flat.getDescripcion());
                incidencia.setLocalizacion(flat.getLocalizacion());
                groupedMap.put(flat.getId(), incidencia);
            }
            
            HistoriaExportDTO historia = new HistoriaExportDTO();
            historia.setFechaCambio(flat.getFechaCambio());
            historia.setEstado(flat.getEstado());
            historia.setHistoriaDescripcion(flat.getHistoriaDescripcion());
            
            incidencia.getHistorias().add(historia);
        }

        return new ArrayList<>(groupedMap.values());
    }
}