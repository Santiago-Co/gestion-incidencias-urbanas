package util;

import java.util.Map;

public class MapEspecialidades {

	private static final Map<String, String> MAP =
			Map.ofEntries(
					Map.entry("Alumbrado", "Electricidad urbana"),
					Map.entry("Limpieza", "Servicios de limpieza"),
					Map.entry("Mobiliario urbano", "Mantenimiento"),
					Map.entry("Zonas verdes", "Jardineria"),
					Map.entry("Señalización y calzada", "Seguridad vial")
			);
	
	private MapEspecialidades() {}
	
	public static String getEspecialidad(String tipo) {
		return MAP.get(tipo);
	}
}
