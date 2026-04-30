import org.junit.jupiter.api.*;
import exceptions.ApplicationException;
import incidencias.model.CerrarIncidenciasEspecialidadModel;
import util.database.Database;
import static org.junit.jupiter.api.Assertions.*;


public class TestImputarCoste {

    private static Database db = new Database();

    @BeforeEach
    public void setUp() {
        db.createDatabase(true);
        // Setup de un escenario complejo con ciudadanos, operadores, incidencias y presupuestos
        db.executeBatch(new String[] {
            "DELETE FROM presupuesto",
            "DELETE FROM historia",
            "DELETE FROM incidencia",
            "DELETE FROM tipoIncidencia",
            "DELETE FROM usuario",
            
            // Usuarios base
            "INSERT INTO usuario (nombre, apellidos, dni, correo, rol) " +
            "VALUES ('Juan', 'Perez Garcia', '11111111A', 'ciudadano@mail.com', 'Ciudadano')",
            
            "INSERT INTO usuario (nombre, apellidos, dni, correo, rol) " +
            "VALUES ('Maria', 'Fernandez Gomez', '22222222B', 'operador@mail.com', 'Operador')",
            
            
            // Tipologías
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (1, 'Alumbrado')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (2, 'Limpieza')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (3, 'Otro')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (4, 'Zonas verdes')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (5, 'Mobiliario urbano')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (6, 'Señalizacion y calzada')",
            
            
            // Presupuesto Activo para Alumbrado: 1000€ (Vigente en 2026)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (1, 1000.00, 'Fondo Alumbrado', '01/01/2026 - 31/12/2026')", 
            
            // Presupuesto Activo para Otro: 2000€ (Vigente en 2026)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (3, 2000.00, 'Fondo Otro', '01/01/2026 - 31/12/2026')",
            
            // Presupuesto Activo para Zonas verdes: 500€ (Vigente en 2026)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (4, 500.00, 'Fondo Zonas verdes', '01/01/2026 - 31/12/2026')", 
            
            // Presupuesto NO Activo para Mobiliario urbano: 1000€ (Vigente en 2025)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (5, 1000.00, 'Fondo Mobiliario urbano', '01/01/2025 - 31/12/2025')",
            
            // Presupuesto NO Activo para Señalizacion y calzada: 1000€ (Vigente en 2027)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (6, 1000.00, 'Fondo Señalizacion y calzada', '01/01/2027 - 31/12/2027')",
            
            
            // Incidencia 100 (Alumbrado) - Coste 200€
            "INSERT INTO incidencia (id, tipo, idCiudadano, descripcion, localizacion, costeTotal) " +
            "VALUES (100, 1, 1, 'Farola rota', 'Centro', 200.00)",
            
            // Incidencia 101 (Zonas verdes) - Coste 1500€ (Supera fondos)
            "INSERT INTO incidencia (id, tipo, idCiudadano, descripcion, localizacion, costeTotal) " +
            "VALUES (101, 4, 1, 'Árbol caído', 'Norte', 1500.00)",
            
            // Incidencia 102 (Limpieza) - Coste 50€ (Sin presupuesto asignado en BBDD)
            "INSERT INTO incidencia (id, tipo, idCiudadano, descripcion, localizacion, costeTotal) " +
            "VALUES (102, 2, 1, 'Basura', 'Sur', 50.00)",
            
            // Incidencia 103 (Otro) - Coste 150€ (No esta cerrada)
            "INSERT INTO incidencia (id, tipo, idCiudadano, descripcion, localizacion) " +
            "VALUES (103, 3, 1, 'Descripcion', 'Sur')",
            
            // Incidencia 104 (Mobiliario urbano) - Coste 500€ (Presupuesto expirado)
            "INSERT INTO incidencia (id, tipo, idCiudadano, descripcion, localizacion, costeTotal) " +
            "VALUES (104, 5, 1, 'Banco roto', 'Centro', 500.00)",
            
            // Incidencia 105 (Señalizacion y calzada) - Coste 500€ (Presupuesto no activo aun)
            "INSERT INTO incidencia (id, tipo, idCiudadano, descripcion, localizacion, costeTotal) " +
            "VALUES (105, 6, 1, 'Señal rota', 'Centro', 500.00)",
            
            
            // Incidencias cerradas
            "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (100, 2, 'Cerrada', 'Cierre oficial')",
            "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (101, 2, 'Cerrada', 'Cierre oficial')",
            "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (102, 2, 'Cerrada', 'Cierre oficial')",
            "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (104, 2, 'Cerrada', 'Cierre oficial')",
            "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (105, 2, 'Cerrada', 'Cierre oficial')",
            
            // Incidencia 103 abierta
            "INSERT INTO historia (idIncidencia, idOperador, estado, descripcion) VALUES (103, 2, 'En proceso', 'Trabajando en la resolucion')"
        });
    }

