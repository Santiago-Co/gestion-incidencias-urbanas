package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import org.jdatepicker.impl.JDatePickerImpl;
import util.swing.StyleUtil;

public class CrearPresupuestosView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String[] TIPOS_INCIDENCIA = {
        "Alumbrado", "Limpieza", "Mobiliario urbano", 
        "Zonas verdes", "Señalización y calzada", "Otro"
    };

    private JButton btnClose;
    private JComboBox<String> cbFiltroTipo;
    private JButton btnNuevoPresupuesto;
    private JPanel pnlTarjeta;
    private JLabel lblTarjetaConcepto, lblTarjetaEstado, lblTarjetaVigencia;
    private JProgressBar progressBarFondos;
    private JLabel lblValTotal, lblValConsumido, lblValDisponible;
    private JDialog modalCrear;
    private JComboBox<String> cbModalTipo;
    private JTextField txtConcepto, txtImporte;
    private JDatePickerImpl dateInicio, dateFin;
    private JButton btnGenerar, btnCancelarModal, btnCloseModal;

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

        JLabel lblFiltro = new JLabel("Seleccione el Tipo de Incidencia:");
        StyleUtil.styleLabel(lblFiltro, 16, false);

        cbFiltroTipo = new JComboBox<>(TIPOS_INCIDENCIA);
        StyleUtil.styleComboBox(cbFiltroTipo, 400, 45);
        cbFiltroTipo.setSelectedIndex(-1); 

        pnlTarjeta = StyleUtil.createAbstractPanel();
        pnlTarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        pnlTarjeta.setPreferredSize(new Dimension(750, 200));

        lblTarjetaConcepto = new JLabel("No hay datos disponibles");
        lblTarjetaConcepto.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTarjetaEstado = new JLabel("• INACTIVO");
        lblTarjetaEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTarjetaEstado.setForeground(new Color(156, 163, 175));
        lblTarjetaVigencia = new JLabel("Vigencia: -");
        lblTarjetaVigencia.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        progressBarFondos = new JProgressBar(0, 100);
        progressBarFondos.setPreferredSize(new Dimension(680, 20));
        progressBarFondos.setBorderPainted(false); // Quitamos el borde por defecto
        
        progressBarFondos.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = progressBar.getWidth();
                int height = progressBar.getHeight();
                int amountFull = getAmountFull(progressBar.getInsets(), width, height);

                g2.setColor(progressBar.getBackground());
                g2.fillRoundRect(0, 0, width, height, 10, 10);

                if (amountFull > 0) {
                    g2.setColor(progressBar.getForeground());
                    g2.fillRoundRect(0, 0, amountFull, height, 10, 10);
                }
                g2.dispose();
            }
        });

        lblValTotal = new JLabel("0.00 €");
        lblValConsumido = new JLabel("0.00 €");
        lblValDisponible = new JLabel("0.00 €");

        JPanel pnlCifras = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlCifras.setBackground(Color.WHITE);
        pnlCifras.add(new JLabel("Asignado:")); pnlCifras.add(lblValTotal);
        pnlCifras.add(new JLabel("Consumido:")); pnlCifras.add(lblValConsumido);
        pnlCifras.add(new JLabel("Disponible:")); pnlCifras.add(lblValDisponible);

        GridBagConstraints gTarjeta = new GridBagConstraints();
        gTarjeta.gridx = 0; gTarjeta.gridy = 0; gTarjeta.anchor = GridBagConstraints.WEST;
        pnlTarjeta.add(lblTarjetaConcepto, gTarjeta);
        gTarjeta.gridx = 1; gTarjeta.anchor = GridBagConstraints.EAST; gTarjeta.weightx = 1.0;
        pnlTarjeta.add(lblTarjetaEstado, gTarjeta);
        gTarjeta.gridx = 0; gTarjeta.gridy = 1; gTarjeta.gridwidth = 2; gTarjeta.insets = new Insets(10, 0, 20, 0);
        pnlTarjeta.add(lblTarjetaVigencia, gTarjeta);
        gTarjeta.gridy = 2; gTarjeta.fill = GridBagConstraints.HORIZONTAL;
        pnlTarjeta.add(progressBarFondos, gTarjeta);
        gTarjeta.gridy = 3; gTarjeta.fill = GridBagConstraints.NONE;
        pnlTarjeta.add(pnlCifras, gTarjeta);

        btnNuevoPresupuesto = new JButton("Nueva Partida Presupuestaria");
        StyleUtil.styleSecondaryButton(btnNuevoPresupuesto);
        btnNuevoPresupuesto.setPreferredSize(new Dimension(300, 45));
        
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);

        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, 850);
        JPanel accionesPanel = StyleUtil.createActionsPanel(new JButton[]{btnNuevoPresupuesto});
        JPanel mainPanel = StyleUtil.createMainPanel();
        mainPanel.setPreferredSize(new Dimension(850, 550));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 20, 0); gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblTitle, gbc);
        gbc.gridy = 1; mainPanel.add(lblFiltro, gbc);
        gbc.gridy = 2; mainPanel.add(cbFiltroTipo, gbc);
        gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; mainPanel.add(pnlTarjeta, gbc);
        gbc.gridy = 4; mainPanel.add(accionesPanel, gbc);

        pnlTarjeta.setVisible(false);
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    private void initModal() {
        modalCrear = new JDialog(this, "Generar Nueva Partida", true);
        modalCrear.setUndecorated(true);
        int width = 550;
        int height = 620;
        modalCrear.setSize(width, height);
        modalCrear.setShape(new RoundRectangle2D.Double(0, 0, width, height, 15, 15));
        modalCrear.setLocationRelativeTo(this);

        JLabel titleModal = new JLabel("Configurar Partida Presupuestaria");
        StyleUtil.styleWindowTitle(titleModal);
        btnCloseModal = new JButton();
        StyleUtil.styleCloseButton(btnCloseModal);
        btnCloseModal.addActionListener(e -> cerrarModal());

        JPanel topPanelModal = StyleUtil.createTopPanel(null, titleModal, btnCloseModal, width);
        util.swing.SwingUtil.enableWindowDrag(modalCrear, topPanelModal);

        JPanel mainPanelModal = StyleUtil.createMainPanel();

        cbModalTipo = new JComboBox<>(TIPOS_INCIDENCIA);
        StyleUtil.styleComboBox(cbModalTipo, 470, 40);

        txtConcepto = new JTextField();
        StyleUtil.styleTextField(txtConcepto, 470, 40);

        txtImporte = new JTextField();
        StyleUtil.styleTextField(txtImporte, 470, 50);
        txtImporte.setFont(new Font("Monospaced", Font.BOLD, 20));

        
        dateInicio = util.swing.SwingUtil.crearDatePicker();
        dateFin = util.swing.SwingUtil.crearDatePicker();
        
        JPanel pnlFechasMod = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlFechasMod.setBackground(Color.WHITE);
        pnlFechasMod.add(new JLabel("Fecha de Inicio:"));
        pnlFechasMod.add((Component) dateInicio);
        pnlFechasMod.add(new JLabel("Fecha de Fin:"));
        pnlFechasMod.add((Component) dateFin);

        btnGenerar = new JButton("Generar Presupuesto");
        StyleUtil.styleSecondaryButton(btnGenerar);
        btnGenerar.setPreferredSize(new Dimension(300, 45));
        
        btnCancelarModal = new JButton("Cancelar");
        StyleUtil.styleSecondaryButton(btnCancelarModal);
        btnCancelarModal.addActionListener(e -> cerrarModal());

        JPanel accionesPanelModal = StyleUtil.createActionsPanel(new JButton[]{btnCancelarModal, btnGenerar});

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 5, 0); mainPanelModal.add(new JLabel("Tipo de Incidencia"), gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 15, 0); mainPanelModal.add(cbModalTipo, gbc);
        gbc.gridy = 2; mainPanelModal.add(new JLabel("Concepto"), gbc);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 15, 0); mainPanelModal.add(txtConcepto, gbc);
        gbc.gridy = 4; mainPanelModal.add(new JLabel("Importe Total (€)"), gbc);
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 20, 0); mainPanelModal.add(txtImporte, gbc);
        gbc.gridy = 6; gbc.insets = new Insets(0, 0, 10, 0); mainPanelModal.add(new JLabel("Periodo de Vigencia (Modificable)"), gbc);
        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 25, 0); mainPanelModal.add(pnlFechasMod, gbc);
        gbc.gridy = 8; mainPanelModal.add(accionesPanelModal, gbc);

        modalCrear.getContentPane().setLayout(new BorderLayout());
        modalCrear.getContentPane().add(topPanelModal, BorderLayout.NORTH);
        modalCrear.getContentPane().add(mainPanelModal, BorderLayout.CENTER);
    }

    public void abrirModalLimpio(int selectedIndexFiltro) {
        if (selectedIndexFiltro >= 0) cbModalTipo.setSelectedIndex(selectedIndexFiltro);
        txtConcepto.setText("");
        txtImporte.setText("");

        
        Calendar cal = Calendar.getInstance();
        Date fechaInicio = cal.getTime();
        cal.add(Calendar.YEAR, 1);
        Date fechaFin = cal.getTime();

        dateInicio.getModel().setDate(cal.get(Calendar.YEAR)-1, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dateInicio.getModel().setSelected(true);
        
        dateFin.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dateFin.getModel().setSelected(true);

        modalCrear.setVisible(true);
    }

    public void cerrarModal() { modalCrear.setVisible(false); }
    
    public JPanel getPnlTarjeta() { return pnlTarjeta; }
    public JLabel getLblTarjetaConcepto() { return lblTarjetaConcepto; }
    public JLabel getLblTarjetaEstado() { return lblTarjetaEstado; }
    public JLabel getLblTarjetaVigencia() { return lblTarjetaVigencia; }
    public JProgressBar getProgressBarFondos() { return progressBarFondos; }
    public JLabel getLblValTotal() { return lblValTotal; }
    public JLabel getLblValConsumido() { return lblValConsumido; }
    public JLabel getLblValDisponible() { return lblValDisponible; }
    public JComboBox<String> getCbModalTipo() { return cbModalTipo; }
    public JComboBox<String> getCbFiltroTipo() { return cbFiltroTipo; }
    public JButton getBtnNuevoPresupuesto() { return btnNuevoPresupuesto; }
    public JButton getBtnClose() { return btnClose; }
    public JTextField getTxtConcepto() { return txtConcepto; }
    public JTextField getTxtImporte() { return txtImporte; }
    public JDatePickerImpl getDateInicio() { return dateInicio; }
    public JDatePickerImpl getDateFin() { return dateFin; }
    public JButton getBtnGenerar() { return btnGenerar; }
}