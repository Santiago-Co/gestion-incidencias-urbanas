package util.swing;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class TextAreaRenderer extends JTextArea implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            									   boolean hasFocus, int row, int column) {

        setText(value != null ? value.toString() : "");

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
        
        int preferredHeight = getPreferredSize().height;
        if (table.getRowHeight(row) != preferredHeight) {
            table.setRowHeight(row, preferredHeight);
        }

        return this;
    }
}
