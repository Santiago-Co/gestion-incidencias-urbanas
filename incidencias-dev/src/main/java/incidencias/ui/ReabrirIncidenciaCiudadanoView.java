package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import util.swing.StyleUtil;
import util.swing.SwingUtil;

public class ReabrirIncidenciaCiudadanoView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JScrollPane scrollTabla;
    private JTable tablaIncidencias;
    private JButton btnReabrir;
    private JButton btnClose;

    private JDialog modalReabrir;
    private JTextArea txtMotivo;
    private JButton btnConfirmarReapertura;
    private JButton btnCancelarReapertura;
    private JButton btnCloseModal;

    public ReabrirIncidenciaCiudadanoView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
        
    }

    private void initComponents() {
        
        JLabel titleWin = new JLabel("Reabrir incidencias");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Mis incidencias cerradas o rechazadas");
        StyleUtil.styleLabel(lblTitle, 24, true);
        
        final JLabel lblDesc = new JLabel("Selecciona una incidencia del listado para solicitar su reapertura.");
        StyleUtil.styleLabel(lblDesc, 14, false);

        
        String[] columnas = {"ID", "Tipo", "Descripción", "Localización", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaIncidencias = new JTable(modelo) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int col) {
                return false; 
            }
        };
        
        StyleUtil.styleTable(tablaIncidencias);
        scrollTabla = new JScrollPane(tablaIncidencias);
        scrollTabla.setPreferredSize(new Dimension(700, 300));

        btnReabrir = new JButton("Reabrir incidencia");
        StyleUtil.styleSecondaryButton(btnReabrir);
        btnReabrir.setPreferredSize(new Dimension(220, 50));

        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, this.getWidth());
        
        JButton[] btns = {btnReabrir};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);
        
        JPanel mainPanel = StyleUtil.createMainPanel();

        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(5, 5, 5, 5);
        gbcTitle.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblTitle, gbcTitle);

        GridBagConstraints gbcDesc = new GridBagConstraints();
        gbcDesc.gridx = 0;
        gbcDesc.gridy = 1;
        gbcDesc.insets = new Insets(0, 5, 10, 5);
        gbcDesc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblDesc, gbcDesc);

        GridBagConstraints gbcScroll = new GridBagConstraints();
        gbcScroll.gridx = 0;
        gbcScroll.gridy = 2;
        gbcScroll.insets = new Insets(5, 5, 10, 5);
        gbcScroll.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrollTabla, gbcScroll);

        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 3;
        gbcPnlAcciones.insets = new Insets(5, 5, 10, 5);
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);

        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    

    public JScrollPane getScrollTabla() { return this.scrollTabla; }
    public JTable getTablaIncidencias() { return this.tablaIncidencias; }
    public JButton getBtnReabrir() { return this.btnReabrir; }
    public JButton getBtnClose() { return this.btnClose; }
    public JDialog getModalReabrir() { return this.modalReabrir; }
    public JTextArea getTxtMotivo() { return this.txtMotivo; }
    public JButton getBtnConfirmarReapertura() { return this.btnConfirmarReapertura; }
    public JButton getBtnCancelarReapertura() { return this.btnCancelarReapertura; }
    public JButton getBtnCloseModal() { return this.btnCloseModal; }
    
}