package br.barretuino.consulta;

import java.util.List;

import javax.persistence.EntityManager;

import br.barretuino.util.EntityManagerProducer;

public class ConsultaModeloFiltroEmFabricanteECategoria {

	public static void main(String[] args) {
		EntityManager em = new EntityManagerProducer().create();

		List<String> modelos = em.createQuery("select mc.descricao from ModeloCarro mc "
				+ "where mc.fabricante.nome = 'Honda' "
				+ "and mc.categoria in ('SEDAN_MEDIO','SEDAN_GRANDE')"
								, String.class).getResultList();
		
		for (String modelo : modelos) {
			System.out.println(modelo);
		}
		
		System.out.println("-----------------------------------------");
		
		modelos = em.createQuery("select mc.descricao from ModeloCarro mc "
				+ "where mc.fabricante.nome = 'Honda' "
				+ "and mc.categoria like 'HATCH%'"
								, String.class).getResultList();

		for (String modelo : modelos) {
			System.out.println(modelo);
		}
		
		em.close();
	}
	
}
