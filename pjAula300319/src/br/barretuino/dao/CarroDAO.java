package br.barretuino.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import br.barretuino.modelagem.Carro;
import br.barretuino.service.NegocioException;
import br.barretuino.util.EntityManagerProducer;

public class CarroDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager = new EntityManagerProducer().create();
	
	public Carro buscarPeloCodigo(Long codigo) {
		return manager.find(Carro.class, codigo);
	}
	
	public void salvar(Carro fabricante) {
		manager.merge(fabricante);
	}

	@SuppressWarnings("unchecked")
	public List<Carro> buscarTodos() {
		return manager.createQuery("from Carro").getResultList();
	}
	
	@Transactional
	public void excluir(Carro carro) throws NegocioException {
		carro = buscarPeloCodigo(carro.getCodigo());
		try {
			manager.remove(carro);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Carro não pode ser excluído.");
		}
	}
	
}
