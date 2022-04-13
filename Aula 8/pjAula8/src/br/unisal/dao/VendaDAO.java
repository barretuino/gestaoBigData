package br.unisal.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
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
		stm.setDate(1, new Date(2022,4,12));
		stm.setInt(2, venda.getProduto());
		stm.setDouble(3, venda.getQuantidade());
		stm.setDouble(4, venda.getValorVenda());
		
		stm.execute();
	}
}
