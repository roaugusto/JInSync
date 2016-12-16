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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.jinsync.model.Copybook;
import br.com.jinsync.model.Language;

public class ProcessCopy {

	private boolean isDec = false;
	private boolean isOccurs = false;
	private boolean isPointer = false;
	private boolean isRedef = false;
	private boolean isDependingOn = false;
	private boolean isSignal = false;

	private String nameCopybook = "";
	private String fieldName = "";
	private String fieldType = "";
	private String seqLine = "";
	private String fieldRedef = "";
	private String fieldDepending = "";
	private String compType = "";

	private String partInt = "0";
	private String partDec = "0";
	private String qtdOccurs = "0";

	private int fieldSeq = 0;
	private int fieldLength = 0;

	private int ctrlGrp = 0;
	private int qtdLargerGrp = 0;

	private int indRedefGrp = 0;
	private int indFirstGrp = 0;
	private int posAtuGrp = 0;

	private int qtdFirstTime = 0;
	private int posStartField = 1;
	private int qtdTimes = 1;
	private int seqAnt = 0;

	private List<Integer> seqGrp = new ArrayList<Integer>();
	private List<Integer> lvlGrp = new ArrayList<Integer>();
	private List<Integer> lenGrp = new ArrayList<Integer>();
	private List<Integer> qtdOccursGrp = new ArrayList<Integer>();

	private List<Integer> qtdGrp = new ArrayList<Integer>();
	private List<Integer> lvlGrpHasRedef = new ArrayList<Integer>();
	private List<Integer> grpRedefCurrent = new ArrayList<Integer>();

	private List<Integer> posStartAnt = new ArrayList<Integer>();
	private List<Integer> seqAntGrp = new ArrayList<Integer>();

	private List<Boolean> isEndGrp = new ArrayList<Boolean>();
	private List<Boolean> isStartGrp = new ArrayList<Boolean>();
	private List<Boolean> isRedefGrp = new ArrayList<Boolean>();

	private List<String> tabNiveis = new ArrayList<String>();
	private List<String> dependField = new ArrayList<String>();

	private List<Copybook> listTotCopy;
	private BufferedReader buffReader;

