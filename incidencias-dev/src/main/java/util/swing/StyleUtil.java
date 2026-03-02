package util.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

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
	
}
