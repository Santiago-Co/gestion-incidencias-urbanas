package controller;

import model.ClasificarIncidenciasModel;
import util.Util;
import view.ClasificarIncidenciasView;

public class ClasificarIncidenciasController {
    
	private ClasificarIncidenciasView view;
    private ClasificarIncidenciasModel model;
	
	
    public ClasificarIncidenciasController(ClasificarIncidenciasView l, ClasificarIncidenciasModel m) {
        this.view = l;
        this.model = m;
        this.initController();
        this.initView();
        
    }
    
    public void initView() {
    	
    	view.setVisible(true);
    }

    public void initController() {  
    	view.getBtnValidar().addActionListener(e -> validarIncidencia());
    	
    	Util.addCloseAction(view.getBtnClose(), "Login");
    }

    private void validarIncidencia() {
    	
    }

    
}