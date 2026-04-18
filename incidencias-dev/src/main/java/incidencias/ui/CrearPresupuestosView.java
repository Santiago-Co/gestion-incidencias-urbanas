package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import util.swing.StyleUtil;
import util.swing.SwingUtil;

public class CrearPresupuestosView extends JFrame {

    private static final long serialVersionUID = 1L;

    // Tipos de incidencia fijos para la interfaz
    private static final String[] TIPOS_INCIDENCIA = {
        "Alumbrado", "Limpieza", "Mobiliario urbano", 
        "Zonas verdes", "Señalización y calzada", "Otro"
    };

    // Componentes Panel Principal
    private JButton btnClose;
    private JComboBox<String> cbFiltroTipo;
    private JButton btnNuevoPresupuesto;

    // Tarjeta del Presupuesto
    private JPanel pnlTarjeta;
    private JLabel lblTarjetaConcepto;
    private JLabel lblTarjetaEstado;
    private JLabel lblTarjetaVigencia;
    private JProgressBar progressBarFondos;
    private JLabel lblValTotal;
    private JLabel lblValConsumido;
    private JLabel lblValDisponible;

    // Modal Crear
    private JDialog modalCrear;
    private JComboBox<String> cbModalTipo;
    private JTextField txtConcepto;
    private JTextField txtImporte;
    private JRadioButton rbAnual;
    private JRadioButton rbPersonalizado;
    private JDatePickerImpl dateInicio;
    private JDatePickerImpl dateFin;
    private JPanel pnlFechas;
    private JButton btnGenerar;
    private JButton btnCancelarModal;
    private JButton btnCloseModal;

