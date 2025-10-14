package pjAula10;

import java.util.Objects;

//1. DIMENSÃO: Produto (Exemplo de hierarquia: Categoria -> Nome)
class Produto {
	private String categoria;
	private String nome;

	public Produto(String categoria, String nome) {
		this.categoria = categoria;
		this.nome = nome;
	}

	public String getCategoria() { 
		return categoria; 
	}

	public String getNome() {
		return nome; 
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Produto produto = (Produto) o;
		return Objects.equals(categoria, produto.categoria) && Objects.equals(nome, produto.nome);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, nome);
	}
}