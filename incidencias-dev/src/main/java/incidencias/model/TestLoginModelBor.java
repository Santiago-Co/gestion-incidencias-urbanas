package test_login.model;

import java.util.List;

import test_login.model.DTOs.UsuarioDTO;
import util.LogSystem;
import util.Util;
import util.database.Database;

public class TestLoginModel {
	    
    private Database db = new Database();
    
    public static final String SQL_EXISTE_USUARIO = "SELECT id FROM usuario "
    											  + "WHERE dni = ? OR correo = ?";

    public UsuarioDTO validarUsuario(String id) {
    	
    	Util.validateNotNull(id, "Debe introducir un DNI o Correo");
    	Util.validateCondition( (Util.esDNIValido(id) || Util.esCorreoValido(id)),
    							"Formato no válido, debe introducir un DNI o un correo");
    	
    	LogSystem.logFlow("Validación usuario", "ejecución query");
    	
        List<UsuarioDTO> usuarios = db.executeQueryPojo(UsuarioDTO.class, SQL_EXISTE_USUARIO, id, id);
    	Util.validateCondition(!usuarios.isEmpty(), "Usuario inválido: no tiene permisos para acceder al sistema");
    	
    	return usuarios.get(0);
    }
}