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
import java.text.NumberFormat;
import java.util.List;

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

import br.com.jinsync.model.Language;

public class ExportExcelString extends SwingWorker<Object, Object>{
	
	private JTable tableName;
	private String nameFile;
	
	private List<String> tipoConteudo;
	private List<String> tamanhoConteudo;
	private List<String> decimalConteudo;
	private List<Integer> totalConteudo;
	
	@Override
	protected Object doInBackground() throws Exception {
		
		final String dir = System.getProperty("user.dir") + "\\string";

		File arqProp = new File(dir);
		if (!arqProp.exists()) {
			arqProp.mkdirs();
		}

		String nameFile = this.nameFile;

		int lin = 0;
		int col = 0;
		int pos = 0;
		int posFim = 0;
		int valDec = 0;

		pos = nameFile.lastIndexOf("(");
		if (pos > 0) {
			posFim = nameFile.lastIndexOf(")");
			nameFile = dir + "\\" + nameFile.substring(pos + 1, posFim) + "_string" + ".xlsx";
		} else {
			pos = nameFile.lastIndexOf("\\");
			if (pos > 0) {
				posFim = nameFile.lastIndexOf(".");
				if (posFim > 0) {
					nameFile = dir + "\\" + nameFile.substring(pos + 1, posFim) + "_string" + ".xlsx";
				} else {
					nameFile = dir + nameFile.substring(pos) + "_string" + ".xlsx";
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
			cs4.setFont(f);

			cs2.setAlignment(CellStyle.ALIGN_LEFT);
			cs3.setAlignment(CellStyle.ALIGN_RIGHT);
			cs4.setAlignment(CellStyle.ALIGN_RIGHT);

			r = s.createRow(lin);

			r = s.createRow(0); // Criando a primeira linha na LINHA zero, que
								// seria o número 1
			c = r.createCell(0); // Criando a celula na posicao ZERO, que seria
									// A, com referencia na linha zero acima =
									// A1
			c.setCellStyle(cs);
			c.setCellValue(Language.stringFieldName);

			c = r.createCell(1);
			c.setCellStyle(cs);
			c.setCellValue(Language.stringType);

			c = r.createCell(2);
			c.setCellStyle(cs);
			c.setCellValue(Language.stringLength);

			c = r.createCell(3);
			c.setCellStyle(cs);
			c.setCellValue(Language.stringDecimal);

			c = r.createCell(4);
			c.setCellStyle(cs);
			c.setCellValue(Language.stringTotal);

			c = r.createCell(5);
			c.setCellStyle(cs4);
			c.setCellValue(Language.stringContent);

			col = 0;
			lin = lin + 1;
			for (int i = 0; i < model.getColumnCount(); i++) {

				r = s.createRow(lin);
				c = r.createCell(col);
				c.setCellStyle(cs2);
				c.setCellValue(model.getColumnName(i));

				col = col + 1;

				c = r.createCell(col);
				c.setCellStyle(cs2);
				c.setCellValue(tipoConteudo.get(i));

				col = col + 1;
				c = r.createCell(col);
				c.setCellStyle(cs2);
				if (tamanhoConteudo.get(i).matches("-?\\d+(\\.\\d+)?")) {
					c.setCellValue(Double.parseDouble(tamanhoConteudo.get(i)));
					c.setCellType(Cell.CELL_TYPE_NUMERIC);
					c.setCellStyle(cs3);
				} else {
					c.setCellValue(tamanhoConteudo.get(i));
				}

				col = col + 1;
				c = r.createCell(col);
				c.setCellStyle(cs2);
				if (decimalConteudo.get(i).matches("-?\\d+(\\.\\d+)?")) {
					c.setCellValue(Integer.parseInt(decimalConteudo.get(i)));
					valDec = Integer.parseInt(decimalConteudo.get(i));
					c.setCellType(Cell.CELL_TYPE_NUMERIC);
					c.setCellStyle(cs3);
				} else {
					c.setCellValue(decimalConteudo.get(i));
					valDec = 0;
				}

				col = col + 1;
				c = r.createCell(col);
				c.setCellStyle(cs2);
				c.setCellValue(totalConteudo.get(i));

				col = col + 1;
				c = r.createCell(col);
				c.setCellStyle(cs2);

				String valor = model.getValueAt(0, i).toString().trim();
				if (valor == null) {
					valor = "";
				}

				if (valor.matches("-?\\d+(\\.\\d+)?")) {

					if (Double.parseDouble(valor) > 0 && valDec > 0) {
						double val2 = Double.parseDouble(valor) / (Math.pow(10, valDec));
						NumberFormat format = NumberFormat.getInstance();
						format.setMinimumFractionDigits(2);
						format.setMaximumFractionDigits(valDec);
						c.setCellValue(format.format(val2).toString());

					} else {
						c.setCellValue(Double.parseDouble(valor));
					}

					// c.setCellType(Cell.CELL_TYPE_NUMERIC);
					c.setCellStyle(cs3);
				} else {
					c.setCellValue(valor);
				}

				lin = lin + 1;
				col = 0;
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
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
			//e.printStackTrace();
		}

			
		return null;
	}

	public void setTableName(JTable tableName) {
		this.tableName = tableName;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public void setTipoConteudo(List<String> tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	public void setTamanhoConteudo(List<String> tamanhoConteudo) {
		this.tamanhoConteudo = tamanhoConteudo;
	}

	public void setDecimalConteudo(List<String> decimalConteudo) {
		this.decimalConteudo = decimalConteudo;
	}

	public void setTotalConteudo(List<Integer> totalConteudo) {
		this.totalConteudo = totalConteudo;
	}
		
		
	

}
