package br.barretuino.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import br.barretuino.dao.CarroDAO;
import br.barretuino.modelagem.Carro;

public class CadastroCarroService implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private CarroDAO carroDAO;
	
	@Transactional
	public void salvar(Carro carro) throws NegocioException {
		
		if (carro.getPlaca() == null || carro.getPlaca().trim().equals("")) {
			throw new NegocioException("A placa é obrigatória");
		}
		
		this.carroDAO.salvar(carro);
	}

}
