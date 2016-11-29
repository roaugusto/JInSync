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

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcelFile  extends SwingWorker<Object, Object>{

	private JTable tableName;
	private String name;
	
	@Override
	protected Object doInBackground() throws Exception {
		
		int valuePgsBar = 0;
		int qtdTotLin = 0;
		
		final String dir = System.getProperty("user.dir") + "\\file";

		File arqProp = new File(dir);
		if (!arqProp.exists()) {
			arqProp.mkdirs();
		}

		String nameFile = name;

		int lin = 0;
		int col = 0;
		int pos = 0;
		int posEnd = 0;

		pos = nameFile.lastIndexOf("(");
		if (pos > 0) {
			posEnd = nameFile.lastIndexOf(")");
			nameFile = dir + "\\" + nameFile.substring(pos + 1, posEnd) + "_file" + ".xlsx";
		} else {
			pos = nameFile.lastIndexOf("\\");
			if (pos > 0) {
				posEnd = nameFile.lastIndexOf(".");
				if (posEnd > 0) {
					nameFile = dir + "\\" + nameFile.substring(pos + 1, posEnd) + "_file" + ".xlsx";
				} else {
					nameFile = dir + nameFile.substring(pos) + "_file" + ".xlsx";
				}
			}
		}

		FileOutputStream out;

		try {
			out = new FileOutputStream(nameFile);
			TableModel model = tableName.getModel();

			XSSFWorkbook wb = new XSSFWorkbook(); // Criando area de trabalho
													// para o excel
			XSSFSheet s = wb.createSheet(); // criando uma nova sheet

			XSSFFont f = wb.createFont();
			XSSFFont f2 = wb.createFont();

			XSSFCellStyle cs = wb.createCellStyle();
			XSSFCellStyle cs2 = wb.createCellStyle();
			XSSFCellStyle cs3 = wb.createCellStyle();
			XSSFCellStyle cs4 = wb.createCellStyle();

			f.setFontHeightInPoints((short) 8);
			f2.setFontHeightInPoints((short) 8);

			f.setBoldweight(Font.BOLDWEIGHT_BOLD);
			f2.setBoldweight(Font.BOLDWEIGHT_NORMAL);

			f.setFontName("Courier New");
			f2.setFontName("Courier New");

			XSSFRow r = null; // Criando uma referencia para Linha
			XSSFCell c = null; // Referencia para Celula

			cs.setFont(f);
			cs2.setFont(f2);
			cs3.setFont(f2);
			cs4.setFont(f2);

			cs2.setAlignment(CellStyle.ALIGN_LEFT);
			cs3.setAlignment(CellStyle.ALIGN_RIGHT);
			cs4.setAlignment(CellStyle.ALIGN_RIGHT);

			r = s.createRow(lin);
			for (int i = 1; i < model.getColumnCount(); i++) {
				c = r.createCell(col);
				c.setCellStyle(cs);
				c.setCellValue(model.getColumnName(i));
				col = col + 1;
			}

			col = 0;
			qtdTotLin = model.getRowCount() - 1;
			
			if (qtdTotLin == 0){
				qtdTotLin = 1;
			}
			
			for (int i = 0; i < model.getRowCount(); i++) {
				valuePgsBar = ( i * 100 ) /  qtdTotLin;
				lin = lin + 1;
				r = s.createRow(lin);
				col = 0;
				for (int j = 1; j < model.getColumnCount(); j++) {
					String valor = model.getValueAt(i, j).toString();
					if (valor == null) {
						valor = "";
					}

					c = r.createCell(col);
					c.setCellStyle(cs2);

					valor = valor.trim();
					if (valor.matches("-?\\d+(\\.\\d+)?")) {
						c.setCellValue(Double.parseDouble(valor));
						c.setCellType(Cell.CELL_TYPE_NUMERIC);
						c.setCellStyle(cs4);
					} else {
						c.setCellValue(valor);
					}
					col = col + 1;
					setProgress(valuePgsBar);
				}
			}

			for (int i = 0; i <= model.getColumnCount(); i++) {
				s.autoSizeColumn(i);
			}

			wb.write(out);
			wb.close();
			out.close();

			Desktop desktop = Desktop.getDesktop();
			desktop.open(new File(nameFile));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}

		return null;
	}

	public void setTableName(JTable tableName) {
		this.tableName = tableName;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
