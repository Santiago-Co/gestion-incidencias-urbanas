package incidencias.model.Entitiy;

public class TipoIncidenciaTecnicoEntity {

	private int id;
	private int idTipo;
	private int idTecnico;
	
	public TipoIncidenciaTecnicoEntity() {}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getIdTipo() { return idTipo; }
	public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

	public int getIdTecnico() { return idTecnico; }
	public void setIdTecnico(int idTecnico) { this.idTecnico = idTecnico; }
	
}
