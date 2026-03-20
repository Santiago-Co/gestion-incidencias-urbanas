package incidencias.model.DTOs;

public class TecnicoDTO {

	private int id;
    private String nombre;
    private String apellidos;
    private String tipo;
    
    public TecnicoDTO() {}
    
    public TecnicoDTO(int id, String nombre, String apellidos, String tipo) {
    	this.id = id;
    	this.nombre = nombre;
    	this.apellidos = apellidos;
    	this.tipo = tipo;
    }
    
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getApellidos() { return apellidos; }
	public void setApellidos(String apellidos) { this.apellidos = apellidos; }
	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }
}
