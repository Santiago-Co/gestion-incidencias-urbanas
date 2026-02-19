package test_login.model.DTOs;

public class UsuarioDTO {
	
	private int id;

	public UsuarioDTO() {}

	public UsuarioDTO(int rowId) {
		this.id = rowId;
	}

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }

}
