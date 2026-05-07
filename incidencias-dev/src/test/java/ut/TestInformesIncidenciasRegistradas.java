package ut;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.ApplicationException;
import incidencias.model.DTOs.IncidenciaDTO;
import incidencias.model.InformesIncidenciasRegistradasModel;
import util.database.Database;

/**
 * Pruebas del modelo de informes de incidencias registradas.
 */
public class TestInformesIncidenciasRegistradas {

	private static Database db = new Database();

	@BeforeEach
	public void setUp() {
		db.createDatabase(true);
		loadCleanDatabase(db);
	}

	@AfterEach
	public void tearDown() {
	}

	public static void loadCleanDatabase(Database db) {
		db.executeBatch(new String[] {
				"delete from equipoTecnicos",
				"delete from trabajo",
				"delete from historia",
				"delete from incidencia",
				"delete from tipoIncidenciaTecnico",
				"delete from presupuesto",
				"delete from tipoIncidencia",
				"delete from usuario",
				"delete from parametros",

				"insert into usuario(id, nombre, apellidos, dni, correo, rol) values " +
						"(1, 'Ana', 'Ciudadana', '11111111A', 'ana@mail.com', 'Ciudadano')",
				"insert into usuario(id, nombre, apellidos, dni, correo, rol) values " +
						"(2, 'Luis', 'Operador', '22222222B', 'luis@mail.com', 'Operador')",

				"insert into tipoIncidencia(id, nombre) values (1, 'Alumbrado')",
				"insert into tipoIncidencia(id, nombre) values (2, 'Limpieza')",
				"insert into tipoIncidencia(id, nombre) values (3, 'Zonas verdes')",
				"insert into tipoIncidencia(id, nombre) values (4, 'Mobiliario urbano')",
				"insert into tipoIncidencia(id, nombre) values (5, 'Señalizacion y calzada')",
				"insert into tipoIncidencia(id, nombre) values (6, 'Mantenimiento')",

				"insert into incidencia(id, tipo, idCiudadano, descripcion, localizacion) values " +
						"(100, 1, 1, 'Farola rota', 'Centro')",
				"insert into incidencia(id, tipo, idCiudadano, descripcion, localizacion) values " +
						"(101, 1, 1, 'Cable suelto', 'Norte')",
				"insert into incidencia(id, tipo, idCiudadano, descripcion, localizacion) values " +
						"(102, 2, 1, 'Basura', 'Centro')",
				"insert into incidencia(id, tipo, idCiudadano, descripcion, localizacion) values " +
						"(103, 3, 1, 'Arbol caido', 'Sur')",
				"insert into incidencia(id, tipo, idCiudadano, descripcion, localizacion) values " +
						"(104, 4, 1, 'Banco roto', 'Centro')",
				"insert into incidencia(id, tipo, idCiudadano, descripcion, localizacion) values " +
						"(105, 5, 1, 'Señal rota', 'Este')",

				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1001, 100, 2, '2026-01-01 08:00:00', 'Nueva', 'Alta')",
				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1002, 100, 2, '2026-01-01 12:00:00', 'Validada', 'Validacion')",

				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1011, 101, 2, '2026-01-02 08:00:00', 'Nueva', 'Alta')",

				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1021, 102, 2, '2026-01-03 08:00:00', 'En proceso', 'Alta')",

				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1031, 103, 2, '2026-01-04 08:00:00', 'Resuelta', 'Alta')",

				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1041, 104, 2, '2026-01-05 08:00:00', 'Nueva', 'Alta')",

				"insert into historia(id, idIncidencia, idOperador, fechaCambio, estado, descripcion) values " +
						"(1051, 105, 2, '2026-01-06 08:00:00', 'Rechazado', 'Alta')"
		});
	}

	// //////////////////////////////////////////////
	// Pruebas de obtención de incidencias por filtro
	// //////////////////////////////////////////////

	/**
	 * CP1:
	 * Intervalo válido con varias incidencias.
	 * Cubre CE-F1, CE-F5, CE-F6, CE-S3, CE-S4, CE-S5 y CE-S6.
	 */
	@Test
	public void testGetIncidenciasPorFechaValida() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Fecha", "2026-01-01", "2026-01-06");

		assertAll("CP1 - Fecha válida con varias incidencias",
				() -> assertEquals(6, incidencias.size(), "El número de incidencias es incorrecto"),
				() -> assertEquals(105, incidencias.get(0).getId()),
				() -> assertEquals("Señalizacion y calzada", incidencias.get(0).getTipo()),
				() -> assertEquals("Señal rota", incidencias.get(0).getDescripcion()),
				() -> assertEquals("Este", incidencias.get(0).getLocalizacion()),
				() -> assertEquals("2026-01-06 08:00:00", fechaAsText(incidencias.get(0).getFechaCambio())),
				() -> assertEquals("Rechazado", incidencias.get(0).getEstado()),

				() -> assertEquals(100, incidencias.get(5).getId()),
				() -> assertEquals("Alumbrado", incidencias.get(5).getTipo()),
				() -> assertEquals("Farola rota", incidencias.get(5).getDescripcion()),
				() -> assertEquals("Centro", incidencias.get(5).getLocalizacion()),
				() -> assertEquals("2026-01-01 08:00:00", fechaAsText(incidencias.get(5).getFechaCambio())),
				() -> assertEquals("Validada", incidencias.get(5).getEstado()));
	}

	/**
	 * CP2:
	 * Fecha de inicio nula.
	 * Cubre CE-F2.
	 */
	@Test
	public void testGetIncidenciasPorFechaInicioNula() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Fecha", null, "2026-01-06");
		});
	}

	/**
	 * CP3:
	 * Fecha de fin nula.
	 * Cubre CE-F3.
	 */
	@Test
	public void testGetIncidenciasPorFechaFinNula() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Fecha", "2026-01-01", null);
		});
	}

	/**
	 * CP4:
	 * Fecha de inicio posterior a la de fin.
	 * Cubre CE-F4.
	 */
	@Test
	public void testGetIncidenciasPorFechaIntervaloIncoherente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Fecha", "2026-01-06", "2026-01-01");
		});
	}
	
	/**
	 * CP5:
	 * Formato de fecha no válido.
	 * Cubre CE-F7.
	 */
	@Test
	public void testGetIncidenciasPorFechaFormatoInvalido() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Fecha", "01/01/2026", "2026-01-06");
		});
	}

	/**
	 * CP6:
	 * Tipo existente.
	 * Cubre CE-T1, CE-S2, CE-S5 y CE-S6.
	 */
	@Test
	public void testGetIncidenciasPorTipoExistente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Tipo", "Limpieza");

		assertAll("CP6 - Tipo existente con una única incidencia",
				() -> assertEquals(1, incidencias.size(), "El número de incidencias debe ser 1"),
				() -> assertEquals(102, incidencias.get(0).getId()),
				() -> assertEquals("Limpieza", incidencias.get(0).getTipo()),
				() -> assertEquals("Basura", incidencias.get(0).getDescripcion()),
				() -> assertEquals("Centro", incidencias.get(0).getLocalizacion()),
				() -> assertEquals("2026-01-03 08:00:00", fechaAsText(incidencias.get(0).getFechaCambio())),
				() -> assertEquals("En proceso", incidencias.get(0).getEstado()));
	}

	/**
	 * CP7:
	 * Tipo nulo.
	 * Cubre CE-T2.
	 */
	@Test
	public void testGetIncidenciasPorTipoNulo() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Tipo", (String) null);
		});
	}

	/**
	 * CP8:
	 * Tipo no existente.
	 * Cubre CE-T3 y CE-S1.
	 */
	@Test
	public void testGetIncidenciasPorTipoNoExistente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Tipo", "Mantenimiento");

		assertEquals(0, incidencias.size(), "La lista debe estar vacía");
	}

	/**
	 * CP9:
	 * Localización existente.
	 * Cubre CE-L1, CE-S3, CE-S4, CE-S5 y CE-S6.
	 */
	@Test
	public void testGetIncidenciasPorLocalizacionExistente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Localización", "Centro");

		assertAll("CP9 - Localización existente",
				() -> assertEquals(3, incidencias.size(), "El número de incidencias es incorrecto"),

				() -> assertEquals(104, incidencias.get(0).getId()),
				() -> assertEquals("Mobiliario urbano", incidencias.get(0).getTipo()),
				() -> assertEquals("Banco roto", incidencias.get(0).getDescripcion()),
				() -> assertEquals("Centro", incidencias.get(0).getLocalizacion()),
				() -> assertEquals("2026-01-05 08:00:00", fechaAsText(incidencias.get(0).getFechaCambio())),
				() -> assertEquals("Nueva", incidencias.get(0).getEstado()),

				() -> assertEquals(102, incidencias.get(1).getId()),
				() -> assertEquals("Limpieza", incidencias.get(1).getTipo()),
				() -> assertEquals("Basura", incidencias.get(1).getDescripcion()),
				() -> assertEquals("Centro", incidencias.get(1).getLocalizacion()),
				() -> assertEquals("2026-01-03 08:00:00", fechaAsText(incidencias.get(1).getFechaCambio())),
				() -> assertEquals("En proceso", incidencias.get(1).getEstado()),

				() -> assertEquals(100, incidencias.get(2).getId()),
				() -> assertEquals("Alumbrado", incidencias.get(2).getTipo()),
				() -> assertEquals("Farola rota", incidencias.get(2).getDescripcion()),
				() -> assertEquals("Centro", incidencias.get(2).getLocalizacion()),
				() -> assertEquals("2026-01-01 08:00:00", fechaAsText(incidencias.get(2).getFechaCambio())),
				() -> assertEquals("Validada", incidencias.get(2).getEstado()));
	}

	/**
	 * CP10:
	 * Localización nula.
	 * Cubre CE-L2.
	 */
	@Test
	public void testGetIncidenciasPorLocalizacionNula() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Localización", (String) null);
		});
	}

	/**
	 * CP11:
	 * Localización no existente.
	 * Cubre CE-L3 y CE-S1.
	 */
	@Test
	public void testGetIncidenciasPorLocalizacionNoExistente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Localización", "Hospital");

		assertEquals(0, incidencias.size(), "La lista debe estar vacía");
	}

	/**
	 * CP12:
	 * Estado existente.
	 * Cubre CE-E1, CE-S3, CE-S4, CE-S5, CE-S6.
	 */
	@Test
	public void testGetIncidenciasPorEstadoExistente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Estado", "Nueva");

		assertAll("CP12 - Estado existente",
				() -> assertEquals(2, incidencias.size(), "El número de incidencias es incorrecto"),

				() -> assertEquals(104, incidencias.get(0).getId()),
				() -> assertEquals("Mobiliario urbano", incidencias.get(0).getTipo()),
				() -> assertEquals("Banco roto", incidencias.get(0).getDescripcion()),
				() -> assertEquals("Centro", incidencias.get(0).getLocalizacion()),
				() -> assertEquals("2026-01-05 08:00:00", fechaAsText(incidencias.get(0).getFechaCambio())),
				() -> assertEquals("Nueva", incidencias.get(0).getEstado()),

				() -> assertEquals(101, incidencias.get(1).getId()),
				() -> assertEquals("Alumbrado", incidencias.get(1).getTipo()),
				() -> assertEquals("Cable suelto", incidencias.get(1).getDescripcion()),
				() -> assertEquals("Norte", incidencias.get(1).getLocalizacion()),
				() -> assertEquals("2026-01-02 08:00:00", fechaAsText(incidencias.get(1).getFechaCambio())),
				() -> assertEquals("Nueva", incidencias.get(1).getEstado()));
	}

	/**
	 * CP13:
	 * Estado nulo.
	 * Cubre CE-E2.
	 */
	@Test
	public void testGetIncidenciasPorEstadoNulo() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Estado", (String) null);
		});
	}

	/**
	 * CP14:
	 * Estado no existente.
	 * Cubre CE-E3 y CE-S1.
	 */
	@Test
	public void testGetIncidenciasPorEstadoNoExistente() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		List<IncidenciaDTO> incidencias = model.getIncidenciasPorFiltro("Estado", "Cerrada");

		assertEquals(0, incidencias.size(), "La lista debe estar vacía");
	}

	/**
	 * CP15:
	 * Filtro no reconocido.
	 * Cubre CE-I1.
	 */
	@Test
	public void testGetIncidenciasPorFiltroNoReconocido() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Técnico", "x");
		});
	}

	/**
	 * CP16:
	 * Número de argumentos incoherente con el filtro Fecha.
	 * Cubre CE-I2.
	 */
	@Test
	public void testGetIncidenciasPorFiltroArgsInsuficientes() {
		InformesIncidenciasRegistradasModel model = new InformesIncidenciasRegistradasModel();
		assertThrows(ApplicationException.class, () -> {
			model.getIncidenciasPorFiltro("Fecha", "2026-01-01");
		});
	}

	// Convierte la fecha a texto para comparar el valor devuelto por el DTO.
	private String fechaAsText(Object fecha) {
		if (fecha == null) {
			return null;
		}
		if (fecha instanceof String) {
			return (String) fecha;
		}
		if (fecha instanceof Date) {
			return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) fecha);
		}
		if (fecha instanceof LocalDateTime) {
			return ((LocalDateTime) fecha).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return String.valueOf(fecha);
	}
}