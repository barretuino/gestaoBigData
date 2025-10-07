package pjAula9;

public class Venda {
	private int id;
	private String produto;
	private int quantidade;
	private double precoUnitario;
	private double valorTotal; // Campo transformado (Calculado)

	// Construtor usado na Extração (E)
	public Venda(int id, String produto, int quantidade, double precoUnitario) {
		this.id = id;
		this.produto = produto;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
	}

	// Método para a Transformação (T)
	public void calcularValorTotal() {
		this.valorTotal = this.quantidade * this.precoUnitario;
	}

	// Getters para a Carga (L)
	public int getId() { 
		return id; 
	}
	
	public String getProduto() { 
		return produto; 
	}
	public int getQuantidade() { 
		return quantidade; 
	}
	public double getValorTotal() { 
		return valorTotal; 
	}

	public double getPrecoUnitario() {
		return precoUnitario;
	}
}