package incidencias.model;

public class UsuarioDTO {
	
	private int id;

	public UsuarioDTO() {}

	public UsuarioDTO(int rowId) {
		this.id = rowId;
	}

	public int getId() { return this.id; }

}
