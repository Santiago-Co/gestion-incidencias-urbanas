package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import incidencias.model.AsignacionIncidenciasModel;
import incidencias.model.CerrarIncidenciasEspecialidadModel;
import incidencias.model.ClasificarIncidenciasModel;
import incidencias.model.CrearIncidenciaModel;
import incidencias.model.PlanificacionIncidenciasModel;
import incidencias.model.ReabrirIncidenciaCiudadanoModel;
import incidencias.model.ResolverIncidenciasModel;
import incidencias.model.Entitiy.IncidenciaEntity;
import util.database.Database;

public class Test_uo296954 {

    private Database db;

    @BeforeEach
    public void setUpDatabase() {
        db = new Database();
        db.createDatabase(false);
        db.loadDatabase();
    }

    @Test
    public void crearIncidenciaInsertaEstadoNueva() {
        CrearIncidenciaModel model = new CrearIncidenciaModel();
        IncidenciaEntity incidencia = new IncidenciaEntity();
        incidencia.setTipo(1);
        incidencia.setIdCiudadano(1);
        incidencia.setDescripcion("Farola de prueba");
        incidencia.setLocalizacion("Centro");

        int idNuevaIncidencia = model.registrarYRecuperarIncidencia(incidencia).getId();

        assertEquals("Nueva", getUltimoEstado(idNuevaIncidencia));
    }

    @Test
    public void clasificarIncidenciaValidarInsertaEstadoValidada() {
        ClasificarIncidenciasModel model = new ClasificarIncidenciasModel();

        model.actualizarEstadoIncidenciaValidar(1, 3, "Alumbrado");

        assertEquals("Validada", getUltimoEstado(1));
    }

    @Test
    public void clasificarIncidenciaEliminarInsertaEstadoRechazado() {
        ClasificarIncidenciasModel model = new ClasificarIncidenciasModel();

        model.actualizarEstadoIncidenciaEliminar(3, 3, "Limpieza", "Datos insuficientes");

        assertEquals("Rechazado", getUltimoEstado(3));
    }

    @Test
    public void asignacionIncidenciaInsertaEstadoAsignadaYEquipoTecnico() {
        AsignacionIncidenciasModel model = new AsignacionIncidenciasModel();

        boolean asignada = model.asignarIncidencia(2, new int[] { 4, 9 }, 3);

        assertTrue(asignada);
        assertEquals("Asignada", getUltimoEstado(2));
        assertEquals(2, getNumeroTecnicosUltimaHistoria(2));
    }

    @Test
    public void planificacionIncidenciaInsertaEstadoEnProceso() {
        PlanificacionIncidenciasModel model = new PlanificacionIncidenciasModel();

        model.planificarIncidencia(5, 4, 6, "Plan de trabajo");

        assertEquals("En proceso", getUltimoEstado(5));
    }

    @Test
    public void planificacionEliminarInsertaEstadoRechazado() {
        PlanificacionIncidenciasModel model = new PlanificacionIncidenciasModel();

        model.actualizarEstadoIncidenciaEliminar(5, 4, "No procede actuar");

        assertEquals("Rechazado", getUltimoEstado(5));
    }

    @Test
    public void resolverIncidenciaInsertaEstadoResuelta() {
        ResolverIncidenciasModel model = new ResolverIncidenciasModel();

        model.resolverIncidencia(7, 8, 3, "Trabajo finalizado", 25.0, 7.0);

        assertEquals("Resuelta", getUltimoEstado(7));
    }

    @Test
    public void resolverEliminarInsertaEstadoRechazado() {
        ResolverIncidenciasModel model = new ResolverIncidenciasModel();

        model.actualizarEstadoIncidenciaEliminar(7, 8, "Sin posibilidad tecnica");

        assertEquals("Rechazado", getUltimoEstado(7));
    }

    @Test
    public void cerrarIncidenciaInsertaEstadoCerrada() {
        CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

        model.cerrarIncidencia(13, 14, "Cierre validado");

        assertEquals("Cerrada", getUltimoEstado(13));
    }

    @Test
    public void reabrirIncidenciaInsertaEstadoNueva() {
        ReabrirIncidenciaCiudadanoModel model = new ReabrirIncidenciaCiudadanoModel();

        model.reabrirIncidencia(10, "Sigue sin arreglarse");

        assertEquals("Nueva", getUltimoEstado(10));
    }

    @Test
    public void falloIntencional_crearIncidenciaConObjetoNull() {
        CrearIncidenciaModel model = new CrearIncidenciaModel();
        assertDoesNotThrow(() -> model.registrarYRecuperarIncidencia(null));
    }

    @Test
    public void falloIntencional_clasificarIncidenciaConTipoNull() {
        ClasificarIncidenciasModel model = new ClasificarIncidenciasModel();
        assertDoesNotThrow(() -> model.actualizarEstadoIncidenciaValidar(1, 3, null));
    }

    private String getUltimoEstado(int idIncidencia) {
        List<Object[]> rows = db.executeQueryArray(
                "SELECT estado FROM historia WHERE idIncidencia = ? ORDER BY id DESC LIMIT 1",
                idIncidencia);
        return rows.get(0)[0].toString();
    }

    private int getNumeroTecnicosUltimaHistoria(int idIncidencia) {
        List<Object[]> rows = db.executeQueryArray(
                "SELECT COUNT(*) FROM equipoTecnicos et "
                        + "JOIN historia h ON h.id = et.idHistoria "
                        + "WHERE h.idIncidencia = ? "
                        + "AND h.id = (SELECT MAX(id) FROM historia WHERE idIncidencia = ?)",
                idIncidencia, idIncidencia);
        Number count = (Number) rows.get(0)[0];
        return count.intValue();
    }
}
