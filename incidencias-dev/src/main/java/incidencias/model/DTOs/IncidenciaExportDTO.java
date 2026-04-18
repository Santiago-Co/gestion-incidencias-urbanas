package incidencias.model.DTOs;

import java.util.ArrayList;
import java.util.List;

public class IncidenciaExportDTO {
    private int id;
    private String tipo;
    private int idCiudadano;
    private String descripcion;
    private String localizacion;
    private List<HistoriaExportDTO> historias = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getIdCiudadano() { return idCiudadano; }
    public void setIdCiudadano(int idCiudadano) { this.idCiudadano = idCiudadano; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getLocalizacion() { return localizacion; }
    public void setLocalizacion(String localizacion) { this.localizacion = localizacion; }

    public List<HistoriaExportDTO> getHistorias() { return historias; }
    public void setHistorias(List<HistoriaExportDTO> historias) { this.historias = historias; }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append(String.format(
            "  {\n" +
            "    \"id\": %d,\n" +
            "    \"tipo\": \"%s\",\n" +
            "    \"idCiudadano\": %d,\n" +
            "    \"descripcion\": \"%s\",\n" +
            "    \"localizacion\": \"%s\",\n" +
            "    \"historias\": [\n",
            id, escapeJson(tipo), idCiudadano, escapeJson(descripcion), escapeJson(localizacion)
        ));

        for (int i = 0; i < historias.size(); i++) {
            json.append(historias.get(i).toJson());
            if (i < historias.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("    ]\n  }");
        return json.toString();
    }

    private String escapeJson(String data) {
        if (data == null) return "";
        return data.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}