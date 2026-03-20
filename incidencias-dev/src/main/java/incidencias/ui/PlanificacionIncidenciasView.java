package incidencias.ui;

import javax.swing.*;
import util.swing.StyleUtil;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class PlanificacionIncidenciasView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JScrollPane scrollTabla;
    private JTable tablaIncidencias;
    private JTextField textFieldHorasPrevistas;
    private JTextArea textAreaDescripcion;
    private JButton btnGuardar;
    private JButton btnClose;
    private JButton btnEliminar;

    public PlanificacionIncidenciasView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
    	
    	// Inicializacion de componentes
    	
        final JLabel titleWin = new JLabel("  Planificación de incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Mis incidencias asignadas");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        tablaIncidencias = new JTable();
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTabla.setPreferredSize(new Dimension(860, 300));
        
        final JLabel lblHoras = new JLabel("Horas previstas:");
        StyleUtil.styleLabel(lblHoras, 14, false);
        
        textFieldHorasPrevistas = new JTextField();
        StyleUtil.styleTextField(textFieldHorasPrevistas, 100, 30);
        textFieldHorasPrevistas.setColumns(6);
        
        final JLabel lblDesc = new JLabel("Descripción del trabajo:");
        StyleUtil.styleLabel(lblDesc, 14, false);
        
        textAreaDescripcion = new JTextArea(6, 60);
        JScrollPane scrollDesc = new JScrollPane(textAreaDescripcion);
        StyleUtil.styleTextArea(textAreaDescripcion, scrollDesc, 860, 120);
        
        btnGuardar = new JButton("Guardar");
        StyleUtil.styleSecondaryButton(btnGuardar);
        
        btnEliminar = new JButton("Eliminar");
        StyleUtil.styleSecondaryButtonRed(btnEliminar);
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        // Creacion de las secciones de la pantalla
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JPanel horasPanel = StyleUtil.createAbstractPanel();
        
        JButton[] btns = {btnEliminar, btnGuardar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);
        
        JPanel mainPanel = StyleUtil.createMainPanel();
     
        // Colocacion de los componentes de la pantalla
        
        GridBagConstraints gbcLblHoras = new GridBagConstraints();
        gbcLblHoras.gridx = 0;
        gbcLblHoras.gridy = 0;
        gbcLblHoras.anchor = GridBagConstraints.WEST;
        horasPanel.add(lblHoras, gbcLblHoras);
        
        GridBagConstraints gbcTxtHoras = new GridBagConstraints();
        gbcTxtHoras.gridx = 1;
        gbcTxtHoras.gridy = 0;
        gbcTxtHoras.insets = new Insets(0, 10, 0, 0);
        horasPanel.add(textFieldHorasPrevistas, gbcTxtHoras);
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblTitle, gbcTitle);
        
        GridBagConstraints gbcScrollTabla = new GridBagConstraints();
        gbcScrollTabla.gridx = 0;
        gbcScrollTabla.gridy = 1;
        gbcScrollTabla.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(scrollTabla, gbcScrollTabla);
        
        GridBagConstraints gbcPnlHoras = new GridBagConstraints();
        gbcPnlHoras.gridx = 0;
        gbcPnlHoras.gridy = 2;
        gbcPnlHoras.insets = new Insets(0, 0, 15, 0);
        gbcPnlHoras.anchor = GridBagConstraints.WEST;
        mainPanel.add(horasPanel, gbcPnlHoras);
        
        GridBagConstraints gbcLblDescripcion = new GridBagConstraints();
        gbcLblDescripcion.gridx = 0;
        gbcLblDescripcion.gridy = 3;
        gbcLblDescripcion.insets = new Insets(0, 0, 5, 0);
        gbcLblDescripcion.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblDesc, gbcLblDescripcion);
        
        GridBagConstraints gbcScrollDescripcion = new GridBagConstraints();
        gbcScrollDescripcion.gridx = 0;
        gbcScrollDescripcion.gridy = 4;
        gbcScrollDescripcion.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(scrollDesc, gbcScrollDescripcion);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 5;
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        // Inicializacion del JFrame
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    public JScrollPane getScrollTabla() { return this.scrollTabla; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JTextField getTextFieldHorasPrevistas() { return this.textFieldHorasPrevistas; }
    public JTextArea getTextAreaDescripcion() { return this.textAreaDescripcion; }
    public JButton getBtnGuardar() { return this.btnGuardar; }
    public JButton getBtnClose() { return this.btnClose; }
    public JButton getBtnEliminar() { return this.btnEliminar; }
}