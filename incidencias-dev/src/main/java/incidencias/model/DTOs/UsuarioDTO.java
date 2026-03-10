package incidencias.model.DTOs;

public class UsuarioDTO {
    private int id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String rol;
    
    public UsuarioDTO() {}

    public UsuarioDTO(int id, String nombre, String apellidos, String correo, String rol) {
        this.id = id;        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.correo = correo;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getApellidos() { return apellidos; }
	public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}