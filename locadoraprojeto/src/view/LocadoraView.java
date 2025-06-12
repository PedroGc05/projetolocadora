package view;

import controller.ClienteController;
import controller.FilmeController;
import controller.LocacaoController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Cliente;
import model.Filme;
import model.Locacao;

public class LocadoraView {

    private final ClienteController clienteController = new ClienteController();
    private final FilmeController filmeController = new FilmeController();
    private final LocacaoController locacaoController = new LocacaoController();
    private final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n======================================");
            System.out.println("        LOCADORA DE FILMES");
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
            clienteController.adicionarCliente(nome, cpf, telefone, endereco, email);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
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
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
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
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        try {
            List<Cliente> clientes = clienteController.listarTodosClientes();
            if (clientes == null || clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
            } else {
                System.out.println("\n----- Lista de Clientes -----");
                clientes.forEach(
                        cliente -> System.out.println("ID: " + cliente.getId() + " - Nome: " + cliente.getNome()));
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    private void buscarClientePorId() {
        try {
            System.out.print("ID do cliente: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = clienteController.buscarClientePorId(id);
            if (cliente != null) {
                System.out.println("\n----- Detalhes do Cliente -----");
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("CPF: " + cliente.getCpf());
                System.out.println("Telefone: " + cliente.getTelefone());
                System.out.println("Endereço: " + cliente.getEndereco());
                System.out.println("E-mail: " + cliente.getEmail());
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
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
            scanner.nextLine();
            System.out.print("Disponível (S/N): ");
            String disponivel = scanner.nextLine();
            boolean disponivelBool = disponivel.equalsIgnoreCase("S");
            filmeController.adicionarFilme(titulo, genero, ano, duracao, disponivelBool);
            System.out.println("Filme cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
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
            scanner.nextLine();
            System.out.print("Disponível (S/N): ");
            String disponivel = scanner.nextLine();
            boolean disponivelBool = disponivel.equalsIgnoreCase("S");
            filmeController.atualizarFilme(titulo, genero, ano, duracao, disponivelBool, id);
            System.out.println("Filme atualizado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
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
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarFilmes() {
        try {
            List<Filme> filmes = filmeController.listarTodosFilmes();
            if (filmes == null || filmes.isEmpty()) {
                System.out.println("Nenhum filme cadastrado.");
            } else {
                System.out.println("\n----- Lista de Filmes -----");
                filmes.forEach(filme -> System.out.println("ID: " + filme.getId() + " - Título: " + filme.getTitulo()
                        + " - Disponível: " + (filme.isDisponivel() ? "Sim" : "Não")));
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar filmes: " + e.getMessage());
        }
    }

    private void buscarFilmePorId() {
        try {
            System.out.print("ID do filme: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Filme filme = filmeController.buscarFilmePorId(id);
            if (filme != null) {
                System.out.println("\n----- Detalhes do Filme -----");
                System.out.println("Título: " + filme.getTitulo());
                System.out.println("Gênero: " + filme.getGenero());
                System.out.println("Ano: " + filme.getAnoLancamento());
                System.out.println("Duração: " + filme.getDuracao() + " minutos");
                System.out.println("Disponível: " + (filme.isDisponivel() ? "Sim" : "Não"));
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void realizarLocacao() {
        try {
            listarClientes();
            System.out.print("\nID do cliente: ");
            int clienteId = scanner.nextInt();

            List<Filme> filmes = filmeController.listarTodosFilmes().stream()
                    .filter(Filme::isDisponivel)
                    .toList();

            System.out.println("\n----- Filmes Disponíveis -----");
            if (filmes.isEmpty()) {
                System.out.println("Não há filmes disponíveis no momento.");
                return;
            }

            filmes.forEach(filme -> System.out.println("ID: " + filme.getId() + " - Título: " + filme.getTitulo()));

            System.out.print("\nID do filme que deseja alugar: ");
            int filmeId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Data aluguel (AAAA-MM-DD) ou deixe em branco para hoje: ");
            String dataAluguelInput = scanner.nextLine();
            String dataAluguel = dataAluguelInput.isEmpty() ? LocalDate.now().format(formatter) : dataAluguelInput;

            LocalDate dataAluguelDate = LocalDate.parse(dataAluguel, formatter);
            LocalDate dataDevolucaoCalculada = dataAluguelDate.plusDays(3);

            System.out.println("Data prevista para devolução: " + dataDevolucaoCalculada.format(formatter));
            System.out.print("Deseja manter esta data de devolução? (S/N): ");
            String manterData = scanner.nextLine();

            String dataDevolucao;
            if (manterData.equalsIgnoreCase("S") || manterData.isEmpty()) {
                dataDevolucao = dataDevolucaoCalculada.format(formatter);
            } else {
                System.out.print("Informe a data de devolução (AAAA-MM-DD): ");
                dataDevolucao = scanner.nextLine();
                if (dataDevolucao.isEmpty()) {
                    dataDevolucao = dataDevolucaoCalculada.format(formatter);
                }
            }

            locacaoController.adicionarLocacao(clienteId, filmeId, dataAluguel, dataDevolucao, 0.0, "Alugado");

            List<Locacao> locacoes = locacaoController.listarTodasLocacoes();
            Locacao locacaoRealizada = locacoes.get(locacoes.size() - 1);

            System.out.println("\n----- Locação Realizada com Sucesso! -----");
            System.out.println("Cliente: " + locacaoRealizada.getCliente().getNome());
            System.out.println("Filme: " + locacaoRealizada.getFilme().getTitulo());
            System.out.println("Data de aluguel: " + locacaoRealizada.getDataAluguel());
            System.out.println("Data prevista de devolução: " + locacaoRealizada.getDataDevolucao());
            System.out.println("Valor da locação: R$ " + String.format("%.2f", locacaoRealizada.getValor()));
            System.out.println("\nParabéns, filme alugado com sucesso! Aproveite!");

        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarLocacao() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();

            Locacao locacaoAtual = locacaoController.buscarLocacaoPorId(id);
            System.out.println("\n----- Detalhes da Locação Atual -----");
            System.out.println("Cliente: " + locacaoAtual.getCliente().getNome() + " (ID: "
                    + locacaoAtual.getCliente().getId() + ")");
            System.out.println(
                    "Filme: " + locacaoAtual.getFilme().getTitulo() + " (ID: " + locacaoAtual.getFilme().getId() + ")");
            System.out.println("Data de aluguel: " + locacaoAtual.getDataAluguel());
            System.out.println("Data de devolução: " + locacaoAtual.getDataDevolucao());
            System.out.println("Status: " + locacaoAtual.getStatus());

            System.out.print("\nNovo ID do cliente (atual: " + locacaoAtual.getCliente().getId() + "): ");
            int clienteId = scanner.nextInt();
            System.out.print("Novo ID do filme (atual: " + locacaoAtual.getFilme().getId() + "): ");
            int filmeId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nova data de aluguel (atual: " + locacaoAtual.getDataAluguel() + "): ");
            String dataAluguel = scanner.nextLine();
            if (dataAluguel.isEmpty()) {
                dataAluguel = locacaoAtual.getDataAluguel();
            }
            System.out.print("Nova data de devolução (atual: " + locacaoAtual.getDataDevolucao() + "): ");
            String dataDevolucao = scanner.nextLine();
            if (dataDevolucao.isEmpty()) {
                dataDevolucao = locacaoAtual.getDataDevolucao();
            }
            System.out.print("Novo status (Alugado/Devolvido) (atual: " + locacaoAtual.getStatus() + "): ");
            String status = scanner.nextLine();
            if (status.isEmpty()) {
                status = locacaoAtual.getStatus();
            }

            double valor = locacaoAtual.getValor();

            locacaoController.atualizarLocacao(clienteId, filmeId, dataAluguel, dataDevolucao, valor, status, id);

            Locacao locacaoAtualizada = locacaoController.buscarLocacaoPorId(id);
            System.out.println("\n----- Locação Atualizada com Sucesso! -----");
            System.out.println("Cliente: " + locacaoAtualizada.getCliente().getNome());
            System.out.println("Filme: " + locacaoAtualizada.getFilme().getTitulo());
            System.out.println("Data de aluguel: " + locacaoAtualizada.getDataAluguel());
            System.out.println("Data de devolução: " + locacaoAtualizada.getDataDevolucao());
            System.out.println("Status: " + locacaoAtualizada.getStatus());
            System.out.println("Valor atualizado: R$ " + String.format("%.2f", locacaoAtualizada.getValor()));

        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void deletarLocacao() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Locacao locacao = locacaoController.buscarLocacaoPorId(id);
            System.out.println("\n----- Detalhes da Locação a ser Deletada -----");
            System.out.println("Cliente: " + locacao.getCliente().getNome());
            System.out.println("Filme: " + locacao.getFilme().getTitulo());
            System.out.println("Data de aluguel: " + locacao.getDataAluguel());
            System.out.println("Status: " + locacao.getStatus());

            System.out.print("\nConfirma a exclusão desta locação? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                locacaoController.deletarLocacao(id);
                System.out.println("Locação deletada com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarLocacoes() {
        try {
            List<Locacao> locacoes = locacaoController.listarTodasLocacoes();
            if (locacoes == null || locacoes.isEmpty()) {
                System.out.println("Nenhuma locação cadastrada.");
            } else {
                System.out.println("\n----- Lista de Locações -----");
                locacoes.forEach(locacao -> {
                    System.out.println("ID: " + locacao.getId() +
                            " - Cliente: " + locacao.getCliente().getNome() +
                            " - Filme: " + locacao.getFilme().getTitulo() +
                            " - Aluguel: " + locacao.getDataAluguel() +
                            " - Devolução: " + locacao.getDataDevolucao() +
                            " - Valor: R$ " + String.format("%.2f", locacao.getValor()) +
                            " - Status: " + locacao.getStatus());
                });
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao listar locações: " + e.getMessage());
        }
    }

    private void buscarLocacaoPorId() {
        try {
            System.out.print("ID da locação: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Locacao locacao = locacaoController.buscarLocacaoPorId(id);
            if (locacao != null) {
                System.out.println("\n----- Detalhes da Locação -----");
                System.out.println("ID: " + locacao.getId());
                System.out.println("Cliente: " + locacao.getCliente().getNome());
                System.out.println("Filme: " + locacao.getFilme().getTitulo());
                System.out.println("Data de aluguel: " + locacao.getDataAluguel());
                System.out.println("Data de devolução: " + locacao.getDataDevolucao());
                System.out.println("Valor: R$ " + String.format("%.2f", locacao.getValor()));
                System.out.println("Status: " + locacao.getStatus());

                if (locacao.getStatus().equalsIgnoreCase("Alugado")) {
                    double valorAtual = locacaoController.calcularValorAtual(id);
                    System.out.println("Valor atual com base na data de hoje: R$ " + String.format("%.2f", valorAtual));
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void devolverFilme() {
        try {
            List<Locacao> locacoesEmAberto = locacaoController.listarTodasLocacoes().stream()
                    .filter(loc -> "Alugado".equalsIgnoreCase(loc.getStatus()))
                    .toList();

            if (locacoesEmAberto.isEmpty()) {
                System.out.println("Não há locações em aberto para devolução.");
                return;
            }

            System.out.println("\n----- Locações em Aberto -----");
            locacoesEmAberto.forEach(loc -> System.out.println("ID: " + loc.getId() +
                    " - Cliente: " + loc.getCliente().getNome() +
                    " - Filme: " + loc.getFilme().getTitulo() +
                    " - Data aluguel: " + loc.getDataAluguel() +
                    " - Data prevista devolução: " + loc.getDataDevolucao()));

            System.out.print("\nID da locação para devolução: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Locacao locacao = locacaoController.buscarLocacaoPorId(id);

            if (locacao != null && "Alugado".equalsIgnoreCase(locacao.getStatus())) {
                double valorAtual = locacaoController.calcularValorAtual(id);

                System.out.println("\n----- Confirmação de Devolução -----");
                System.out.println("Cliente: " + locacao.getCliente().getNome());
                System.out.println("Filme: " + locacao.getFilme().getTitulo());
                System.out.println("Data de aluguel: " + locacao.getDataAluguel());
                System.out.println("Data prevista de devolução: " + locacao.getDataDevolucao());
                System.out.println("Data atual de devolução: " + LocalDate.now().format(formatter));
                System.out.println("Valor a pagar: R$ " + String.format("%.2f", valorAtual));

                System.out.print("\nConfirmar a devolução? (S/N): ");
                String confirmacao = scanner.nextLine();

                if (confirmacao.equalsIgnoreCase("S")) {
                    locacaoController.registrarDevolucao(locacao);

                    System.out.println("\n----- Filme Devolvido com Sucesso! -----");
                    System.out.println("Valor pago: R$ " + String.format("%.2f", locacao.getValor()));
                    System.out.println("Agradecemos a preferência!");
                } else {
                    System.out.println("Operação cancelada.");
                }
            } else {
                System.out.println("Locação não encontrada ou já devolvida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}