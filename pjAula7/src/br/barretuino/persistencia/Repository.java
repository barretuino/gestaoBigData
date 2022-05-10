package br.barretuino.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.barretuino.modelagem.Aplicacao;

public class Repository {

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pjextensao");
	static EntityManager em = emf.createEntityManager();
	
	public static List<Object> buscar(String campo, String valor) {
		return em.createQuery("from Conta where " + campo + "='" + valor + "'")
						.getResultList();
	}
	
	public static Object buscar(Class<?> clazz, int id) {		
		return em.find(clazz, id);
	}
	
	public static List<Object> listar(Class<?> clazz) {		
		return (List<Object>) em.find(clazz, null);
	}

	public static void atualizar(Object obj) {
		em.getTransaction().begin();
		em.merge(obj);		
		em.getTransaction().commit();
	}
	
	public static void remover(Object obj) {
		em.getTransaction().begin();
		em.remove(obj);		
		em.getTransaction().commit();
	}

	public static void close() {
		em.close();
		emf.close();
	}
}