package br.unisal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.unisal.model.Venda;

/**
 * Classe de DAO - Atividades de Objetos de Banco
 * @author Paulo Barreto
 * @data 12/04/2022
 */

public class VendaDAO {
	private Connection conn;
	public VendaDAO() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Método de Inclusão de Itens no SGBD
	public void incluir(Venda venda) throws SQLException {
		String sql = "insert into vendas values(?,?,?,?)";
		PreparedStatement stm = 
				this.conn.prepareStatement(sql);
		
		stm.setDate(1, venda.getData());
		stm.setInt(2, venda.getProduto());
		stm.setDouble(3, venda.getQuantidade());
		stm.setDouble(4, venda.getValorVenda());
		
		stm.execute();
	}
	
	public void pesquisa() throws SQLException {
		String sql = "select produto, data, "
				+ " sum(quantidade), sum(valor_venda)"
				+ " from vendas "
				+ " where quantidade > 0"
				+ " group by produto, data";
		PreparedStatement stm = 
				this.conn.prepareStatement(sql);
		
		long inicio = System.currentTimeMillis(); 
		ResultSet rs = stm.executeQuery();
		
		while(rs.next()) {			
			System.out.print("Produto " + rs.getInt(1));
			System.out.print("Data " + rs.getDate(2));
			System.out.print(" Quantidade " + rs.getDouble(3));
			System.out.println(" Valor de Venda " + rs.getDouble(4));
		}
		
		System.out.println("Pesquisa demorou " 
				+ ((System.currentTimeMillis() - inicio) / 1000) + " segundos"); 
	}
	
	public ResultSet pesquisaFull() throws SQLException {
		String sql = "select * from vendas";
		PreparedStatement stm = 
				this.conn.prepareStatement(sql);		
		return stm.executeQuery();
	}
}
