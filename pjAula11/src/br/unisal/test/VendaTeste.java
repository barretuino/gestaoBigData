package br.unisal.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unisal.dao.VendaDAO;
import br.unisal.model.Venda;

public class VendaTeste {
	public static void main(String[] args) throws InterruptedException {
		VendaDAO dao = new VendaDAO();
		
		try {
			//dao.pesquisa();
			
			ResultSet dados = dao.pesquisaFull();
			//Opção com mapas
			/*Map<Integer, Venda> resumo = new HashMap<Integer, Venda>();
			
			while(dados.next()) {
				if(resumo.containsKey(dados.getInt(1))) {
					Venda venda = resumo.get(dados.getInt(1));
					venda.setQuantidade(venda.getQuantidade() 
							+  dados.getDouble(3));
					venda.setValorVenda(venda.getValorVenda() 
									+  dados.getDouble(4));
					resumo.put(dados.getInt(1), venda); 
				}else {
					Venda venda = new Venda(dados.getDate(1), 
										    dados.getInt(2), 
										    dados.getDouble(3), 
										    dados.getDouble(4));
					resumo.put(dados.getInt(1), venda);
				}
			}*/
			
			
			//Opção com Stream
			long inicio = System.currentTimeMillis();
			List<Venda> lista = new ArrayList<Venda>();
			while(dados.next()) {
				Venda venda = new Venda(dados.getDate(1), 
					    dados.getInt(2), 
					    dados.getDouble(3), 
					    dados.getDouble(4));
				lista.add(venda);
			}
			double vendas = 0d;
			double quantidade = 0d;
			for(Venda v : lista) {
				if(v.getQuantidade() > 0) {
					vendas += v.getValorVenda();
					quantidade += v.getQuantidade();
				}
			}
			System.out.println("Vendas " + vendas);
			System.out.println("Quantidade " + quantidade);
			System.out.println("Pesquisa demorou " 
					+ ((System.currentTimeMillis() - inicio) / 1000) + " segundos");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		//Inserção Unitizada
//		Venda venda = new Venda(new Date(118,4,1), 17, 150, 1_650);
//		
//		//Inserção em Massa
//		long nElementos = 1_000_000;
//		for(int mes=1; mes<=12; mes++) {
//			for(int i=0; i<nElementos; i++) {
//				Random r = new Random();
//				int dia = r.nextInt(31);
//				Date data = new Date(118, mes, dia);
//				Venda v = new Venda(data, 77866, 
//						r.nextInt()/-10_000_000, 
//						r.nextDouble()*1000);
//				try {
//					dao.incluir(v);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		try {
//			dao.incluir(venda);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
}
