package br.barretuino.consulta;

import java.util.List;

import javax.persistence.EntityManager;

import br.barretuino.util.EntityManagerProducer;

public class ConsultaFabricantes {

	public static void main(String[] args) {		 
		EntityManager em = new EntityManagerProducer().create();
		
		List<String> nomesDosFabricantes = em.createQuery("select f.nome from Fabricante f", String.class)
											.getResultList();
		
		for (String nome : nomesDosFabricantes) {
			System.out.println("Nome: " + nome);
		}
		
		em.close();
	}
	
}
