package incidencias.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.jdatepicker.impl.*;
import util.swing.StyleUtil;
import util.swing.SwingUtil;

public class ExportarHistorialView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JDatePickerImpl datePickerInicio;
    private JDatePickerImpl datePickerFin;
    private MultiSelectPanel cbTipoIncidencia;
    private MultiSelectPanel cbZona;
    private JButton btnClose;
    private JButton btnCancelar;
    private JButton btnDescargar;

    public ExportarHistorialView() {
        setUndecorated(true);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
        final JLabel titleWin = new JLabel("Exportar datos (JSON)");
        StyleUtil.styleWindowTitle(titleWin);
        
        final JLabel lblTitle = new JLabel("Exportar datos (JSON)");
        StyleUtil.styleLabel(lblTitle, 26, true);
        
        final JLabel lblDesc = new JLabel("Selecciona los filtros para descargar el historial de incidencias.");
        StyleUtil.styleLabel(lblDesc, 16, false);
        
        final JLabel lblFechaInicio = new JLabel("Fecha de inicio");
        StyleUtil.styleLabel(lblFechaInicio, 14, false);
        
        final JLabel lblFechaFin = new JLabel("Fecha de fin");
        StyleUtil.styleLabel(lblFechaFin, 14, false);
        
        final JLabel lblTipoIncidencia = new JLabel("Tipo de Incidencia");
        StyleUtil.styleLabel(lblTipoIncidencia, 14, false);
        
        final JLabel lblZona = new JLabel("Zona");
        StyleUtil.styleLabel(lblZona, 14, false);
        
        datePickerInicio = SwingUtil.crearDatePicker();
        datePickerFin = SwingUtil.crearDatePicker();
        
        String[] tipos = {"Todos", "Alumbrado", "Limpieza", "Mobiliario urbano", "Zonas verdes", "Señalización y calzada", "Otro"};
        cbTipoIncidencia = new MultiSelectPanel(tipos, "Todos");
        
        String[] zonas = {"Todas las zonas", "Centro", "Norte", "Sur", "Este", "Oeste", "Parque Central", "Zona Universitaria", "Poligono Industrial", "Estacion", "Hospital"};
        cbZona = new MultiSelectPanel(zonas, "Todas las zonas");
        
        btnCancelar = new JButton("Cancelar");
        StyleUtil.styleSecondaryButtonRed(btnCancelar);
        
        
        btnDescargar = new JButton("Descargar JSON");
        StyleUtil.styleSecondaryButton(btnDescargar);
        btnDescargar.setPreferredSize(new Dimension(160, 45));
        btnDescargar.setMinimumSize(new Dimension(160, 45));
        
        btnClose = new JButton();
        StyleUtil.styleCloseButton(btnClose);
        
        JPanel topPanel = StyleUtil.createTopPanel(this, titleWin, btnClose, 700);
        
        JPanel formPanel = StyleUtil.createAbstractPanel();
        
        GridBagConstraints gbcLblFi = new GridBagConstraints();
        gbcLblFi.gridx = 0;
        gbcLblFi.gridy = 0;
        gbcLblFi.insets = new Insets(0, 0, 5, 20);
        gbcLblFi.anchor = GridBagConstraints.WEST;
        formPanel.add(lblFechaInicio, gbcLblFi);
        
        GridBagConstraints gbcLblFf = new GridBagConstraints();
        gbcLblFf.gridx = 1;
        gbcLblFf.gridy = 0;
        gbcLblFf.insets = new Insets(0, 0, 5, 0);
        gbcLblFf.anchor = GridBagConstraints.WEST;
        formPanel.add(lblFechaFin, gbcLblFf);
        
        GridBagConstraints gbcTfFi = new GridBagConstraints();
        gbcTfFi.gridx = 0;
        gbcTfFi.gridy = 1;
        gbcTfFi.insets = new Insets(0, 0, 15, 20);
        gbcTfFi.anchor = GridBagConstraints.WEST;
        formPanel.add(datePickerInicio, gbcTfFi);
        
        GridBagConstraints gbcTfFf = new GridBagConstraints();
        gbcTfFf.gridx = 1;
        gbcTfFf.gridy = 1;
        gbcTfFf.insets = new Insets(0, 0, 15, 0);
        gbcTfFf.anchor = GridBagConstraints.WEST;
        formPanel.add(datePickerFin, gbcTfFf);
        
        GridBagConstraints gbcLblTi = new GridBagConstraints();
        gbcLblTi.gridx = 0;
        gbcLblTi.gridy = 2;
        gbcLblTi.gridwidth = 2;
        gbcLblTi.insets = new Insets(0, 0, 5, 0);
        gbcLblTi.anchor = GridBagConstraints.WEST;
        formPanel.add(lblTipoIncidencia, gbcLblTi);
        
        GridBagConstraints gbcCbTi = new GridBagConstraints();
        gbcCbTi.gridx = 0;
        gbcCbTi.gridy = 3;
        gbcCbTi.gridwidth = 2;
        gbcCbTi.fill = GridBagConstraints.HORIZONTAL;
        gbcCbTi.insets = new Insets(0, 0, 15, 0);
        gbcCbTi.anchor = GridBagConstraints.WEST;
        formPanel.add(cbTipoIncidencia, gbcCbTi);
        
        GridBagConstraints gbcLblZ = new GridBagConstraints();
        gbcLblZ.gridx = 0;
        gbcLblZ.gridy = 4;
        gbcLblZ.gridwidth = 2;
        gbcLblZ.insets = new Insets(0, 0, 5, 0);
        gbcLblZ.anchor = GridBagConstraints.WEST;
        formPanel.add(lblZona, gbcLblZ);
        
        GridBagConstraints gbcCbZ = new GridBagConstraints();
        gbcCbZ.gridx = 0;
        gbcCbZ.gridy = 5;
        gbcCbZ.gridwidth = 2;
        gbcCbZ.fill = GridBagConstraints.HORIZONTAL;
        gbcCbZ.insets = new Insets(0, 0, 0, 0);
        gbcCbZ.anchor = GridBagConstraints.WEST;
        formPanel.add(cbZona, gbcCbZ);
        
        JButton[] btns = {btnCancelar, btnDescargar};
        JPanel accionesPanel = StyleUtil.createActionsPanel(btns);
        
        JPanel mainPanel = StyleUtil.createMainPanel();
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 15, 0);
        gbcTitle.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblTitle, gbcTitle);
        
        GridBagConstraints gbcDesc = new GridBagConstraints();
        gbcDesc.gridx = 0;
        gbcDesc.gridy = 1;
        gbcDesc.insets = new Insets(0, 0, 30, 0);
        gbcDesc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblDesc, gbcDesc);
        
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx = 0;
        gbcForm.gridy = 2;
        gbcForm.insets = new Insets(0, 0, 30, 0);
        gbcForm.anchor = GridBagConstraints.WEST;
        mainPanel.add(formPanel, gbcForm);
        
        GridBagConstraints gbcPnlAcciones = new GridBagConstraints();
        gbcPnlAcciones.gridx = 0;
        gbcPnlAcciones.gridy = 3;
        gbcPnlAcciones.insets = new Insets(0, 0, 0, 0);
        gbcPnlAcciones.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(accionesPanel, gbcPnlAcciones);
        
        StyleUtil.setupWindow(this, topPanel, mainPanel, false);
    }

    public JDatePickerImpl getDatePickerInicio() { return this.datePickerInicio; }
    public JDatePickerImpl getDatePickerFin() { return this.datePickerFin; }
    public MultiSelectPanel getCbTipoIncidencia() { return this.cbTipoIncidencia; }
    public MultiSelectPanel getCbZona() { return this.cbZona; }
    public JButton getBtnClose() { return this.btnClose; }
    public JButton getBtnCancelar() { return this.btnCancelar; }
    public JButton getBtnDescargar() { return this.btnDescargar; }

    @SuppressWarnings("serial")
	public class MultiSelectPanel extends JPanel {
        private String[] allItems;
        private List<String> selectedItems;
        private String optionTodos;
        private JPanel chipsPanel;

        public MultiSelectPanel(String[] items, String optionTodos) {
            this.allItems = items;
            this.optionTodos = optionTodos;
            this.selectedItems = new ArrayList<>();
            this.selectedItems.add(optionTodos);

            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(620, 45));
            styleMultiSelectContainer(this);

            chipsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
            chipsPanel.setBackground(Color.WHITE);
            
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            rightPanel.setBackground(Color.WHITE);
            
           
            JLabel lblClear = new JLabel("X");
            lblClear.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            lblClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lblClear.setForeground(new Color(156, 163, 175));
            lblClear.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedItems.clear();
                    selectedItems.add(optionTodos);
                    updateUIChips();
                }
            });
            
           
            JLabel lblArrow = new JLabel("v");
            lblArrow.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            lblArrow.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lblArrow.setForeground(new Color(156, 163, 175));
            lblArrow.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    showPopup();
                }
            });
            
            rightPanel.add(lblClear);
            rightPanel.add(lblArrow);

            add(chipsPanel, BorderLayout.CENTER);
            add(rightPanel, BorderLayout.EAST);

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    showPopup();
                }
            });
            chipsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    showPopup();
                }
            });

            updateUIChips();
        }

        private void showPopup() {
            JPopupMenu popup = new JPopupMenu();
            for (String item : allItems) {
                if (!selectedItems.contains(item)) {
                    JMenuItem mi = new JMenuItem(item);
                    styleMultiSelectMenuItem(mi);
                    mi.addActionListener(e -> selectItem(item));
                    popup.add(mi);
                }
            }
            if (popup.getComponentCount() > 0) {
                popup.show(this, 0, getHeight());
            }
        }

        private void selectItem(String item) {
            if (item.equals(optionTodos)) {
                selectedItems.clear();
                selectedItems.add(optionTodos);
            } else {
                selectedItems.remove(optionTodos);
                if (!selectedItems.contains(item)) {
                    selectedItems.add(item);
                }
            }
            updateUIChips();
        }

        private void removeItem(String item) {
            selectedItems.remove(item);
            if (selectedItems.isEmpty()) {
                selectedItems.add(optionTodos);
            }
            updateUIChips();
        }

        private void updateUIChips() {
            chipsPanel.removeAll();
            for (String item : selectedItems) {
                JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
                
                
                JLabel lbl = new JLabel(item);
                JLabel btnClose = null;

                if (!item.equals(optionTodos)) {
                    
                    btnClose = new JLabel(" x");
                    btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            removeItem(item);
                        }
                    });
                }
                
                styleMultiSelectChip(chip, lbl, btnClose);
                chip.add(lbl);
                if (btnClose != null) {
                    chip.add(btnClose);
                }
                
                chipsPanel.add(chip);
            }
            chipsPanel.revalidate();
            chipsPanel.repaint();
        }
        
        public List<String> getSelectedItems() {
            return selectedItems;
        }
    }

    public static void styleComboBoxModern(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setForeground(new Color(55, 65, 81));
        combo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combo.setFocusable(false);
    }

    public static void styleMultiSelectContainer(JPanel panel) {
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    public static void styleMultiSelectChip(JPanel chip, JLabel text, JLabel closeBtn) {
        chip.setBackground(new Color(238, 242, 255));
        chip.setBorder(BorderFactory.createLineBorder(new Color(199, 210, 254), 1, true));
        
        text.setFont(new Font("Segoe UI", Font.BOLD, 12));
        text.setForeground(new Color(67, 56, 202));
        
        if (closeBtn != null) {
            closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            closeBtn.setForeground(new Color(99, 102, 241));
            closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    public static void styleMultiSelectMenuItem(JMenuItem item) {
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setBackground(Color.WHITE);
        item.setForeground(new Color(31, 41, 55));
        item.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}