package br.barretuino.consulta;

import java.util.List;

import javax.persistence.EntityManager;

import br.barretuino.util.EntityManagerProducer;

public class ConsultaFabricantesPeloModeloCarro {

	public static void main(String[] args) {		
		EntityManager em = new EntityManagerProducer().create();

		List<String> nomeDosFabricantes = em.createQuery("select mc.fabricante.nome from ModeloCarro mc", String.class)
												.getResultList();
		
		for (String nomeFabricante : nomeDosFabricantes) {
			System.out.println("Nome: " + nomeFabricante);
		}
		
		em.close();
	}
	
}
