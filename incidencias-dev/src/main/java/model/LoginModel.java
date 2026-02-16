package model;

import java.util.List;

import dto.UsuarioDTO;
import util.Database;
import util.LogSystem;
import util.Util;

public class LoginModel {
	
    private final static String MSG_ID_NO_NULA = "El identificador no puede ser nulo";
    private Database db = new Database();
    
    public static final String SQL_EXISTE_USUARIO = 
            "SELECT id, rol FROM usuarios WHERE dni = ? OR correo = ?";

    public List<UsuarioDTO> validarUsuario(String id) {
    	LogSystem.logFlow("Validación usuario", "ejecución query");
    	Util.validateNotNull(id, MSG_ID_NO_NULA);
        return db.executeQueryPojo(UsuarioDTO.class, SQL_EXISTE_USUARIO, id, id);
        
    }

   
}