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

package br.com.jinsync.controller;

import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import br.com.jinsync.model.Copybook;
import br.com.jinsync.model.Language;


public class TableSwingWorker extends SwingWorker<FileTableModel, String[]> {

    private final FileTableModel tableModel;
	private List<Copybook> listTotCopy;
	
	private String[] contentStr;
	private int lenFile;
	
	private File arqFile;
	private Container container;
	
	private FileInputStream inputStream;
	private BufferedInputStream bufInSt; 

	private int qtdTotLin;
	private int valuePgsBar = 0;
	private TranslateData procFile = new TranslateData();		
	
	private final int maxLin = 50000;

	private JProgressBar progressBar;

    public TableSwingWorker(FileTableModel tableModel, List<Copybook> listTotCopy, String arq, int lenFile, JProgressBar progressBar, Container container) {
        
    	this.tableModel = tableModel;
        this.listTotCopy = listTotCopy;
        this.lenFile = lenFile;
        this.container = container;
        
        this.progressBar = progressBar;
        
		arqFile = new File(arq);
		qtdTotLin = 0;		
		
		try {
			inputStream = new FileInputStream(arqFile);
			bufInSt = new BufferedInputStream(inputStream);
	        qtdTotLin = (int) inputStream.getChannel().size() / lenFile;
	        
		} catch (FileNotFoundException e) {			
			JOptionPane.showMessageDialog(null, Language.msgFileNotFound, Language.msgErr, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch(OutOfMemoryError erro){
			JOptionPane.showMessageDialog(null, Language.msgBigFile, Language.msgErr, JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {			
			e.printStackTrace();
		}

		
		
    }

    @Override
    protected FileTableModel doInBackground() throws Exception {


    	EnableComponents.enable(container,false);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setString(Language.msgLoadingFile +  "0%");
		progressBar.setStringPainted(true);
				
    	
    	// This is a deliberate pause to allow the UI time to render
        //Thread.sleep(100);

        if (qtdTotLin > maxLin) {
        	JOptionPane.showMessageDialog(null, Language.msgQtdLines, Language.msgInf, JOptionPane.INFORMATION_MESSAGE);
        	qtdTotLin = maxLin;
        }
        
		for (int i=1;i<= qtdTotLin;i++){
			
			valuePgsBar = ( i * 100 ) /  qtdTotLin;
			
			byte[]linTexto = new byte [lenFile];			
			bufInSt.read(linTexto, 0, lenFile); 
			
			contentStr = procFile.ProccessFile(listTotCopy, linTexto, lenFile, i);
			
            publish(contentStr);
            Thread.yield();

			progressBar.setValue(valuePgsBar);
			progressBar.setString(Language.msgLoadingFile + valuePgsBar + "%");

			if (i>= maxLin){
				break;
			}
			
			Thread.sleep(1/10000);
		}

		EnableComponents.enable(container,true);
		
		inputStream.close();
        return tableModel;
    }
    
    public int getQtdTotLin() {
		return qtdTotLin;
	}

	@Override
    protected void process(List<String[]> chunks) {
        //System.out.println("Adding " + chunks.size() + " rows");
        tableModel.addRows(chunks);
    }
        
}