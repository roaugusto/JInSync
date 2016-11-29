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
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.jinsync.model.Copybook;
import br.com.jinsync.model.Language;

public class CopybookTableModel extends AbstractTableModel {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int COL_NOMECOPY = 0;  
	private static final int COL_SEQ = 1;  
	private static final int COL_LVL = 2;
    private static final int COL_FIELDNAME = 3;  
    private static final int COL_REDEFNAME = 4;  
    private static final int COL_FORMAT = 5;
    private static final int COL_FORMAT_INT = 6;
    private static final int COL_FORMAT_DEC = 7;
    private static final int COL_START = 8;
    private static final int COL_LEN = 9;
    
    private List<Copybook> valores;         
  
    public CopybookTableModel(List<Copybook> valores) {  
          this.valores = new ArrayList<Copybook>(valores);  
    }  
  
    @Override
	public int getRowCount() {  
        return valores.size();  
    }  
  
    @Override
	public int getColumnCount() {  
        return 10;  
    }  
  
    @Override
	public String getColumnName(int column) {  
    	
        if (column == COL_NOMECOPY) return Language.tabCopyFile;
        if (column == COL_SEQ) return Language.tabCopySeq;
        if (column == COL_LVL) return Language.tabCopyLvl;
        if (column == COL_FIELDNAME) return Language.tabCopyFieldName;
        if (column == COL_REDEFNAME) return Language.tabCopyInf;
        if (column == COL_FORMAT) return Language.tabCopyFormat;
        if (column == COL_FORMAT_INT) return Language.tabCopyInt;
        if (column == COL_FORMAT_DEC) return Language.tabCopyDec;
        if (column == COL_START) return Language.tabCopyStart;
        if (column == COL_LEN) return Language.tabCopyTot;
        return ""; //Nunca deve ocorrer  
    }  
  
    @Override
	public Object getValueAt(int row, int column) {  
    	Copybook copybook = valores.get(row);  
        
        switch (column){
        	case COL_NOMECOPY:
        		return copybook.getNameCopybook();
	        case COL_SEQ:
	        	return copybook.getSeqField();
	        case COL_LVL:
	        	return copybook.getLvlField().trim();
	        case COL_FIELDNAME:
	        	return copybook.getLvlField() + " " + copybook.getNameField();
	        case COL_REDEFNAME:
	        	return copybook.getRedefField();
	        case COL_FORMAT:
	        	return copybook.getTypeField();
	        case COL_FORMAT_INT:
	        	return copybook.getLenIntField();	        	
	        case COL_FORMAT_DEC:
	        	return copybook.getLenDecField();	        	
	        case COL_START:
	        	return copybook.getPosStartField();
	        case COL_LEN:
	        	return copybook.getLenTotField();	        	
	        default:
	        	return ""; //Nunca deve ocorrer
        }
        
    }  
  
    @Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {  
    	Copybook copybook = valores.get(rowIndex);  
    	
        switch (columnIndex){
        	case COL_NOMECOPY:
        		copybook.setNameCopybook(aValue.toString());
        	case COL_SEQ:
	        	copybook.setSeqField(Integer.valueOf(aValue.toString()));
	        case COL_LVL:
	        	copybook.setLvlField(aValue.toString());
	        case COL_FIELDNAME:
	        	copybook.setNameField(aValue.toString());
	        case COL_REDEFNAME:
	        	copybook.setNameField(aValue.toString());
	        case COL_FORMAT:
	        	copybook.setTypeField(aValue.toString());
	        case COL_FORMAT_INT:
	        	copybook.setLenIntField(aValue.toString());
	        case COL_FORMAT_DEC:
	        	copybook.setLenDecField(aValue.toString());
	        case COL_START:
	        	copybook.setPosStartField(aValue.toString());
	        case COL_LEN:
	        	copybook.setLenTotField(Integer.valueOf(aValue.toString()));
	        	
        }
        
        fireTableDataChanged();
    }  
  
    @Override
	public Class<?> getColumnClass(int columnIndex) {  

        switch (columnIndex){
        	case COL_NOMECOPY:
        		return String.class;
	        case COL_SEQ:
	        	return Integer.class;
	        case COL_LVL:
	        	return String.class;
	        case COL_FIELDNAME:
	        	return String.class;
	        case COL_REDEFNAME:
	        	return String.class;
	        case COL_FORMAT:
	        	return String.class;
	        case COL_FORMAT_INT:
	        	return String.class;
	        case COL_FORMAT_DEC:
	        	return String.class;
	        case COL_START:
	        	return String.class;
	        case COL_LEN:
	        	return Integer.class;
	        default:
	        	return String.class;
        }    
    }  
      
    @Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {  
        return false;  
    }  

    public Copybook get(int row) {  
        return valores.get(row);  
    }  

}  