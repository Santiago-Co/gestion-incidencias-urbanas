package entitiy; 

public class UsuarioEntity {
    private int id;
    private String dni;
    private String correo;
    private String rol;

    
    public UsuarioEntity() {}

    
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getDni() { return this.dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getCorreo() { return this.correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return this.rol; }
    public void setRol(String rol) { this.rol = rol; }
}