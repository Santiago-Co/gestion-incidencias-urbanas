package incidencias.model.DTOs;

public class PresupuestoDTO {

    private double importe;
    private String concepto;
    private String vigencia;
    private double consumido;

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public String getVigencia() { return vigencia; }
    public void setVigencia(String vigencia) { this.vigencia = vigencia; }

    public double getConsumido() { return consumido; }
    public void setConsumido(double consumido) { this.consumido = consumido; }
}