package pjAula10;

import java.util.Objects;

//2. DIMENSÃO: Tempo (Exemplo de hierarquia: Ano -> Mês)
class Tempo {
	private int ano;
	private String mes;

	public Tempo(int ano, String mes) {
		this.ano = ano;
		this.mes = mes;
	}

	public int getAno() { 
		return ano; 
	}
	
	public String getMes() { 
		return mes; 
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tempo tempo = (Tempo) o;
		return ano == tempo.ano && Objects.equals(mes, tempo.mes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano, mes);
	}
}