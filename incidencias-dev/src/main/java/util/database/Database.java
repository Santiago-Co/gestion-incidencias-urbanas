package util.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

import exceptions.ApplicationException;
import util.LogSystem;

/**
 * Encapsula los datos de acceso JDBC, lectura de la configuracion
 * y scripts de base de datos para creacion y carga.
 */
public class Database extends DbUtil {
    //Localizacion de ficheros de configuracion y carga de bases de datos
    private static final String APP_PROPERTIES = "src/main/resources/application.properties";
    private static final String SQL_SCHEMA = "src/main/resources/schema.sql";
    private static final String SQL_LOAD = "src/main/resources/data.sql";
    
    //parametros de la base de datos leidos de application.properties
    private String driver;
    private String url;
    private static boolean databaseCreated=false;
    
    /**
     * Crea una instancia, leyendo los parametros de driver y url de application.properties
     */
    public Database() {
        Properties prop = new Properties();
        
        // LOG: Antes de intentar leer el fichero de propiedades
        LogSystem.logFlow("CONFIG", "Cargando propiedades desde: " + APP_PROPERTIES);
        
        try (FileInputStream fs = new FileInputStream(APP_PROPERTIES)) {
            prop.load(fs);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        driver = prop.getProperty("datasource.driver");
        url = prop.getProperty("datasource.url");
        if (driver == null || url == null)
            throw new ApplicationException("Configuracion de driver y/o url no encontrada en application.properties");
        
        // LOG: Antes de cargar el driver JDBC
        LogSystem.logFlow("DRIVER", "Cargando driver JDBC: " + driver);
        DbUtils.loadDriver(driver);
    }

    public String getUrl() {
        return url;
    }

    /** * Creacion de una base de datos limpia a partir del script schema.sql
     */
    public void createDatabase(boolean onlyOnce) {
        if (!databaseCreated || !onlyOnce) {
            // LOG: Antes de ejecutar el script de esquema
            LogSystem.logFlow("CREATION", "Creando estructura de BD desde: " + SQL_SCHEMA);
            executeScript(SQL_SCHEMA);
            databaseCreated = true; 
        }
    }

    /** * Carga de datos iniciales a partir del script data.sql
     */
    public void loadDatabase() {
        // LOG: Antes de ejecutar la carga de datos
        LogSystem.logFlow("LOADING", "Insertando datos iniciales desde: " + SQL_LOAD);
        executeScript(SQL_LOAD);
    }
}