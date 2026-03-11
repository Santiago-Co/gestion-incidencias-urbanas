package util.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.JTableHeader;

public class StyleUtil {

    public  static final Color BG_COLOR = new Color(249, 250, 251); 
	
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229);
    private static final Color SECONDARY_COLOR = new Color(255, 3, 59);
    private static final Color TEXT_COLOR = new Color(100, 106, 120); 
    private static final Color TITLE_COLOR = new Color(31, 41, 55);
    private static final Color BORDER_COLOR = new Color(209, 213, 219);
    private static final Color TOP_PANEL_BG_COLOR = new Color(229, 231, 235);
    private static final Color TOP_PANEL_TEXT_COLOR = new Color(75, 85, 99);
    private static final Color DANGER_COLOR = new Color(220, 38, 38); 
	
	private StyleUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	private static void styleAbstractButtonPC(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR.darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(PRIMARY_COLOR); }
        });
	}
	
	private static void styleAbstractButtonSC(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(SECONDARY_COLOR);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(null);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { btn.setBackground(SECONDARY_COLOR.darker()); }
            public void mouseExited(MouseEvent evt) { btn.setBackground(SECONDARY_COLOR); }
        });
	}
	
	public static JPanel createTopPanel(JFrame window, JLabel title, JButton close, int width) {
        JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(TOP_PANEL_BG_COLOR);
        topPanel.setPreferredSize(new Dimension(width, 40));
        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(close, BorderLayout.EAST);
        SwingUtil.enableWindowDrag(window, topPanel);
        return topPanel;
	}
	
	public static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setLayout(new GridBagLayout());
        return mainPanel;
	}
	
	public static JPanel createAbstractPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        return panel;
	}
	
	public static JPanel createActionsPanel(JButton[] btns) {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(Color.WHITE);
	    JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
	    eastPanel.setBackground(Color.WHITE);
	    for(JButton btn : btns) eastPanel.add(btn);
	    panel.add(eastPanel, BorderLayout.EAST);
	    return panel;
	}
	
	public static void setupWindow(JFrame window, JPanel topPanel, JPanel mainPanel, boolean close) {
        if(close) window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new BorderLayout());
        window.getContentPane().add(topPanel, BorderLayout.NORTH);
        window.getContentPane().add(mainPanel, BorderLayout.CENTER);
        window.pack();
	}
	
	public static void styleWindowTitle(JLabel title) {
        title.setFont(new Font("Segoe UI", Font.BOLD, 12));
        title.setForeground(TOP_PANEL_TEXT_COLOR);
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
    
    public static void styleLabel(JLabel lbl, int size, boolean isTitle) {
    	lbl.setFont(new Font("Segoe UI", Font.BOLD, size));
    	if(isTitle) lbl.setForeground(TITLE_COLOR);
        else lbl.setForeground(TEXT_COLOR);
    }
    
    public static void styleMainButton(JButton btn) {
        StyleUtil.styleAbstractButtonPC(btn);
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setMinimumSize(new Dimension(300, 45));
    }
    
    public static void styleSecondaryButton(JButton btn) {
        StyleUtil.styleAbstractButtonPC(btn);
        btn.setPreferredSize(new Dimension(100, 45));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setMinimumSize(new Dimension(100, 45));
    }
    
    public static void styleSecondaryButtonRed(JButton btn) {
    	StyleUtil.styleAbstractButtonSC(btn);
        btn.setPreferredSize(new Dimension(100, 45));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setMinimumSize(new Dimension(100, 45));
    }
    
    public static void styleLogoutButton(JButton btn) {
        StyleUtil.styleAbstractButtonPC(btn);
        btn.setForeground(DANGER_COLOR);
        btn.setBackground(Color.WHITE);
        btn.setPreferredSize(new Dimension(300, 45));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setMinimumSize(new Dimension(300, 45));
        btn.setBorder(BorderFactory.createLineBorder(DANGER_COLOR, 1)); 

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { 
                btn.setBackground(DANGER_COLOR); 
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent evt) { 
                btn.setBackground(Color.WHITE);
                btn.setForeground(DANGER_COLOR);
            }
        });
    }
    
    public static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(229, 231, 235));
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(243, 244, 246));
        header.setForeground(new Color(55, 65, 81));
    }
    
    public static void styleTextField(JTextField tf, int width, int height) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setForeground(TITLE_COLOR);
        tf.setPreferredSize(new Dimension(width, height));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    public static void styleTextFieldIdentificador(JTextField tf, String placeholder) {
        tf.setForeground(Color.GRAY);
        tf.setPreferredSize(new Dimension(300, 45));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                    tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(4, 14, 4, 14)));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tf.getText().isEmpty()) {
                    tf.setText(placeholder);
                    tf.setForeground(Color.GRAY);
                    tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)));
                }
            }
        });
    }
    
    public static void styleComboBox(JComboBox<String> combo, int width, int height) {
    	combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    	combo.setBackground(Color.WHITE);
    	combo.setPreferredSize(new Dimension(width, height));
    	combo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public static void styleTextArea(JTextArea area, JScrollPane scroll, int width, int height) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setForeground(TITLE_COLOR);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        
        scroll.setPreferredSize(new Dimension(width, height));
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1)); 
    }
}
