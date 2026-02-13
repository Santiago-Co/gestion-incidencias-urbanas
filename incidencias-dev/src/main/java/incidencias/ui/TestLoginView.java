package incidencias.ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TestLoginView extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel cambioCorreoL;
	private JLabel correoL;
	private JLabel contraseniaL;
	private JTextField registrarDNITF;
    private JTextField registrarCorreoTF;
    private JTextField registrarContraseniaTF;
    private JButton confirmarB;
	
    public TestLoginView() {
        initComponents();
    }
                        
    private void initComponents() {
    	
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	
        final JLabel tituloL = new JLabel("Debe registrarse para acceder al sistema");
        tituloL.setFont(new Font("Segoe UI", 0, 20));
        
        final JLabel infoL = new JLabel("Indique su DNI o su email para identificarse");
        infoL.setFont(new Font("Segoe UI", 0, 14));
        
        cambioCorreoL = new JLabel("Registrarme con mi email");
        cambioCorreoL.setFont(new Font("Segoe UI", 0, 10));
        cambioCorreoL.setForeground(Color.BLUE);
        
        correoL = new JLabel("Email:");
        correoL.setFont(new Font("Segoe UI", 0, 14));
        correoL.setVisible(false);
        
        contraseniaL = new JLabel("Contraseña:");
        contraseniaL.setFont(new Font("Segoe UI", 0, 14));
        contraseniaL.setVisible(false);
        
        registrarDNITF = new JTextField("Introduzca su DNI");
        registrarDNITF.setForeground(Color.GRAY);
                
        registrarCorreoTF = new JTextField();
        registrarCorreoTF.setVisible(false);
        
        registrarContraseniaTF = new JTextField();
        registrarContraseniaTF.setVisible(false);
        
        confirmarB = new JButton("Registrarse");
                
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tituloL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registrarDNITF)
                        .addComponent(correoL)
                        .addComponent(registrarCorreoTF)
                        .addComponent(contraseniaL)
                        .addComponent(registrarContraseniaTF)
                        .addComponent(infoL, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cambioCorreoL)
                        .addComponent(confirmarB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    )
                    .addContainerGap()
                )
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(tituloL)
        			.addGap(18)
        			.addComponent(infoL)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(registrarDNITF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cambioCorreoL)
        			.addGap(18)
        			.addComponent(correoL, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(registrarCorreoTF, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(contraseniaL)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(registrarContraseniaTF, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(confirmarB)
        			.addGap(20))
        );
        getContentPane().setLayout(layout);
        pack();
    }
    
    public JLabel getCambioCorreoL() {return this.cambioCorreoL;}
    public JLabel getCorreoL() {return this.correoL;}
    public JLabel getContraseniaL() {return this.contraseniaL;}
    public JTextField getRegistrarDNITF() {return this.registrarDNITF;}
    public String getDNI() {return this.registrarDNITF.getText();}
    public JTextField getRegistrarCorreoTF() {return this.registrarCorreoTF;}
    public String getCorreo() {return this.registrarCorreoTF.getText();}
    public JTextField getRegistrarContraseniaTF() {return this.registrarContraseniaTF;}
    public String getContrasenia() {return this.registrarContraseniaTF.getText();}
    public JButton getBotonConfirmar() {return this.confirmarB;}
    
}