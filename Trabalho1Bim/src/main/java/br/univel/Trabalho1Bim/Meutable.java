package br.univel.Trabalho1Bim;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class Meutable extends AbstractTableModel{
	
	private List<?> classe;
	private int qtdCol;
	private int qtdLin;

	private String[][] matriz;
	
	public Meutable(int qtdCol1,List<?> classe2){
		this.classe = classe2;
		this.qtdCol = qtdCol1;
		this.qtdLin = classe.size()/this.qtdCol;
		this.matriz = new String[qtdLin][this.qtdCol];
		carregaDados();
	}

	public int getColumnCount() {
		return qtdCol;
	}

	public int getRowCount() {
		return this.qtdLin;
	}

	public Object getValueAt(int row, int col) {
		return matriz[row][col];
	}
	
	private void carregaDados(){
		int i = 0;
		for(int x = 0; x<this.qtdLin; x++){
			for(int z = 0; z<this.qtdCol; z++){
				matriz[x][z] = classe.get(i).toString(); 
				i++;
			}
		}
	}

}
