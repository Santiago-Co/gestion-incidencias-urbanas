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
        db.executeBatch(new String[] {
            "DELETE FROM presupuesto",
            "DELETE FROM tipoIncidencia",
            // Insertamos tipologías válidas para satisfacer las Foreign Keys (CEV1)
            "INSERT INTO tipoIncidencia (nombre) VALUES ('Alumbrado')",
            "INSERT INTO tipoIncidencia (nombre) VALUES ('Limpieza')"
        });
    }

    @Test
    @DisplayName("CE Válidas: Registro de presupuesto exitoso actualiza la BD")
    public void testRegistrarPresupuestoValido() {
    	CrearPresupuestosModel model = new CrearPresupuestosModel();
        // Ejecutamos el método con parámetros completamente válidos (CEV1, CEV2, CEV3, CEV4, CEV5)
        model.registrarPresupuesto(1, 1500.50, "Presupuesto anual Alumbrado", "01/06/2026 - 31/12/2026");

        // Obtenemos el estado de la BD para verificar la Salida (como en el PDF)
        List<Object[]> actual = db.executeQueryArray("SELECT * FROM presupuesto WHERE idTipoIncidencia = 1");

        // Verificamos que se insertó correctamente y los datos coinciden
        assertAll("Verificación de inserción en BD",
            () -> assertEquals(1, actual.size(), "Debe existir exactamente 1 registro"),
            () -> assertEquals(1, actual.get(0)[1], "La tipología de incidencia debe coincidir"),
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
        
        // CEI4: Concepto vacío o nulo
        "1, 1000.0, '',   '01/06/2026 - 31/12/2026', 'El concepto es obligatorio'",
        "1, 1000.0, null, '01/06/2026 - 31/12/2026', 'El concepto es obligatorio'",
        
        // CEI8 y CEI9: Lógica de fechas inválida (Fin antes de inicio, o en el pasado)
        "1, 1000.0, 'Concepto válido', '01/06/2026 - 31/12/2026', 'El periodo de vigencia no tiene el formato adecuado'",
        "1, 1000.0, 'Concepto válido', null,                      'El periodo de vigencia no puede ser nulo'",
        "1, 1000.0, 'Concepto válido', '01/07/2026 - 30/06/2026', 'La fecha de inicio debe ser anterior a la de fin'",
        "1, 1000.0, 'Concepto válido', '01/06/2025 - 31/12/2026', 'La fecha de inicio debe ser posterior a la fecha actual'"
    })
    @DisplayName("CE Inválidas: Validaciones lanzan excepciones correctas")
    public void testRegistrarPresupuestoInvalidos(int idTipo, double importe, String concepto, String vigencia, String mensajeEsperado) {
    	CrearPresupuestosModel model = new CrearPresupuestosModel();
        // Comprobamos que salta la excepción esperada (ApplicationException o la que uses en tu proyecto)
        assertThrows(ApplicationException.class,
        		() -> { model.registrarPresupuesto(idTipo, importe, concepto, vigencia); },
        		"Debe producirse una excepción"
        );
        
        // Comprobamos que la base de datos NO fue alterada
        List<Object[]> bdEstado = db.executeQueryArray("SELECT * FROM presupuesto");
        assertEquals(0, bdEstado.size(), "No se debió insertar ningún presupuesto en la BD");
    }
}