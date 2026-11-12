package br.com.portfolio.estoque.repository;

import br.com.portfolio.estoque.model.Produto;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private final Path arquivo = Path.of("data", "produtos.csv");

    public List<Produto> carregar() throws IOException {
        criarArquivoSeNecessario();
        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        List<Produto> produtos = new ArrayList<>();

        for (int i = 1; i < linhas.size(); i++) {
            String linha = linhas.get(i).trim();
            if (linha.isEmpty()) continue;

            String[] campos = linha.split(";", -1);
            if (campos.length != 4) continue;

            try {
                int id = Integer.parseInt(campos[0]);
                String nome = campos[1];
                BigDecimal preco = new BigDecimal(campos[2]);
                int quantidade = Integer.parseInt(campos[3]);
                produtos.add(new Produto(id, nome, preco, quantidade));
            } catch (RuntimeException e) {
                System.err.println("Linha ignorada no arquivo CSV: " + linha);
            }
        }
        return produtos;
    }

    public void salvar(List<Produto> produtos) throws IOException {
        criarArquivoSeNecessario();
        List<String> linhas = new ArrayList<>();
        linhas.add("id;nome;preco;quantidade");

        for (Produto p : produtos) {
            String nomeSeguro = p.getNome().replace(";", ",").replace("\n", " ");
            linhas.add(p.getId() + ";" + nomeSeguro + ";" +
                    p.getPreco().toPlainString() + ";" + p.getQuantidade());
        }
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    private void criarArquivoSeNecessario() throws IOException {
        Files.createDirectories(arquivo.getParent());
        if (Files.notExists(arquivo)) {
            Files.writeString(arquivo, "id;nome;preco;quantidade\n", StandardCharsets.UTF_8);
        }
    }
}
