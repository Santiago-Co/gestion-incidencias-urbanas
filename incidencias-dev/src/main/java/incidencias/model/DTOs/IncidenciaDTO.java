package incidencias.model.DTOs;

public class IncidenciaDTO {
	
	private int id;
	private String tipo;
	private String descripcion;
	private String fechaHora;
	private String estado;

	public IncidenciaDTO() {}

	public IncidenciaDTO(int rowId, String rowTipo, String rowDesc, String rowFecha, String rowEstado) {
		this.id = rowId;
		this.tipo = rowTipo;
		this.descripcion = rowDesc;
		this.fechaHora = rowFecha;
		this.estado = rowEstado;
	}

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
	public String getTipo() { return this.tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }
	public String getDescripcion() { return this.descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public String getFechaHora() { return this.fechaHora; }
	public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
	public String getEstado() { return this.estado; }
	public void setEstado(String estado) { this.estado = estado; }

}
