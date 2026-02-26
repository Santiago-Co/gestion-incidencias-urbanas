package incidencias.model;

import java.util.List;

import incidencias.model.DTOs.UsuarioDTO;
import util.LogSystem;
import util.Util;
import util.database.Database;

public class LogInModel {
	
    private final static String MSG_ID_NO_NULA = "El identificador no puede ser nulo";
    private Database db = new Database();
    
    public static final String SQL_EXISTE_USUARIO = "SELECT id, rol, correo "
    											  + "FROM usuario "
    											  + "WHERE dni = ? OR correo = ?";

    public UsuarioDTO validarUsuario(String id) {
    	
    	Util.validateNotNull(id, MSG_ID_NO_NULA);
    	Util.validateCondition( (Util.esDNIValido(id) || Util.esCorreoValido(id)),
    							"Formato no válido, debe introducir un DNI o un correo");
    	
    	LogSystem.logFlow("Validación usuario", "ejecución query");
    	
        List<UsuarioDTO> usuarios = db.executeQueryPojo(UsuarioDTO.class, SQL_EXISTE_USUARIO, id, id);
    	Util.validateCondition(!usuarios.isEmpty(), "Usuario inválido: no tiene permisos para acceder al sistema");
    	
    	return usuarios.get(0);
    }

   
}