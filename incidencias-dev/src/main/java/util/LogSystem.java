package util; 

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LogSystem {
    private static final Logger logger = Logger.getLogger("AppFlujo");
    
    static {
        try {
        	/*
        	 * To make the logs stay change the false to true
        	 */
            FileHandler fh = new FileHandler("LogSystem.log", false);
            
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(record.getMillis()));
                    // Format: <Date> <Thread> <Level> >> Message 
                    return String.format("[%s] [Thread-%-4s] [%s] >> %s%n", 
                            fecha, 
                            record.getLongThreadID(), //getThreadID(), 
                            record.getLevel(), 
                            record.getMessage());
                }
            });

            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            
            logger.info("================ " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + " ================");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs a structured operational flow entry at the INFO level.
     * <p>
     * This method uses {@link StackWalker} to automatically inspect the runtime stack,
     * identify the names of the class and the method that invoked it. This allows for context-aware logging
     * without manually passing the method name.
     * </p>
     * <p>
     * The log output is formatted into fixed-width columns:
     * {@code METHOD: <Caller>() | ACTION: <Action> | DETAIL: <Description>}
     * </p>
     *
     * @param action A short keyword representing the type of operation (e.g., "LOGIN", "FETCH").
     * It is automatically converted to uppercase for consistency.
     * @param detail A descriptive message explaining the context or specific data of the event.
     */
    public static void logFlow(String action, String detail) {
    	StackWalker.StackFrame frame = StackWalker.getInstance().walk(f -> f.skip(1).findFirst()).get();
    	String nombreClase = frame.getClassName().substring(frame.getClassName().lastIndexOf(".") + 1);
    	String nombreMetodo = frame.getMethodName();
    	
	    String mensajeDecorado = String.format("CLASE: %-25s | METODO: %-25s | ACCION: %-23s | DETALLE: %s", 
                nombreClase, 
                nombreMetodo + "()", 
                action.toUpperCase(), 
                detail);
    	
        logger.info(mensajeDecorado);
    }
}