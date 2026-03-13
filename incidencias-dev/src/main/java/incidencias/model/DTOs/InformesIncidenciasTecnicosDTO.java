package incidencias.model.DTOs;

public class InformesIncidenciasTecnicosDTO {
	
	private int id;
	private String nombre;
	private String apellidos;
	private int numAsignadas;
	private int numResueltas;
	private int horasReales;
	
	public InformesIncidenciasTecnicosDTO() {}
	
	public InformesIncidenciasTecnicosDTO(int id, String nombre, String apellidos, int numAsignadas, int numResueltas, int horasReales) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.numAsignadas = numAsignadas;
		this.numResueltas = numResueltas;
		this.horasReales = horasReales;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getApellidos() { return apellidos; }
	public void setApellidos(String apellidos) { this.apellidos = apellidos; }
	public int getNumAsignadas() { return numAsignadas; }
	public void setNumAsignadas(int numAsignadas) { this.numAsignadas = numAsignadas; }
	public int getNumResueltas() { return numResueltas; }
	public void setNumResueltas(int numResueltas) { this.numResueltas = numResueltas; }
	public int getHorasReales() { return horasReales; }
	public void setHorasReales(int horasReales) { this.horasReales = horasReales; }
}
