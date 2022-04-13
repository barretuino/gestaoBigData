package br.unisal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de Conexão com o Banco de Dados
 * @author Prof. Paulo Barreto
 * @date 12/04/2022
 */
public class ConnectionFactory {
	public static Connection getConnection() 
						throws SQLException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return
				DriverManager.getConnection(
					"jdbc:mysql://192.168.30.45/test",
					"andre","12345679");
		}catch(ClassNotFoundException erro){
			throw new SQLException(erro.getMessage());
		}
	}
	
	public static void main(String[] args) {
		try {
			ConnectionFactory.getConnection();
			System.out.println("Conexão Realizada com Sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}