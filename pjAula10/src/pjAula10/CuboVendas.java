package pjAula10;

import java.util.*;
import java.util.stream.Collectors;

class CuboVendas {
	private List<TransacaoVenda> dadosBrutos;

	public CuboVendas(List<TransacaoVenda> dados) {
		this.dadosBrutos = dados;
	}

	// OPERAÇÃO OLAP: Roll Up (Agregação: Mês -> Ano / Nome Produto -> Categoria)
	public Map<Integer, Double> rollUpVendasPorAno() {
		// Agrupa os Fatos (Transacoes) pela Dimensão Tempo (nível Ano)
		return dadosBrutos.stream()
				.collect(Collectors.groupingBy(
						transacao -> transacao.getTempo().getAno(), // Agrupa por Ano
						Collectors.summingDouble(TransacaoVenda::getValorVenda) // Soma o Valor de Venda
						));
	}

	// OPERAÇÃO OLAP: Slice (Fatiamento: Filtrar uma dimensão - Categoria)
	public Map<String, Double> slicePorCategoria(String categoriaDesejada) {
		// 1. Fatiar (Filter): Seleciona apenas a categoria desejada
		List<TransacaoVenda> fatia = dadosBrutos.stream()
				.filter(transacao -> transacao.getProduto().getCategoria().equals(categoriaDesejada))
				.collect(Collectors.toList());

		// 2. Agrupar (Roll Up implícito): Agrupa os Fatos filtrados por Nome do Produto
		return fatia.stream()
				.collect(Collectors.groupingBy(
						transacao -> transacao.getProduto().getNome(), // Agrupa por Nome do Produto
						Collectors.summingDouble(TransacaoVenda::getValorVenda)
						));
	}

	// Visualização (Exemplo de Relatório Detalhado - Drill Down implícito)
	public Map<String, Map<String, Double>> vendasDetalhadaPorMesECategoria() {
		return dadosBrutos.stream()
				.collect(Collectors.groupingBy(
						transacao -> transacao.getTempo().getMes(), // Nível 1: Mês
						Collectors.groupingBy(
								transacao -> transacao.getProduto().getCategoria(), // Nível 2: Categoria
								Collectors.summingDouble(TransacaoVenda::getValorVenda)
								)
						));
	}
}