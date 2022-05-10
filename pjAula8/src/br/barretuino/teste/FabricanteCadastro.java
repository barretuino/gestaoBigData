package br.barretuino.teste;

import java.util.List;

import br.barretuino.modelagem.Fabricante;
import br.barretuino.modelagem.FabricanteVO;
import br.barretuino.modelagem.ModeloCarro;
import br.barretuino.service.CadastroFabricanteService;

public class FabricanteCadastro {
	public static void main(String args[]) {
		//1� Passo - Estabelecer uma instancia para servi�o
		CadastroFabricanteService service = new CadastroFabricanteService();
		
		//2� Passo - Constru��o dos elementos � serem persistidos
		Fabricante fabricante1 = new Fabricante();
		Fabricante fabricante2 = new Fabricante();
		Fabricante fabricante3 = new Fabricante();
		Fabricante fabricante4 = new Fabricante();
		Fabricante fabricante5 = new Fabricante();
		Fabricante fabricante6 = new Fabricante();
		
		fabricante1.setNome("Continental");		
		fabricante2.setNome("Panamericana");		
		fabricante3.setNome("Suspens�es Fox");		
		fabricante4.setNome("Delphi");		
		fabricante5.setNome("Turine");		
		fabricante6.setNome("  ");
		
		//3� Passo - Execu��o do Servi�o (posterior chamada a reposit�rio)
		/*try {
			service.salvar(fabricante1);
			service.salvar(fabricante2);
			service.salvar(fabricante3);
			service.salvar(fabricante4);
			service.salvar(fabricante5);
			service.salvar(fabricante6);
		}catch(NegocioException e) {
			System.err.println(e);
		}*/
		
		//4� Passo - Busca de todas as entidades de Fabricante no SGBD
		for(Fabricante fabricante : service.buscarTodos()) {
			System.out.println("Codigo: " + fabricante.getCodigo() 
							+ " Nome: " + fabricante.getNome());
		}
		
		//5� Passo Simples - Busca de um elemento na SGBD
		Fabricante fabricante = service.busca(2L);
		System.out.println("Dados do Fabricante - C�digo " 
					+ fabricante.getCodigo() 
					+ " Nome " + fabricante.getNome());
		
		
		//Testar uma chamada para um m�to que retorna um VO
		List<FabricanteVO> lista = service.buscarVO(new ModeloCarro());
		
		service.close();
	}
}