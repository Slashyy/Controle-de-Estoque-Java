package br.com.portfolio.estoque.model;

import java.math.BigDecimal;

public class Produto {
    private final int id;
    private String nome;
    private BigDecimal preco;
    private int quantidade;

    public Produto(int id, String nome, BigDecimal preco, int quantidade) {
        if (id <= 0) throw new IllegalArgumentException("ID deve ser positivo.");
        setNome(nome);
        setPreco(preco);
        setQuantidade(quantidade);
        this.id = id;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ficar vazio.");
        }
        this.nome = nome.trim();
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Informe uma quantidade positiva.");
        this.quantidade += quantidade;
    }

    public void retirarEstoque(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Informe uma quantidade positiva.");
        if (quantidade > this.quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + this.quantidade);
        }
        this.quantidade -= quantidade;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Produto: %s | Preço: R$ %s | Quantidade: %d",
                id, nome, preco.toPlainString(), quantidade);
    }
}
