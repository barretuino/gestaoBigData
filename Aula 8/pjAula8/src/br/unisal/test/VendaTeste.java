package br.unisal.test;

import java.sql.SQLException;

import br.unisal.dao.VendaDAO;
import br.unisal.model.Venda;

public class VendaTeste {
	public static void main(String[] args) {
		VendaDAO dao = new VendaDAO();
		
		Venda venda = new Venda(null, 17, 150, 1_650);
		try {
			dao.incluir(venda);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
