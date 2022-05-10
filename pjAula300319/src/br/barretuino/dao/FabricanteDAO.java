package br.barretuino.dao;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import br.barretuino.modelagem.Fabricante;
import br.barretuino.service.NegocioException;
import br.barretuino.util.EntityManagerProducer;

public class FabricanteDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager manager = EntityManagerProducer.create();
	
	public void close() {		
		EntityManagerProducer.close(manager);		
	}
	
	public void insert(Fabricante fabricante) {		
		manager.getTransaction().begin();		
		manager.persist(fabricante);				
		manager.getTransaction().commit();		
	}
	
	public void update(Fabricante fabricante) {
		manager.merge(fabricante);
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarTodos() {
		return manager.createQuery("from Fabricante").getResultList();
	}

	@Transactional
	public void excluir(Fabricante fabricante) throws NegocioException {
		Fabricante fabricanteTemp = manager.find(Fabricante.class, fabricante.getCodigo());
		
		manager.remove(fabricanteTemp);
		manager.flush();
	}

	public Fabricante buscarPeloCodigo(Long codigo) {
		return manager.find(Fabricante.class, codigo);
	}
	
}
