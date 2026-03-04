package util;


import javax.swing.*;
import exceptions.ApplicationException;


public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    /*
     * Comprueba si una cadena de texto presenta el formato estándar de un DNI
     */
    public static boolean esDNIValido(String texto) {
        return texto != null && texto.matches("^[0-9]{8}[A-Za-z]$");
    }

    /*
     * Comprueba si una cadena de texto presenta el formato estándar de un correo electrónico
     */
    public static boolean esCorreoValido(String texto) {
        return texto != null && texto.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
    
    /*
     * Permite generar una excepción si un objeto dado es nulo
     */
    public static void validateNotNull(Object obj, String message) {
        if (obj == null) {
            LogSystem.logFlow("ERROR", message);
            throw new ApplicationException(message);
        }
    }
    
    /*
     * Permite generar una excepción si se incumple una condición dada
     */
    public static void validateCondition(boolean cond, String message) {
		 if (!cond) {
			 LogSystem.logFlow("ERROR", message);
			 throw new ApplicationException(message);
		 }
	 }
    
    /**
     * Agrega una acción de cierre total de la aplicación a un botón.
     */
    public static void addCloseAction(JButton btn, String windowName) {
        btn.addActionListener(e -> {
            LogSystem.logFlow("EXIT", "Cerrando aplicación desde: " + windowName);
            System.exit(0);
        });
    }
}