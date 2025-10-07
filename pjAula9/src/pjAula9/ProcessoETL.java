package pjAula9;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessoETL {

	// Configuração do Banco de Dados de Origem (OLTP)
	private static final String URL_OLTP = "jdbc:mysql://localhost:3306/db_oltp";
	private static final String USER_OLTP = "root";
	private static final String PASS_OLTP = "admin";

	// Configuração do Banco de Dados de Destino (DW)
	private static final String URL_DW = "jdbc:mysql://localhost:3306/db_dw";
	private static final String USER_DW = "root";
	private static final String PASS_DW = "admin";

	public static void main(String[] args) {
		try {
			// 1. Extração
			List<Venda> dadosExtraidos = extrair();
			System.out.println("E: " + dadosExtraidos.size() + " registros extraídos.");

			// 2. Transformação
			List<Venda> dadosTransformados = transformar(dadosExtraidos);
			System.out.println("T: " + dadosTransformados.size() + " registros transformados.");

			// 3. Carga
			carregar(dadosTransformados);
			System.out.println("L: Carga finalizada com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erro no processo ETL: " + e.getMessage());
		}
	}

	// --- E: EXTRAÇÃO (Extract) ---
	private static List<Venda> extrair() throws SQLException {
		List<Venda> vendas = new ArrayList<>();
		String sql = "SELECT id, produto, quantidade, preco_unitario FROM vendas_oltp WHERE data >= '2024-01-01'";

		// Uso do try-with-resources para fechar a conexão automaticamente
		try (Connection conn = DriverManager.getConnection(URL_OLTP, USER_OLTP, PASS_OLTP);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Venda venda = new Venda(
						rs.getInt("id"),
						rs.getString("produto"),
						rs.getInt("quantidade"),
						rs.getDouble("preco_unitario")
						);
				vendas.add(venda);
			}
		} // Conexão e recursos são fechados aqui
		return vendas;
	}

	// --- T: TRANSFORMAÇÃO (Transform) ---
	private static List<Venda> transformar(List<Venda> dados) {
		// Aplica a regra de negócio a cada registro
		for (Venda venda : dados) {
			venda.calcularValorTotal(); // Regra de Negócio: Quantidade * Preço Unitário
			// Aqui poderiam ser adicionadas outras regras, como:
			// - Padronizar nome de produtos (venda.setProduto(venda.getProduto().toUpperCase()))
			// - Limpar dados
		}
		return dados;
	}

	// --- L: CARGA (Load) ---
	private static void carregar(List<Venda> dados) throws SQLException {
		String sql = "INSERT INTO fatos_vendas_dw (id_venda, produto, quantidade, valor_total) VALUES (?, ?, ?, ?)";

		// Uso de PreparedStatement para evitar SQL Injection e melhorar performance
		try (Connection conn = DriverManager.getConnection(URL_DW, USER_DW, PASS_DW);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// Desabilita o Auto-commit para carregar todos em uma transação (melhora o desempenho)
			conn.setAutoCommit(false); 

			for (Venda venda : dados) {
				pstmt.setInt(1, venda.getId());
				pstmt.setString(2, venda.getProduto());
				pstmt.setInt(3, venda.getQuantidade());
				pstmt.setDouble(4, venda.getValorTotal());

				pstmt.addBatch(); // Adiciona ao lote de inserção
			}

			// Executa o lote (todas as inserções de uma vez)
			pstmt.executeBatch();

			// Confirma a transação
			conn.commit(); 

		} catch (SQLException e) {
			// Em caso de erro, a transação é desfeita (rollback)
			System.err.println("Erro durante a carga. Executando rollback...");
			throw e; 
		}
	}
}
