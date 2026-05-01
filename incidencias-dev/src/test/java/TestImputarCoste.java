import org.junit.jupiter.api.*;
import exceptions.ApplicationException;
import incidencias.model.CerrarIncidenciasEspecialidadModel;
import util.database.Database;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


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
            
            
            // Tipologias
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (1, 'Alumbrado')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (2, 'Limpieza')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (3, 'Otro')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (4, 'Zonas verdes')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (5, 'Mobiliario urbano')",
            "INSERT INTO tipoIncidencia (id, nombre) VALUES (6, 'Señalizacion y calzada')",
            
            
            // Presupuesto activo para Alumbrado: 1000€ (vigente en 2026)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (1, 1000.00, 'Fondo Alumbrado', '01/01/2026 - 31/12/2026')", 
            
            // Presupuesto activo para Otro: 2000€ (vigente en 2026)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (3, 2000.00, 'Fondo Otro', '01/01/2026 - 31/12/2026')",
            
            // Presupuesto activo para Zonas verdes: 500€ (vigente en 2026)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (4, 500.00, 'Fondo Zonas verdes', '01/01/2026 - 31/12/2026')", 
            
            // Presupuesto NO activo para Mobiliario urbano: 1000€ (vigente en 2025)
            "INSERT INTO presupuesto (idTipoIncidencia, importe, concepto, vigencia) " +
            "VALUES (5, 1000.00, 'Fondo Mobiliario urbano', '01/01/2025 - 31/12/2025')",
            
            // Presupuesto NO activo para Señalizacion y calzada: 1000€ (vigente en 2027)
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
    @DisplayName("CE Validas: Imputar coste cierra incidencia si hay fondos y estado es valido")
    public void testImputarCosteExito() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

    	assertAll(
            () -> {
            	// Ejecucion el metodo con condiciones validas (CEV7 - CEV11)
                assertDoesNotThrow(() -> {
                    model.getPresupuestoRestante(1);
                });
            },
            
            // Verificacion de que todo se actualizo correctamente (CEV12)

            () -> {
                // Comprobacion de que el metodo devuelve el resultado correcto (CEI20)
                double restante = model.getPresupuestoRestante(1);
                assertEquals(800, restante, "El presupuesto debe haberse reducido en 200€");
            },
            
            () -> {
                // Comprobacion de que la BBDD fue actualizada (CEI21)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 1");
                assertEquals(800, bdEstado.get(0)[2], "El presupuesto restante para Alumbrado debe ser 800€ en BBDD");
            }
        );
    }
    
    @Test
    @DisplayName("Error al imputar si la tipologia de incidencia no existe en el sistema")
    public void testImputarTipologiaInvalida() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();
        
    	// Ejecucion con tipologia invalida (CEI14 + CEV13)

    	assertAll(
            () -> {
            	// Comprobacion de que se lanza la excepcion esperada (CEI22)
                assertThrows(ApplicationException.class,
                		() -> {model.getPresupuestoRestante(10);}, // No hay tipologia con ID=10
                		"Debe producirse una excepcion"
                );
            },
            
            () -> {
                // Comprobamos que la BBDD NO fue alterada (CEI23)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto");
                assertAll("Verificacion de insercion en BBDD",
                    () -> assertEquals(800,  bdEstado.get(0)[2], "El presupuesto restante para Alumbrado debe ser 800€ en BBDD"),
                    () -> assertEquals(2000, bdEstado.get(1)[2], "El presupuesto restante para Otro debe ser 2000€ en BBDD"),
                    () -> assertEquals(500,  bdEstado.get(2)[2], "El presupuesto restante para Zonas verdes debe ser 500€ en BBDD"),
                    () -> assertEquals(1000, bdEstado.get(3)[2], "El presupuesto restante para Mobiliario urbano debe ser 1000€ en BBDD"),
                    () -> assertEquals(1000, bdEstado.get(4)[2], "El presupuesto restante para Señalizacion y calzada debe ser 1000€ en BBDD")
                );
            }
        );
    }
    
    @Test
    @DisplayName("Error al imputar si la incidencia no esta cerrada")
    public void testImputarIncidenciaAbierta() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();
        
    	// Ejecucion con incidencia abierta (CEI15 + CEV13)
    	
    	assertAll(
            () -> {
            	// Comprobacion de que se lanza la excepcion esperada (CEI22)
                assertThrows(ApplicationException.class,
                		() -> {model.getPresupuestoRestante(3);}, // La incidencia de tipo Otro (103) no esta cerrada
                		"Debe producirse una excepcion"
                );
            },
            
            () -> {
                // Comprobamos que la BBDD NO fue alterada (CEI23)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 3");
                assertEquals(2000, bdEstado.get(0)[2], "El presupuesto restante para Otro debe ser 2000€ en BBDD");
            }
        );
    }

    @Test
    @DisplayName("Error al imputar si no existe presupuesto para la tipologia")
    public void testImputarSinPresupuesto() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();
        
    	// Ejecucion sin presupuesto valido (CEI16 + CEV13)
    	
    	assertAll(
            () -> {
            	// Comprobacion de que se lanza la excepcion esperada (CEI22)
                assertThrows(ApplicationException.class,
                		() -> {model.getPresupuestoRestante(2);}, // Limpieza no tiene inserción en tabla presupuesto
                		"Debe producirse una excepcion"
                );
            },
            
            () -> {
                // Comprobamos que la BBDD NO fue alterada (CEI23)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto");
                assertAll("Verificacion de insercion en BBDD",
                    () -> assertEquals(800,  bdEstado.get(0)[2], "El presupuesto restante para Alumbrado debe ser 800€ en BBDD"),
                    () -> assertEquals(2000, bdEstado.get(1)[2], "El presupuesto restante para Otro debe ser 2000€ en BBDD"),
                    () -> assertEquals(500,  bdEstado.get(2)[2], "El presupuesto restante para Zonas verdes debe ser 500€ en BBDD"),
                    () -> assertEquals(1000, bdEstado.get(3)[2], "El presupuesto restante para Mobiliario urbano debe ser 1000€ en BBDD"),
                    () -> assertEquals(1000, bdEstado.get(4)[2], "El presupuesto restante para Señalizacion y calzada debe ser 1000€ en BBDD")
                );
            }
        );
    }
    
    @Test
    @DisplayName("Error si el periodo de vigencia del presupuesto no ha comenzado")
    public void testImputarPresupuestoNoActivo1() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

    	// Ejecucion con periodo de vigencia expirado (CEI17 + CEV13)
    	
    	assertAll(
            () -> {
            	// Comprobacion de que se lanza la excepcion esperada (CEI22)
                assertThrows(ApplicationException.class,
                		() -> {model.getPresupuestoRestante(5);}, // El periodo de vigencia era 2025
                		"Debe producirse una excepcion"
                );
            },
            
            () -> {
                // Comprobamos que la BBDD NO fue alterada (CEI23)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 5");
                assertEquals(1000, bdEstado.get(0)[2], "El presupuesto restante para Mobiliario urbano debe ser 1000€ en BBDD");
            }
        );
    }
    
    @Test
    @DisplayName("Error si el periodo de vigencia del presupuesto ya ha finalizado")
    public void testImputarPresupuestoNoActivo2() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

    	// Ejecucion con periodo de vigencia aun no alcanzado (CEI18 + CEV13)
    	
    	assertAll(
            () -> {
            	// Comprobacion de que se lanza la excepcion esperada (CEI22)
                assertThrows(ApplicationException.class,
                		() -> {model.getPresupuestoRestante(6);}, // El periodo de vigencia sera 2027
                		"Debe producirse una excepcion"
                );
            },
            
            () -> {
                // Comprobamos que la BBDD NO fue alterada (CEI23)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 6");
                assertEquals(1000, bdEstado.get(0)[2], "El presupuesto restante para Señalizacion y calzada debe ser 1000€ en BBDD");
            }
        );
    }

    @Test
    @DisplayName("Error si el coste supera los fondos disponibles")
    public void testImputarFondosInsuficientes() {
    	
    	CerrarIncidenciasEspecialidadModel model = new CerrarIncidenciasEspecialidadModel();

    	// Ejecucion con fondos insuficientes (CEI19 + CEV13)
    	
    	assertAll(
            () -> {
            	// Comprobacion de que se lanza la excepcion esperada (CEI22)
                assertThrows(ApplicationException.class,
                		() -> {model.getPresupuestoRestante(4);}, // El coste es 1500€, el presupuesto es 500€
                		"Debe producirse una excepcion"
                );
            },
            
            () -> {
                // Comprobamos que la BBDD NO fue alterada (CEI23)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 4");
                assertEquals(500, bdEstado.get(0)[2], "El presupuesto restante para Zonas verdes debe ser 500€ en BBDD");
            }
        );
    }
}
