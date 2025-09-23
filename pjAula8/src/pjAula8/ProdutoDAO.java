package pjAula8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

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

	/**
	 * Consulta produtos com base em múltiplos critérios, otimizando a performance.
	 * Usa PreparedStatement para evitar injeção de SQL e otimizar a execução.
	 *
	 * @param nome Filtro por nome do produto (pode ser nulo ou vazio).
	 * @param categoria Filtro por categoria (pode ser nulo ou vazio).
	 * @return Uma lista de produtos que correspondem aos critérios.
	 */
	public List<Produto> buscarProdutos(String nome, String categoria) {
		List<Produto> produtos = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("SELECT id, nome, categoria, data_cadastro, preco FROM produtos WHERE 1=1");

			// Adiciona condições ao SQL baseadas nos parâmetros fornecidos
			if (nome != null && !nome.trim().isEmpty()) {
				sql.append(" AND nome LIKE ?");
			}
			if (categoria != null && !categoria.trim().isEmpty()) {
				sql.append(" AND categoria = ?");
			}

			pstmt = conn.prepareStatement(sql.toString());

			int paramIndex = 1;
			// Define os parâmetros do PreparedStatement
			if (nome != null && !nome.trim().isEmpty()) {
				pstmt.setString(paramIndex++, "%" + nome + "%");
			}
			if (categoria != null && !categoria.trim().isEmpty()) {
				pstmt.setString(paramIndex++, categoria);
			}

			System.out.println("Executando a consulta: " + pstmt.toString());
			long startTime = System.nanoTime();

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setCategoria(rs.getString("categoria"));
				produto.setDataCadastro(rs.getDate("data_cadastro"));
				produto.setPreco(rs.getBigDecimal("preco"));
				produtos.add(produto);
			}

			long endTime = System.nanoTime();
			long duration = (endTime - startTime) / 1_000_000; // Convertendo para milissegundos
			System.out.println("Consulta concluída em " + duration + " ms.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Fecha os recursos na ordem inversa de abertura
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return produtos;
	}
}