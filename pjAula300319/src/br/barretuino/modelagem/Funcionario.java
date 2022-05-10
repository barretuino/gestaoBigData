package br.barretuino.modelagem;

import javax.persistence.Entity;

@Entity
public class Funcionario extends Pessoa {

	private String matricula;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
