# SI2025-PL33 — Gestión de incidencias urbanas

> **Este repositorio es un mirror.**
> Es una copia completa del repositorio original
> [brakhage33/SI2025-PL33](https://github.com/brakhage33/SI2025-PL33).

Proyecto de la asignatura **Sistemas de Información (curso 2025)**, grupo de prácticas **PL33**.
Es una aplicación de escritorio en Java que gestiona las incidencias de una ciudad, desde
que un ciudadano la registra hasta que se resuelve y se cierra.

## Funcionalidad

Al iniciar sesión con **DNI o correo**, cada usuario ve un panel con las acciones de su rol.

| Rol | Acciones |
|---|---|
| **Ciudadano** | Crear incidencia, consultar sus incidencias, reabrir incidencias |
| **Operador** | Clasificar (validar o rechazar), asignar a técnicos, ver historial, exportar historial a JSON, crear presupuestos por tipo de incidencia |
| **Técnico** | Planificar incidencias, registrar trabajos diarios, resolver incidencias, ver historial |
| **Técnico responsable** | Ver historial, cerrar las incidencias de su especialidad |
| *Todos* | Informes de incidencias registradas e informes por técnico |

Ciclo de vida de una incidencia:

```
Nueva → Validada → Asignada → En proceso → Resuelta → Cerrada
   └──→ Rechazado
```

Cada cambio de estado queda registrado en la tabla `historia`.

## Tecnologías

- Java 17 + Maven
- Interfaz Swing (patrón MVC: `controller` / `model` / `ui`)
- SQLite vía JDBC + Apache Commons DbUtils
- Lombok, Jackson (exportación JSON), JDatePicker, MigLayout
- Logs con SLF4J + reload4j

Basado en la plantilla [javiertuya/samples-test-dev](https://github.com/javiertuya/samples-test-dev)
de la Universidad de Oviedo.

## Estructura

```
incidencias-dev/
├── pom.xml
└── src/main/
    ├── java/
    │   ├── app/SwingMain.java          # Punto de entrada
    │   ├── incidencias/
    │   │   ├── controller/             # Un controlador por caso de uso
    │   │   ├── model/                  # Acceso a datos, DTOs y entidades
    │   │   └── ui/                     # Vistas Swing
    │   ├── exceptions/
    │   └── util/                       # BD, utilidades Swing y logs
    └── resources/
        ├── application.properties      # Conexión a SQLite (DemoDB.db)
        ├── schema.sql                  # Esquema de la base de datos
        └── data.sql                    # Datos de prueba
```

## Ejecución

Requiere **JDK 17** (no basta con un JRE).

```bash
cd incidencias-dev
mvn install -DskipTests=true
```

Después se ejecuta la clase `app.SwingMain`, que abre una ventana con tres botones:

1. **Inicializar Base de Datos en Blanco**: crea `DemoDB.db` a partir de `schema.sql`.
2. **Cargar Datos Iniciales para Pruebas**: carga `data.sql` (usuarios de cada rol, incidencias de ejemplo).
3. **Ejecutar App**: abre la pantalla de inicio de sesión.

Usuarios de ejemplo (de `data.sql`): `juan.perez@email.com` (ciudadano), `operador@email.com` (operador).

Desde Eclipse: importar `incidencias-dev` como proyecto Maven, hacer *Maven → Update Project*
y ejecutar `SwingMain` como *Java Application*.

## Ramas

| Rama | Contenido |
|---|---|
| `main` | Versión integrada del proyecto |
| `PA_pruebas_UO296954` | Pruebas JUnit individuales (`Test_uo296954`) |
| `PA_pruebas_UO297118` | Pruebas JUnit de informes de incidencias registradas |
| `PA_pruebas_UO300078` | Pruebas JUnit de creación de presupuestos e imputación de costes |

Las pruebas solo están en estas ramas (en `src/test/java`); `main` no incluye tests.

## Autores

Equipo PL33: Jose Angel Prada Zamora, Mario Acuña y Santiago Corujo.


