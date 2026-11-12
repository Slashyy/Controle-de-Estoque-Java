package br.com.portfolio.estoque;

import br.com.portfolio.estoque.model.Produto;
import br.com.portfolio.estoque.repository.ProdutoRepository;
import br.com.portfolio.estoque.service.EstoqueService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EstoqueService estoque = new EstoqueService();
    private static final ProdutoRepository repository = new ProdutoRepository();

    public static void main(String[] args) {
        try {
            estoque.carregarProdutos(repository.carregar());
            System.out.println("Bem-vindo ao Controle de Estoque Java!");

            boolean executando = true;
            while (executando) {
                mostrarMenu();
                int opcao = lerInteiro("Escolha uma opção: ");

                try {
                    switch (opcao) {
                        case 1 -> cadastrar();
                        case 2 -> listar();
                        case 3 -> buscar();
                        case 4 -> atualizar();
                        case 5 -> remover();
                        case 6 -> movimentarEstoque(true);
                        case 7 -> movimentarEstoque(false);
                        case 8 -> estoqueBaixo();
                        case 0 -> executando = false;
                        default -> System.out.println("Opção inválida.");
                    }
                    salvarDados();
                } catch (IllegalArgumentException e) {
                    System.out.println("Não foi possível concluir: " + e.getMessage());
                }
            }
            System.out.println("Sistema encerrado. Até mais!");
        } catch (IOException e) {
            System.err.println("Erro ao acessar os dados: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n========== CONTROLE DE ESTOQUE ==========");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Buscar produto por ID");
        System.out.println("4 - Atualizar produto");
        System.out.println("5 - Remover produto");
        System.out.println("6 - Registrar entrada de estoque");
        System.out.println("7 - Registrar saída de estoque");
        System.out.println("8 - Produtos com estoque baixo");
        System.out.println("0 - Sair");
        System.out.println("=========================================");
    }

    private static void cadastrar() {
        System.out.println("\n--- Cadastro de produto ---");
        String nome = lerTexto("Nome: ");
        BigDecimal preco = lerPreco("Preço (ex.: 19,90): ");
        int quantidade = lerInteiro("Quantidade inicial: ");
        Produto produto = estoque.cadastrarProduto(nome, preco, quantidade);
        System.out.println("Produto cadastrado! ID: " + produto.getId());
    }

    private static void listar() {
        List<Produto> produtos = estoque.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\n--- Produtos cadastrados ---");
        produtos.forEach(System.out::println);
    }

    private static void buscar() {
        int id = lerInteiro("Informe o ID: ");
        Produto produto = estoque.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        System.out.println(produto);
    }

    private static void atualizar() {
        int id = lerInteiro("ID do produto: ");
        String nome = lerTexto("Novo nome: ");
        BigDecimal preco = lerPreco("Novo preço: ");
        int quantidade = lerInteiro("Nova quantidade: ");
        estoque.atualizarProduto(id, nome, preco, quantidade);
        System.out.println("Produto atualizado com sucesso.");
    }

    private static void remover() {
        int id = lerInteiro("ID do produto que deseja remover: ");
        estoque.removerProduto(id);
        System.out.println("Produto removido com sucesso.");
    }

    private static void movimentarEstoque(boolean entrada) {
        int id = lerInteiro("ID do produto: ");
        int quantidade = lerInteiro("Quantidade: ");
        if (entrada) {
            estoque.entradaEstoque(id, quantidade);
            System.out.println("Entrada registrada.");
        } else {
            estoque.saidaEstoque(id, quantidade);
            System.out.println("Saída registrada.");
        }
    }

    private static void estoqueBaixo() {
        int limite = lerInteiro("Mostrar produtos com quantidade igual ou menor que: ");
        List<Produto> produtos = estoque.listarEstoqueBaixo(limite);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto nessa condição.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static BigDecimal lerPreco(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                String valor = scanner.nextLine().trim().replace(",", ".");
                return new BigDecimal(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um preço válido. Exemplo: 19,90");
            }
        }
    }

    private static void salvarDados() {
        try {
            repository.salvar(estoque.listarProdutos());
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar os dados: " + e.getMessage());
        }
    }
}
