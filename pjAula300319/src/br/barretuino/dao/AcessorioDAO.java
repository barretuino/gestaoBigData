package br.barretuino.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.barretuino.modelagem.Acessorio;
import br.barretuino.service.NegocioException;
import br.barretuino.util.Transactional;

public class AcessorioDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public Acessorio buscarPeloCodigo(Long codigo) {
		return manager.find(Acessorio.class, codigo);
	}
	
	public void salvar(Acessorio fabricante) {
		manager.merge(fabricante);
	}

	public List<Acessorio> buscarTodos() {
		return manager.createQuery("from Acessorio").getResultList();
	}
	
	@Transactional
	public void excluir(Acessorio fabricante) throws NegocioException {
		fabricante = buscarPeloCodigo(fabricante.getCodigo());
		try {
			manager.remove(fabricante);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Acessorio não pode ser excluí­do.");
		}
	}
}
