package br.barretuino.service;

public class NegocioException extends Exception{
	private static final long serialVersionUID = -1823583754386279498L;

	public NegocioException(String mensagem) {
		super("[REGRA]: " + mensagem);
	}
}