    public CrearPresupuestosView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
        initModal();
    }

    private void initComponents() {
        JLabel titleWin = new JLabel("Control Presupuestario");
        StyleUtil.styleWindowTitle(titleWin);

        JLabel lblTitle = new JLabel("Partidas Presupuestarias Activas");
        StyleUtil.styleLabel(lblTitle, 26, true);

        JLabel lblFiltro = new JLabel("Seleccione el Tipo de Incidencia para ver su presupuesto:");
        StyleUtil.styleLabel(lblFiltro, 16, false);

        // ComboBox más ancho para encajar en la nueva ventana grande
        cbFiltroTipo = new JComboBox<>(TIPOS_INCIDENCIA);
        StyleUtil.styleComboBox(cbFiltroTipo, 400, 45);
        cbFiltroTipo.setSelectedIndex(-1); 

        // --- TARJETA DE PRESUPUESTO (Ahora más ancha) ---
        pnlTarjeta = StyleUtil.createAbstractPanel();
        pnlTarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        pnlTarjeta.setPreferredSize(new Dimension(750, 200));

        lblTarjetaConcepto = new JLabel("No hay datos disponibles");
        lblTarjetaConcepto.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTarjetaConcepto.setForeground(new Color(31, 41, 55));

        lblTarjetaEstado = new JLabel("• INACTIVO");
        lblTarjetaEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTarjetaEstado.setForeground(new Color(156, 163, 175));

        lblTarjetaVigencia = new JLabel("Vigencia: -");
        lblTarjetaVigencia.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTarjetaVigencia.setForeground(new Color(107, 114, 128));

        progressBarFondos = new JProgressBar(0, 100);
        progressBarFondos.setPreferredSize(new Dimension(680, 20));
        progressBarFondos.setForeground(new Color(34, 197, 94));
        progressBarFondos.setBackground(new Color(229, 231, 235));
        progressBarFondos.setBorderPainted(false);

        JLabel lblTxtTotal = new JLabel("Asignado:");
        StyleUtil.styleLabel(lblTxtTotal, 16, false);
        lblValTotal = new JLabel("0.00 €");
        lblValTotal.setFont(new Font("Monospaced", Font.BOLD, 20));
        lblValTotal.setForeground(new Color(31, 41, 55));

        JLabel lblTxtConsumido = new JLabel("Consumido:");
        StyleUtil.styleLabel(lblTxtConsumido, 16, false);
        lblValConsumido = new JLabel("0.00 €");
        lblValConsumido.setFont(new Font("Monospaced", Font.BOLD, 18));
        lblValConsumido.setForeground(new Color(107, 114, 128));

        JLabel lblTxtDisponible = new JLabel("Disponible:");
        StyleUtil.styleLabel(lblTxtDisponible, 16, false);
        lblValDisponible = new JLabel("0.00 €");
        lblValDisponible.setFont(new Font("Monospaced", Font.BOLD, 18));
        lblValDisponible.setForeground(new Color(34, 197, 94));

        JPanel pnlCifras = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlCifras.setBackground(Color.WHITE);
        pnlCifras.add(lblTxtTotal); pnlCifras.add(lblValTotal);
        pnlCifras.add(new JLabel("  |  "));
        pnlCifras.add(lblTxtConsumido); pnlCifras.add(lblValConsumido);
        pnlCifras.add(new JLabel("  |  "));
        pnlCifras.add(lblTxtDisponible); pnlCifras.add(lblValDisponible);

        GridBagConstraints gTarjeta = new GridBagConstraints();
        gTarjeta.gridx = 0; gTarjeta.gridy = 0;
        gTarjeta.anchor = GridBagConstraints.WEST;
        pnlTarjeta.add(lblTarjetaConcepto, gTarjeta);

        gTarjeta.gridx = 1; gTarjeta.anchor = GridBagConstraints.EAST; gTarjeta.weightx = 1.0;
        pnlTarjeta.add(lblTarjetaEstado, gTarjeta);

        gTarjeta.gridx = 0; gTarjeta.gridy = 1; gTarjeta.gridwidth = 2; gTarjeta.weightx = 0;
        gTarjeta.anchor = GridBagConstraints.WEST; gTarjeta.insets = new Insets(10, 0, 20, 0);
        pnlTarjeta.add(lblTarjetaVigencia, gTarjeta);

        gTarjeta.gridy = 2; gTarjeta.fill = GridBagConstraints.HORIZONTAL; gTarjeta.insets = new Insets(0, 0, 20, 0);
        pnlTarjeta.add(progressBarFondos, gTarjeta);

        gTarjeta.gridy = 3; gTarjeta.insets = new Insets(0, 0, 0, 0);
        pnlTarjeta.add(pnlCifras, gTarjeta);

        btnNuevoPresupuesto = new JButton("Nueva Partida Presupuestaria");
        StyleUtil.styleSecondaryButton(btnNuevoPresupuesto);
        btnNuevoPresupuesto.setPreferredSize(new Dimension(280, 50));

        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);

        // --- ENSAMBLAJE FINAL (Tamaño de ventana forzado) ---
        // 1. Forzamos la barra superior a 850px de ancho
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, 850);
        
        JButton[] btns = {btnNuevoPresupuesto};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);
        
        JPanel mainPanel = StyleUtil.createMainPanel();
        // 2. Forzamos el panel principal a tener un tamaño inicial fijo y grande (850x550)
        mainPanel.setPreferredSize(new Dimension(850, 550));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 20, 0); gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblTitle, gbc);

        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 10, 0);
        mainPanel.add(lblFiltro, gbc);

        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(cbFiltroTipo, gbc);

        gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(pnlTarjeta, gbc);

        gbc.gridy = 4; gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(accionesPanel, gbc);

        // Ocultamos la tarjeta por defecto
        pnlTarjeta.setVisible(false);

        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    private void initModal() {
        modalCrear = new JDialog(this, "Generar Nueva Partida", true);
        modalCrear.setUndecorated(true);
        // Tamaño inicial compacto
        modalCrear.setSize(550, 520);
        modalCrear.setShape(new RoundRectangle2D.Double(0, 0, 550, 520, 15, 15));
        modalCrear.setLocationRelativeTo(this);

        // 1. Cabecera del Modal
        JLabel titleModal = new JLabel("Generar Nueva Partida Presupuestaria");
        StyleUtil.styleWindowTitle(titleModal);
        btnCloseModal = new JButton();
        StyleUtil.styleCloseButton(btnCloseModal);
        btnCloseModal.addActionListener(e -> cerrarModal());

        JPanel topPanelModal = StyleUtil.createTopPanel(null, titleModal, btnCloseModal, 550);
        util.swing.SwingUtil.enableWindowDrag(modalCrear, topPanelModal);

        // 2. Panel Central (Formulario)
        JPanel mainPanelModal = StyleUtil.createMainPanel();

        JLabel lblTipo = new JLabel("Vincular a Tipo de Incidencia");
        StyleUtil.styleLabel(lblTipo, 14, false);
        cbModalTipo = new JComboBox<>(TIPOS_INCIDENCIA);
        StyleUtil.styleComboBox(cbModalTipo, 470, 40);

        JLabel lblConcepto = new JLabel("Concepto descriptivo");
        StyleUtil.styleLabel(lblConcepto, 14, false);
        txtConcepto = new JTextField();
        StyleUtil.styleTextField(txtConcepto, 470, 40);

        JLabel lblImporte = new JLabel("Importe Total Disponible (€)");
        StyleUtil.styleLabel(lblImporte, 14, false);
        txtImporte = new JTextField();
        StyleUtil.styleTextField(txtImporte, 470, 50);
        txtImporte.setFont(new Font("Monospaced", Font.BOLD, 20));
        txtImporte.setForeground(new Color(34, 197, 94));

        JLabel lblVigencia = new JLabel("Periodo de Vigencia");
        StyleUtil.styleLabel(lblVigencia, 14, false);

        rbAnual = new JRadioButton("Formato Anual (Por defecto)");
        rbAnual.setBackground(Color.WHITE);
        rbAnual.setSelected(true);
        rbPersonalizado = new JRadioButton("Periodo Personalizado");
        rbPersonalizado.setBackground(Color.WHITE);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbAnual); bg.add(rbPersonalizado);

        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlRadio.setBackground(Color.WHITE);
        pnlRadio.add(rbAnual); pnlRadio.add(rbPersonalizado);

        // DatePickers
        dateInicio = util.swing.SwingUtil.crearDatePicker();
        dateFin = util.swing.SwingUtil.crearDatePicker();
        pnlFechas = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlFechas.setBackground(Color.WHITE);
        pnlFechas.add(new JLabel("Desde:")); pnlFechas.add((Component) dateInicio);
        pnlFechas.add(new JLabel("Hasta:")); pnlFechas.add((Component) dateFin);
        pnlFechas.setVisible(false);

        // --- LÓGICA DE REDIMENSIÓN ---
        rbAnual.addActionListener(e -> {
            pnlFechas.setVisible(false);
            modalCrear.setSize(550, 520);
            modalCrear.setShape(new RoundRectangle2D.Double(0, 0, 550, 520, 15, 15));
            modalCrear.revalidate();
            modalCrear.repaint();
        });

        rbPersonalizado.addActionListener(e -> {
            pnlFechas.setVisible(true);
            modalCrear.setSize(550, 680); // Crecemos para que quepan las fechas
            modalCrear.setShape(new RoundRectangle2D.Double(0, 0, 550, 680, 15, 15));
            modalCrear.revalidate();
            modalCrear.repaint();
        });

        // 3. Botones Inferiores
        btnGenerar = new JButton("Generar Presupuesto");
        StyleUtil.styleSecondaryButton(btnGenerar);
        btnGenerar.setPreferredSize(new Dimension(180, 45));

        btnCancelarModal = new JButton("Cancelar");
        StyleUtil.styleSecondaryButton(btnCancelarModal);
        btnCancelarModal.setBackground(Color.WHITE);
        btnCancelarModal.setForeground(new Color(79, 70, 229));
        btnCancelarModal.setBorder(BorderFactory.createLineBorder(new Color(79, 70, 229), 1));
        btnCancelarModal.addActionListener(e -> cerrarModal());

        JButton[] btnsModal = {btnCancelarModal, btnGenerar};
        JPanel accionesPanelModal = StyleUtil.createActionsPanel(btnsModal);

        // 4. Colocación de componentes (GridBagLayout)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 5, 0);
        mainPanelModal.add(lblTipo, gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 15, 0);
        mainPanelModal.add(cbModalTipo, gbc);
        
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 5, 0);
        mainPanelModal.add(lblConcepto, gbc);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 15, 0);
        mainPanelModal.add(txtConcepto, gbc);
        
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 5, 0);
        mainPanelModal.add(lblImporte, gbc);
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 20, 0);
        mainPanelModal.add(txtImporte, gbc);
        
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 5, 0);
        mainPanelModal.add(lblVigencia, gbc);
        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 10, 0);
        mainPanelModal.add(pnlRadio, gbc);
        
        gbc.gridy = 8; gbc.insets = new Insets(0, 0, 20, 0);
        mainPanelModal.add(pnlFechas, gbc);
        
        gbc.gridy = 9; gbc.insets = new Insets(10, 0, 0, 0); gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanelModal.add(accionesPanelModal, gbc);

        // RESORTE FINAL: Empuja todo hacia arriba para evitar el espacio vacío al inicio
        gbc.gridy = 10; gbc.weighty = 1.0;
        mainPanelModal.add(Box.createVerticalGlue(), gbc);

        // Ensamblaje final en el JDialog
        modalCrear.getContentPane().setLayout(new BorderLayout());
        modalCrear.getContentPane().add(topPanelModal, BorderLayout.NORTH);
        modalCrear.getContentPane().add(mainPanelModal, BorderLayout.CENTER);
    }

    public void ocultarTarjeta() {
        pnlTarjeta.setVisible(false);
        revalidate();
        repaint();
    }

    public void mostrarTarjetaActiva(String concepto, String vigencia, double importe, double consumido, double disponible, int porcentaje) {
        pnlTarjeta.setVisible(true);

        lblTarjetaConcepto.setText(concepto);
        lblTarjetaEstado.setText("• ACTIVO");
        lblTarjetaEstado.setForeground(new Color(34, 197, 94)); // Verde
        lblTarjetaVigencia.setText("Vigencia: " + vigencia);

        lblValTotal.setText(String.format("%.2f €", importe));
        lblValConsumido.setText(String.format("%.2f €", consumido));
        lblValDisponible.setText(String.format("%.2f €", Math.max(0, disponible)));

        progressBarFondos.setValue(porcentaje);

        if (porcentaje > 90) {
            progressBarFondos.setForeground(new Color(239, 68, 68)); // Rojo
            lblValDisponible.setForeground(new Color(239, 68, 68));
        } else {
            progressBarFondos.setForeground(new Color(34, 197, 94)); // Verde
            lblValDisponible.setForeground(new Color(34, 197, 94));
        }

        revalidate();
        repaint();
    }

    public void mostrarTarjetaInactiva() {
        pnlTarjeta.setVisible(true);

        lblTarjetaConcepto.setText("Sin partida presupuestaria asignada");
        lblTarjetaEstado.setText("• INACTIVO");
        lblTarjetaEstado.setForeground(new Color(156, 163, 175)); // Gris
        lblTarjetaVigencia.setText("Vigencia: -");

        lblValTotal.setText("0.00 €");
        lblValConsumido.setText("0.00 €");
        lblValDisponible.setText("0.00 €");
        lblValDisponible.setForeground(new Color(156, 163, 175)); // Gris

        progressBarFondos.setValue(0);
        
        revalidate();
        repaint();
    }

    public void abrirModalLimpio(int selectedIndexFiltro) {
       
        if (selectedIndexFiltro >= 0) {
            cbModalTipo.setSelectedIndex(selectedIndexFiltro);
        }
        
        txtConcepto.setText("");
        txtImporte.setText("");
        rbAnual.setSelected(true);
        dateInicio.getModel().setValue(null);
        dateFin.getModel().setValue(null);
        
        modalCrear.setVisible(true);
    }

    public void cerrarModal() {
        modalCrear.setVisible(false);
    }
    
    
    public JPanel getPnlTarjeta() { return pnlTarjeta; }
    public JComboBox<String> getCbFiltroTipo() { return cbFiltroTipo; }
    public JButton getBtnNuevoPresupuesto() { return btnNuevoPresupuesto; }
    public JButton getBtnClose() { return btnClose; }
    public JLabel getLblTarjetaConcepto() { return lblTarjetaConcepto; }
    public JLabel getLblTarjetaEstado() { return lblTarjetaEstado; }
    public JLabel getLblTarjetaVigencia() { return lblTarjetaVigencia; }
    public JProgressBar getProgressBarFondos() { return progressBarFondos; }
    public JLabel getLblValTotal() { return lblValTotal; }
    public JLabel getLblValConsumido() { return lblValConsumido; }
    public JLabel getLblValDisponible() { return lblValDisponible; }
    public JDialog getModalCrear() { return modalCrear; }
    public JComboBox<String> getCbModalTipo() { return cbModalTipo; }
    public JTextField getTxtConcepto() { return txtConcepto; }
    public JTextField getTxtImporte() { return txtImporte; }
    public JRadioButton getRbAnual() { return rbAnual; }
    public JRadioButton getRbPersonalizado() { return rbPersonalizado; }
    public org.jdatepicker.impl.JDatePickerImpl getDateInicio() { return dateInicio; }
    public org.jdatepicker.impl.JDatePickerImpl getDateFin() { return dateFin; }
    public JButton getBtnGenerar() { return btnGenerar; }
    public JButton getBtnCancelarModal() { return btnCancelarModal; }
    public JButton getBtnCloseModal() { return btnCloseModal; }
}