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

package br.com.jinsync.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Language {

	private static final String dirLangProp = System.getProperty("user.dir") + "\\config";
	private static final String langProp = "language";

	public static String menuParam = "";
	public static String menuParamUser = "";
	public static String menuParamFtp = "";
	public static String menuHelp = "";
	public static String menuHelpAbout = "";

	public static String tabCopy = "";
	public static String tabString = "";
	public static String tabFile = "";

	public static String tabCopyFile = "";
	public static String tabCopySeq = "";
	public static String tabCopyLvl = "";
	public static String tabCopyFieldName = "";
	public static String tabCopyInf = "";
	public static String tabCopyFormat = "";
	public static String tabCopyInt = "";
	public static String tabCopyDec = "";
	public static String tabCopyStart = "";
	public static String tabCopyTot = "";

	public static String txtFilePds = "";
	public static String txtOutput = "";
	public static String txtDeveloped = "";
	public static String txtFileLength = "";
	public static String txtUser = "";
	public static String txtPassword = "";
	public static String txtIp = "";
	public static String txtVersion = "";
	public static String txtDescription = "";
	public static String txtDescrDetail = "";
	public static String txtLine = "";

	public static String formUser = "";
	public static String formIp = "";
	public static String formAbout = "";

	public static String btnOk = "";
	public static String btnCancel = "";

	public static String msgErr = "";
	public static String msgBigFile = "";
	public static String msgCopyNotFound = "";
	public static String msgCopyError = "";
	public static String msgFileNotFound = "";
	public static String msgParameterNotFound = "";
	public static String msgLoadingFile = "";
	public static String msgExportingExcel = "";  
	public static String msgInf = ""; 
	public static String msgCancelProcess = "";
	public static String msgQtdLines = "";
	
	public static String consoleConnectionRefused = "";
	public static String consoleOpenConnection = "";
	public static String consoleFileReception = "";
	public static String consoleReadPDS = ""; 
	public static String consoleClientFtp= "";
	
	public static String stringFieldName = "";
	public static String stringType = "";
	public static String stringLength = "";
	public static String stringDecimal = "";
	public static String stringTotal = "";
	public static String stringContent = "";
	
	public static void loadParameters() {

		File arqProp = new File(dirLangProp);

		if (!arqProp.exists()) {
			arqProp.mkdirs();
		}
		
		try {

			File file = new File(dirLangProp);
			URL[] urls = { file.toURI().toURL() };
			ClassLoader loader = new URLClassLoader(urls);

			//Locale locale = Locale.ENGLISH;
			Locale locale = Locale.getDefault();

			String nameFile = dirLangProp + "\\" + langProp + "_" + locale.toString() + ".properties";
			File file2 = new File(nameFile);

			if (!file2.exists()) {
				setLanguagePtBr(dirLangProp + "\\" + "language_pt_BR.properties");
				setLanguageEnUs(dirLangProp + "\\" + "language_en_US.properties");
			}

			ResourceBundle bundle = ResourceBundle.getBundle(langProp, locale, loader);

			menuParam = bundle.getString("menuParam");
			menuParamUser = bundle.getString("menuParamUser");
			menuParamFtp = bundle.getString("menuParamFtp");
			menuHelp = bundle.getString("menuHelp");
			menuHelpAbout = bundle.getString("menuHelpAbout");

			tabCopy = bundle.getString("tabCopy");
			tabString = bundle.getString("tabString");
			tabFile = bundle.getString("tabFile");

			tabCopyFile = bundle.getString("tabCopyFile");
			tabCopySeq = bundle.getString("tabCopySeq");
			tabCopyLvl = bundle.getString("tabCopyLvl");
			tabCopyFieldName = bundle.getString("tabCopyFieldName");
			tabCopyInf = bundle.getString("tabCopyInf");
			tabCopyFormat = bundle.getString("tabCopyFormat");
			tabCopyInt = bundle.getString("tabCopyInt");
			tabCopyDec = bundle.getString("tabCopyDec");
			tabCopyStart = bundle.getString("tabCopyStart");
			tabCopyTot = bundle.getString("tabCopyTot");

			txtOutput = bundle.getString("txtOutput");
			txtDeveloped = bundle.getString("txtDeveloped");
			txtFilePds = bundle.getString("txtFilePds");
			txtFileLength = bundle.getString("txtFileLength");

			txtUser = bundle.getString("txtUser");
			txtPassword = bundle.getString("txtPassword");
			txtIp = bundle.getString("txtIp");
			txtVersion = bundle.getString("txtVersion");
			txtDescription = bundle.getString("txtDescription");
			txtDescrDetail = bundle.getString("txtDescrDetail");
			txtLine = bundle.getString("txtLine");

			formUser = bundle.getString("formUser");
			formIp = bundle.getString("formIp");
			formAbout = bundle.getString("formAbout");

			btnOk = bundle.getString("btnOk");
			btnCancel = bundle.getString("btnCancel");

			msgErr = bundle.getString("msgErr");
			msgBigFile = bundle.getString("msgBigFile");
			msgCopyNotFound = bundle.getString("msgCopyNotFound");
			msgCopyError = bundle.getString("msgCopyError");
			msgFileNotFound = bundle.getString("msgFileNotFound");
			msgParameterNotFound = bundle.getString("msgParameterNotFound");
			msgLoadingFile = bundle.getString("msgLoadingFile");
			msgExportingExcel = bundle.getString("msgExportingExcel");
			msgInf = bundle.getString("msgInf");
			msgCancelProcess = bundle.getString("msgCancelProcess");
			msgQtdLines = bundle.getString("msgQtdLines");

			consoleConnectionRefused = bundle.getString("consoleConnectionRefused");
			consoleOpenConnection = bundle.getString("consoleOpenConnection");
			consoleFileReception = bundle.getString("consoleFileReception");
			consoleReadPDS = bundle.getString("consoleReadPDS"); 
			consoleClientFtp= bundle.getString("consoleClientFtp");

			stringFieldName = bundle.getString("stringFieldName");
			stringType = bundle.getString("stringType");
			stringLength = bundle.getString("stringLength");
			stringDecimal = bundle.getString("stringDecimal");
			stringTotal = bundle.getString("stringTotal");
			stringContent = bundle.getString("stringContent");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static void setLanguagePtBr(String nameFile) {

		Properties prop = new Properties();
		
		prop.setProperty("menuParam","Parâmetros FTP");
		prop.setProperty("menuParamUser","Setar Usuário");
		prop.setProperty("menuParamFtp","Setar IP FTP");
		prop.setProperty("menuHelp","Ajuda");
		prop.setProperty("menuHelpAbout","Sobre JInSync");
		prop.setProperty("tabCopy","Copybook");
		prop.setProperty("tabString","String");
		prop.setProperty("tabFile","Arquivo");
		prop.setProperty("tabCopyFile","Nome do Copybook");
		prop.setProperty("tabCopySeq","SEQ");
		prop.setProperty("tabCopyLvl","Nível");
		prop.setProperty("tabCopyFieldName","NOME DO CAMPO");
		prop.setProperty("tabCopyInf","INFORMACOES ADICIONAIS");
		prop.setProperty("tabCopyFormat","FORMATO");
		prop.setProperty("tabCopyInt","INTEIROS");
		prop.setProperty("tabCopyDec","DECIMAIS");
		prop.setProperty("tabCopyStart","INICIO");
		prop.setProperty("tabCopyTot","TOTAL");
		prop.setProperty("txtFilePds","Arquivo (PC) ou PDS (FTP):");
		prop.setProperty("txtOutput","Saída");
		prop.setProperty("txtDeveloped","Desenvolvido por");
		prop.setProperty("txtFileLength","Tamanho do arquivo");
		prop.setProperty("txtUser","Usuário");
		prop.setProperty("txtPassword","Senha");
		prop.setProperty("txtIp","IP");
		prop.setProperty("txtVersion","Versão");
		prop.setProperty("txtDescription","Descrição");
		prop.setProperty("txtDescrDetail","Leitor de Copybook COBOL");
		prop.setProperty("txtLine","Linha");
		prop.setProperty("formUser","Informar usuário");
		prop.setProperty("formIp","Informar IP do FTP");
		prop.setProperty("formAbout","Sobre");
		prop.setProperty("btnOk","OK");
		prop.setProperty("btnCancel","Cancelar");
		
		prop.setProperty("msgErr","Erro");
		prop.setProperty("msgBigFile","Arquivo muito grande! Favor informar um arquivo menor.");		
		prop.setProperty("msgCopyNotFound","Copybook não informado!");
		prop.setProperty("msgCopyError","Copybook inválido ou situação não prevista. Contate o desenvolvedor");
		prop.setProperty("msgFileNotFound", "Arquivo não informado!");
		prop.setProperty("msgParameterNotFound", "Parâmetro FTP não informado!");		
		prop.setProperty("msgLoadingFile","Carregando o arquivo...");
		prop.setProperty("msgExportingExcel","Exportando para o Excel...");  
		prop.setProperty("msgInf","Mensagem");
		prop.setProperty("msgCancelProcess","Cancelar o processamento?");
		prop.setProperty("msgQtdLines", "Arquivo muito grande! Serão exibidas as primeiras 50000 linhas!");

		prop.setProperty("consoleConnectionRefused","Conexão recusada... Login ou senha inválidos!");
		prop.setProperty("consoleOpenConnection","Abrindo a conexão ftp...");
		prop.setProperty("consoleFileReception","Recepção do arquivo efetuada com sucesso!");
		prop.setProperty("consoleReadPDS","Lendo PDS...");
		prop.setProperty("consoleClientFtp", "Cliente FTP não disponível!");

		prop.setProperty("stringFieldName", "CAMPO"); 
		prop.setProperty("stringType", "TIPO");
		prop.setProperty("stringLength", "TAMANHO");
		prop.setProperty("stringDecimal", "DECIMAL");
		prop.setProperty("stringTotal", "TAM TOTAL");
		prop.setProperty("stringContent", "CONTEUDO");

		
		try {
			// Criamos um objeto FileOutputStream
			FileOutputStream fos = new FileOutputStream(nameFile);
			// grava os dados no arquivo
			prop.store(fos, "LANGUAGE FILE:");
			// fecha o arquivo
			fos.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

	}

	public static void setLanguageEnUs(String nameFile) {

		Properties prop = new Properties();
		
		prop.setProperty("menuParam","FTP Parameters");
		prop.setProperty("menuParamUser","Set User");
		prop.setProperty("menuParamFtp","Set IP FTP");
		prop.setProperty("menuHelp","Help");
		prop.setProperty("menuHelpAbout","About JInSync");
		prop.setProperty("tabCopy","Copybook");
		prop.setProperty("tabString","String");
		prop.setProperty("tabFile","File");
		prop.setProperty("tabCopyFile","Copybook Name");
		prop.setProperty("tabCopySeq","SEQ");
		prop.setProperty("tabCopyLvl","Level");
		prop.setProperty("tabCopyFieldName","FIELD NAME");
		prop.setProperty("tabCopyInf","ADDITIONAL INFORMATION");
		prop.setProperty("tabCopyFormat","FORMAT");
		prop.setProperty("tabCopyInt","INTEGER");
		prop.setProperty("tabCopyDec","DECIMALS");
		prop.setProperty("tabCopyStart","START");
		prop.setProperty("tabCopyTot","TOTAL");
		prop.setProperty("txtFilePds","File (PC) or PDS (FTP):");
		prop.setProperty("txtOutput","Output");
		prop.setProperty("txtDeveloped","Developed by");
		prop.setProperty("txtFileLength","File Length");
		prop.setProperty("txtUser","User");
		prop.setProperty("txtPassword","Password");
		prop.setProperty("txtIp","IP");
		prop.setProperty("txtVersion","Version");
		prop.setProperty("txtDescription","Description");
		prop.setProperty("txtDescrDetail","COBOL Copybook reader");
		prop.setProperty("txtLine","Line");
		
		prop.setProperty("formUser","Inform your user");
		prop.setProperty("formIp","Inform the IP/FTP");
		prop.setProperty("formAbout","About");
		
		prop.setProperty("btnOk","OK");
		prop.setProperty("btnCancel","Cancel");
		
		prop.setProperty("msgErr","Error");
		prop.setProperty("msgBigFile","File too large! Please enter a smaller File.");		
		prop.setProperty("msgCopyNotFound","Copybook not reported!");
		prop.setProperty("msgCopyError","Copybook invalid or situation not provided. Contact developer");
		prop.setProperty("msgFileNotFound", "File not found!");
		prop.setProperty("msgParameterNotFound", "FTP Parameter not reported!");		
		prop.setProperty("msgLoadingFile","Loading file...");
		prop.setProperty("msgExportingExcel","Exporting to Excel...");  
		prop.setProperty("msgInf","Message");
		prop.setProperty("msgCancelProcess","Cancel process?");
		prop.setProperty("msgQtdLines", "File too large! Will be displayed the first 50000 lines!");

		prop.setProperty("consoleConnectionRefused","Connection refused... Invalid User/Password!");
		prop.setProperty("consoleOpenConnection","Opening ftp connection...");
		prop.setProperty("consoleFileReception","Receiving the file successfully!");
		prop.setProperty("consoleReadPDS","Reading PDS...");
		prop.setProperty("consoleClientFtp", "FTP Client not availble!");

		prop.setProperty("stringFieldName", "FIELD"); 
		prop.setProperty("stringType", "TYPE");
		prop.setProperty("stringLength", "LENGTH");
		prop.setProperty("stringDecimal", "DECIMAL");
		prop.setProperty("stringTotal", "LEN TOTAL");
		prop.setProperty("stringContent", "CONTENT");
	
		try {
			// Criamos um objeto FileOutputStream
			FileOutputStream fos = new FileOutputStream(nameFile);
			// grava os dados no arquivo
			prop.store(fos, "LANGUAGE FILE:");
			// fecha o arquivo
			fos.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

	}


}
