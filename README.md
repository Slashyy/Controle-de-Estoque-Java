# Controle de Estoque Java

Aplicação de console desenvolvida em Java para controlar produtos e movimentações básicas de estoque. O projeto utiliza Programação Orientada a Objetos e salva os dados em um arquivo CSV, mantendo os produtos cadastrados mesmo depois de fechar o programa.

## Funcionalidades

- Cadastrar produtos
- Listar produtos cadastrados
- Buscar produto pelo ID
- Atualizar nome, preço e quantidade
- Remover produtos
- Registrar entrada e saída de estoque
- Exibir produtos com estoque baixo
- Salvar os dados automaticamente em `data/produtos.csv`

## Tecnologias e conceitos

- Java 17
- Maven
- Programação Orientada a Objetos
- `ArrayList` e `BigDecimal`
- Leitura e escrita de arquivos
- Tratamento de exceções e validação de dados

## Como executar

### Pré-requisitos
- JDK 17 ou superior
- Maven (ou uma IDE como IntelliJ IDEA)

### IntelliJ IDEA
1. Extraia o projeto.
2. Abra a pasta `ControleEstoqueJava` no IntelliJ.
3. Aguarde o Maven carregar o projeto.
4. Execute a classe `br.com.portfolio.estoque.Main`.

### Terminal
Na pasta do projeto, execute:

```bash
mvn clean compile
java -cp target/classes br.com.portfolio.estoque.Main
```

No Windows, esse comando também funciona no Prompt de Comando ou PowerShell, desde que o JDK e o Maven estejam configurados no PATH.

## Organização

```text
ControleEstoqueJava/
├── data/
│   └── produtos.csv
├── src/main/java/br/com/portfolio/estoque/
│   ├── Main.java
│   ├── model/
│   │   └── Produto.java
│   └── service/
│       └── EstoqueService.java
├── .gitignore
├── pom.xml
└── README.md
```

## Próximas melhorias

- Criar testes automatizados com JUnit
- Substituir o arquivo CSV por banco de dados MySQL
- Criar uma API REST com Spring Boot
- Desenvolver uma interface gráfica ou web

## Autor

Projeto pessoal feito por João Zaminelli.
