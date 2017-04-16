package br.univel.Trabalho1Bim;

import br.univel.anotacoes.AnotaColuna;
import br.univel.anotacoes.AnotaTabela;

@AnotaTabela(nome="Pessoa_F")
public class Pessoa {
	
	
	public Pessoa(){
		this(1, null, 1);
	}

	public Pessoa(int id, String nome, int idade) {
		super();
		this.id = id;
		this.nome = nome;
		this.idade = idade;
	}
	@AnotaColuna(nome="Id")
	private int id;
	
	@AnotaColuna(nome = "NOME",tipo = "VARCHAR(100)")
	private String nome;
	
	@AnotaColuna(nome="Idade")
	private int idade;
	
	
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
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
}
