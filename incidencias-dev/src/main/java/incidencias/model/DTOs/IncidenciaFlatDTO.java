package incidencias.model.DTOs;

public class IncidenciaFlatDTO {
    private int id;
    private String tipo;
    private int idCiudadano;
    private String descripcion;
    private String localizacion;
    private String fechaCambio;
    private String estado;
    private String historiaDescripcion;

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

    public String getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(String fechaCambio) { this.fechaCambio = fechaCambio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getHistoriaDescripcion() { return historiaDescripcion; }
    public void setHistoriaDescripcion(String historiaDescripcion) { this.historiaDescripcion = historiaDescripcion; }
}