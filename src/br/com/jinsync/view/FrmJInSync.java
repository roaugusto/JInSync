//This file is part of JInSync, developed by Rodrigo Augusto Silva dos Santos
//
//JInSync is free software: you can redistribute it and/or modify it under 
//the terms of the GNU Less General Public License as published by the Free 
//Software Foundation, either version 3 of the License, or (at your option) 
//any later version.
//
//JInSync is distributed in the hope that it will be useful, but WITHOUT ANY 
//WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
//FOR A PARTICULAR PURPOSE.  See the GNU Less General Public License for more 
//details.
//
//You should have received a copy of the GNU Less General Public License 
//along with JInSync.  If not, see <http://www.gnu.org/licenses/>.    
//
//-------------------------------------------------------------------------
//
//Este arquivo é parte do programa JInSync, desenvolvido por Rodrigo Augusto
//Silva dos Santos
//
//JInSync é um software livre; você pode redistribuí-lo e/ou modificá-lo 
//dentro dos termos da Licença Pública Geral Menor GNU como publicada 
//pela Fundação do Software Livre (FSF); na versão 3 da Licença, ou 
//(na sua opinião) qualquer versão.
//
//Este programa é distribuído na esperança de que possa ser  útil, mas 
//SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer 
//MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral Menor 
//GNU para maiores detalhes.
//
//Você deve ter recebido uma cópia da Licença Pública Geral Menor GNU 
//junto com este programa, Se não, veja <http://www.gnu.org/licenses/>.

package br.com.jinsync.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import br.com.jinsync.controller.CopybookTableModel;
import br.com.jinsync.controller.EnableComponents;
import br.com.jinsync.controller.ExportExcelBook;
import br.com.jinsync.controller.ExportExcelFile;
import br.com.jinsync.controller.ExportExcelString;
import br.com.jinsync.controller.FileTableModel;
import br.com.jinsync.controller.ProcessCopy;
import br.com.jinsync.controller.SwingWorkerCustom;
import br.com.jinsync.controller.TableSwingWorker;
import br.com.jinsync.model.Copybook;
import br.com.jinsync.model.Language;
import br.com.jinsync.model.ParameterDir;
import br.com.jinsync.model.ParameterFile;
import br.com.jinsync.model.ParameterFtp;
import br.com.jinsync.model.ParameterUser;
import br.com.jinsync.model.Parameters;

public class FrmJInSync extends JFrame implements KeyListener {

	private static FrmJInSync frmJInSync;
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> console = new DefaultListModel<String>();
	private DefaultTableModel tableStringModel;
	private FileTableModel tableFileModel;

	private JList<String> listTerminal;
	private CopybookTableModel modeloCopybook;

	private JScrollPane scrConsole;
	private JTextField txtPath;
	private JButton btnExcel;
	private JButton btnExcelFile;
	private JButton btnProcFile;
	private JButton btnDirFile;

	private JScrollPane scrCopy = new JScrollPane();
	private GridBagConstraints gbc_scrCopy;

	JScrollPane scrTableString = new JScrollPane();
	JScrollPane scrFile = new JScrollPane();

	private String nameCopy = "";
	private String user = "";
	private String password = "";
	private String ipFtp = "";
	private String path = "";
	private String nameFile = "";

	private int qtdLargerGrp = 0;

	private File arquivo;

	List<Copybook> listTotCopy = new ArrayList<Copybook>();
	private JTable tableCopy;
	private JTable tableString;
	private JTable tableFile;

	private FTPClient ftp;
	private boolean conexao = false;
	private String filtro = "";

	private JTabbedPane grpFontes;
	private JTextArea textAreaString;
	private JButton btnExcelString;
	private JButton btnClearString;
	private String[] contentStr;
	private JButton btnClearCopy;
	private JButton btnClearFile;

	private List<String> contentType;
	private List<String> contentLength;
	private List<String> contentDecimal;
	private List<Integer> contentTotal;

	private List<String> dependField = new ArrayList<String>();
	private List<Integer> dependValue = new ArrayList<Integer>();
	private List<String> defField = new ArrayList<String>();

	private boolean fileOk;

	private Panel panelConsole;
	private JTextField txtFile;
	private JTextField txtLength;
	private JProgressBar progressBar;

	private JPanel tabFile;
	private JPanel tabCopybook;
	private JPanel tabString;

	// private ProcessFile procFile = new ProcessFile();

