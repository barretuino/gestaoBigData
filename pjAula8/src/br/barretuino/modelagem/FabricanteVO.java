package br.barretuino.modelagem;

public class FabricanteVO {
	private Long id;
	private String nome;
	private String descricao;
	private String placa;
	
	public FabricanteVO(Long id, String nome, 
				String descricao, String placa) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.placa = placa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	} 
}
