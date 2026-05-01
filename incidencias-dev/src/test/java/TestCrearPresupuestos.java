import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import exceptions.ApplicationException;
import incidencias.model.CrearPresupuestosModel;
import util.database.Database;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class TestCrearPresupuestos {

    private static Database db = new Database();

    @BeforeEach
    public void setUp() {      
        db.createDatabase(true);
        // Insercion de tipologias validas para pruebas
        db.executeBatch(new String[] {
            "DELETE FROM presupuesto",
            "DELETE FROM tipoIncidencia",
            "INSERT INTO tipoIncidencia (nombre) VALUES ('Alumbrado')",
            "INSERT INTO tipoIncidencia (nombre) VALUES ('Limpieza')"
        });
    }

    @Test
    @DisplayName("CE Validas: Registro de presupuesto exitoso con actualizacion de la BBDD")
    public void testRegistrarPresupuestoValido() {
    	
    	CrearPresupuestosModel model = new CrearPresupuestosModel();
    	
        // Ejecucion el metodo con parametros validos (CEV1 - CEV4)
        model.registrarPresupuesto(1, 1500.50, "Presupuesto anual Alumbrado", "01/06/2026 - 31/12/2026");

        // Estado de la BBDD para verificar la salida
        List<Object[]> actual = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 1");

        // Verificacion de que todo se inserto correctamente y los datos coinciden (CEV5)
        assertAll("Verificacion de insercion en BBDD",
        	// Comprobacion de que se creo el registro en BBDD (CEI10)
            () -> assertEquals(1, actual.size(), "Debe existir exactamente 1 registro"),
            // Comprobacion de que los campos coinciden (CEI11)
            () -> assertEquals(1, actual.get(0)[1], "La tipologia de incidencia debe coincidir"),
            () -> assertEquals(1500.50, actual.get(0)[2], "El importe debe coincidir"),
            () -> assertEquals("Presupuesto anual Alumbrado", actual.get(0)[3], "El concepto debe coincidir"),
            () -> assertEquals("01/06/2026 - 31/12/2026", actual.get(0)[4], "El periodo de vigencia debe coincidir")
        );
    }

    @ParameterizedTest
    @CsvSource({
        // CEI1: Tipo de incidencia no existe (99)
        "99, 1000.0, 'Concepto válido', '01/06/2026 - 31/12/2026', 'El tipo de incidencia no existe'",
        
        // CEI2 y CEI3: Importe cero o negativo
        "1, 0.0,     'Concepto válido', '01/06/2026 - 31/12/2026', 'El importe debe ser mayor que cero'",
        "1, -500.50, 'Concepto válido', '01/06/2026 - 31/12/2026', 'El importe debe ser mayor que cero'",
        
        // CEI4 y CEI5: Concepto vacio o nulo
        "1, 1000.0, '',   '01/06/2026 - 31/12/2026', 'El concepto es obligatorio'",
        "1, 1000.0, null, '01/06/2026 - 31/12/2026', 'El concepto es obligatorio'",
        
        // CEI6 - CEI9: Logica de fechas invalida
        "1, 1000.0, 'Concepto válido', '01/06/2026 - 31/12/2026', 'El periodo de vigencia no tiene el formato adecuado'",
        "1, 1000.0, 'Concepto válido', null,                      'El periodo de vigencia no puede ser nulo'",
        "1, 1000.0, 'Concepto válido', '01/07/2026 - 30/06/2026', 'La fecha de inicio debe ser anterior a la de fin'",
        "1, 1000.0, 'Concepto válido', '01/06/2025 - 31/12/2026', 'La fecha de inicio debe ser posterior a la fecha actual'"
    })
    @DisplayName("CE Invalidas: Se producen excepciones y no se actualiza la BBDD")
    public void testRegistrarPresupuestosInvalidos(int idTipo, double importe, String concepto, String vigencia, String mensajeEsperado) {
    	
    	CrearPresupuestosModel model = new CrearPresupuestosModel();
        
    	// Comprobacion de que se detecta el fallo (CEV6)
    	
    	assertAll("Verificacion de la deteccion de errores",
            () -> {
            	// Comprobacion de que salta la excepción esperada (CEI12)
                assertThrows(ApplicationException.class,
                		() -> { model.registrarPresupuesto(idTipo, importe, concepto, vigencia); },
                		"Debe producirse una excepcion");
            },
            
            () -> {
                // Comprobacion de que la BBDD NO fue alterada (CEI13)
                List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto");
                assertEquals(0, bdEstado.size(), "No se debio insertar ningun presupuesto en la BBDD");
            }
        );
    }
}