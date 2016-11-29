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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ParameterFtp {

	private static final String pathFtpProp = System.getProperty("user.dir") + "\\config";	
	private static final String ftpProp = System.getProperty("user.dir") + "\\config\\ftp.properties";	
	private String ipFtp;
	
	public String getFtp(){
		
		ipFtp = "";
		Properties prop = new Properties();
		
		prop = new Properties();
		
		File arqProp = new File(ftpProp);			
		if (arqProp.exists()) {
			try {
				FileInputStream fis = new FileInputStream(ftpProp);
				prop.load(fis);
				ipFtp = prop.getProperty("ftp");
				fis.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}    	
		}
		
		return ipFtp;
	
	}

	public void setFtp(String ipFtp){
		
		Properties prop = new Properties();
		prop.setProperty("ftp", ipFtp);
		
		File arqProp = new File(pathFtpProp);			
		if (!arqProp.exists()) {
			arqProp.mkdirs();
		}			
			
		try {
			//Criamos um objeto FileOutputStream            
			FileOutputStream fos = new FileOutputStream(ftpProp);
			//grava os dados no arquivo
			prop.store(fos, "FTP FILE:");
			//fecha o arquivo
			fos.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}    	
		
	}	
}