	public void processCopy(String nomeDir) {

		int numLinha = 0;
		String line = "";
		String lineAnt = "";
		String lineAux = "";
		String lineErr = "";
		int lenLine = 0;
		boolean lineOk = false;

		File file = new File(nomeDir);
		String nameFileExt = file.getName();

		listTotCopy = new ArrayList<Copybook>();

		String nameFile = removeExtension(nameFileExt);
		nameCopybook = nameFile;

		initVars();
		
		try {
			FileReader reader = new FileReader(file);
			buffReader = new BufferedReader(reader);

			while (true) {
				lineOk = false;
				line = buffReader.readLine();
				numLinha = numLinha + 1;

				if (line == null)
					break;

				lenLine = line.length();

				if (lenLine > 72) {
					line = line.substring(0, 72);
				} else {
					line = line.substring(0, lenLine);
				}

				if (lenLine < 7) {
					line = "";
				} else {
					line = line.substring(6);
					if (line.substring(0, 1).equals("*")) {
						line = "";
					}
				}

				lineAux = line.trim();

				String verifLin = line.trim();
				if (verifLin.equals(") END-EXEC.")) {
					lineAnt = "";
					lineAux = "";
				}

				if (!lineAux.equals("")) {
					if (!line.substring(0, 1).equals("*")) {
						lineAnt = lineAnt + line;
						if (line.contains(".")) {
							try {
								lineOk = processLine(lineAnt);
								verifComp(lineAnt);
								lineErr = lineAnt;
								lineAnt = "";
								if (lineOk) {
									recordLine();
								}
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null,
										Language.msgCopyError + "\n " + Language.txtLine + ": " + lineErr,
										Language.msgErr, JOptionPane.ERROR_MESSAGE);
								lineErr = "";
								e.printStackTrace();
								return;
							}
						}
					}
				}
			}

			buffReader.close();

			qtdLargerGrp = 0;

			for (Copybook cp : listTotCopy) {
				for (int i = 1; i <= ctrlGrp; i++) {
					if (cp.getSeqField().equals(seqGrp.get(i))) {

						cp.setLenTotField(lenGrp.get(i));

						if (lenGrp.get(i) > qtdLargerGrp) {
							qtdLargerGrp = lenGrp.get(i);
						}

					}
				}
			}

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, Language.msgFileNotFound);
			erro.printStackTrace();
			return;
		}

	}

	private void initVars() {
		
		seqGrp.add(0);
		lvlGrp.add(0);
		isEndGrp.add(false);
		isStartGrp.add(true);

		qtdOccursGrp.add(0);
		isRedefGrp.add(true);
		lenGrp.add(0);
		
		lvlGrpHasRedef.add(0);
		grpRedefCurrent.add(0);

		qtdGrp.add(0);
		posStartAnt.add(0);
		seqAntGrp.add(0);
				
	}

	private String removeExtension(String s) {

		String separator = System.getProperty("file.separator");
		String filename;

		int lastSeparatorIndex = s.lastIndexOf(separator);
		if (lastSeparatorIndex == -1) {
			filename = s;
		} else {
			filename = s.substring(lastSeparatorIndex + 1);
		}

		int extensionIndex = filename.lastIndexOf(".");
		if (extensionIndex == -1)
			return filename;

		return filename.substring(0, extensionIndex);
	}

	private boolean processLine(String line) throws Exception {

		String lineAux = "";
		String fieldTypeDec = "";

		fieldName = "";
		fieldType = "";
		partInt = "0";
		partDec = "0";
		seqLine = "";
		isSignal = false;
		compType = "";
		qtdOccurs = "0";
		isOccurs = false;
		isPointer = false;

		fieldLength = 0;

		int posIni = 0;
		int posIni2 = 0;
		int posFim = 0;

		line = line.replaceAll("\t", "");
		line = line.toUpperCase();

		lineAux = line.trim();
		posIni = lineAux.indexOf(" ");
		seqLine = lineAux.substring(0, posIni);
		lineAux = lineAux.substring(posIni).trim();

		if (lineAux.equals(".")) {
			return false;
		}

		if (seqLine.equals("88")) {
			return false;
		}

		posIni = lineAux.indexOf(" ");
		if (posIni < 0) {
			posIni = lineAux.indexOf(".");
		}
		fieldName = lineAux.substring(0, posIni).trim();

		if (fieldName.contains(".")) {
			fieldName = fieldName.replace(".", "");
			return true;

		}

		lineAux = lineAux.substring(posIni).trim();
		posIni = lineAux.indexOf("REDEFINES");

		if (posIni >= 0) {
			isRedef = true;
			lineAux = lineAux.substring(posIni + 9).trim();
			posIni = lineAux.indexOf(" ");
			if (posIni < 0) {
				posIni = lineAux.indexOf(".");
			}
			fieldRedef = lineAux.substring(0, posIni).trim();
			lineAux = lineAux.substring(posIni).trim();
		} else {
			isRedef = false;
			fieldRedef = "";
		}

		posIni = lineAux.indexOf(" POINTER");
		if (posIni >= 0) {
			fieldType = "9";
			fieldLength = 4;
			isPointer = true;
		}

		if (lineAux.equals(".")) {
			return true;
		}

		posIni = lineAux.indexOf("VALUE ");
		if (posIni >= 0) {
			lineAux = lineAux.substring(0, posIni).trim() + ".";
		}

		posIni = lineAux.indexOf("PIC ");
		posIni2 = lineAux.indexOf("PICTURE ");
		
		if (posIni >= 0 || posIni2 >= 0) {
			
			if(posIni >=0){
				posIni = posIni + 4;	
			}else{
				posIni = posIni + 8;
			}
			
			lineAux = lineAux.substring(posIni).trim();
			posIni = lineAux.indexOf("(");

			if (posIni < 0) {
				posIni = lineAux.indexOf(" ");
				if (posIni < 0) {
					posIni = lineAux.indexOf("V");
					if (posIni < 0) {
						posIni = lineAux.indexOf(".");
						isDec = false;
					} else {
						isDec = true;
					}
				}
				fieldType = lineAux.substring(0, posIni).trim();
				partInt = Integer.toString(fieldType.length());
				if (fieldType.substring(0, 1).equals("+") || fieldType.substring(0, 1).equals("-")) {
					fieldType = "X";
				}
				fieldType = fieldType.substring(0, 1).trim();
				fieldLength = Integer.parseInt(partInt) + Integer.parseInt(partDec);
				if (isDec) {
					posIni = lineAux.indexOf("V");
					if (posIni >= 0) {
						lineAux = lineAux.substring(posIni).trim();
						posIni = lineAux.indexOf("(");
						if (posIni < 0) {
							posIni = lineAux.indexOf(" ");
							if (posIni < 0) {
								posIni = lineAux.indexOf(".");
							}
							fieldTypeDec = lineAux.substring(1, posIni).trim();
							partDec = Integer.toString(fieldTypeDec.length());
							fieldLength = Integer.parseInt(partInt) + Integer.parseInt(partDec);
							return true;
						} else {
							posFim = lineAux.indexOf(")");
							partDec = lineAux.substring(posIni + 1, posFim).trim();
						}

					}
				}
				// return;
			} else {
				fieldType = lineAux.substring(0, posIni).trim();
				lineAux = lineAux.substring(posIni + 1).trim();
				posIni = lineAux.indexOf(")");
				partInt = lineAux.substring(0, posIni).trim();
			}

			if (fieldType.contains("S")) {
				fieldType = "9";
				isSignal = true;
			} else {
				isSignal = false;
			}

			if (fieldType.substring(0, 1).equals("+") || fieldType.substring(0, 1).equals("-")) {
				fieldType = "X";
				partInt = Integer.toString(Integer.parseInt(partInt) + 1);
			}

		}

		if (partDec.trim().equals("")) {
			partDec = "0";
		}

		fieldLength = Integer.parseInt(partInt) + Integer.parseInt(partDec);

		lineAux = lineAux.substring(posIni + 1).trim();
		if (lineAux.equals(".")) {
			return true;
		}

		posIni = lineAux.indexOf("V");
		if (posIni >= 0) {
			posIni = lineAux.indexOf("(");
			if (posIni < 0) {
				posIni = lineAux.indexOf(" ");
				if (posIni < 0) {
					posIni = lineAux.indexOf(".");
				}
				fieldTypeDec = lineAux.substring(1, posIni).trim();
				partDec = Integer.toString(fieldTypeDec.length());
				fieldLength = Integer.parseInt(partInt) + Integer.parseInt(partDec);
				// return;
			} else {
				posFim = lineAux.indexOf(")");
				partDec = lineAux.substring(posIni + 1, posFim).trim();
			}

		}

		fieldLength = Integer.parseInt(partInt) + Integer.parseInt(partDec);

		posIni = lineAux.indexOf("OCCURS ");
		if (posIni >= 0) {
			isOccurs = true;
			lineAux = lineAux.substring(posIni + 7).trim();
			posIni = lineAux.indexOf("TO ");
			if (posIni >= 0) {
				lineAux = lineAux.substring(posIni + 3).trim();
			}
			posIni = lineAux.indexOf("TIMES");
			if (posIni < 0) {
				posIni = lineAux.indexOf(".");
			}
			qtdOccurs = lineAux.substring(0, posIni).trim();
			fieldLength = fieldLength * Integer.parseInt(qtdOccurs);
			posIni = lineAux.indexOf(" ON ");
			if (posIni >= 0) {
				isDependingOn = true;
				fieldDepending = lineAux.substring(posIni + 4).trim();
				fieldDepending = fieldDepending.replace(".", "");
				dependField.add(fieldDepending);
			} else {
				isDependingOn = false;
			}

		} else {
			isOccurs = false;
		}

		return true;

	}

	private void verifComp(String lineAux) {

		int fieldLengthAux = 0;
		int i = 0;

		if (lineAux.contains("COMP-1")) {
			compType = "COMP-1";
			fieldLength = 4;
			return;
		}
		if (lineAux.contains("COMP-2")) {
			compType = "COMP-2";
			fieldLength = 8;
			return;
		}
		if (lineAux.contains("COMP-3")) {
			compType = "COMP-3";

			if (isSignal) {
				fieldLengthAux = 1 + fieldLength;
			} else {
				fieldLengthAux = fieldLength;
			}

			fieldLength = fieldLengthAux / 2;

			i = (fieldLengthAux % 2);
			if (i > 0) {
				fieldLength = fieldLength + 1;
			}
			return;
		}

		if (lineAux.contains(" COMP ") || lineAux.contains(" COMP.")) {
			compType = "COMP";
			if (fieldLength <= 4) {
				fieldLength = 2;
			} else {
				if (fieldLength <= 9) {
					fieldLength = 4;
				} else {
					fieldLength = 8;
				}
			}
			return;
		}

	}

	private void recordLine() throws Exception {

		Copybook copybook = null;
		copybook = new Copybook();

		int convertNum = 0;
		int j = 0;

		fieldSeq = fieldSeq + 1;

		if (fieldType.equals("")) {
			fieldType = "Group";
		}

		if (fieldType.equals("9")) {
			fieldType = "Zoned Decimal";
			if (isSignal) {
				fieldType = fieldType + " with signal";
			}
		}

		if (fieldType.equals("X")) {
			fieldType = "Character";
		}

		if (compType.equals("COMP")) {
			fieldType = "Binary";
		}

		if (compType.equals("COMP-1") || compType.equals("COMP-2")) {
			fieldType = "Floating Point";
		}

		if (compType.equals("COMP-3")) {
			fieldType = "Packed Decimal";
		}

		if (fieldType.equals("Group")) {

			setCtrlGrp();

			seqGrp.set(ctrlGrp, fieldSeq);
			lvlGrp.set(ctrlGrp, Integer.parseInt(seqLine.trim()));
			isEndGrp.set(ctrlGrp, false);
			isStartGrp.set(ctrlGrp, true);

		}

		if (partInt.equals("0")) {
			partInt = "";
		}

		if (partDec.equals("0")) {
			partDec = "";
		}

		if (indRedefGrp > 0) {
			if (Integer.parseInt(seqLine.trim()) <= lvlGrpHasRedef.get(indRedefGrp)) {
				indRedefGrp = indRedefGrp - 1;
			}
		}

		if (fieldType.equals("Group")) {
			if (isOccurs) {
				qtdOccursGrp.set(ctrlGrp, Integer.parseInt(qtdOccurs));
			} else {
				qtdOccursGrp.set(ctrlGrp, 1);
			}

			if (isRedef) {
				isRedefGrp.set(ctrlGrp, true);
				indRedefGrp = indRedefGrp + 1;
				lvlGrpHasRedef.add(0);
				grpRedefCurrent.add(0);
				lvlGrpHasRedef.set(indRedefGrp, Integer.parseInt(seqLine.trim()));
				grpRedefCurrent.set(indRedefGrp, ctrlGrp);
			} else {
				isRedefGrp.set(ctrlGrp, false);
			}
		}

		if (isRedef) {

			for (Copybook cp : listTotCopy) {
				if (cp.getNameField().equals(fieldRedef)) {
					posStartField = Integer.parseInt(cp.getPosStartField());
					cp = null;
				}
			}
		}

		copybook.setNameCopybook(nameCopybook);
		copybook.setSeqField(fieldSeq);

		seqLine = verifSeqLine(seqLine) + seqLine;

		copybook.setLvlField(seqLine);
		copybook.setNameField(fieldName);

		if (!fieldRedef.equals("")) {
			copybook.setRedefField("REDEFINES " + fieldRedef);
		} else {
			if (isOccurs) {
				String val = "OCCURS " + qtdOccurs + " TIMES";
				if (isDependingOn) {
					val = val + " DEPENDING ON " + fieldDepending;
				}
				copybook.setRedefField(val);
			} else {
				if (isPointer) {
					fieldRedef = "POINTER";
				}
				copybook.setRedefField(fieldRedef);
			}
		}

		copybook.setTypeField(fieldType);

		if (partInt.equals("")) {
			copybook.setLenIntField(partInt);
		} else {
			convertNum = Integer.parseInt(partInt);
			copybook.setLenIntField(Integer.toString(convertNum));
		}

		if (partDec.equals("") || partDec.equals("0")) {
			copybook.setLenDecField("");
		} else {
			convertNum = Integer.parseInt(partDec);
			copybook.setLenDecField(Integer.toString(convertNum));
		}

		copybook.setLenTotField(fieldLength);

		for (int i = 1; i <= ctrlGrp; i++) {

			if (isStartGrp.get(i)) {
				if (i > 1) {
					qtdFirstTime = 1;
					while (j < ctrlGrp) {
						j = j + 1;
						if (!isEndGrp.get(j)) {
							qtdFirstTime = qtdFirstTime * qtdOccursGrp.get(j);
						}
					}
				} else {
					qtdFirstTime = qtdOccursGrp.get(i);
				}
				
				indFirstGrp = indFirstGrp + 1;
				qtdGrp.add(0);
				posStartAnt.add(0);
				seqAntGrp.add(0);
				
				qtdGrp.set(indFirstGrp, qtdOccursGrp.get(i));
				posStartAnt.set(indFirstGrp, posStartField);
				seqAntGrp.set(indFirstGrp, Integer.parseInt(seqLine.trim()));
			}

			if (Integer.parseInt(seqLine.trim()) == 1) {
				posStartField = 1;
			}
							
			if (i == 1) {

				if (Integer.parseInt(seqLine.trim()) < seqAnt) {
					while ((Integer.parseInt(seqLine.trim()) <= seqAntGrp.get(indFirstGrp))) {
						if (qtdGrp.get(indFirstGrp) > 0) {
							qtdFirstTime = qtdFirstTime / qtdGrp.get(indFirstGrp);
							if (isRedef) {
								posStartField = posStartAnt.get(indFirstGrp) + lenGrp.get(posAtuGrp);
							}
							indFirstGrp = indFirstGrp - 1;
							posAtuGrp = posAtuGrp - 1;
						}
					}
				}
			}

			if (i == ctrlGrp) {
				qtdTimes = qtdOccursGrp.get(i);
			} else {
				qtdTimes = qtdFirstTime;
			}

			if (lvlGrp.get(i) < Integer.parseInt(seqLine.trim())) {

				if (!isEndGrp.get(i)) {
					if (!isRedef) {
						if (i >= grpRedefCurrent.get(indRedefGrp)) {
							int newLenGrp = lenGrp.get(i) + (fieldLength * qtdTimes);
							lenGrp.set(i, newLenGrp);
						}
						posAtuGrp = i;
					}
				}

			} else {
				if (isStartGrp.get(i)) {
					isStartGrp.set(i, false);
				} else {
					isEndGrp.set(i, true);
				}
			}
		}

		copybook.setPosStartField(Integer.toString(posStartField));

		listTotCopy.add(copybook);

		posStartField = posStartField + fieldLength;
		seqAnt = Integer.parseInt(seqLine.trim());

	}

	private void setCtrlGrp() {

		ctrlGrp = ctrlGrp + 1;

		seqGrp.add(0);
		lvlGrp.add(0);
		isEndGrp.add(false);
		isStartGrp.add(true);

		qtdOccursGrp.add(0);
		isRedefGrp.add(true);
		lenGrp.add(0);

	}

	private String verifSeqLine(String linha) {

		String spaces = "";
		String saida = "";
		boolean achou = false;
		int i = 0;

		for (i = 0; i <= tabNiveis.size(); i++) {
			if (tabNiveis.size() < i + 1) {
				break;
			} else {
				spaces = tabNiveis.get(i);
				if (spaces.equals(linha.trim())) {
					achou = true;
					break;
				}
			}
		}

		if (achou == false) {
			tabNiveis.add(linha.trim());
		}

		for (int k = 1; k <= i; k++) {
			saida = saida + "  ";
		}

		return saida;

	}

	public List<Copybook> getListTotCopy() {
		return listTotCopy;
	}

	public int getQtdLargerGrp() {
		return qtdLargerGrp;
	}

	public List<String> getDependField() {
		return dependField;
	}

	
	
}
