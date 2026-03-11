package incidencias.model.Entitiy;


public class IncidenciaEntity {
	private int id; 
	private int idCiudadano;
	private String tipo; 
	private String descripcion; 
	private String localizacion; 
	private int horasPrevistas;
	private int horasReales;
	private String descripcionPreviaTrabajo;
	private String descripcionFinalTrabajo;
	
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

	public String getDescripcionPreviaTrabajo() { return descripcionPreviaTrabajo;	}
	public void setDescripcionPreviaTrabajo(String descripcionTrabajo) { this.descripcionPreviaTrabajo = descripcionTrabajo; }

	public int getHorasPrevistas() { return horasPrevistas; }
	public void setHorasPrevistas(int horasPrevistas) { this.horasPrevistas = horasPrevistas; }

	public String getDescripcionFinalTrabajo() { return descripcionFinalTrabajo; }
	public void setDescripcionFinalTrabajo(String descripcionFinalTrabajos) { this.descripcionFinalTrabajo = descripcionFinalTrabajos; }

	public int getHorasReales() { return horasReales; }
	public void setHorasReales(int horasReales) { this.horasReales = horasReales; }
}