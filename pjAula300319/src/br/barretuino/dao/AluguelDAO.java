package br.barretuino.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import br.barretuino.modelagem.Aluguel;

public class AluguelDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	private ApoliceSeguroDAO apoliceSeguroDAO;
	
	public void salvar(Aluguel aluguel) {
		apoliceSeguroDAO.salvar(aluguel.getApoliceSeguro());
		
		manager.merge(aluguel);
	}

}
