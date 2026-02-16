package dto;



public class IncidenciaDTO {

    private int id;
    private int ciudadanoId;
    private Integer tecnicoId;   
    private String fechaRegistro;
    private String estado;
    private String tipo;
    private String descripcion;
    private String localizacion;
    
    public IncidenciaDTO() {}

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public int getCiudadanoId() { return ciudadanoId; }
    public void setCiudadanoId(int ciudadanoId) { this.ciudadanoId = ciudadanoId; }
    public Integer getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Integer tecnicoId) { this.tecnicoId = tecnicoId; }
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getLocalizacion() { return localizacion; }
    public void setLocalizacion(String localizacion) { this.localizacion = localizacion; }
}