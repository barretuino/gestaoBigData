package br.barretuino.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import br.barretuino.modelagem.ApoliceSeguro;

public class ApoliceSeguroDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public void salvar(ApoliceSeguro apoliceSeguro) {
		manager.persist(apoliceSeguro);
	}

}
