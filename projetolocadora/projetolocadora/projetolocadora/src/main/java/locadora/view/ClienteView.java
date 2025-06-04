package locadora.view;

import locadora.controller.ClienteController;
import locadora.controller.FilmeController;
import locadora.controller.LocacaoController;
import locadora.model.Cliente;
import locadora.model.Filme;
import locadora.model.Locacao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClienteView {

    private final ClienteController clienteController = new ClienteController();
    private final FilmeController filmeController = new FilmeController();
    private final LocacaoController locacaoController = new LocacaoController();
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n======================================");
            System.out.println("         LOCADORA DE FILMES");
            System.out.println("======================================");
            System.out.println(" [1] Seção de Clientes");
            System.out.println(" [2] Seção de Filmes");
            System.out.println(" [3] Seção de Locações");
            System.out.println(" [0] Sair");
            System.out.print("Escolha uma seção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1 -> menuClientes();
                    case 2 -> menuFilmes();
                    case 3 -> menuLocacoes();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
            }
        } while (opcao != 0);

        fecharConexoes();
    }

    private void menuClientes() {
        int opcao;
        do {
            System.out.println("\n------ Seção de Clientes ------");
            System.out.println(" 1. Cadastrar Cliente");
            System.out.println(" 2. Atualizar Cliente");
            System.out.println(" 3. Deletar Cliente");
            System.out.println(" 4. Listar Clientes");
            System.out.println(" 5. Buscar Cliente por ID");
            System.out.println(" 0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> atualizarCliente();
                    case 3 -> deletarCliente();
                    case 4 -> listarClientes();
                    case 5 -> buscarClientePorId();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void menuFilmes() {
        int opcao;
        do {
            System.out.println("\n------ Seção de Filmes ------");
            System.out.println(" 1. Cadastrar Filme");
            System.out.println(" 2. Atualizar Filme");
            System.out.println(" 3. Deletar Filme");
            System.out.println(" 4. Listar Filmes");
            System.out.println(" 5. Buscar Filme por ID");
            System.out.println(" 0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1 -> cadastrarFilme();
                    case 2 -> atualizarFilme();
                    case 3 -> deletarFilme();
                    case 4 -> listarFilmes();
                    case 5 -> buscarFilmePorId();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void menuLocacoes() {
        int opcao;
        do {
            System.out.println("\n------ Seção de Locações ------");
            System.out.println(" 1. Realizar Locação");
            System.out.println(" 2. Atualizar Locação");
            System.out.println(" 3. Deletar Locação");
            System.out.println(" 4. Listar Locações");
            System.out.println(" 5. Buscar Locação por ID");
            System.out.println(" 6. Devolver Filme");
            System.out.println(" 0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1 -> realizarLocacao();
                    case 2 -> atualizarLocacao();
                    case 3 -> deletarLocacao();
                    case 4 -> listarLocacoes();
                    case 5 -> buscarLocacaoPorId();
                    case 6 -> devolverFilme();
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            clienteController.adicionarCliente(nome, cpf, telefone, endereco, email, 0);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        }
    }

    private void atualizarCliente() {
        try {
            System.out.print("ID do cliente: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            clienteController.atualizarCliente(nome, cpf, telefone, endereco, email, id);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void deletarCliente() {
        try {
            System.out.print("ID do cliente: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            clienteController.deletarCliente(id);
            System.out.println("Cliente deletado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteController.listarTodosClientes();
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(
                    cliente -> System.out.println("ID: " + cliente.getCpf() + " - Nome: " + cliente.getNome()));
        }
    }

    private void buscarClientePorId() {
        try {
            System.out.print("ID do cliente: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = clienteController.buscarClientePorId(id);
            if (cliente != null) {
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CPF: " + cliente.getCpf());
                System.out.println("Telefone: " + cliente.getTelefone());
                System.out.println("Endereço: " + cliente.getEndereco());
                System.out.println("E-mail: " + cliente.getEmail());
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void cadastrarFilme() {
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Gênero: ");
            String genero = scanner.nextLine();
            System.out.print("Ano de lançamento: ");
            int ano = scanner.nextInt();
            System.out.print("Duração (min): ");
            int duracao = scanner.nextInt();
            System.out.print("Disponível (true/false): ");
            boolean disponivel = scanner.nextBoolean();
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            filmeController.adicionarFilme(titulo, genero, ano, duracao, disponivel, id);
            System.out.println("Filme cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        }
    }

    private void atualizarFilme() {
        try {
            System.out.print("ID do filme: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Gênero: ");
            String genero = scanner.nextLine();
            System.out.print("Ano de lançamento: ");
            int ano = scanner.nextInt();
            System.out.print("Duração (min): ");
            int duracao = scanner.nextInt();
            System.out.print("Disponível (true/false): ");
            boolean disponivel = scanner.nextBoolean();
            scanner.nextLine();
            filmeController.atualizarFilme(titulo, genero, ano, duracao, disponivel, id);
            System.out.println("Filme atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        }
    }

    private void deletarFilme() {
        try {
            System.out.print("ID do filme: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            filmeController.deletarFilme(id);
            System.out.println("Filme deletado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void listarFilmes() {
        List<Filme> filmes = filmeController.listarTodosFilmes();
        if (filmes == null || filmes.isEmpty()) {
            System.out.println("Nenhum filme cadastrado.");
        } else {
            filmes.forEach(filme -> System.out.println("ID: " + filme.getId() + " - Título: " + filme.getTitulo()
                    + " - Disponível: " + filme.isDisponivel()));
        }
    }

    private void buscarFilmePorId() {
        try {
            System.out.print("ID do filme: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Filme filme = filmeController.buscarFilmePorId(id);
            if (filme != null) {
                System.out.println("Título: " + filme.getTitulo());
                System.out.println("Gênero: " + filme.getGenero());
                System.out.println("Ano: " + filme.getAnoLancamento());
                System.out.println("Duração: " + filme.getDuracao());
                System.out.println("Disponível: " + filme.isDisponivel());
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void realizarLocacao() {
        try {
            System.out.print("ID do cliente: ");
            int clienteId = scanner.nextInt();
            System.out.print("ID do filme: ");
            int filmeId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Data aluguel (AAAA-MM-DD): ");
            String dataAluguel = scanner.nextLine();
            System.out.print("Data devolução (AAAA-MM-DD): ");
            String dataDevolucao = scanner.nextLine();
            System.out.print("Valor: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Status (Alugado/Devolvido): ");
            String status = scanner.nextLine();
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            locacaoController.adicionarLocacao(clienteId, filmeId, dataAluguel, dataDevolucao, valor, status, id);
            System.out.println("Parabéns, filme alugado, aproveite!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        }
    }

    private void atualizarLocacao() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            System.out.print("ID do cliente: ");
            int clienteId = scanner.nextInt();
            System.out.print("ID do filme: ");
            int filmeId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Data aluguel (AAAA-MM-DD): ");
            String dataAluguel = scanner.nextLine();
            System.out.print("Data devolução (AAAA-MM-DD): ");
            String dataDevolucao = scanner.nextLine();
            System.out.print("Valor: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Status (Alugado/Devolvido): ");
            String status = scanner.nextLine();
            locacaoController.atualizarLocacao(clienteId, filmeId, dataAluguel, dataDevolucao, valor, status, id);
            System.out.println("Alterações de locação feitas com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        }
    }

    private void deletarLocacao() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            locacaoController.deletarLocacao(id);
            System.out.println("Locação deletada com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void listarLocacoes() {
        List<Locacao> locacoes = locacaoController.listarTodasLocacoes();
        if (locacoes == null || locacoes.isEmpty()) {
            System.out.println("Nenhuma locação cadastrada.");
        } else {
            locacoes.forEach(locacao -> System.out
                    .println("ID: " + locacao.getId() + " - Cliente: " + locacao.getCliente().getNome() + " - Filme: "
                            + locacao.getFilme().getTitulo() + " - Status: " + locacao.getStatus()));
        }
    }

    private void buscarLocacaoPorId() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Locacao locacao = locacaoController.buscarLocacaoPorId(id);
            if (locacao != null) {
                System.out.println("Cliente: " + locacao.getCliente().getNome());
                System.out.println("Filme: " + locacao.getFilme().getTitulo());
                System.out.println("Data aluguel: " + locacao.getDataAluguel());
                System.out.println("Data devolução: " + locacao.getDataDevolucao());
                System.out.println("Valor: " + locacao.getValor());
                System.out.println("Status: " + locacao.getStatus());
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void devolverFilme() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Locacao locacao = locacaoController.buscarLocacaoPorId(id);
            if (locacao != null && "Alugado".equalsIgnoreCase(locacao.getStatus())) {
                locacao.devolver();
                locacaoController.atualizarLocacao(
                        locacao.getCliente().getId(),
                        locacao.getFilme().getId(),
                        locacao.getDataAluguel(),
                        locacao.getDataDevolucao(),
                        locacao.getValor(),
                        locacao.getStatus(),
                        locacao.getId());
                locacaoController.registrarDevolucao(locacao);
                System.out.println("Filme devolvido com sucesso.");
            } else {
                System.out.println("Locação não encontrada ou já devolvida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private void fecharConexoes() {
        clienteController.fecharConexao();
        filmeController.fecharConexao();
        locacaoController.fecharConexao();
        scanner.close();
    }
}