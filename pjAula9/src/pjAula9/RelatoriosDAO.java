package pjAula9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RelatoriosDAO {

	// Detalhes da sua conexão com o banco de dados
	private static final String URL = "jdbc:mysql://localhost:3306/curso";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";

	private Connection getConnection() throws SQLException {
		try {
			// Carrega o driver JDBC do MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("Driver JDBC não encontrado.");
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	private void medirEExecutarConsulta(String sql, String descricao, PreparedStatementSetter setter) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			setter.setParameters(pstmt);

			System.out.println("--- " + descricao + " ---");
			System.out.println("SQL: " + pstmt);

			long startTime = System.nanoTime();
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					// Apenas para demonstrar que a consulta retornou algo
					// Em um caso real, você processaria os dados aqui
					System.out.println("Resultado: " + rs.getObject(1));
				}
			}

			long endTime = System.nanoTime();
			long duration = (endTime - startTime) / 1_000_000;
			System.out.println("Consulta concluída em " + duration + " ms.\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FunctionalInterface
	private interface PreparedStatementSetter {
		void setParameters(PreparedStatement ps) throws SQLException;
	}

	/**
	 * Exemplo 1: Contagem total de produtos.
	 * Sem filtros, esta é a agregação mais simples.
	 */
	public void contarTodosProdutos() {
		String sql = "SELECT COUNT(*) FROM produtos";
		medirEExecutarConsulta(sql, "1. Contagem total de produtos", ps -> {});
	}

	/**
	 * Exemplo 2: Soma total do preço de todos os produtos.
	 */
	public void somarPrecoTotal() {
		String sql = "SELECT SUM(preco) FROM produtos";
		medirEExecutarConsulta(sql, "2. Soma total do preço dos produtos", ps -> {});
	}

	/**
	 * Exemplo 3: Encontrando o produto mais caro e o mais barato.
	 * Esta consulta usa duas funções de agregação em uma única busca.
	 */
	public void encontrarMinMaxPreco() {
		String sql = "SELECT MIN(preco), MAX(preco) FROM produtos";
		medirEExecutarConsulta(sql, "3. Encontrando o menor e o maior preço", ps -> {});
	}

	/**
	 * Exemplo 4: Média de preço por categoria.
	 * Demonstra o uso de GROUP BY, que é fundamental para agregações mais complexas.
	 * A performance aqui depende dos índices na coluna `categoria`.
	 */
	public void mediaPrecoPorCategoria() {
		String sql = "SELECT categoria, AVG(preco) FROM produtos GROUP BY categoria";
		medirEExecutarConsulta(sql, "4. Média de preço por categoria", ps -> {});
	}

	/**
	 * Exemplo 5: Agregações com filtro.
	 * A cláusula WHERE melhora a performance ao reduzir o volume de dados antes da agregação.
	 */
	public void somarPrecoPorCategoria(String categoria) {
		String sql = "SELECT SUM(preco) FROM produtos WHERE categoria = ?";
		medirEExecutarConsulta(sql, "5. Soma total para uma categoria específica", ps -> ps.setString(1, categoria));
	}

	/**
	 * Exemplo 6: Contagem de produtos por categoria, filtrando o resultado da agregação.
	 * Demonstra o uso de HAVING. A performance é afetada pela necessidade de agrupar primeiro.
	 */
	public void contarProdutosComMaisDeXUnidades(int minUnidades) {
		String sql = "SELECT categoria, COUNT(*) FROM produtos GROUP BY categoria HAVING COUNT(*) > ?";
		medirEExecutarConsulta(sql, "6. Contagem de categorias com mais de X produtos", ps -> ps.setInt(1, minUnidades));
	}

	/**
	 * Exemplo 7: Agregação em conjunto (subquery).
	 * Encontra a categoria com o maior número de produtos.
	 */
	public void encontrarCategoriaMaisComum() {
		String sql = "SELECT categoria FROM produtos GROUP BY categoria ORDER BY COUNT(*) DESC LIMIT 1";
		medirEExecutarConsulta(sql, "7. Encontrando a categoria com mais produtos", ps -> {});
	}

	/**
	 * Exemplo 8: Agregação em um período de tempo (otimização por partição).
	 * Se a tabela for particionada por data_cadastro, esta busca será muito rápida.
	 */
	public void contarProdutosNoPeriodo(String dataInicio, String dataFim) {
		String sql = "SELECT COUNT(*) FROM produtos WHERE data_cadastro BETWEEN ? AND ?";
		medirEExecutarConsulta(sql, "8. Contagem de produtos em um período específico", ps -> {
			ps.setString(1, dataInicio);
			ps.setString(2, dataFim);
		});
	}

	/**
	 * Exemplo 9: Agregação com JOIN (se houvesse outra tabela).
	 * Exemplo teórico para ilustrar a agregação entre tabelas, fundamental para BI.
	 * Digamos que exista uma tabela `vendas`.
	 */
	public void somarVendasPorProduto() {
		String sql = "SELECT p.nome, SUM(v.quantidade) FROM produtos p JOIN vendas v ON p.id = v.produto_id GROUP BY p.nome";
		medirEExecutarConsulta(sql, "9. Soma de vendas por produto (JOIN com uma tabela 'vendas')", ps -> {});
	}

	/**
	 * Exemplo 10: Agregação complexa com múltiplos agrupamentos.
	 * Encontra a média de preço por categoria e ano de cadastro.
	 */
	public void mediaPrecoPorCategoriaEData() {
		String sql = "SELECT categoria, YEAR(data_cadastro), AVG(preco) FROM produtos GROUP BY categoria, YEAR(data_cadastro)";
		medirEExecutarConsulta(sql, "10. Média de preço por categoria e ano", ps -> {});
	}

	// --- NOVAS VARIAÇÕES COM JOIN E FILTROS COMPLEXOS ---

	/**
	 * Exemplo 11: Total de vendas por produto (Agregação Simples com JOIN).
	 * Otimização: Uso do JOIN para calcular o faturamento. O banco de dados faz a união e a soma
	 * de forma otimizada.
	 */
	public void totalFaturamentoPorProduto() {
		String sql = "SELECT p.nome, SUM(v.quantidade * v.valor_unitario) AS faturamento " +
				"FROM produtos p JOIN vendas v ON p.id = v.produto_id " +
				"GROUP BY p.nome ORDER BY faturamento DESC";
		medirEExecutarConsulta(sql, "11. Total de faturamento por produto (JOIN + SUM)", ps -> {});
	}

	/**
	 * Exemplo 12: Média de preço de venda (AVG) dos produtos mais vendidos.
	 * Otimização: Uso de subconsulta no HAVING para filtrar apenas os produtos que tiveram
	 * um número mínimo de vendas (ex: > 1000 unidades).
	 */
	public void mediaVendaProdutosMaisVendidos(int minVendas) {
		String sql = "SELECT p.nome, AVG(v.valor_unitario) FROM produtos p JOIN vendas v ON p.id = v.produto_id " +
				"GROUP BY p.nome HAVING SUM(v.quantidade) > ?";
		medirEExecutarConsulta(sql, "12. Média de preço de venda (AVG) para produtos com > X vendas", ps -> ps.setInt(1, minVendas));
	}

	/**
	 * Exemplo 13: Contagem de produtos por faixa de preço (Agregação por Faixa - BUCKETING).
	 * Otimização: Uso da função CASE para criar "baldes" de preço, permitindo agregar
	 * grupos de produtos sem a necessidade de um campo específico no BD.
	 */
	public void contarProdutosPorFaixaDePreco() {
		String sql = "SELECT " +
				"CASE " +
				"WHEN preco < 50.00 THEN 'Barato' " +
				"WHEN preco BETWEEN 50.00 AND 500.00 THEN 'Mediano' " +
				"ELSE 'Caro' END AS faixa_preco, " +
				"COUNT(*) FROM produtos GROUP BY faixa_preco";
		medirEExecutarConsulta(sql, "13. Contagem de produtos por Faixa de Preço (CASE)", ps -> {});
	}

	/**
	 * Exemplo 14: Agregação por Mês e Categoria (Série Temporal + Agrupamento).
	 * Otimização: Uso de funções de data (`DATE_FORMAT`) em conjunto com `GROUP BY`.
	 * Essencial para relatórios de BI.
	 */
	public void faturamentoMensalPorCategoria(String categoria) {
		String sql = "SELECT DATE_FORMAT(v.data_venda, '%Y-%m') AS mes_venda, SUM(v.quantidade * v.valor_unitario) " +
				"FROM vendas v JOIN produtos p ON v.produto_id = p.id " +
				"WHERE p.categoria = ? " +
				"GROUP BY mes_venda ORDER BY mes_venda";
		medirEExecutarConsulta(sql, "14. Faturamento Mensal para uma Categoria (Série Temporal)", ps -> ps.setString(1, categoria));
	}

	/**
	 * Exemplo 15: Cálculo de estoque (Usando Subtração de Agregações).
	 * Exemplo teórico que calcula o estoque: Total de produtos cadastrados - Total de produtos vendidos.
	 * Otimização: Combina duas agregações (ou uma subconsulta) para chegar a um valor derivado.
	 */
	public void calcularEstoqueRemanescente(int produtoId) {
		// Esta é uma simplificação. Um cálculo real de estoque seria mais complexo.
		String sql = "SELECT p.nome, p.quantidade_inicial - (SELECT SUM(v.quantidade) FROM vendas v WHERE v.produto_id = p.id) AS estoque " +
				"FROM produtos p WHERE p.id = ?";
		// NOTA: Esta consulta requer que a tabela 'produtos' tenha uma coluna 'quantidade_inicial'.
		// Usei a estrutura para ilustrar o conceito de subconsulta de agregação.
		medirEExecutarConsulta(sql, "15. Cálculo de Estoque (Agregação de Subconsulta)", ps -> ps.setInt(1, produtoId));
	}

	/**
	 * Exemplo 16: Cálculo da Margem de Lucro Bruta por Produto (JOIN Triplo).
	 * Margem = (Faturamento - Custo do Produto - Custo do Frete)
	 * Otimização: Combinação de três tabelas (produtos, vendas, logistica) e cálculo
	 * de uma métrica de negócio (Margem) no banco de dados.
	 */
	public void calcularMargemDeLucroBruta(String nomeProduto) {
		// Nota: Usamos p.preco como CUSTO_PRODUTO para demonstração.
		// Em um sistema real, produtos teria uma coluna 'custo_unitario'.
		String sql = "SELECT p.nome, " +
				"SUM(v.quantidade * v.valor_unitario) AS Faturamento, " +
				"SUM(v.quantidade * p.preco) AS Custo_Produto, " +
				"SUM(l.custo_frete) AS Custo_Frete, " +
				"SUM((v.quantidade * v.valor_unitario) - (v.quantidade * p.preco) - l.custo_frete) AS Lucro_Bruto " +
				"FROM produtos p " +
				"JOIN vendas v ON p.id = v.produto_id " +
				"JOIN logistica l ON v.id = l.venda_id " +
				"WHERE p.nome = ? " +
				"GROUP BY p.nome";
		medirEExecutarConsulta(sql, "16. Margem de Lucro Bruta por Produto (JOIN Triplo)", ps -> ps.setString(1, nomeProduto));
	}

	/**
	 * Exemplo 17: Ranqueamento de Produtos por Faturamento dentro da Categoria (Window Function).
	 * Otimização: Uso da função de janela RANK() ou DENSE_RANK() (MySQL 8.0+)
	 * para ranquear registros dentro de um grupo (PARTITION BY).
	 */
	public void ranquearProdutosPorFaturamento() {
	    String sql = "SELECT " +
	                 "    SQ.categoria, " +
	                 "    SQ.nome AS Produto, " +
	                 "    SQ.Faturamento, " +
	                 "    RANK() OVER (PARTITION BY SQ.categoria ORDER BY SQ.Faturamento DESC) AS Rank_Categoria " + // AQUI USAMOS SQ
	                 "FROM ( " +
	                 "    SELECT " +
	                 "        p.id, " +
	                 "        p.nome, " +
	                 "        p.categoria, " +
	                 "        SUM(v.quantidade * v.valor_unitario) AS Faturamento " +
	                 "    FROM produtos p " +
	                 "    JOIN vendas v ON p.id = v.produto_id " +
	                 "    GROUP BY p.id, p.nome, p.categoria " +
	                 ") AS SQ " + // O ALIAS DA SUBQUERY É DEFINIDO AQUI
	                 "ORDER BY SQ.categoria, Rank_Categoria";
	    
	    medirEExecutarConsulta(sql, "Ranqueamento de Produto por Categoria (Window Function)", ps -> {});
	}

	/**
	 * Exemplo 18: Vendas Acumuladas no Ano por Mês (Running Total - Window Function).
	 * Otimização: Uso da função de janela SUM() OVER (ORDER BY) para calcular
	 * o acumulado (*Running Total*) sem usar variáveis de sessão.
	 */
	public void vendasAcumuladasNoAno(int ano) {
		String sql = "SELECT " +
				"    DATE_FORMAT(v.data_venda, '%Y-%m') AS Mes, " +
				"    SUM(v.quantidade * v.valor_unitario) AS Faturamento_Mensal, " +
				"    SUM(SUM(v.quantidade * v.valor_unitario)) OVER (ORDER BY DATE_FORMAT(v.data_venda, '%Y-%m')) AS Acumulado_Ano " +
				"FROM vendas v " +
				"WHERE YEAR(v.data_venda) = ? " +
				"GROUP BY Mes " +
				"ORDER BY Mes";
		medirEExecutarConsulta(sql, "18. Vendas Acumuladas no Ano (Running Total)", ps -> ps.setInt(1, ano));
	}

	/**
	 * Exemplo 19: Análise de Conversão de Pedidos (Agregação Condicional - PIVÔ).
	 * Otimização: Uso de SUM(CASE WHEN...) para "pivotar" os dados, transformando
	 * os valores da coluna 'status_entrega' em colunas separadas para contagem.
	 */
	public void analiseDeConversaoDePedidos() {
		String sql = "SELECT " +
				"    COUNT(l.venda_id) AS Total_Vendas, " +
				"    SUM(CASE WHEN l.status_entrega = 'APROVADO' THEN 1 ELSE 0 END) AS Aprovados, " +
				"    SUM(CASE WHEN l.status_entrega = 'ENVIADO' THEN 1 ELSE 0 END) AS Enviados, " +
				"    SUM(CASE WHEN l.status_entrega = 'ENTREGUE' THEN 1 ELSE 0 END) AS Entregues, " +
				"    SUM(CASE WHEN l.status_entrega = 'CANCELADO' THEN 1 ELSE 0 END) AS Cancelados, " +
				"    (SUM(CASE WHEN l.status_entrega = 'ENTREGUE' THEN 1 ELSE 0 END) / COUNT(l.venda_id)) * 100 AS Taxa_Entrega " +
				"FROM logistica l";
		medirEExecutarConsulta(sql, "19. Análise de Conversão de Pedidos (Pivotamento)", ps -> {});
	}

	/**
	 * Exemplo 20: Vendas do Último Trimestre vs. Trimestre Anterior (Subconsultas com Filtro de Data).
	 * Otimização: Uso de subconsultas (ou CTEs, se o BD suportar) para filtrar conjuntos
	 * de dados temporais e comparar os resultados lado a lado.
	 */
	public void vendasComparativoTrimestral() {
		String sql = "SELECT " +
				"    SUM(CASE WHEN v.data_venda >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) THEN (v.quantidade * v.valor_unitario) ELSE 0 END) AS Vendas_T3, " +
				"    SUM(CASE WHEN v.data_venda BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 MONTH) AND DATE_SUB(CURDATE(), INTERVAL 3 MONTH) THEN (v.quantidade * v.valor_unitario) ELSE 0 END) AS Vendas_T2 " +
				"FROM vendas v";
		medirEExecutarConsulta(sql, "20. Comparativo de Vendas: Último Trimestre vs. Trimestre Anterior (Filtro Temporal)", ps -> {});
	}
	
	/**
	 * 21. Top 10 Clientes por Faturamento (JOIN Múltiplo).
	 * Utiliza 3 JOINs para agregar faturamento e limitar o resultado.
	 */
	public void top10ClientesPorFaturamento() {
	    String sql = "SELECT c.nome, SUM(ip.quantidade * ip.preco_unitario) AS Faturamento_Total " +
	                 "FROM clientes c " +
	                 "JOIN pedidos p ON c.id = p.cliente_id " +
	                 "JOIN itens_pedido ip ON p.id = ip.pedido_id " +
	                 "WHERE p.status != 'CANCELADO' " +
	                 "GROUP BY c.nome " +
	                 "ORDER BY Faturamento_Total DESC " +
	                 "LIMIT 10";
	    medirEExecutarConsulta(sql, "1. Top 10 Clientes por Faturamento", ps -> {});
	}

	/**
	 * 22. Valor Médio do Pedido por Cidade (JOIN Otimizado + HAVING).
	 * Agrupa o valor total do pedido pela cidade do cliente.
	 */
	public void valorMedioPedidoPorCidade() {
	    String sql = "SELECT c.cidade, AVG(p.valor_total) AS Valor_Medio_Pedido " +
	                 "FROM clientes c " +
	                 "JOIN pedidos p ON c.id = p.cliente_id " +
	                 "GROUP BY c.cidade " +
	                 "HAVING COUNT(p.id) > 50";
	    medirEExecutarConsulta(sql, "2. Valor Médio do Pedido por Cidade", ps -> {});
	}

	// ### Otimizações de Estoque e Produto
	/**
	 * 23. Produtos Sem Venda Recente (LEFT JOIN Otimizado).
	 * Identifica produtos parados no estoque, crucial para gestão de inventário.
	 */
	public void produtosSemVendaRecente(int dias) {
	    String sql = "SELECT p.nome " +
	                 "FROM produtos p " +
	                 "LEFT JOIN itens_pedido ip ON p.id = ip.produto_id " +
	                 "LEFT JOIN pedidos pe ON ip.pedido_id = pe.id AND pe.data_pedido >= DATE_SUB(CURDATE(), INTERVAL ? DAY) " +
	                 "WHERE pe.id IS NULL";
	    medirEExecutarConsulta(sql, "3. Produtos Sem Venda nos Últimos " + dias + " Dias", ps -> ps.setInt(1, dias));
	}

	/**
	 * 24. Estoque Médio Vendido por Categoria (Agregação Múltipla em 4 Tabelas).
	 * Combina informações de estoque atual com a média de venda histórica.
	 */
	public void estoqueMedioVendidoPorCategoria() {
	    String sql = "SELECT " +
	                 "    p.categoria, " +
	                 "    e.quantidade_atual AS Estoque_Atual_Snapshot, " +
	                 "    AVG(ip.quantidade) AS Media_Vendida_Por_Item " +
	                 "FROM produtos p " +
	                 "JOIN estoque e ON p.id = e.produto_id " +
	                 "JOIN itens_pedido ip ON p.id = ip.produto_id " +
	                 "GROUP BY p.categoria, e.quantidade_atual";
	    medirEExecutarConsulta(sql, "4. Estoque Médio Vendido por Categoria", ps -> {});
	}

	/**
	 * 25. Contagem de Produtos em Falta no Estoque (Filtro Simples).
	 * Consulta crítica para alertar sobre baixo estoque.
	 */
	public void produtosEmAlertaDeEstoque(int limite) {
	    String sql = "SELECT COUNT(*) AS Produtos_Em_Alerta " +
	                 "FROM estoque e " +
	                 "WHERE e.quantidade_atual <= ?";
	    medirEExecutarConsulta(sql, "5. Contagem de Produtos em Falta (Estoque <= " + limite + ")", ps -> ps.setInt(1, limite));
	}

	// Otimizações Avançadas (Window Functions e Pivotamento)
	/**
	 * 26. Faturamento Acumulado (Running Total) por Mês e Cliente (Window Function).
	 * Demonstra SUM() OVER (PARTITION BY...) para calcular a lealdade do cliente.
	 */
	public void faturamentoAcumuladoPorCliente() {
	    String sql = "SELECT " +
	                 "    c.nome, " +
	                 "    DATE_FORMAT(p.data_pedido, '%Y-%m') AS Mes, " +
	                 "    SUM(ip.quantidade * ip.preco_unitario) AS Faturamento_Mensal, " +
	                 "    SUM(SUM(ip.quantidade * ip.preco_unitario)) OVER (PARTITION BY c.nome ORDER BY DATE_FORMAT(p.data_pedido, '%Y-%m')) AS Acumulado_Cliente " +
	                 "FROM clientes c " +
	                 "JOIN pedidos p ON c.id = p.cliente_id " +
	                 "JOIN itens_pedido ip ON p.id = ip.pedido_id " +
	                 "GROUP BY c.nome, Mes " +
	                 "ORDER BY c.nome, Mes";
	    medirEExecutarConsulta(sql, "6. Faturamento Acumulado por Cliente (Running Total)", ps -> {});
	}

	/**
	 * 27. Taxa de Cancelamento por Categoria (Agregação Condicional - Pivotamento).
	 * Usa SUM(CASE WHEN...) para calcular a taxa de cancelamento.
	 */
	public void taxaDeCancelamentoPorCategoria() {
	    String sql = "SELECT " +
	                 "    pr.categoria, " +
	                 "    COUNT(pe.id) AS Total_Pedidos, " +
	                 "    SUM(CASE WHEN pe.status = 'CANCELADO' THEN 1 ELSE 0 END) AS Total_Cancelados, " +
	                 "    (SUM(CASE WHEN pe.status = 'CANCELADO' THEN 1 ELSE 0 END) / COUNT(pe.id)) * 100 AS Taxa_Cancelamento " +
	                 "FROM produtos pr " +
	                 "JOIN itens_pedido ip ON pr.id = ip.produto_id " +
	                 "JOIN pedidos pe ON ip.pedido_id = pe.id " +
	                 "GROUP BY pr.categoria";
	    medirEExecutarConsulta(sql, "7. Taxa de Cancelamento por Categoria (Pivotamento)", ps -> {});
	}

	// Otimizações Temporais e Validação
	/**
	 * 28. Ranking de Vendas Trimestrais (JOIN Otimizado + Filtro Temporal).
	 * Limita a consulta a um período recente para performance.
	 */
	public void rankingVendasTrimestrais() {
	    String sql = "SELECT " +
	                 "    p.nome, " +
	                 "    SUM(ip.quantidade) AS Quantidade_Vendida " +
	                 "FROM produtos p " +
	                 "JOIN itens_pedido ip ON p.id = ip.produto_id " +
	                 "JOIN pedidos pe ON ip.pedido_id = pe.id " +
	                 "WHERE pe.data_pedido >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) " +
	                 "GROUP BY p.nome " +
	                 "ORDER BY Quantidade_Vendida DESC " +
	                 "LIMIT 20";
	    medirEExecutarConsulta(sql, "8. Ranking de Vendas no Último Trimestre", ps -> {});
	}

	/**
	 * 29. Pedidos que Excederiam o Estoque (Demonstração de Risco).
	 * Usa HAVING para filtrar grupos que superam a quantidade em estoque.
	 */
	public void pedidosQueExcederiamEstoque() {
	    String sql = "SELECT ip.pedido_id, p.nome, e.quantidade_atual AS Estoque_Atual, SUM(ip.quantidade) AS Pedido_Total " +
	                 "FROM itens_pedido ip " +
	                 "JOIN produtos p ON ip.produto_id = p.id " +
	                 "JOIN estoque e ON ip.produto_id = e.produto_id " +
	                 "GROUP BY ip.pedido_id, p.nome, e.quantidade_atual " +
	                 "HAVING SUM(ip.quantidade) > e.quantidade_atual";
	    medirEExecutarConsulta(sql, "9. Pedidos que Excederiam o Estoque Atual", ps -> {});
	}

	/**
	 * 30. Vendas do Dia por Hora (Série Temporal Detalhada).
	 * Agregação por HOUR() em uma data específica, excelente para painéis de BI em tempo real.
	 */
	public void vendasDoDiaPorHora() {
	    String sql = "SELECT " +
	                 "    DATE_FORMAT(data_pedido, '%H:00') AS Hora_do_Dia, " +
	                 "    COUNT(id) AS Numero_Pedidos, " +
	                 "    SUM(valor_total) AS Faturamento_Horario " +
	                 "FROM pedidos " +
	                 "WHERE DATE(data_pedido) = CURDATE() " +
	                 "GROUP BY Hora_do_Dia " +
	                 "ORDER BY Hora_do_Dia";
	    medirEExecutarConsulta(sql, "10. Vendas do Dia Atual por Hora", ps -> {});
	}
	
	//Lento
	public void agregarNoJava() {
	    // Busca TODOS os pedidos e o nome do cliente correspondente.
	    // NÃO há GROUP BY no SQL.
	    String sql = "SELECT c.nome, p.id AS pedido_id " +
	                 "FROM pedidos p " +
	                 "JOIN clientes c ON p.cliente_id = c.id";

	    System.out.println("--- 2. Agregação LENTA (JAVA) ---");
	    System.out.println("SQL: SELECT c.nome, p.id ... (Sem GROUP BY)");
	    
	    long startTime = System.nanoTime();
	    
	    // Mapa para armazenar o resultado da agregação: Cliente -> Contagem
	    Map<String, Integer> contagemPedidos = new HashMap<>();
	    int totalRegistrosMovidos = 0;

	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        // APLICAÇÃO JAVA recebe TODOS os registros e faz o loop de agregação
	        while (rs.next()) {
	            String nomeCliente = rs.getString("nome");
	            
	            // Lógica de agregação (COUNT) feita no Java
	            contagemPedidos.put(
	                nomeCliente, 
	                contagemPedidos.getOrDefault(nomeCliente, 0) + 1
	            );
	            totalRegistrosMovidos++;
	        }

	        long endTime = System.nanoTime();
	        long duration = (endTime - startTime) / 1_000_000;

	        System.out.println("Total de Registros Movidos (Rede): " + totalRegistrosMovidos);
	        System.out.println("Resultados processados: " + contagemPedidos.size() + " clientes únicos.");
	        System.out.println("Tempo de Execução (Java + Busca Total): " + duration + " ms.\n");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	//Otimizado
	public void agregarNoBancoDeDados() {
	    String sql = "SELECT c.nome, COUNT(p.id) AS Total_Pedidos " +
	                 "FROM clientes c " +
	                 "JOIN pedidos p ON c.id = p.cliente_id " +
	                 "GROUP BY c.nome " +
	                 "ORDER BY Total_Pedidos DESC";

	    System.out.println("--- 1. Agregação OTIMIZADA (SQL) ---");
	    System.out.println("SQL: SELECT c.nome, COUNT(p.id) ... GROUP BY c.nome");

	    long startTime = System.nanoTime();
	    
	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        int contagemClientes = 0;
	        
	        // A aplicação Java apenas itera sobre o RESULTADO FINAL agregado
	        while (rs.next()) {
	            // String nome = rs.getString("nome");
	            // int total = rs.getInt("Total_Pedidos");
	            contagemClientes++;
	        }
	        
	        long endTime = System.nanoTime();
	        long duration = (endTime - startTime) / 1_000_000;
	        
	        System.out.println("Resultados processados: " + contagemClientes + " clientes únicos.");
	        System.out.println("Tempo de Execução (SQL Puro): " + duration + " ms.\n");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Método principal para demonstrar a execução
	public static void main(String[] args) {
		RelatoriosDAO relatorios = new RelatoriosDAO();

		//1
		relatorios.contarTodosProdutos();
		//2
		relatorios.somarPrecoTotal();
		//3
		relatorios.encontrarMinMaxPreco();
		//4
		relatorios.mediaPrecoPorCategoria();
		//5
		relatorios.somarPrecoPorCategoria("Eletrônicos"); // Exemplo com filtro
		//6
		relatorios.contarProdutosComMaisDeXUnidades(500); // Exemplo com HAVING
		//7
		relatorios.encontrarCategoriaMaisComum();
		//8
		relatorios.contarProdutosNoPeriodo("2023-01-01", "2023-12-31");
		//9
		relatorios.somarVendasPorProduto(); // Este exemplo exige a tabela `vendas`
		//10
		relatorios.mediaPrecoPorCategoriaEData();

		System.out.println("\n##################################################");
		System.out.println("### OTIMIZAÇÕES COM TABELAS RELACIONAIS (VENDAS) ###");
		System.out.println("####################################################\n");
		//11
		relatorios.totalFaturamentoPorProduto();
		//12
		relatorios.mediaVendaProdutosMaisVendidos(50); 
		//13
		relatorios.contarProdutosPorFaixaDePreco();
		//14
		relatorios.faturamentoMensalPorCategoria("Eletrônicos"); 
		// Para o Exemplo 15, substitua '1' pelo ID de um produto que você tenha na tabela.
		// Você precisará adicionar a coluna 'quantidade_inicial' na tabela produtos para rodar de fato.
		relatorios.calcularEstoqueRemanescente(1); 
		//16
		relatorios.calcularMargemDeLucroBruta("Produto 779883");
		//17
		relatorios.ranquearProdutosPorFaturamento();
		//18
		relatorios.vendasAcumuladasNoAno(2025);
		//19
		relatorios.analiseDeConversaoDePedidos();
		//20
		relatorios.vendasComparativoTrimestral();
		
		// --- 1. Otimizações de Cliente e Faturamento (JOIN Múltiplo) ---
	    // 21
		relatorios.top10ClientesPorFaturamento();
	    // 22
	    relatorios.valorMedioPedidoPorCidade();

	    // --- 2. Otimizações de Estoque e Produto ---
	    // Você pode alterar o número de dias e o limite de estoque para testar diferentes cenários.
	    // 23
	    relatorios.produtosSemVendaRecente(90);      // Produtos sem venda nos últimos 90 dias
	    // 24
	    relatorios.estoqueMedioVendidoPorCategoria();
	    // 25
	    relatorios.produtosEmAlertaDeEstoque(10);    // Produtos com estoque menor ou igual a 10

	    // --- 3. Otimizações Avançadas (Window Functions e Pivotamento) ---
	    // 26
	    relatorios.faturamentoAcumuladoPorCliente();
	    // 27
	    relatorios.taxaDeCancelamentoPorCategoria();

	    // --- 4. Otimizações Temporais e Validação ---
	    // 28
	    relatorios.rankingVendasTrimestrais();
	    // 29
	    relatorios.pedidosQueExcederiamEstoque();
	    
	    //30 Este método só retorna dados se houver pedidos feitos no dia em que você executar o código.
	    relatorios.vendasDoDiaPorHora(); 
	    
	    //Método Lento
	    relatorios.agregarNoBancoDeDados();
	    //Método Otimizado
	    relatorios.agregarNoJava(); 
	}
}