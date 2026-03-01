package incidencias.model.DTOs;

/**
 * Data Transfer Object que contiene las filas relativas a los
 * datos de una historia. Contiene; el ID de la historia; el ID
 * de la incidencia, técnico y operador asociados; la fecha de
 * cambio de estado de la incidencia; el estado de la incidencia
 * en ese punto; y la descripción del estado.
 */
public class HistoriaDTO {
	
	private int id;
	private int idIncidencia;
	private int idTecnico;
	private int idOperador;
	private String fechaCambio;
	private String estado;
	private String descripcion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdIncidencia() {
		return idIncidencia;
	}
	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
	public int getIdTecnico() {
		return idTecnico;
	}
	public void setIdTecnico(int idTecnico) {
		this.idTecnico = idTecnico;
	}
	public int getIdOperador() {
		return idOperador;
	}
	public void setIdOperador(int idOperador) {
		this.idOperador = idOperador;
	}
	public String getFechaCambio() {
		return fechaCambio;
	}
	public void setFechaCambio(String fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
