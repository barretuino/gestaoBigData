package pjAula8;

import java.math.BigDecimal;
import java.sql.Date;

public class Produto {

    private int id;
    private String nome;
    private String categoria;
    private Date dataCadastro;
    private BigDecimal preco;

    public Produto() {
    }

    public Produto(int id, String nome, String categoria, Date dataCadastro, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.dataCadastro = dataCadastro;
        this.preco = preco;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", preco=" + preco +
                '}';
    }
}