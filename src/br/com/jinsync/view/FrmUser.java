//    This file is part of JInSync, developed by Rodrigo Augusto Silva dos Santos
//
//    JInSync is free software: you can redistribute it and/or modify it under 
//    the terms of the GNU Less General Public License as published by the Free 
//    Software Foundation, either version 3 of the License, or (at your option) 
//    any later version.
//
//    JInSync is distributed in the hope that it will be useful, but WITHOUT ANY 
//    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
//    FOR A PARTICULAR PURPOSE.  See the GNU Less General Public License for more 
//    details.
//
//    You should have received a copy of the GNU Less General Public License 
//    along with JInSync.  If not, see <http://www.gnu.org/licenses/>.    
//
//    -------------------------------------------------------------------------
//
//    Este arquivo é parte do programa JInSync, desenvolvido por Rodrigo Augusto
//    Silva dos Santos
//
//    JInSync é um software livre; você pode redistribuí-lo e/ou modificá-lo 
//    dentro dos termos da Licença Pública Geral Menor GNU como publicada 
//    pela Fundação do Software Livre (FSF); na versão 3 da Licença, ou 
//    (na sua opinião) qualquer versão.
//
//    Este programa é distribuído na esperança de que possa ser  útil, mas 
//    SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer 
//    MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral Menor 
//    GNU para maiores detalhes.
//
//    Você deve ter recebido uma cópia da Licença Pública Geral Menor GNU 
//    junto com este programa, Se não, veja <http://www.gnu.org/licenses/>.

package br.com.jinsync.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import br.com.jinsync.model.Language;
import br.com.jinsync.model.ParameterUser;

public class FrmUser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField txtUser;
	private JPasswordField txtPassword;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			FrmUser dialog = new FrmUser();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FrmUser() {		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmUser.class.getResource("/resources/about.png")));
						
		setModal(true);
		setResizable(false);
		setTitle(Language.formUser);
		setBounds(100, 100, 198, 154);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{40, 96, 0};
		gbl_contentPanel.rowHeights = new int[]{-5, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUsurio = new JLabel(Language.txtUser);
			GridBagConstraints gbc_lblUsurio = new GridBagConstraints();
			gbc_lblUsurio.anchor = GridBagConstraints.WEST;
			gbc_lblUsurio.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsurio.gridx = 0;
			gbc_lblUsurio.gridy = 0;
			contentPanel.add(lblUsurio, gbc_lblUsurio);
		}
		{
			txtUser = new JTextField();
			GridBagConstraints gbc_txtUser = new GridBagConstraints();
			gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUser.insets = new Insets(0, 0, 5, 0);
			gbc_txtUser.anchor = GridBagConstraints.NORTH;
			gbc_txtUser.gridx = 1;
			gbc_txtUser.gridy = 0;
			contentPanel.add(txtUser, gbc_txtUser);
			txtUser.setColumns(10);
			
		}
		{
			JLabel lblPassword = new JLabel(Language.txtPassword);
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 1;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			txtPassword = new JPasswordField();
			GridBagConstraints gbc_txtPassword = new GridBagConstraints();
			gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPassword.gridx = 1;
			gbc_txtPassword.gridy = 1;
			contentPanel.add(txtPassword, gbc_txtPassword);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			GridBagLayout gbl_buttonPane = new GridBagLayout();
			gbl_buttonPane.columnWidths = new int[]{15, 72, 44, 0};
			gbl_buttonPane.rowHeights = new int[]{23, 9, 0};
			gbl_buttonPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_buttonPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			buttonPane.setLayout(gbl_buttonPane);
			{
				JButton okButton = new JButton(Language.btnOk);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						setUser(txtUser.getText(), new String(txtPassword.getPassword()));
						dispose();
					}
				});
				okButton.setActionCommand(Language.btnOk);
				GridBagConstraints gbc_okButton = new GridBagConstraints();
				gbc_okButton.anchor = GridBagConstraints.NORTH;
				gbc_okButton.insets = new Insets(0, 0, 5, 5);
				gbc_okButton.gridx = 1;
				gbc_okButton.gridy = 0;
				buttonPane.add(okButton, gbc_okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(Language.btnCancel);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand(Language.btnCancel);
				GridBagConstraints gbc_cancelButton = new GridBagConstraints();
				gbc_cancelButton.insets = new Insets(0, 0, 5, 0);
				gbc_cancelButton.anchor = GridBagConstraints.NORTH;
				gbc_cancelButton.gridx = 2;
				gbc_cancelButton.gridy = 0;
				buttonPane.add(cancelButton, gbc_cancelButton);
			}
		}
		
		addWindowListener(new WindowAdapter(){
	        @Override
			public void windowOpened(WindowEvent e){
	        	String nomeUsuar = txtUser.getText().trim(); 
	    		if (!nomeUsuar.equals("")){
	    			txtPassword.grabFocus();
	    		}

	        }
	      }
	    );   
			
		txtUser.setText(getUser());
		txtPassword.setText(getPassword());

	}

	private void setUser(String usuar, String sen){
		
		ParameterUser parUsuar = new ParameterUser();
		parUsuar.setUsuar(usuar, sen);	
		
	}

	private String getUser(){
		
		ParameterUser parUsuar = new ParameterUser();
		return parUsuar.getUsuar();
		
	}

	private String getPassword(){
		
		ParameterUser parUser = new ParameterUser();
		return parUser.getPassword();
		
	}

}
