package br.unisal.model;

import java.util.Date;

/**
 * Classe de Modelagem Conceitual
 * @author Paulo Barreto
 * @data 12/04/2022
 */
public class Venda {
	//Atributos
	private Date data;
	private int produto;
	private double quantidade;
	private double valorVenda;
	
	public Venda(Date data, int produto, double quantidade, double valorVenda) {
		this.data = data;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorVenda = valorVenda;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}
}