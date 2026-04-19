package incidencias.model.Entitiy;

public class HistoriaEntity {

	private int id;
	private int idIncidencia;
	private int idOperador;
	private String fechaCambio;
	private String estado;
	private String descripcion;
	
	public HistoriaEntity() {
		
	}
	
	public HistoriaEntity(int id, int idIncidencia, int idOperador, String fechaCambio, String estado, String descripcion) {
		this.setId(id);
		this.setIdIncidencia(idIncidencia);
		this.setIdOperador(idOperador);
		this.setFechaCambio(fechaCambio);
		this.setEstado(estado);
		this.setDescripcion(descripcion);
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public int getIdIncidencia() { return idIncidencia; }
	public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }
	public int getIdOperador() { return idOperador; }
	public void setIdOperador(int idOperador) { this.idOperador = idOperador; }
	public String getFechaCambio() { return fechaCambio; }
	public void setFechaCambio(String fechaCambio) { this.fechaCambio = fechaCambio; }
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
