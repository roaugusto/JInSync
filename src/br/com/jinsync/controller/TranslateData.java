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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.jinsync.model.Copybook;

public class TranslateData {

	private List<String> tpContent;
	private List<String> lengthContent;
	private List<String> decimalContent;
	private List<Integer> totalContent;
	
	private String[] contentStr;

	public static final int UNSIGNED_BYTE = 0xff;
	public static final int BITS_RIGHT = 0xf;
	
	public String[] ProccessFile(List<Copybook> listTotCopy, byte[] linTexto , int tamArq, Integer seqLin){
		
		int qtdLinhas = 0;
		int ind = 0;
		int i = 0;

		int[] seqGrp = new int[100];
		int[] qtdOccurs = new int[100];
		int[] lvlOccurs = new int[100];
		int[] tamTotGrp = new int[100];
		int[] indOccurs = new int[100];

		seqGrp[0] = 0;
		qtdOccurs[0] = 1;
		lvlOccurs[0] = 0;
		indOccurs[0] = 0;

		byte[] texto = linTexto;

		int tamTexto = texto.length;

		List<String> content = new ArrayList<String>();
		tpContent = new ArrayList<String>();
		lengthContent = new ArrayList<String>();
		decimalContent = new ArrayList<String>();
		totalContent = new ArrayList<Integer>();

		qtdLinhas = listTotCopy.size();
		content.add(seqLin.toString());

		for (i = 0; i < qtdLinhas; i++) {

			Copybook cp = listTotCopy.get(i);

			if (Integer.parseInt(cp.getLvlField().trim()) <= lvlOccurs[ind]) {
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
				if (indOccurs[ind] <= 1) {
					posIni = Integer.parseInt(cp.getPosStartField()) - 1;
				} else {
					posIni = (Integer.parseInt(cp.getPosStartField()) - 1) + (tamTotGrp[ind] * (indOccurs[ind] - 1));
				}

				int tamCampo = cp.getLenTotField();
				try {
					String val = "";
					int calcTam = tamTexto - posIni;
					int tamDecCampo = 0;

					if (cp.getLenDecField().trim().equals("")){
						tamDecCampo=0;
					}else{
						tamDecCampo = Integer.parseInt(cp.getLenDecField().trim());
					}

					if (tamCampo > calcTam){
						tamCampo = calcTam;
					}
					
					if (cp.getTypeField().equals("Character")) {
						byte[] texto2 = new byte[tamCampo];					
						
						System.arraycopy(texto, posIni, texto2, 0, tamCampo);	
						val = new String(texto2, "Cp037");
					}

					if (cp.getTypeField().equals("Zoned Decimal")) {
						byte[] texto2 = new byte[tamCampo];
						System.arraycopy(texto, posIni, texto2, 0, tamCampo);	
						val = new String(texto2, "Cp037");
					}

					if (cp.getTypeField().equals("Binary")) {
						byte[] texto2 = new byte[tamCampo];
						System.arraycopy(texto, posIni, texto2, 0, tamCampo);	
						
						StringBuilder sb = new StringBuilder(texto2.length * 2);
						   for(byte b: texto2)
						      sb.append(String.format("%02x", b & 0xff));
						   
						val = Long.toString(Long.parseLong(sb.toString(),16));
					}

					if (cp.getTypeField().equals("Packed Decimal")) {
						byte[] texto2 = new byte[tamCampo];
						System.arraycopy(texto, posIni, texto2, 0, tamCampo);							
						Long val2 = parseComp3(texto2);
																		
						int exp = tamDecCampo;
						
						if (tamDecCampo > 0){
							double result=  Math.pow(10, exp); 
							String casas=repeat("0",exp);						
							DecimalFormat df = new DecimalFormat("0." + casas);
							
							double val3 = val2 / result;
							val = df.format(val3);
						}else{
							val = Long.toString(val2);
						}
							
					}

					tpContent.add(cp.getTypeField());
					lengthContent.add(cp.getLenIntField().trim());
					decimalContent.add(cp.getLenDecField().trim());
					totalContent.add(cp.getLenTotField());
					content.add(val);

				} catch (Exception e) {
					content.add("");
				}

			} else {
				if (cp.getRedefField().contains("OCCURS")) {
					int pos = cp.getRedefField().indexOf("OCCURS") + 7;
					int posFim = cp.getRedefField().indexOf("TIMES") - 1;
					ind = ind + 1;
					qtdOccurs[ind] = Integer.parseInt(cp.getRedefField().substring(pos, posFim).trim());
					lvlOccurs[ind] = Integer.parseInt(cp.getLvlField().trim());
					seqGrp[ind] = i;
					tamTotGrp[ind] = cp.getLenTotField() / qtdOccurs[ind];
					indOccurs[ind] = 1;

					if (cp.getRedefField().contains("DEPENDING")) {
						pos = cp.getRedefField().indexOf("DEPENDING ON") + 13;

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

		contentStr = new String[content.size()];
		contentStr = content.toArray(contentStr);
				
		return contentStr;
	}
	
	public long parseComp3(byte[] data) {
	    long val = 0L;
	    boolean negative = false;
	    for (int i = 0; i < data.length; i++) {
	        int raw = data[i] & UNSIGNED_BYTE;
	        int digitA = raw >> 4;
	        int digitB = raw & BITS_RIGHT;

	        if (digitA < 10) {
	            val *= 10L;
	            val += digitA;

	        } else if (digitA == 11 || digitA == 13) { // Some non-IBM systems store the sign on left or use 11 for negative.
	            negative = true;
	        }

	        if (digitB < 10) {
	            val *= 10L;
	            val += digitB;

	        } else if (digitB == 11 || digitB == 13) {
	            negative = true;
	        }
	    }
	    if (negative)
	        val = -val;
	    return val;
	}

	private String repeat(String str, int times){
		return new String (new char[times]).replace("\0", str);
	}
}
