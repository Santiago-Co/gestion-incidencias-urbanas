package incidencias.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VistaConsultaIncidencias extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaConsultaIncidencias frame = new VistaConsultaIncidencias();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaConsultaIncidencias() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Debe registrarse para acceder al sistema:");
		lblNewLabel.setBounds(9, 10, 417, 13);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Registrarse con DNI");
		rdbtnNewRadioButton.setBounds(9, 29, 229, 21);
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Registrarse con correo electrónico");
		rdbtnNewRadioButton_1.setBounds(9, 52, 229, 21);
		rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(rdbtnNewRadioButton_1);
		
		textField = new JTextField();
		textField.setBounds(244, 30, 182, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(244, 53, 182, 18);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Registrarse");
		btnNewButton.setBounds(10, 79, 113, 20);
		contentPane.add(btnNewButton);

	}
}
