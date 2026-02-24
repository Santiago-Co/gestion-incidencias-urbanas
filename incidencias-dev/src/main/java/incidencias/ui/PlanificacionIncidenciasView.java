package incidencias.ui;

import javax.swing.*;
import java.awt.*;

public class PlanificacionIncidenciasView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1186021057741936838L;
	
	private JTable tablaIncidencias;
    private JTextField textFieldHorasPrevistas;
	private JTextArea textAreaDescripcion;
    private JButton btnGuardar;
	
	public PlanificacionIncidenciasView() {
		setTitle("Planificación de Incidencias");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 700);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout(10, 10));
		
		/*
		 * PANEL NORTE
		 */
		JPanel panelNorte = new JPanel();
		getContentPane().add(panelNorte, BorderLayout.NORTH);
		
		JLabel labelIncidenciasAsignadas = new JLabel("Incidencias Asignadas");
		panelNorte.add(labelIncidenciasAsignadas);
		
		/*
		 * CENTRO
		 */
		String[] columnas = {"id", "tipo", "descripcion", "fecha-hora", "estado"};
		Object[][] datos = {};
		tablaIncidencias = new JTable(datos, columnas);
		
		JScrollPane scrollIncidencias = new JScrollPane(tablaIncidencias);
		getContentPane().add(scrollIncidencias, BorderLayout.CENTER);
		
		/*
		 * PANEL SUR
		 */
		JPanel panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout(10, 10));
		
		getContentPane().add(panelSur, BorderLayout.SOUTH);
		
		JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 10, 10));
		
		// Horas Previstas
		panelFormulario.add(new JLabel("Horas Previstas:"));
		textFieldHorasPrevistas = new JTextField();
		panelFormulario.add(textFieldHorasPrevistas);
		
		// Descripción
		panelFormulario.add(new JLabel("Descripción"));
		textAreaDescripcion = new JTextArea(3, 20);
		JScrollPane scrollPaneDescripcion = new JScrollPane(textAreaDescripcion);
		panelFormulario.add(scrollPaneDescripcion);
		
		btnGuardar = new JButton("Guardar");
		panelSur.add(btnGuardar, BorderLayout.EAST);
		
		panelSur.add(panelFormulario, BorderLayout.CENTER);
		
	}

	public JTable getTablaIncidencias() { return this.tablaIncidencias; }
	public JTextField getTextFieldHorasPrevistas() { return this.textFieldHorasPrevistas; }
	public JTextArea getTextAreaDescripcion() { return this.textAreaDescripcion; }
	public JButton getBtnGuardar() { return this.btnGuardar; }
	
}
