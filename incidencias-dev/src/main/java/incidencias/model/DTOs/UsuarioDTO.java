package incidencias.model.DTOs;

public class UsuarioDTO {
    private int id;
    private String rol;
    private String correo;
    
    public UsuarioDTO() {}

    public UsuarioDTO(int id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}