	private boolean isProcessStarted = false;
	private String[] columnNamesFile;
	private TableSwingWorker tbWorker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					frmJInSync = new FrmJInSync();
					frmJInSync.setLocationRelativeTo(null);
					frmJInSync.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public FrmJInSync() {
		setTitle("JInSync " + Parameters.version);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmJInSync.class.getResource("/resources/about.png")));

		setFocusable(true);
		addKeyListener(this);
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Language.loadParameters();

		setBounds(100, 100, 1136, 665);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 600, 53, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		grpFontes = new JTabbedPane(SwingConstants.TOP);
		GridBagConstraints gbc_grpFontes = new GridBagConstraints();
		gbc_grpFontes.insets = new Insets(0, 0, 5, 0);
		gbc_grpFontes.fill = GridBagConstraints.BOTH;
		gbc_grpFontes.gridx = 0;
		gbc_grpFontes.gridy = 0;
		getContentPane().add(grpFontes, gbc_grpFontes);

		tabCopybook = new JPanel();
		tabCopybook.setBackground(Color.WHITE);
		grpFontes.addTab(Language.tabCopy, null, tabCopybook, null);

		GridBagLayout gbl_tabCopybook = new GridBagLayout();
		gbl_tabCopybook.columnWidths = new int[] { 10, 1, 0, 349, 0, 0, 62, 28, 71, 0, 0, 0 };
		gbl_tabCopybook.rowHeights = new int[] { 1, 0, 0, 0 };
		gbl_tabCopybook.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_tabCopybook.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabCopybook.setLayout(gbl_tabCopybook);

		JLabel lblCopybook = new JLabel(Language.txtFilePds);
		GridBagConstraints gbc_lblCopybook = new GridBagConstraints();
		gbc_lblCopybook.anchor = GridBagConstraints.EAST;
		gbc_lblCopybook.insets = new Insets(0, 0, 5, 5);
		gbc_lblCopybook.gridx = 1;
		gbc_lblCopybook.gridy = 1;
		tabCopybook.add(lblCopybook, gbc_lblCopybook);

		txtPath = new JTextField();
		GridBagConstraints gbc_txtPath = new GridBagConstraints();
		gbc_txtPath.gridwidth = 4;
		gbc_txtPath.insets = new Insets(0, 0, 5, 5);
		gbc_txtPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPath.gridx = 2;
		gbc_txtPath.gridy = 1;
		tabCopybook.add(txtPath, gbc_txtPath);
		txtPath.setColumns(10);

		JButton btnDiretorio = new JButton("");
		btnDiretorio.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/folder.png")));
		btnDiretorio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openDirectory();
			}
		});
		GridBagConstraints gbc_btnDiretorio = new GridBagConstraints();
		gbc_btnDiretorio.insets = new Insets(0, 0, 5, 5);
		gbc_btnDiretorio.gridx = 6;
		gbc_btnDiretorio.gridy = 1;
		tabCopybook.add(btnDiretorio, gbc_btnDiretorio);

		btnClearCopy = new JButton("");
		btnClearCopy.setEnabled(false);
		btnClearCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tableCopy = new JTable();
				scrCopy.setViewportView(tableCopy);
				tableString = new JTable();
				scrTableString.setViewportView(tableString);

				btnExcelString.setEnabled(false);
				btnClearString.setEnabled(false);
				textAreaString.setText("");

				console.removeAllElements();
				btnExcel.setEnabled(false);
				btnClearCopy.setEnabled(false);
				grpFontes.setEnabledAt(1, false);

				tableFile = new JTable();
				scrFile.setViewportView(tableFile);
				btnExcelFile.setEnabled(false);
				btnClearFile.setEnabled(false);
				grpFontes.setEnabledAt(2, false);

			}
		});
		btnClearCopy.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/cancel.png")));
		GridBagConstraints gbc_btnLimpar = new GridBagConstraints();
		gbc_btnLimpar.fill = GridBagConstraints.BOTH;
		gbc_btnLimpar.insets = new Insets(0, 0, 5, 5);
		gbc_btnLimpar.gridx = 9;
		gbc_btnLimpar.gridy = 1;
		tabCopybook.add(btnClearCopy, gbc_btnLimpar);

		JButton btnProcessarBook = new JButton("");
		btnProcessarBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadData();
				btnClearCopy.setEnabled(true);
				grpFontes.setEnabledAt(1, true);
				grpFontes.setEnabledAt(2, true);
			}
		});
		btnProcessarBook.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/engine.png")));
		GridBagConstraints gbc_btnProcessarBook = new GridBagConstraints();
		gbc_btnProcessarBook.fill = GridBagConstraints.VERTICAL;
		gbc_btnProcessarBook.insets = new Insets(0, 0, 5, 5);
		gbc_btnProcessarBook.gridx = 7;
		gbc_btnProcessarBook.gridy = 1;
		tabCopybook.add(btnProcessarBook, gbc_btnProcessarBook);

		btnExcel = new JButton("");
		btnExcel.setEnabled(false);
		btnExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				expExcelBook();
				// exportarExcelBook(tableCopy, txtPath.getText(),
				// "Horizontal");
			}
		});
		btnExcel.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/excel.png")));
		GridBagConstraints gbc_btnExcel = new GridBagConstraints();
		gbc_btnExcel.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcel.gridx = 8;
		gbc_btnExcel.gridy = 1;
		tabCopybook.add(btnExcel, gbc_btnExcel);

		// JScrollPane scrCopy = new JScrollPane();
		gbc_scrCopy = new GridBagConstraints();
		gbc_scrCopy.gridwidth = 9;
		gbc_scrCopy.insets = new Insets(0, 0, 0, 5);
		gbc_scrCopy.fill = GridBagConstraints.BOTH;
		gbc_scrCopy.gridx = 1;
		gbc_scrCopy.gridy = 2;
		// tabCopybook.add(scrCopy, gbc_scrCopy);

		panelConsole = new Panel();
		GridBagConstraints gbc_panelConsole = new GridBagConstraints();
		gbc_panelConsole.insets = new Insets(0, 0, 5, 0);
		gbc_panelConsole.fill = GridBagConstraints.BOTH;
		gbc_panelConsole.gridx = 0;
		gbc_panelConsole.gridy = 1;
		getContentPane().add(panelConsole, gbc_panelConsole);
		panelConsole.setLayout(new BorderLayout(0, 0));

		listTerminal = new JList<String>();
		listTerminal.setFont(new Font("Courier New", Font.PLAIN, 11));
		listTerminal.setModel(console);

		scrConsole = new JScrollPane(listTerminal);
		panelConsole.add(scrConsole);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu(Language.menuParam);
		menuBar.add(mnNewMenu);

		JMenuItem mnItemUsuario = new JMenuItem(Language.menuParamUser);
		mnItemUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrmUser frmUsuar = new FrmUser();
				frmUsuar.setLocationRelativeTo(null);
				frmUsuar.setVisible(true);
				setUser();
			}
		});
		mnNewMenu.add(mnItemUsuario);

		JMenuItem mnItemFtp = new JMenuItem(Language.menuParamFtp);
		mnItemFtp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FrmFtp frmFtp = new FrmFtp();
				frmFtp.setLocationRelativeTo(null);
				frmFtp.setVisible(true);
				setFtp();
			}
		});

		mnNewMenu.add(mnItemFtp);

		JMenu mnAjuda = new JMenu(Language.menuHelp);
		menuBar.add(mnAjuda);

		JMenuItem mntmNewMenuItem = new JMenuItem(Language.menuHelpAbout);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrmAbout frmSobre = new FrmAbout();
				frmSobre.setLocationRelativeTo(null);
				frmSobre.setVisible(true);
			}
		});
		mnAjuda.add(mntmNewMenuItem);

		tabCopybook.setFont(new Font("Arial", Font.PLAIN, 12));
		tabCopybook.add(scrCopy, gbc_scrCopy);

		tabString = new JPanel();
		tabString.setFont(new Font("Arial", Font.PLAIN, 12));
		tabString.setBackground(Color.WHITE);
		grpFontes.addTab(Language.tabString, null, tabString, null);
		grpFontes.setEnabledAt(1, false);
		GridBagLayout gbl_tabString = new GridBagLayout();
		gbl_tabString.columnWidths = new int[] { 10, 1, 346, 349, 0, 0, 62, 28, 71, 0, 0, 0 };
		gbl_tabString.rowHeights = new int[] { 1, 0, 0, 17, 22, 0, 259, 0 };
		gbl_tabString.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_tabString.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabString.setLayout(gbl_tabString);

		JLabel lblString = new JLabel("String:");
		GridBagConstraints gbc_lblString = new GridBagConstraints();
		gbc_lblString.anchor = GridBagConstraints.WEST;
		gbc_lblString.insets = new Insets(0, 0, 5, 5);
		gbc_lblString.gridx = 1;
		gbc_lblString.gridy = 1;
		tabString.add(lblString, gbc_lblString);

		textAreaString = new JTextArea();

		JScrollPane scrStringData = new JScrollPane(textAreaString);
		GridBagConstraints gbc_scrString = new GridBagConstraints();
		gbc_scrString.gridheight = 3;
		gbc_scrString.gridwidth = 8;
		gbc_scrString.insets = new Insets(0, 0, 5, 5);
		gbc_scrString.fill = GridBagConstraints.BOTH;
		gbc_scrString.gridx = 1;
		gbc_scrString.gridy = 2;
		tabString.add(scrStringData, gbc_scrString);

		btnClearString = new JButton("");
		btnClearString.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scrTableString.setViewportView(tableString);
				btnExcelString.setEnabled(false);
				btnClearString.setEnabled(false);
				textAreaString.setText("");
				tableString = new JTable();
				scrTableString.setViewportView(tableString);
			}
		});

		btnExcelString = new JButton("");
		btnExcelString.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				expExcelString();
			}
		});

		JButton btnProcessarArq = new JButton("");
		btnProcessarArq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				processString();
			}
		});
		btnProcessarArq.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/engine.png")));
		GridBagConstraints gbc_btnProcessarArq = new GridBagConstraints();
		gbc_btnProcessarArq.fill = GridBagConstraints.VERTICAL;
		gbc_btnProcessarArq.insets = new Insets(0, 0, 5, 5);
		gbc_btnProcessarArq.gridx = 9;
		gbc_btnProcessarArq.gridy = 2;
		tabString.add(btnProcessarArq, gbc_btnProcessarArq);
		btnExcelString.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/excel.png")));
		btnExcelString.setEnabled(false);
		GridBagConstraints gbc_btnExcelString = new GridBagConstraints();
		gbc_btnExcelString.fill = GridBagConstraints.VERTICAL;
		gbc_btnExcelString.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcelString.gridx = 9;
		gbc_btnExcelString.gridy = 3;
		tabString.add(btnExcelString, gbc_btnExcelString);
		btnClearString.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/cancel.png")));
		btnClearString.setEnabled(false);
		GridBagConstraints gbc_btnClearString = new GridBagConstraints();
		gbc_btnClearString.fill = GridBagConstraints.VERTICAL;
		gbc_btnClearString.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearString.gridx = 9;
		gbc_btnClearString.gridy = 4;
		tabString.add(btnClearString, gbc_btnClearString);

		JLabel lblSaida = new JLabel(Language.txtOutput);
		GridBagConstraints gbc_lblSaida = new GridBagConstraints();
		gbc_lblSaida.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaida.gridx = 1;
		gbc_lblSaida.gridy = 5;
		tabString.add(lblSaida, gbc_lblSaida);

		GridBagConstraints gbc_scrArquivo = new GridBagConstraints();
		gbc_scrArquivo.fill = GridBagConstraints.BOTH;
		gbc_scrArquivo.gridwidth = 9;
		gbc_scrArquivo.insets = new Insets(0, 0, 0, 5);
		gbc_scrArquivo.gridx = 1;
		gbc_scrArquivo.gridy = 6;
		tabString.add(scrTableString, gbc_scrArquivo);

		tabFile = new JPanel();
		tabFile.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (isProcessStarted) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ESCAPE) {
						int opc = JOptionPane.showConfirmDialog(null, Language.msgCancelProcess, Language.msgInf,
								JOptionPane.OK_CANCEL_OPTION);
						if (opc == JOptionPane.OK_OPTION) {
							escProcessFile();
						}
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		tabFile.setFont(new Font("Arial", Font.PLAIN, 12));
		tabFile.setBackground(Color.WHITE);
		grpFontes.addTab(Language.tabFile, null, tabFile, null);
		grpFontes.setEnabledAt(2, false);
		GridBagLayout gbl_tabFile = new GridBagLayout();
		gbl_tabFile.columnWidths = new int[] { 10, 1, 49, 459, 0, 0, 62, 28, 0, 71, 0, 0, 0, 0 };
		gbl_tabFile.rowHeights = new int[] { 1, 0, 0, 17, 22, 0, 259, 0, 0 };
		gbl_tabFile.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_tabFile.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		tabFile.setLayout(gbl_tabFile);

		JLabel lblFile = new JLabel(Language.txtFilePds);
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.anchor = GridBagConstraints.EAST;
		gbc_lblFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblFile.gridx = 1;
		gbc_lblFile.gridy = 1;
		tabFile.add(lblFile, gbc_lblFile);

		txtFile = new JTextField();
		txtFile.setColumns(10);
		GridBagConstraints gbc_txtFile = new GridBagConstraints();
		gbc_txtFile.gridwidth = 5;
		gbc_txtFile.insets = new Insets(0, 0, 5, 5);
		gbc_txtFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFile.gridx = 2;
		gbc_txtFile.gridy = 1;
		tabFile.add(txtFile, gbc_txtFile);

		btnClearFile = new JButton("");
		btnClearFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				tableFile = new JTable();
				scrFile.setViewportView(tableFile);
				btnExcelFile.setEnabled(false);
				btnClearFile.setEnabled(false);
				progressBar.setStringPainted(true);
				progressBar.setValue(0);
				progressBar.setString("");

			}
		});

		btnExcelFile = new JButton("");
		btnExcelFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				expExcelFile();
			}
		});

		btnProcFile = new JButton("");
		btnProcFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});

		btnDirFile = new JButton("");
		btnDirFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openDirFile();
			}
		});
		btnDirFile.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/folder.png")));
		GridBagConstraints gbc_btnDirFile = new GridBagConstraints();
		gbc_btnDirFile.fill = GridBagConstraints.VERTICAL;
		gbc_btnDirFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnDirFile.gridx = 7;
		gbc_btnDirFile.gridy = 1;
		tabFile.add(btnDirFile, gbc_btnDirFile);
		btnProcFile.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/engine.png")));
		GridBagConstraints gbc_btnProcFile = new GridBagConstraints();
		gbc_btnProcFile.fill = GridBagConstraints.VERTICAL;
		gbc_btnProcFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnProcFile.gridx = 8;
		gbc_btnProcFile.gridy = 1;
		tabFile.add(btnProcFile, gbc_btnProcFile);
		btnExcelFile.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/excel.png")));
		btnExcelFile.setEnabled(false);
		GridBagConstraints gbc_btnExcelFile = new GridBagConstraints();
		gbc_btnExcelFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcelFile.gridx = 9;
		gbc_btnExcelFile.gridy = 1;
		tabFile.add(btnExcelFile, gbc_btnExcelFile);
		btnClearFile.setIcon(new ImageIcon(FrmJInSync.class.getResource("/resources/cancel.png")));
		btnClearFile.setEnabled(false);
		GridBagConstraints gbc_btnLimparFile = new GridBagConstraints();
		gbc_btnLimparFile.fill = GridBagConstraints.VERTICAL;
		gbc_btnLimparFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnLimparFile.gridx = 10;
		gbc_btnLimparFile.gridy = 1;
		tabFile.add(btnClearFile, gbc_btnLimparFile);

		JLabel lblTamanho = new JLabel(Language.txtFileLength);
		GridBagConstraints gbc_lblTamanho = new GridBagConstraints();
		gbc_lblTamanho.anchor = GridBagConstraints.WEST;
		gbc_lblTamanho.insets = new Insets(0, 0, 5, 5);
		gbc_lblTamanho.gridx = 1;
		gbc_lblTamanho.gridy = 2;
		tabFile.add(lblTamanho, gbc_lblTamanho);

		txtLength = new JTextField();
		GridBagConstraints gbc_txtTamanho = new GridBagConstraints();
		gbc_txtTamanho.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTamanho.insets = new Insets(0, 0, 5, 5);
		gbc_txtTamanho.gridx = 2;
		gbc_txtTamanho.gridy = 2;
		tabFile.add(txtLength, gbc_txtTamanho);
		txtLength.setColumns(10);

		JLabel label_1 = new JLabel(Language.txtOutput);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 3;
		tabFile.add(label_1, gbc_label_1);

		GridBagConstraints gbc_scrFile = new GridBagConstraints();
		gbc_scrFile.gridheight = 3;
		gbc_scrFile.fill = GridBagConstraints.BOTH;
		gbc_scrFile.gridwidth = 10;
		gbc_scrFile.insets = new Insets(0, 0, 5, 5);
		gbc_scrFile.gridx = 1;
		gbc_scrFile.gridy = 4;
		tabFile.add(scrFile, gbc_scrFile);

		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 10;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 7;
		tabFile.add(progressBar, gbc_progressBar);

		JLabel lblNewLabel = new JLabel(Language.txtDeveloped + " Rodrigo Augusto Silva dos Santos - 2016");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		loadParameters();

	}

	public void openDirectory() {

		JFileChooser dir = new JFileChooser();
		// String path = "C:\\";
		String path = System.getProperty("user.dir").substring(0, 3);

		if (!txtPath.getText().equals("") && (!txtPath.getText().contains("("))) {
			path = txtPath.getText();
		}

		dir.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dir.setCurrentDirectory(new File(path));

		int res = dir.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {
			txtPath.setText(dir.getSelectedFile().toString());
			String nomeDir = dir.getSelectedFile().toString();

			ParameterDir parDir = new ParameterDir();
			parDir.setDir(nomeDir);

			arquivo = dir.getSelectedFile();
			nameCopy = arquivo.getName();
			tableCopy = new JTable();
			scrCopy.setViewportView(tableCopy);

			tableFile = new JTable();
			scrFile.setViewportView(tableFile);

		}

	}

	public void loadData() {

		if (txtPath.getText().equals("")) {
			JOptionPane.showMessageDialog(null, Language.msgCopyNotFound, Language.msgErr, JOptionPane.ERROR_MESSAGE);
			return;
		}

		ParameterDir parDir = new ParameterDir();
		parDir.setDir(txtPath.getText());

		if (txtPath.getText().indexOf("(") > 0) {
			downloadFileFtp(txtPath.getText(), "CPY");
		} else {
			ProcessCopy pc = new ProcessCopy();
			pc.processCopy(txtPath.getText());
			listTotCopy = pc.getListTotCopy();
			dependField = pc.getDependField();
			
			listCopy(listTotCopy);
			btnExcel.setEnabled(true);
			qtdLargerGrp = pc.getQtdLargerGrp();
			txtLength.setText(Integer.toString(qtdLargerGrp));
		}

	}

	private void listCopy(List<Copybook> listCopy) {

		tableCopy = new JTable();
		tableCopy.setBackground(Color.WHITE);
		tableCopy.setRowSelectionAllowed(true);
		tableCopy.setCellSelectionEnabled(true);
		tableCopy.setColumnSelectionAllowed(false);

		tableCopy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		// listCopy = listTotCopy;

		modeloCopybook = new CopybookTableModel(listCopy);
		tableCopy.setModel(modeloCopybook);
		tableCopy.setFont(new Font("Courier New", Font.PLAIN, 12));

		tableCopy.getColumnModel().getColumn(0).setMinWidth(0);
		tableCopy.getColumnModel().getColumn(0).setPreferredWidth(0);
		tableCopy.getColumnModel().getColumn(0).setMaxWidth(0);

		// Coluna Sequencia
		tableCopy.getColumnModel().getColumn(1).setPreferredWidth(60);

		// Coluna Nivel
		tableCopy.getColumnModel().getColumn(2).setMinWidth(0);
		tableCopy.getColumnModel().getColumn(2).setPreferredWidth(0);
		tableCopy.getColumnModel().getColumn(2).setMaxWidth(0);

		// Coluna Nome
		tableCopy.getColumnModel().getColumn(3).setPreferredWidth(300);

		// Coluna Redefines
		tableCopy.getColumnModel().getColumn(4).setPreferredWidth(300);

		// Coluna Formato
		tableCopy.getColumnModel().getColumn(5).setPreferredWidth(120);

		// Coluna Inteiro
		tableCopy.getColumnModel().getColumn(6).setPreferredWidth(70);
		tableCopy.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		// Coluna Decimal
		tableCopy.getColumnModel().getColumn(7).setPreferredWidth(70);
		tableCopy.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

		// Coluna Inicio
		tableCopy.getColumnModel().getColumn(8).setPreferredWidth(50);
		tableCopy.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);

		tableCopy.getColumnModel().getColumn(9).setPreferredWidth(68);

		tableCopy.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		scrCopy.setViewportView(tableCopy);

	}

	public void loadParameters() {

		setFtp();
		setUser();
		setNameCopy();
		setNameFile();

		if (txtPath.getText().equals("")) {
			txtPath.setText(path);
		}

		if (txtFile.getText().equals("")) {
			txtFile.setText(nameFile);
		}

	}

	public boolean downloadFileFtp(String pds, String tipo) {

		connectionFtp(pds, tipo);

		return false;

	}

	public void connectionFtp(String nomePds, String tipo) {

		// setNomePds(nomePds);

		ftp = new FTPClient();
		conexao = true;
		filtro = "";

		if (ipFtp.equals("")) {
			JOptionPane.showMessageDialog(null, Language.msgParameterNotFound, Language.msgErr,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (user.equals("")) {
			FrmUser frmPar = new FrmUser();
			frmPar.setLocationRelativeTo(null);
			frmPar.setVisible(true);
			loadParameters();
		}

		openConnection openConnect = new openConnection();
		openConnect.setType(tipo);
		openConnect.start();

	}

	private void rollScroll() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JScrollBar temp = scrConsole.getVerticalScrollBar();
				temp.setValue(temp.getMaximum());
			}
		});
	}

	private void loadTableString() {

		tableString = new JTable();
		List<String> campos = new ArrayList<String>();

		int qtdLinhas = 0;
		int ind = 0;
		int i = 0;

		int[] seqGrp = new int[100];
		int[] qtdOccurs = new int[100];
		int[] nivOccurs = new int[100];
		int[] indOccurs = new int[100];

		qtdLinhas = listTotCopy.size();

		for (i = 0; i < qtdLinhas; i++) {
			Copybook cp = listTotCopy.get(i);
			if (Integer.parseInt(cp.getLvlField().trim()) <= nivOccurs[ind]) {
				if (indOccurs[ind] < qtdOccurs[ind]) {
					indOccurs[ind] = indOccurs[ind] + 1;
					i = seqGrp[ind] + 1;
					cp = listTotCopy.get(i);
				} else {
					indOccurs[ind] = 1;
					ind = ind - 1;
				}
			}

			if (cp.getTypeField() != "Group") {

				if (cp.getRedefField().contains("OCCURS")) {
					int pos = cp.getRedefField().indexOf("OCCURS") + 7;
					int posFim = cp.getRedefField().indexOf("TIMES") - 1;
					int qtdOc = Integer.parseInt(cp.getRedefField().substring(pos, posFim).trim());

					for (int k = 1; k <= qtdOc; k++) {
						String val = "";
						String virg = "";
						val = val + virg + k;
						virg = ", ";
						val = "(" + val + ")";
						campos.add(cp.getNameField().toString() + val);
					}
					
				} else {

					if (qtdOccurs[ind] > 1) {
						String val = "";
						String virg = "";
						for (int k = 1; k <= ind; k++) {
							val = val + virg + indOccurs[k];
							virg = ", ";
						}
						val = "(" + val + ")";
						campos.add(cp.getNameField().toString() + val);
					} else {
						campos.add(cp.getNameField().toString());
					}

				}
			} else {
				if (cp.getRedefField().contains("OCCURS")) {
					int pos = cp.getRedefField().indexOf("OCCURS") + 7;
					int posFim = cp.getRedefField().indexOf("TIMES") - 1;
					ind = ind + 1;
					qtdOccurs[ind] = Integer.parseInt(cp.getRedefField().trim().substring(pos, posFim));
					nivOccurs[ind] = Integer.parseInt(cp.getLvlField().trim());
					seqGrp[ind] = i;
					indOccurs[ind] = 1;

					if (cp.getRedefField().contains("DEPENDING")) {
						pos = cp.getRedefField().indexOf("DEPENDING ON") + 13;
						String val = cp.getRedefField().substring(pos).trim();

						for (int k = 0; k < dependField.size(); k++) {
							if (val.equals(dependField.get(k))) {
								if (dependValue.size() == 0){
									qtdOccurs[ind] = 1;	
								}else{
									qtdOccurs[ind] = dependValue.get(k);	
								}								
							}
						}
					}
				}
			}

			if (i == qtdLinhas - 1) {
				if (indOccurs[ind] < qtdOccurs[ind]) {
					indOccurs[ind] = indOccurs[ind] + 1;
					i = seqGrp[ind];
				} else {
					indOccurs[ind] = 1;
					ind = ind - 1;
					if (ind >=  0) {
						if (indOccurs[ind] < qtdOccurs[ind]) {
							indOccurs[ind] = indOccurs[ind] + 1;
							i = seqGrp[ind];
						} 
					}
				}
			}

		}

		String[] columnNamesString = new String[campos.size()];
		columnNamesString = campos.toArray(columnNamesString);

		tableStringModel = new DefaultTableModel(new Object[][] {},
				// TITULOS DAS SUAS COLUNAS
				columnNamesString) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		tableString.setModel(tableStringModel);

		for (i = 0; i < columnNamesString.length; i++) {
			int teste = columnNamesString[i].length();
			tableString.getColumnModel().getColumn(i).setPreferredWidth(teste * 8);
		}

		tableString.getTableHeader().setReorderingAllowed(false);
		tableString.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrTableString.setViewportView(tableString);

	}

	public void processString() {

		int qtdLinhas = 0;
		int ind = 0;
		int i = 0;

		int[] seqGrp = new int[100];
		int[] qtdOccurs = new int[100];
		int[] nivOccurs = new int[100];
		int[] tamTotGrp = new int[100];
		int[] indOccurs = new int[100];

		seqGrp[0] = 0;
		qtdOccurs[0] = 1;
		nivOccurs[0] = 0;
		indOccurs[0] = 0;

		if (textAreaString.getText().equals("")) {
			return;
		}

		String str = new String();
		str = textAreaString.getText();
		str = str.replace("\n", "");

		List<String> conteudo = new ArrayList<String>();
		contentType = new ArrayList<String>();
		contentLength = new ArrayList<String>();
		contentDecimal = new ArrayList<String>();
		contentTotal = new ArrayList<Integer>();

		qtdLinhas = listTotCopy.size();

		for (i = 0; i < qtdLinhas; i++) {

			Copybook cp = listTotCopy.get(i);

			if (Integer.parseInt(cp.getLvlField().trim()) <= nivOccurs[ind]) {
				if (indOccurs[ind] < qtdOccurs[ind]) {
					indOccurs[ind] = indOccurs[ind] + 1;
					i = seqGrp[ind] + 1;
					cp = listTotCopy.get(i);
				} else {
					indOccurs[ind] = 1;
					ind = ind - 1;
				}
			}

			if (cp.getTypeField() != "Group") {

				int posIni = 0;
				int lenField = 0;
				int qtdOc = 0;
				
				int qtd = 0;
				for (int l = 1; l <= ind; l++){
					qtd = qtd + (tamTotGrp[l] * (indOccurs[l] - 1));
				}
				
				posIni = (Integer.parseInt(cp.getPosStartField()) - 1) +  qtd;

				if (cp.getRedefField().contains("OCCURS")) {
					int posIniOcc = cp.getRedefField().indexOf("OCCURS") + 7;
					int posFimOcc = cp.getRedefField().indexOf("TIMES") - 1;
					qtdOc = Integer.parseInt(cp.getRedefField().substring(posIniOcc, posFimOcc).trim());
					lenField = cp.getLenTotField() / qtdOc;
				}else{
					lenField = cp.getLenTotField();
					qtdOc = 1;
				}
											
				int posFim = posIni + lenField;
				
				try {
					
					String val = "";
					
					for (int j = 1; j <= qtdOc; j ++){
						val = "";
						
						if (posFim < str.length()) {
							val = str.substring(posIni, posFim);
						} else {
							if (posIni < str.length()) {
								val = str.substring(posIni, str.length());
							}
						}

						contentType.add(cp.getTypeField());
						contentLength.add(cp.getLenIntField().trim());
						contentDecimal.add(cp.getLenDecField().trim());
						contentTotal.add(lenField);
						conteudo.add(val);				
						
						posIni = posIni + lenField;
						posFim = posFim + lenField;
						
					}

					if (dependField.size() > 0) {
						for (int k = 0; k < dependField.size(); k++) {
							if (cp.getNameField().equals(dependField.get(k))) {
								dependValue.add(Integer.parseInt(val.trim()));
							}
						}
					}

				} catch (Exception e) {
					conteudo.add("");
				}

			} else {
				if (cp.getRedefField().contains("OCCURS")) {
					int pos = cp.getRedefField().indexOf("OCCURS") + 7;
					int posFim = cp.getRedefField().indexOf("TIMES") - 1;
					ind = ind + 1;
					qtdOccurs[ind] = Integer.parseInt(cp.getRedefField().substring(pos, posFim).trim());
					nivOccurs[ind] = Integer.parseInt(cp.getLvlField().trim());
					seqGrp[ind] = i;
					tamTotGrp[ind] = cp.getLenTotField() / qtdOccurs[ind];
					indOccurs[ind] = 1;

					if (cp.getRedefField().contains("DEPENDING")) {
						pos = cp.getRedefField().indexOf("DEPENDING ON") + 13;
						String val = cp.getRedefField().substring(pos).trim();

						for (int k = 0; k < dependField.size(); k++) {
							if (val.equals(dependField.get(k))) {
								
								if (dependValue.size() ==0 ){
									qtdOccurs[ind] = 1;
								}else{
									qtdOccurs[ind] = dependValue.get(k);
								}																
							}
						}
					}
				}
			}

			if (i == qtdLinhas - 1) {
				if (indOccurs[ind] < qtdOccurs[ind]) {
					indOccurs[ind] = indOccurs[ind] + 1;
					i = seqGrp[ind];
				} else {
					indOccurs[ind] = 1;
					ind = ind - 1;
					if (ind >=  0) {
						if (indOccurs[ind] < qtdOccurs[ind]) {
							indOccurs[ind] = indOccurs[ind] + 1;
							i = seqGrp[ind];
						}
					}
				}
			}

		}

		contentStr = new String[conteudo.size()];
		contentStr = conteudo.toArray(contentStr);

		loadTableString();

		tableStringModel.addRow(contentStr);
		btnExcelString.setEnabled(true);
		btnClearString.setEnabled(true);

	}

	private void setFtp() {

		ParameterFtp parFtp = new ParameterFtp();
		this.ipFtp = parFtp.getFtp();

	}

	private void setUser() {

		ParameterUser parUsuar = new ParameterUser();
		this.user = parUsuar.getUsuar();
		this.password = parUsuar.getPassword();

	}

	private void setNameCopy() {

		ParameterDir parDir = new ParameterDir();
		String dir = parDir.getDir();

		if (dir != null) {
			arquivo = new File(dir);
			this.path = dir;
			this.nameCopy = arquivo.getName();
		}

	}

	private void setNameFile() {

		ParameterFile parFile = new ParameterFile();
		this.nameFile = parFile.getFile();

	}

	private class openConnection extends SwingWorkerCustom {

		private String tipo = "";

		@Override
		public Object construct() {

			conexao = false;

			try {
				ftp.connect(ipFtp);
				ftp.setBufferSize(2048 * 2048);

				// verifica se conectou com sucesso!
				if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {

					boolean success = ftp.login(user, password);

					if (tipo.equals("CPY")) {
						ftp.setFileType(FTP.ASCII_FILE_TYPE);
					} else {
						ftp.setFileType(FTP.BINARY_FILE_TYPE);
					}

					ftp.enterLocalPassiveMode();

					if (success) {
						// System.out.println("Conexao efetuada com sucesso!");
						conexao = true;
					} else {
						ftp.disconnect();
						console.addElement(Language.consoleConnectionRefused);
						rollScroll();
						// System.out.println("Conexão recusada");
						// System.exit(1);
						conexao = false;
					}

				} else {
					// erro ao se conectar
					ftp.disconnect();
					// System.out.println("Conexão recusada");
					// System.exit(1);
					conexao = false;
				}

			} catch (Exception e) {
				// System.out.println("Ocorreu um erro: " + e);
				JOptionPane.showMessageDialog(null, Language.consoleClientFtp, Language.msgErr,
						JOptionPane.ERROR_MESSAGE);
			}
			return conexao;

		}

		@Override
		public void start() {
			console.addElement(Language.consoleOpenConnection);
			rollScroll();
			super.start();
		}

		@Override
		public void finished() {
			if (conexao == true) {

				if (tipo.equals("CPY")) {
					receiveCopybook recArq = new receiveCopybook();
					recArq.start();
				}

				if (tipo.equals("DAT")) {
					receiveFile recFile = new receiveFile();
					recFile.start();
				}

			}
		}

		public void setType(String tipo) {
			this.tipo = tipo;

		}
	}

	private class receiveCopybook extends SwingWorkerCustom {

		@Override
		public Object construct() {

			final String dir = System.getProperty("user.dir") + "\\copybook";

			File arqProp = new File(dir);
			if (!arqProp.exists()) {
				arqProp.mkdirs();
			}

			conexao = true;
			String[] listaPds;
			String nomePds = txtPath.getText();
			fileOk = false;

			Pattern pattern = Pattern.compile("\\(.+?\\)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(nomePds);
			while (matcher.find()) {
				filtro = matcher.group();
			}
			String nomePdsAlterado = nomePds.replace(filtro, "");
			filtro = filtro.replace("(", "");
			filtro = filtro.replace(")", "");

			String pds = "'" + nomePdsAlterado + "'";

			try {

				ftp.changeWorkingDirectory(pds);
				listaPds = ftp.listNames();

				if (listaPds != null) {
					for (String name : listaPds) {
						String nameFileMainframe = name;

						if (nameFileMainframe.startsWith(filtro)) {
							nameFileMainframe = nameFileMainframe.replace("'", "");
							nameFileMainframe = nameFileMainframe.toUpperCase();

							nameCopy = dir + "\\" + nameFileMainframe + ".cpy";
							FileOutputStream arqMainframe = new FileOutputStream(nameCopy);

							ftp.retrieveFile(nameFileMainframe, arqMainframe);
							arqMainframe.close();
							console.addElement(Language.consoleFileReception);
							rollScroll();
							fileOk = true;
							break;
						}

					}

					if (fileOk == false) {
						console.addElement(Language.msgFileNotFound);
						rollScroll();
					}

					ftp.disconnect();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
				// e.printStackTrace();
				fileOk = false;
			}

			return fileOk;

		}

		@Override
		public void start() {
			console.addElement(Language.consoleReadPDS);
			rollScroll();
			super.start();
		}

		@Override
		public void finished() {

			if (fileOk == true) {

				ProcessCopy pc = new ProcessCopy();
				pc.processCopy(nameCopy);
				listTotCopy = pc.getListTotCopy();
				listCopy(listTotCopy);
				btnExcel.setEnabled(true);
				qtdLargerGrp = pc.getQtdLargerGrp();
				txtLength.setText(Integer.toString(qtdLargerGrp));
			}

		}
	}

	private class receiveFile extends SwingWorkerCustom {

		String nameFileFtp = "";

		@Override
		public Object construct() {

			final String dir = System.getProperty("user.dir") + "\\file";

			File arqProp = new File(dir);
			if (!arqProp.exists()) {
				arqProp.mkdirs();
			}

			conexao = true;
			nameFileFtp = txtFile.getText().trim();
			String arqMain = "'" + txtFile.getText().trim() + "'";

			try {
				nameFileFtp = dir + "\\" + nameFileFtp + ".DAT";

				String remoteFile2 = arqMain;
				File downloadFile2 = new File(nameFileFtp);
				OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
				InputStream inputStream = ftp.retrieveFileStream(remoteFile2);

				String tamanho = ftp.getReplyString();
				int posIni = tamanho.indexOf("recfm") + 5;
				tamanho = tamanho.substring(posIni);
				tamanho = tamanho.replace("\n", "");
				tamanho = tamanho.replace("\r", "");
				tamanho = tamanho.trim();

				byte[] bytesArray = new byte[1024];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(bytesArray)) != -1) {
					outputStream2.write(bytesArray, 0, bytesRead);
				}

				outputStream2.close();
				inputStream.close();

				console.addElement(Language.consoleFileReception);
				rollScroll();
				fileOk = true;

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
				// e.printStackTrace();
				fileOk = false;
			}

			return fileOk;

		}

		@Override
		public void start() {
			console.addElement(Language.consoleReadPDS);
			rollScroll();
			super.start();
		}

		@Override
		public void finished() {

			if (fileOk == true) {
				processFile(nameFileFtp);
			}

		}
	}

	protected void loadFile() {

		if (txtFile.getText().equals("")) {
			JOptionPane.showMessageDialog(null, Language.msgFileNotFound, Language.msgErr, JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (txtFile.getText().indexOf("\\") > 0) {
			processFile(txtFile.getText());
		} else {
			downloadFileFtp(txtFile.getText(), "DAT");
		}

	}

	private void processFile(String nameCopy) {

		loadColumnsLayoutTableFile();

		String arq = nameCopy;
		int lenFile = 0;

		int qtdColumns = columnNamesFile.length;
		tableFileModel = new FileTableModel(columnNamesFile, qtdColumns);

		isProcessStarted = true;
		tabFile.setFocusable(true);
		tabFile.requestFocusInWindow();

		grpFontes.setEnabledAt(0, false);
		grpFontes.setEnabledAt(1, false);

		try {
			lenFile = Integer.parseInt(txtLength.getText());
		} catch (NumberFormatException e) {
			lenFile = qtdLargerGrp;
		}

		tbWorker = new TableSwingWorker(tableFileModel, listTotCopy, arq, lenFile, progressBar, tabFile);
		tbWorker.execute();

		loadLayoutTableFile();

		tbWorker.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (name.equals("state")) {
					SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
					switch (state) {
					case DONE:
						isProcessStarted = false;
						grpFontes.setEnabledAt(0, true);
						grpFontes.setEnabledAt(1, true);
					}
				}
			}
		});

	}

	private void escProcessFile() {

		tbWorker.cancel(true);
		EnableComponents.enable(tabFile, true);

	}

	private void loadColumnsLayoutTableFile() {

		tableFile = new JTable();
		List<String> campos = new ArrayList<String>();
		defField = new ArrayList<String>();

		int qtdLinhas = 0;
		int ind = 0;
		int i = 0;

		int[] seqGrp = new int[100];
		int[] qtdOccurs = new int[100];
		int[] nivOccurs = new int[100];
		int[] indOccurs = new int[100];

		qtdLinhas = listTotCopy.size();

		campos.add("SEQ");
		defField.add("Zoned Decimal");

		for (i = 0; i < qtdLinhas; i++) {
			Copybook cp = listTotCopy.get(i);
			if (Integer.parseInt(cp.getLvlField().trim()) <= nivOccurs[ind]) {
				if (indOccurs[ind] < qtdOccurs[ind]) {
					indOccurs[ind] = indOccurs[ind] + 1;
					i = seqGrp[ind] + 1;
					cp = listTotCopy.get(i);
				} else {
					indOccurs[ind] = 1;
					ind = ind - 1;
				}
			}

			if (cp.getTypeField() != "Group") {

				if (cp.getTypeField() != "Group") {
					if (qtdOccurs[ind] > 1) {
						String val = "";
						String virg = "";
						for (int k = 1; k <= ind; k++) {
							val = val + virg + indOccurs[k];
							virg = ", ";
						}
						val = "(" + val + ")";
						campos.add(cp.getNameField().toString() + val);
						defField.add(cp.getTypeField());
					} else {
						campos.add(cp.getNameField().toString());
						defField.add(cp.getTypeField());
					}

				}

			} else {
				if (cp.getRedefField().contains("OCCURS")) {
					int pos = cp.getRedefField().indexOf("OCCURS") + 7;
					int posFim = cp.getRedefField().indexOf("TIMES") - 1;
					ind = ind + 1;
					qtdOccurs[ind] = Integer.parseInt(cp.getRedefField().trim().substring(pos, posFim));
					nivOccurs[ind] = Integer.parseInt(cp.getLvlField().trim());
					seqGrp[ind] = i;
					indOccurs[ind] = 1;

					if (cp.getRedefField().contains("DEPENDING")) {
						pos = cp.getRedefField().indexOf("DEPENDING ON") + 13;
						String val = cp.getRedefField().substring(pos).trim();

						for (int k = 0; k < dependField.size(); k++) {
							if (val.equals(dependField.get(k))) {
								qtdOccurs[ind] = dependValue.get(k);
							}
						}
					}
				}
			}

			if (i == qtdLinhas - 1) {
				if (indOccurs[ind] < qtdOccurs[ind]) {
					indOccurs[ind] = indOccurs[ind] + 1;
					i = seqGrp[ind];
				} else {
					indOccurs[ind] = 1;
					ind = ind - 1;
				}
			}

		}

		columnNamesFile = new String[campos.size()];
		columnNamesFile = campos.toArray(columnNamesFile);

	}

	private void loadLayoutTableFile() {

		int i = 0;

		tableFile.setModel(tableFileModel);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

		for (i = 0; i < defField.size(); i++) {
			if (defField.get(i).equals("Zoned Decimal") || defField.get(i).equals("Binary")
					|| defField.get(i).equals("Packed Decimal")) {
				tableFile.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
			} else {
				tableFile.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
			}
		}

		for (i = 0; i < columnNamesFile.length; i++) {
			int teste = columnNamesFile[i].length();
			if (teste == 3) {
				teste = 5;
			}
			tableFile.getColumnModel().getColumn(i).setPreferredWidth(teste * 8);
		}

		tableFile.getTableHeader().setReorderingAllowed(false);
		tableFile.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrFile.setViewportView(tableFile);

	}

	public void openDirFile() {

		JFileChooser dir = new JFileChooser();
		// String path = "C:\\";
		String path = System.getProperty("user.dir").substring(0, 3);

		if (!txtFile.getText().equals("") && (!txtFile.getText().contains("("))) {
			path = txtFile.getText();
		}

		dir.setFileSelectionMode(JFileChooser.FILES_ONLY);
		dir.setCurrentDirectory(new File(path));

		int res = dir.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {
			txtFile.setText(dir.getSelectedFile().toString());
			String nameFile = dir.getSelectedFile().toString();

			ParameterFile parFile = new ParameterFile();
			parFile.setFile(nameFile);

			tableFile = new JTable();
			scrFile.setViewportView(tableFile);

		}

	}

	private void expExcelBook() {

		ExportExcelBook expBook = new ExportExcelBook();
		expBook.setTableName(tableCopy);
		expBook.setNameFile(txtPath.getText());

		EnableComponents.enable(tabCopybook, false);
		grpFontes.setEnabledAt(1, false);
		grpFontes.setEnabledAt(2, false);

		expBook.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (name.equals("state")) {
					SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
					switch (state) {
					case DONE:
						EnableComponents.enable(tabCopybook, true);
						grpFontes.setEnabledAt(1, true);
						grpFontes.setEnabledAt(2, true);
					}
				}
			}
		});

		expBook.execute();

	}

	private void expExcelString() {

		ExportExcelString expString = new ExportExcelString();
		expString.setTableName(tableString);
		expString.setNameFile(txtPath.getText());

		expString.setTipoConteudo(contentType);
		expString.setTamanhoConteudo(contentLength);
		expString.setDecimalConteudo(contentDecimal);
		expString.setTotalConteudo(contentTotal);

		EnableComponents.enable(tabString, false);
		grpFontes.setEnabledAt(0, false);
		grpFontes.setEnabledAt(2, false);

		expString.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (name.equals("state")) {
					SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
					switch (state) {
					case DONE:
						EnableComponents.enable(tabString, true);
						grpFontes.setEnabledAt(0, true);
						grpFontes.setEnabledAt(2, true);
					}
				}
			}
		});

		expString.execute();

	}

	private void expExcelFile() {

		progressBar.setStringPainted(true);
		progressBar.setValue(0);

		btnExcelFile.setEnabled(false);

		ExportExcelFile exportExcelFile = new ExportExcelFile();

		exportExcelFile.setTableName(tableFile);
		exportExcelFile.setName(txtFile.getText());

		EnableComponents.enable(tabFile, false);
		grpFontes.setEnabledAt(0, false);
		grpFontes.setEnabledAt(1, false);

		exportExcelFile.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (name.equals("progress")) {
					int progress = (int) evt.getNewValue();
					progressBar.setValue(progress);
					progressBar.setString(Language.msgExportingExcel + progress + "%");
					repaint();
				} else if (name.equals("state")) {
					SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
					switch (state) {
					case DONE:
						EnableComponents.enable(tabFile, true);
						grpFontes.setEnabledAt(0, true);
						grpFontes.setEnabledAt(1, true);
					}
				}
			}
		});

		exportExcelFile.execute();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
