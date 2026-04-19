package util.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import exceptions.UnexpectedException;

public class TableCellRendererAsignacion extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private int maxAsignadas;
	private int filaAsignadas;

	public TableCellRendererAsignacion(int maxAsignadas, int filaAsignadas) {
		this.maxAsignadas = maxAsignadas;
		this.filaAsignadas = filaAsignadas;
        setHorizontalAlignment(SwingConstants.CENTER);
    }

	private boolean esFilaBloqueada(JTable tabla, int row) throws UnexpectedException{
		Object numAsignadas = tabla.getModel().getValueAt(row, filaAsignadas);
		if(numAsignadas == null) throw new UnexpectedException("Ha ocurrido un error al cargar la tabla de técnicos disponibles");
    	return (int)numAsignadas >= maxAsignadas;
    }
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (esFilaBloqueada(table, row)) {
            setForeground(Color.GRAY);
            setBackground(Color.WHITE); 
        } else {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
        }

        return this;
    }
	
}
