package br.barretuino.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import br.barretuino.dao.AluguelDAO;
import br.barretuino.modelagem.Aluguel;

public class CadastroAluguelService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AluguelDAO aluguelDAO;
	
	@Transactional
	public void salvar(Aluguel aluguel) throws NegocioException {
		
		if (aluguel.getCarro() == null) {
			throw new NegocioException("O carro é obrigatório");
		}
		
		this.aluguelDAO.salvar(aluguel);
	}
}