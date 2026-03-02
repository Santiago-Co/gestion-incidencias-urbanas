package util.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class StyleUtil {

	private StyleUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	
    public static void styleCloseButton(JButton btn) {
    	btn.setText("×");
    	btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(45, 40));
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setOpaque(true); btn.setBackground(Color.RED); btn.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setOpaque(false); btn.setForeground(Color.BLACK); }
        });
    }
    
    public static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(new Color(229, 231, 235));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(55, 65, 81));
    }
	
}
