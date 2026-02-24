package incidencias.model;

import incidencias.model.DTOs.UsuarioDTO;
import util.LogSystem;

public class DashboardModel {

    private UsuarioDTO usuarioActual;

    public void setUsuarioActual(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
    }

    public UsuarioDTO getUsuarioActual() {
        return usuarioActual;
    }

    public void cerrarSesion() {
        if (usuarioActual != null) {
            LogSystem.logFlow("LOGOUT", "El usuario " + usuarioActual.getId() + " ha cerrado sesión.");
            this.usuarioActual = null;
        }
    }
}