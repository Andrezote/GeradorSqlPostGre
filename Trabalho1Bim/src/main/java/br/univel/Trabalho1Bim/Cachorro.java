package br.univel.Trabalho1Bim;

import br.univel.anotacoes.AnotaColuna;

public class Cachorro {

	private String nome;
	private Long peso;
	@AnotaColuna(nome = "id", pk = true)
	private int id;
	private String cor;
	private String sexo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getPeso() {
		return peso;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
