package app;

import javax.swing.SwingUtilities;

import incidencias.ui.ExportarHistorialView;

public class Pruebas {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	ExportarHistorialView view = new ExportarHistorialView();
            view.setVisible(true);
        });
    }
}