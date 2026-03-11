package incidencias.model.DTOs;

public class IncidenciaDTO {

    private int id;
    private int idCiudadano;
    private String tipo;
    private String descripcion;
    private String localizacion;
    private int horasPrevistas;
    private int horasReales;
    private String descripcionPreviaTrabajo;
    private String descripcionFinalTrabajo;
    private String fechaCambio; // de la clase "Historia"
    private String estado;      // de la clase "Historia"
    
    public IncidenciaDTO() {}

    public IncidenciaDTO(int rowId, String rowTipo, String rowDesc, String rowLoc,
    					 int rowHP, String rowDescT, String rowFecha, String rowEstado)
    {
        this.id = rowId;
        this.tipo = rowTipo;
        this.descripcion = rowDesc;
        this.localizacion = rowLoc;
        this.horasPrevistas = rowHP;
        this.descripcionPreviaTrabajo = rowDescT;
        this.fechaCambio = rowFecha;
        this.estado = rowEstado;
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public int getCiudadanoId() { return idCiudadano; }
    public void setCiudadanoId(int ciudadanoId) { this.idCiudadano = ciudadanoId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getLocalizacion() { return localizacion; }
    public void setLocalizacion(String localizacion) { this.localizacion = localizacion; }

    public int getHorasPrevistas() { return horasPrevistas; }
    public void setHorasPrevistas(int horasPrevistas) { this.horasPrevistas = horasPrevistas; }

    public String getDescripcionPreviaTrabajo() { return descripcionPreviaTrabajo; }
    public void setDescripcionPreviaTrabajo(String descripcionTrabajo) { this.descripcionPreviaTrabajo = descripcionTrabajo; }

	public String getFechaCambio() { return fechaCambio; }
	public void setFechaCambio(String fechaCambio) { this.fechaCambio = fechaCambio; }

	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }

	public String getDescripcionFinalTrabajo() { return descripcionFinalTrabajo; }
	public void setDescripcionFinalTrabajo(String descripcionFinalTrabajo) { this.descripcionFinalTrabajo = descripcionFinalTrabajo; }

	public int getHorasReales() { return horasReales; }
	public void setHorasReales(int horasReales) { this.horasReales = horasReales; }
}