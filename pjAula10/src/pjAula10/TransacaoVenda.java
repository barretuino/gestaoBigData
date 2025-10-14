package pjAula10;

//3. FATO: TransacaoVenda (A tabela de fatos)
class TransacaoVenda {
	private Produto produto;
	private Tempo tempo;
	private double valorVenda; // MEDIDA (Métrica)

	public TransacaoVenda(Produto produto, Tempo tempo, double valorVenda) {
		this.produto = produto;
		this.tempo = tempo;
		this.valorVenda = valorVenda;
	}

	public Produto getProduto() { 
		return produto; 
	}
	
	public Tempo getTempo() { 
		return tempo; 
	}
	
	public double getValorVenda() { 
		return valorVenda; 
	}
}