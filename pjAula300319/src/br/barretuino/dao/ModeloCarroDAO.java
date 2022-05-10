package br.barretuino.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import br.barretuino.modelagem.ModeloCarro;
import br.barretuino.service.NegocioException;
import br.barretuino.util.EntityManagerProducer;

public class ModeloCarroDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager = new EntityManagerProducer().create();
	
	public ModeloCarro buscarPeloCodigo(Long codigo) {
		return manager.find(ModeloCarro.class, codigo);
	}
	
	public void salvar(ModeloCarro modeloCarro) {
		manager.merge(modeloCarro);
	}

	public List<ModeloCarro> buscarTodos() {
		return manager.createQuery("from ModeloCarro").getResultList();
	}
	
	@Transactional
	public void excluir(ModeloCarro modeloCarro) throws NegocioException {
		modeloCarro = buscarPeloCodigo(modeloCarro.getCodigo());
		try {
			manager.remove(modeloCarro);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Este modelo não pode ser excluído.");
		}
	}
	
}
