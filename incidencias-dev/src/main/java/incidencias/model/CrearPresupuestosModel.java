package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.PresupuestoDTO;
import util.Util;
import util.database.Database;

public class CrearPresupuestosModel {

    private Database db = new Database();

    private static final String SQL_SELECT_PRESUPUESTO_ACTIVO = 
        "SELECT p.importe, p.concepto, p.vigencia, " +
        "COALESCE((SELECT SUM(costeTotal) FROM incidencia i WHERE i.tipo = p.idTipoIncidencia), 0) AS consumido " +
        "FROM presupuesto p " +
        "WHERE p.idTipoIncidencia = ? " +
        "ORDER BY p.id DESC LIMIT 1";

    private static final String SQL_INSERT_PRESUPUESTO = 
        "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) VALUES (?, ?, ?, ?)";

    public Object[] obtenerPresupuestoActivo(int idTipoIncidencia) {

        List<PresupuestoDTO> lista = db.executeQueryPojo(
                PresupuestoDTO.class, 
                SQL_SELECT_PRESUPUESTO_ACTIVO, 
                idTipoIncidencia
        );

        if (lista.isEmpty()) {
            return null;
        }

        PresupuestoDTO p = lista.get(0);

        return new Object[] {
            p.getImporte(),
            p.getConcepto(),
            p.getVigencia(),
            p.getConsumido()
        };
    }

    public void registrarPresupuesto(int idTipoIncidencia, double importe, String concepto, String vigencia) {
        Util.validateCondition(importe > 0, "El importe del presupuesto debe ser mayor a 0.");
        Util.validateCondition(concepto != null && !concepto.trim().isEmpty(), "El concepto no puede estar vacío.");

        db.executeUpdate(SQL_INSERT_PRESUPUESTO, idTipoIncidencia, importe, concepto, vigencia);
    }
}