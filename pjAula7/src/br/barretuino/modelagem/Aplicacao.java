package br.barretuino.modelagem;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aplicacao_financeira")
@SequenceGenerator(name = "SEQ_APLICACOES", sequenceName = "SEQ_APLICACOES", 
							initialValue = 1)
public class Aplicacao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_APLICACOES")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Conta conta;
	private String descricao;
	private Date data;
	private double saldo;
	private double remuneracao;
	private int duracaoAplicacao;
	
	@Enumerated(EnumType.ORDINAL)
	private Periodo periodoAplicacao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(double remuneracao) {
		this.remuneracao = remuneracao;
	}

	public int getDuracaoAplicacao() {
		return duracaoAplicacao;
	}

	public void setDuracaoAplicacao(int duracaoAplicacao) {
		this.duracaoAplicacao = duracaoAplicacao;
	}

	public Periodo getPeriodoAplicacao() {
		return periodoAplicacao;
	}

	public void setPeriodoAplicacao(Periodo periodoAplicacao) {
		this.periodoAplicacao = periodoAplicacao;
	}	
}