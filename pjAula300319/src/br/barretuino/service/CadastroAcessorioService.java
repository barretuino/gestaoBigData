package br.barretuino.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import br.barretuino.dao.AcessorioDAO;
import br.barretuino.modelagem.Acessorio;

public class CadastroAcessorioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AcessorioDAO acessorioDAO;
	
	@Transactional
	public void salvar(Acessorio acessorio) throws NegocioException {
		
		if (acessorio.getDescricao() == null || acessorio.getDescricao().trim().equals("")) {
			throw new NegocioException("A descrição do acessório é obrigatório");
		}
		
		this.acessorioDAO.salvar(acessorio);
	}

}
