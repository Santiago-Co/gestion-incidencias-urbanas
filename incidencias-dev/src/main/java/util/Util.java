package util;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.commons.beanutils.PropertyUtils;
import exceptions.ApplicationException;


public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }


    public static boolean esDNIValido(String texto) {
        return texto != null && texto.matches("^[0-9]{8}[A-Za-z]$");
    }

    public static boolean esCorreoValido(String texto) {
        return texto != null && texto.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
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

    public static void validateNotNull(Object obj, String message) {
        if (obj == null) {
            LogSystem.logFlow("ERROR", message);
            throw new ApplicationException(message);
        }
    }

    /**
     * Envuelve la ejecución de un método en un manejador de excepciones estándar para UI.
     */
    public static void exceptionWrapper(Runnable consumer) {
        try {
            consumer.run();
        } catch (ApplicationException e) {
            showMessage(e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            e.printStackTrace();
            showMessage("Ocurrió un error inesperado:\n" + e.toString(), "Excepción no controlada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showMessage(String message, String title, int type) {
        JOptionPane pane = new JOptionPane(message, type, JOptionPane.DEFAULT_OPTION);
        pane.setOptions(new Object[] { "ACEPTAR" });
        JDialog d = pane.createDialog(pane, title);
        d.setLocation(200, 200);
        d.setVisible(true);
    }

    /**
     * Ajusta todas las columnas de la tabla al tamaño de su contenido.
     * Requiere la clase auxiliar TableColumnAdjuster.
     */
    public static void autoAdjustColumns(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // Nota: Asegúrate de tener la clase TableColumnAdjuster en tu proyecto
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
    }

    public static String getSelectedKey(JTable table) {
        int row = table.getSelectedRow();
        if (row >= 0)
            return table.getModel().getValueAt(row, 0).toString();
        else
            return "";
    }

    public static String selectAndGetSelectedKey(JTable table, String key) {
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            if (table.getModel().getValueAt(i, 0).equals(key)) {
                table.setRowSelectionInterval(i, i);
                return key;
            }
        }
        return "";
    }

    /**
     * Crea un TableModel a partir de una lista de POJOs usando Apache BeanUtils.
     */
    public static <E> TableModel getTableModelFromPojos(List<E> pojos, String[] colProperties) {
        DefaultTableModel tm;
        if (pojos == null) {
            return new DefaultTableModel(colProperties, 0);
        } else {
            tm = new DefaultTableModel(colProperties, pojos.size());
        }

        for (int i = 0; i < pojos.size(); i++) {
            for (int j = 0; j < colProperties.length; j++) {
                try {
                    Object pojo = pojos.get(i);
                    Object value = PropertyUtils.getSimpleProperty(pojo, colProperties[j]);
                    tm.setValueAt(value, i, j);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException("Error al acceder a la propiedad: " + colProperties[j], e);
                }
            }
        }
        return tm;
    }

    /**
     * Crea un modelo de tabla tipo "Registro" (vertical) para un solo POJO.
     */
    public static <E> TableModel getRecordModelFromPojo(E pojo, String[] colProperties) {
        DefaultTableModel tm = new DefaultTableModel(new String[] { "Campo", "Valor" }, colProperties.length);
        for (int j = 0; j < colProperties.length; j++) {
            try {
                tm.setValueAt(colProperties[j], j, 0);
                Object value = PropertyUtils.getSimpleProperty(pojo, colProperties[j]);
                tm.setValueAt(value, j, 1);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Error al acceder a la propiedad: " + colProperties[j], e);
            }
        }
        return tm;
    }

    /**
     * Crea un Comboboxmodel a partir de una lista de arrays de objetos.
     */
    public static ComboBoxModel<Object> getComboModelFromList(List<Object[]> lst) {
        DefaultComboBoxModel<Object> cm = new DefaultComboBoxModel<>();
        if (lst != null) {
            for (Object[] row : lst) {
                if (row.length > 0) {
                    cm.addElement(row[0]);
                }
            }
        }
        return cm;
    }
}