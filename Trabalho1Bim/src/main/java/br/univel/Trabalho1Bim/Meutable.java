package br.univel.Trabalho1Bim;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.univel.anotacoes.AnotaColuna;

public class Meutable extends AbstractTableModel {

	private List<?> classe;
	private int qtdCol;
	private int qtdLin;
	private Object obj;

	private String[][] matriz;

	public Meutable(int qtdCol1, List<?> classe2, Object o) {
		this.classe = classe2;
		this.qtdCol = qtdCol1;
		this.qtdLin = classe.size() / this.qtdCol;
		this.matriz = new String[qtdLin][this.qtdCol];
		this.obj = o;
		carregaDados();
	}

	public int getColumnCount() {
		return qtdCol;
	}

	public int getRowCount() {
		return this.qtdLin;
	}

	@Override
	public String getColumnName(int column) {
		Class<?> cz = obj.getClass();
		List<String> nomesC = new ArrayList<String>();
		String nomeColuna = null;
		for (Field field : cz.getDeclaredFields()) {
			if (field.isAnnotationPresent(AnotaColuna.class)) {
				AnotaColuna ac = field.getAnnotation(AnotaColuna.class);
				if (ac.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = ac.nome();
				}
			} else {
				nomeColuna = field.getName().toUpperCase();
			}
			nomesC.add(nomeColuna);
		}
		return nomesC.get(column);
	}

	public Object getValueAt(int row, int col) {
		return matriz[row][col];
	}

	private void carregaDados() {
		int i = 0;
		for (int x = 0; x < this.qtdLin; x++) {
			for (int z = 0; z < this.qtdCol; z++) {
				matriz[x][z] = classe.get(i).toString();
				i++;
			}
		}
	}

}
