package br.barretuino.service;

import java.util.List;

import br.barretuino.modelagem.Fabricante;
import br.barretuino.modelagem.FabricanteVO;
import br.barretuino.modelagem.ModeloCarro;
import br.barretuino.repository.FabricanteRepository;

public class CadastroFabricanteService {

	//1º Passo - Repository
	private FabricanteRepository repositorio = new FabricanteRepository();
	
	//2º Passo - Ação de Persistência
	public void salvar(Fabricante fabricante)throws NegocioException {
		if(fabricante.getNome() == null 
				|| fabricante.getNome().trim().equals("")) {			
			throw new NegocioException("O nome do fabricante é " +
				"obrigatório.");
		}
		this.repositorio.inserir(fabricante);
	}

	public List<Fabricante> buscarTodos() {
		return repositorio.buscar();
	}

	public void close() {
		repositorio.close();		
	}

	public Fabricante busca(Long codigo) {
		return repositorio.buscar(codigo);
	}	
	
	public List<FabricanteVO> buscarVO(ModeloCarro marca){
		return repositorio.buscarVO(marca);
	}
}