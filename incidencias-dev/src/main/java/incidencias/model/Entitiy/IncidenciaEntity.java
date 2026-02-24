package incidencias.model.Entitiy;


public class IncidenciaEntity {
	private int id; 
	private int idCiudadano;
	private String tipo; 
	private String descripcion; 
	private String localizacion; 
	private int horasPrevistas;
	private String descripcionTrabajo;
	
	public IncidenciaEntity() {}
	
	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	
	public int getIdCiudadano() { return this.idCiudadano; }
	public void setIdCiudadano(int ciudadanoId) { this.idCiudadano = ciudadanoId; }
	
	public String getTipo() { return this.tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }
	
	public String getDescripcion() { return this.descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	
	public String getLocalizacion() { return this.localizacion;	}
	public void setLocalizacion(String localizacion) { this.localizacion = localizacion; }

	public String getDescripcionTrabajo() { return descripcionTrabajo;	}
	public void setDescripcionTrabajo(String descripcionTrabajo) { this.descripcionTrabajo = descripcionTrabajo; }

	public int getHorasPrevistas() { return horasPrevistas; }
	public void setHorasPrevistas(int horasPrevistas) { this.horasPrevistas = horasPrevistas; }
}