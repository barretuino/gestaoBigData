package br.barretuino.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import br.barretuino.modelagem.Fabricante;
import br.barretuino.modelagem.FabricanteVO;
import br.barretuino.modelagem.ModeloCarro;

public class FabricanteRepository {
	//1� Passo - Estabelecer a nossa Entity Manager Factory
	private EntityManagerFactory emf =
			Persistence.createEntityManagerFactory("pjextensao");
	
	//2� Passo - Manager que ser� a conex�o
	private EntityManager em = emf.createEntityManager();
	
	//3� Passo - Comandos de SQL
	public void inserir(Fabricante fabricante) {
		em.getTransaction().begin();
		em.persist(fabricante);
		em.getTransaction().commit();
	}
	
	//4� Passo - Close
	public void close() {
		em.close();
		emf.close();
	}
	
	//M�todo para realizar a busca de uma lista
	public List<Fabricante> buscar(){
		return em.createQuery("from Fabricante").getResultList();
	}
	public Fabricante buscar(Long codigo) {
		return em.find(Fabricante.class, codigo);
	}
	
	//M�todo para executar Stored Procedure
	public String executaProcedure() {
		//1� Passo - chamada da Procedure
		StoredProcedureQuery spq = 
				em.createStoredProcedureQuery("analisarPedido");
		
		//2� Passo - parametrizar a procedure para a execu��o
		spq.registerStoredProcedureParameter(
				"vCodigo", Integer.class, ParameterMode.IN);
		spq.setParameter("vCodigo", 3);
		
		//3� Passo - recep��o da sa�da de execu��o da procedure
		spq.registerStoredProcedureParameter(
				"vAnalise", String.class, ParameterMode.OUT);
		
		//4� Passo - A execu��o
		spq.execute();
		
		//5� Passo - Receber o retorno desta execu��o do passo 4�
		return (String) spq.getOutputParameterValue("vAnalise");		
	}
	
	public List<FabricanteVO> buscarVO(ModeloCarro marca){
		return em.createQuery(
				"select new br.barretuino.modelagem.FabricanteVO("
						+ "	f.id, f.nome, m.descricao, c.placa )"
				+ " from "
					+ "	Fabricante f, ModeloCarro m, Carro c "
				+ " where "
					+ "	m.fabricante = f.codigo and c.modelo = m.id "
				+ "order "
					+ " by f.id").getResultList();
	}
}
