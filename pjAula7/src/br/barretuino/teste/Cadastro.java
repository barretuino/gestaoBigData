package br.barretuino.teste;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.barretuino.modelagem.Aplicacao;
import br.barretuino.modelagem.Conta;
import br.barretuino.modelagem.Periodo;
import br.barretuino.persistencia.Repository;

public class Cadastro {
	public static void main(String[] args) {
		Conta conta = new Conta();
		List<Aplicacao> aplicacoes = new ArrayList<Aplicacao>();
		
		conta.setTitular("Luiz Henrique");
		conta.setBanco("Santander");
		conta.setAgencia("0090");
		conta.setNumero("123456");
		
		Aplicacao ap1 = new Aplicacao();
		ap1.setDescricao("Aplicação em Boi Gordo");
		ap1.setData(new Date(119, 2, 1));
		ap1.setDuracaoAplicacao(12);
		ap1.setPeriodoAplicacao(Periodo.MENSAL);
		ap1.setRemuneracao(11.50d);
		ap1.setSaldo(33_000d);
		ap1.setConta(conta);
		
		Aplicacao ap2 = new Aplicacao();
		ap2.setDescricao("Créditos da Divida Externa");
		ap2.setData(new Date(115, 11, 25));
		ap2.setDuracaoAplicacao(10);
		ap2.setPeriodoAplicacao(Periodo.ANUAL);
		ap2.setRemuneracao(4.0d);
		ap2.setSaldo(100_000d);
		ap2.setConta(conta);
		
		Aplicacao ap3 = new Aplicacao();
		ap3.setDescricao("Lú");
		ap3.setData(new Date(119, 2, 30));
		ap3.setDuracaoAplicacao(14);
		ap3.setPeriodoAplicacao(Periodo.SEMANAL);
		ap3.setRemuneracao(-10d);
		ap3.setSaldo(1_000d);
		ap3.setConta(conta);
		
		aplicacoes.add(ap1);
		aplicacoes.add(ap2);
		aplicacoes.add(ap3);
		
		conta.setAplicacoes(aplicacoes);
		
		/**
		 * Persistência propriamente dita
		 */
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pjextensao");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(conta);
		em.getTransaction().commit();
		System.out.println("Conta inserida com sucesso!");
		
		em.close();
		emf.close();
		
		/** Exemplo de Pesquisa */
		Conta pesquisa = (Conta) Repository.buscar(Conta.class, 52);
		System.out.println(pesquisa.getTitular());
		for(Aplicacao a : pesquisa.getAplicacoes()) {
			System.out.println("ID " + a.getId() + " - " + a.getDescricao());
		}		
		
		/** Exemplo de Atualização */
		Aplicacao apA = (Aplicacao) Repository.buscar(Aplicacao.class, 52);
		apA.setDescricao("Nova Descrição");
		Repository.atualizar(apA);
				
		System.out.println(((Aplicacao)Repository.buscar(Aplicacao.class, 52)).getDescricao());
		
		//Repository.remover((Conta)Repository.buscar(Conta.class, 52));
		
		List<Object> contaA = Repository.buscar("titular", "Luiz Henrique");
		System.out.println(contaA.size());
		for(Object a: contaA) {
			System.out.println(((Conta)a).getAplicacoes().size());
		}
		
		Repository.close();
	}
}