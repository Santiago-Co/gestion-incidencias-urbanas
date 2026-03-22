package incidencias.model.DTOs;

public class TrabajoDTO {
	private int id;
	private int idIncidencia;
	private String fecha;
    private String descripcion;

    public int getId() { return id; }
	public void setId(int id) {	this.id = id; }
	public int getIdIncidencia() { return idIncidencia; }
	public void setIdIncidencia(int idIncidencia) {	this.idIncidencia = idIncidencia; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
