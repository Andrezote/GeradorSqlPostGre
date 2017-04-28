package br.univel.Trabalho1Bim;

import java.math.BigDecimal;

import br.univel.anotacoes.AnotaColuna;
import br.univel.anotacoes.AnotaTabela;

@AnotaTabela(nome = "Magos_Rs")
public class Mago {

	@AnotaColuna(nome = "id_mago", pk = true)
	private int id;
	@AnotaColuna(nome = "classe_mago")
	private String Classe;
	@AnotaColuna(nome = "experiencia")
	private BigDecimal xp;
	@AnotaColuna(nome = "lvl")
	private int level;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClasse() {
		return Classe;
	}

	public void setClasse(String classe) {
		Classe = classe;
	}

	public BigDecimal getXp() {
		return xp;
	}

	public void setXp(BigDecimal xp) {
		this.xp = xp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
