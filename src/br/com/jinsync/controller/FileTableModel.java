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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class FileTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames;
	private List<String[]> data;
	
	public FileTableModel(String[] columnNames, int qtdColumns){
		
		this.columnNames = columnNames;
		//this.data = data;
		data = new ArrayList<>(qtdColumns);
		
	}
	
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }


    @Override
    public int getColumnCount() {
        return columnNames.length;
    }


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        String[] value = data.get(row);
                
        return value[col];
    }

    public void addRow(String[] value) {
        int rowCount = getRowCount();
        data.add(value);
        fireTableRowsInserted(rowCount, rowCount);
    }


    public void addRows(String[]... value) {
        addRows(Arrays.asList(value));
    }


    public void addRows(List<String[]> value) {
        int rowCount = getRowCount();
        data.addAll(value);
        fireTableRowsInserted(rowCount, rowCount);
    }

	
}
