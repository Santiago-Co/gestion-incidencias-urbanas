package incidencias.model.DTOs;

public class HistoriaExportDTO {
    private String fechaCambio;
    private String estado;
    private String historiaDescripcion;

    public String getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(String fechaCambio) { this.fechaCambio = fechaCambio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getHistoriaDescripcion() { return historiaDescripcion; }
    public void setHistoriaDescripcion(String historiaDescripcion) { this.historiaDescripcion = historiaDescripcion; }

    public String toJson() {
        return String.format(
            "      {\n" +
            "        \"fechaCambio\": \"%s\",\n" +
            "        \"estado\": \"%s\",\n" +
            "        \"historiaDescripcion\": \"%s\"\n" +
            "      }",
            escapeJson(fechaCambio), escapeJson(estado), escapeJson(historiaDescripcion)
        );
    }

    private String escapeJson(String data) {
        if (data == null) return "";
        return data.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}