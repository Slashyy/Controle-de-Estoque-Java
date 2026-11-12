package br.com.portfolio.estoque.service;

import br.com.portfolio.estoque.model.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class EstoqueService {
    private final List<Produto> produtos = new ArrayList<>();

    public void carregarProdutos(List<Produto> produtosSalvos) {
        produtos.clear();
        produtos.addAll(produtosSalvos);
    }

    public List<Produto> listarProdutos() {
        return produtos.stream()
                .sorted(Comparator.comparingInt(Produto::getId))
                .toList();
    }

    public Produto cadastrarProduto(String nome, BigDecimal preco, int quantidade) {
        int proximoId = produtos.stream()
                .mapToInt(Produto::getId)
                .max()
                .orElse(0) + 1;

        Produto produto = new Produto(proximoId, nome, preco, quantidade);
        produtos.add(produto);
        return produto;
    }

    public Optional<Produto> buscarPorId(int id) {
        return produtos.stream().filter(p -> p.getId() == id).findFirst();
    }

    public void atualizarProduto(int id, String nome, BigDecimal preco, int quantidade) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setQuantidade(quantidade);
    }

    public void removerProduto(int id) {
        Produto produto = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        produtos.remove(produto);
    }

    public void entradaEstoque(int id, int quantidade) {
        buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."))
                .adicionarEstoque(quantidade);
    }

    public void saidaEstoque(int id, int quantidade) {
        buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."))
                .retirarEstoque(quantidade);
    }

    public List<Produto> listarEstoqueBaixo(int limite) {
        return produtos.stream()
                .filter(p -> p.getQuantidade() <= limite)
                .sorted(Comparator.comparingInt(Produto::getQuantidade))
                .toList();
    }
}
