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

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import br.com.jinsync.model.Language;
import br.com.jinsync.model.Parameters;
import java.awt.Color;

public class FrmAbout extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
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
			FrmAbout dialog = new FrmAbout();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FrmAbout() {
		
		setTitle("JInSync - " + Language.formAbout);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 343, 311);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{15, 35, 259, 15};
		gridBagLayout.rowHeights = new int[]{15, 65, 0, 16, 0, 20, 0, 0, 0, 0, 0, 16, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 4.9E-324};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(FrmAbout.class.getResource("/resources/about.png")));
			lblNewLabel.setAutoscrolls(true);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 1;
			getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		}
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.anchor = GridBagConstraints.WEST;
		gbc_contentPanel.fill = GridBagConstraints.VERTICAL;
		gbc_contentPanel.insets = new Insets(0, 0, 5, 0);
		gbc_contentPanel.gridx = 2;
		gbc_contentPanel.gridy = 1;
		getContentPane().add(contentPanel, gbc_contentPanel);
		{
			JLabel lblFtpmainframe = new JLabel("JInSync");
			lblFtpmainframe.setFont(new Font("Tahoma", Font.BOLD, 17));
			contentPanel.add(lblFtpmainframe);
		}
		{
			JLabel lblVerso = new JLabel(Language.txtVersion + " " + Parameters.version);
			lblVerso.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblVerso = new GridBagConstraints();
			gbc_lblVerso.gridwidth = 2;
			gbc_lblVerso.anchor = GridBagConstraints.WEST;
			gbc_lblVerso.insets = new Insets(0, 0, 5, 0);
			gbc_lblVerso.gridx = 1;
			gbc_lblVerso.gridy = 2;
			getContentPane().add(lblVerso, gbc_lblVerso);
		}
		{
			JLabel lblNewLabel_1 = new JLabel(Language.txtDescription);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.gridwidth = 2;
			gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_1.gridx = 1;
			gbc_lblNewLabel_1.gridy = 3;
			getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel(Language.txtDescrDetail);
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.gridwidth = 2;
			gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_2.gridx = 1;
			gbc_lblNewLabel_2.gridy = 4;
			getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		}
		{
			JLabel lblGnuLessGeneral = new JLabel("GNU Less General Public License");
			lblGnuLessGeneral.setForeground(Color.BLUE);
			lblGnuLessGeneral.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblGnuLessGeneral.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblGnuLessGeneral.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() > 0) {
			          if (Desktop.isDesktopSupported()) {
			                Desktop desktop = Desktop.getDesktop();
			                try {
			                    URI uri = new URI("https://www.gnu.org/licenses/gpl-3.0-standalone.html");
			                    desktop.browse(uri);
			                } catch (IOException ex) {
			                    ex.printStackTrace();
			                } catch (URISyntaxException ex) {
			                    ex.printStackTrace();
			                }
			        }
			      }
			   }
			});
			
			
			GridBagConstraints gbc_lblGnuLessGeneral = new GridBagConstraints();
			gbc_lblGnuLessGeneral.insets = new Insets(0, 0, 5, 5);
			gbc_lblGnuLessGeneral.gridx = 1;
			gbc_lblGnuLessGeneral.gridy = 6;
			getContentPane().add(lblGnuLessGeneral, gbc_lblGnuLessGeneral);
		}
		{
			GridBagConstraints separatorConstraint = new GridBagConstraints();
			separatorConstraint.insets = new Insets(0, 0, 5, 0);
			separatorConstraint.gridx = 1;
			separatorConstraint.gridy = 7;
	        separatorConstraint.weightx = 1.0;
	        separatorConstraint.fill = GridBagConstraints.HORIZONTAL;
	        separatorConstraint.gridwidth = 2;
	        
	        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
	        getContentPane().add(separator, separatorConstraint);
	        
		}
		{
			JLabel lblDesenvolvidoPor = new JLabel(Language.txtDeveloped + ":");
			lblDesenvolvidoPor.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_lblDesenvolvidoPor = new GridBagConstraints();
			gbc_lblDesenvolvidoPor.anchor = GridBagConstraints.WEST;
			gbc_lblDesenvolvidoPor.gridwidth = 2;
			gbc_lblDesenvolvidoPor.insets = new Insets(0, 0, 5, 0);
			gbc_lblDesenvolvidoPor.gridx = 1;
			gbc_lblDesenvolvidoPor.gridy = 8;
			getContentPane().add(lblDesenvolvidoPor, gbc_lblDesenvolvidoPor);
		}
		{
			JLabel lblRodrigoAugustoSilva = new JLabel("Rodrigo Augusto Silva dos Santos");
			GridBagConstraints gbc_lblRodrigoAugustoSilva = new GridBagConstraints();
			gbc_lblRodrigoAugustoSilva.anchor = GridBagConstraints.WEST;
			gbc_lblRodrigoAugustoSilva.gridwidth = 2;
			gbc_lblRodrigoAugustoSilva.insets = new Insets(0, 0, 5, 0);
			gbc_lblRodrigoAugustoSilva.gridx = 1;
			gbc_lblRodrigoAugustoSilva.gridy = 9;
			getContentPane().add(lblRodrigoAugustoSilva, gbc_lblRodrigoAugustoSilva);
		}
		{
			JLabel lblRoaugustogmailcom = new JLabel("ro.augusto@gmail.com");
			GridBagConstraints gbc_lblRoaugustogmailcom = new GridBagConstraints();
			gbc_lblRoaugustogmailcom.anchor = GridBagConstraints.WEST;
			gbc_lblRoaugustogmailcom.gridwidth = 2;
			gbc_lblRoaugustogmailcom.insets = new Insets(0, 0, 5, 0);
			gbc_lblRoaugustogmailcom.gridx = 1;
			gbc_lblRoaugustogmailcom.gridy = 10;
			getContentPane().add(lblRoaugustogmailcom, gbc_lblRoaugustogmailcom);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			GridBagConstraints gbc_buttonPane = new GridBagConstraints();
			gbc_buttonPane.anchor = GridBagConstraints.NORTHEAST;
			gbc_buttonPane.gridx = 2;
			gbc_buttonPane.gridy = 11;
			getContentPane().add(buttonPane, gbc_buttonPane);
			{
				JButton okButton = new JButton(Language.btnOk);
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand(Language.btnOk);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
	}

}
