package br.barretuino.modelagem;

import javax.persistence.Entity;

@Entity
public class Motorista extends Pessoa {

	private String numeroCNH;

	public String getNumeroCNH() {
		return numeroCNH;
	}
	public void setNumeroCNH(String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}
	
}
