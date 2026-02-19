package incidencias.model.Entitiy;


public class IncidenciaEntity {
	private int id; 
	private int ciudadanoId;
	private int tecnicoId;
	private String fechaRegistro;
	private String estado; 
	private String tipo; 
	private String descripcion; 
	private String localizacion; 
	
	
	
	
	public IncidenciaEntity() {}
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	public int getCiudadanoId() { return this.ciudadanoId; }
	public void setCiudadanoId(int ciudadanoId) { this.ciudadanoId = ciudadanoId; }
	public int getTecnicoId() { return this.tecnicoId; }
	public void setTecnicoId(int tecnicoId) { this.tecnicoId = tecnicoId; }
	public String getFechaRegistro() { return this.fechaRegistro; }
	public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
	public String getEstado() { return this.estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public String getTipo() { return this.tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }
	public String getDescripcion() { return this.descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public String getLocalizacion() { return this.localizacion;	}
	public void setLocalizacion(String localizacion) { this.localizacion = localizacion; }
	
}