package incidencias.model.DTOs;

public class TecnicoDTO {

	private int id;
    private String nombre;
    private String apellidos;
    private String especialidad;
    
    public TecnicoDTO() {}
    
    public TecnicoDTO(int id, String nombre, String apellidos, String especialidad) {
    	this.id = id;
    	this.nombre = nombre;
    	this.apellidos = apellidos;
    	this.especialidad = especialidad;
    }
    
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getApellidos() { return apellidos; }
	public void setApellidos(String apellidos) { this.apellidos = apellidos; }
	public String getEspecialidad() { return especialidad; }
	public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