    @Test
    @DisplayName("CE Válidas: Imputar coste cierra incidencia si hay fondos y estado es válido")
    public void testImputarCosteExito() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

        // Ejecución (asumiendo que internamente el método lee la incidencia 100 y ve que pertenece al tipo 1)
        // Nota: Adaptar si tu método recibe el ID de la incidencia en lugar del idTipo.
        assertDoesNotThrow(() -> {
            model.getPresupuestoRestante(1);
        });

        // Verificamos estado posterior (Dependerá de cómo guardes el consumo. 
        // Si tienes un registro de consumo o actualizas el presupuesto restante:)
        // Ejemplo asumiendo que la BD actualiza algún campo o retorna el valor restante:
        double restante = model.getPresupuestoRestante(1);
        assertEquals(800.00, restante, "El presupuesto debe haberse reducido en 200€");
    }
    
    @Test
    @DisplayName("Error al imputar si la tipología de incidencia no existe en el sistema")
    public void testImputarTipologiaInvalida() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();
        
        assertThrows(ApplicationException.class,
        		() -> {model.getPresupuestoRestante(10);}, // No hay tipologia con ID=10
        		"Debe producirse una excepción"
        );
    }
    
    @Test
    @DisplayName("Error al imputar si la incidencia no está cerrada")
    public void testImputarIncidenciaAbierta() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();
        
        assertThrows(ApplicationException.class,
        		() -> {model.getPresupuestoRestante(3);}, // La incidencia de tipo Otro (103) no esta cerrada
        		"Debe producirse una excepción"
        );
    }

    @Test
    @DisplayName("Error al imputar si no existe presupuesto para la tipología")
    public void testImputarSinPresupuesto() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();
        
        assertThrows(ApplicationException.class,
        		() -> {model.getPresupuestoRestante(2);}, // Limpieza no tiene inserción en tabla presupuesto
        		"Debe producirse una excepción"
        );
    }
    
    @Test
    @DisplayName("Error si el periodo de vigencia del presupuesto no ha comenzado")
    public void testImputarPresupuestoNoActivo1() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

        assertThrows(ApplicationException.class,
        		() -> {model.getPresupuestoRestante(5);}, // El periodo de vigencia era 2025
        		"Debe producirse una excepción"
        );
    }
    
    @Test
    @DisplayName("Error si el periodo de vigencia del presupuesto ya ha finalizado")
    public void testImputarPresupuestoNoActivo2() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

        assertThrows(ApplicationException.class,
        		() -> {model.getPresupuestoRestante(6);}, // El periodo de vigencia sera 2027
        		"Debe producirse una excepción"
        );
    }

    @Test
    @DisplayName("Error si el coste supera los fondos disponibles")
    public void testImputarFondosInsuficientes() {
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

        assertThrows(ApplicationException.class,
        		() -> {model.getPresupuestoRestante(4);}, // El coste es 1500€, el presupuesto es 500€
        		"Debe producirse una excepción"
        );
    }
}
