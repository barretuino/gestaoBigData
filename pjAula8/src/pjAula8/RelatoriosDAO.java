package pjAula8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	// Método principal para demonstrar a execução
	public static void main(String[] args) {
		RelatoriosDAO relatorios = new RelatoriosDAO();

		relatorios.contarTodosProdutos();
		relatorios.somarPrecoTotal();
		relatorios.encontrarMinMaxPreco();
		relatorios.mediaPrecoPorCategoria();
		relatorios.somarPrecoPorCategoria("Eletrônicos"); // Exemplo com filtro
		relatorios.contarProdutosComMaisDeXUnidades(500); // Exemplo com HAVING
		relatorios.encontrarCategoriaMaisComum();
		relatorios.contarProdutosNoPeriodo("2023-01-01", "2023-12-31");
		// relatorios.somarVendasPorProduto(); // Este exemplo exige a tabela `vendas`
		relatorios.mediaPrecoPorCategoriaEData();
	}
}