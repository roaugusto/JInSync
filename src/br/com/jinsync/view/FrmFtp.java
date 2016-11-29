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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import br.com.jinsync.model.Language;
import br.com.jinsync.model.ParameterFtp;

public class FrmFtp extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtFtp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FrmFtp dialog = new FrmFtp();
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
	public FrmFtp() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmFtp.class.getResource("/resources/about.png")));
		setModal(true);
		setTitle(Language.formIp);
		setBounds(100, 100, 237, 129);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{25, 176, 0};
		gbl_contentPanel.rowHeights = new int[]{15, 20, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblFtp = new JLabel(Language.txtIp);
			GridBagConstraints gbc_lblFtp = new GridBagConstraints();
			gbc_lblFtp.anchor = GridBagConstraints.SOUTHEAST;
			gbc_lblFtp.insets = new Insets(0, 0, 5, 5);
			gbc_lblFtp.gridx = 0;
			gbc_lblFtp.gridy = 1;
			contentPanel.add(lblFtp, gbc_lblFtp);
		}
		{
			txtFtp = new JTextField();
			GridBagConstraints gbc_txtFtp = new GridBagConstraints();
			gbc_txtFtp.insets = new Insets(0, 0, 5, 0);
			gbc_txtFtp.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtFtp.anchor = GridBagConstraints.SOUTH;
			gbc_txtFtp.gridx = 1;
			gbc_txtFtp.gridy = 1;
			contentPanel.add(txtFtp, gbc_txtFtp);
			txtFtp.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(Language.btnOk);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						gravarFtp(txtFtp.getText());
						dispose();
					}
				});
				okButton.setActionCommand(Language.btnOk);
				buttonPane.add(okButton);
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
				buttonPane.add(cancelButton);
			}
		}
		
		txtFtp.setText(buscarFtp());
		
	}
	
	private void gravarFtp(String ipFtp){
		
		ParameterFtp parFtp = new ParameterFtp();
		parFtp.setFtp(ipFtp);		
		
	}

	private String buscarFtp(){
		
		ParameterFtp parFtp = new ParameterFtp();
		return parFtp.getFtp();
		
	}
}
