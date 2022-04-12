package br.com.anhanguera.jdbc;

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
					"jdbc:mysql://localhost/unisal",
					"paulo","12345679");
		}catch(ClassNotFoundException erro){
			throw new SQLException(erro.getMessage());
		}
	}
}
