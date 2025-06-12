package controller;

import dal.ClienteDAO;
import dal.FilmeDAO;
import dal.LocacaoDAO;
import factory.LocacaoFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.naming.directory.InvalidAttributesException;
import model.Cliente;
import model.Filme;
import model.Locacao;
import util.LoggerUtil;

public class LocacaoController {
    private final LocacaoDAO locacaoDAO;
    private final ClienteDAO clienteDAO;
    private final FilmeDAO filmeDAO;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocacaoController() {
        this.locacaoDAO = new LocacaoDAO();
        this.clienteDAO = new ClienteDAO();
        this.filmeDAO = new FilmeDAO();
    }

    public void adicionarLocacao(int clienteId, int filmeId, String dataAluguel, String dataDevolucao, double valor,
            String status) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Filme filme = filmeDAO.buscarPorId(filmeId);

            if (!filme.isDisponivel()) {
                LoggerUtil.log("Tentativa de alugar filme indisponível: Filme ID " + filmeId);
                throw new IllegalStateException("O filme selecionado não está disponível para locação.");
            }

            if (dataAluguel == null || dataAluguel.trim().isEmpty()) {
                dataAluguel = LocalDate.now().format(formatter);
            }

            if (dataDevolucao == null || dataDevolucao.trim().isEmpty()) {
                LocalDate dataAluguelDate = LocalDate.parse(dataAluguel, formatter);
                dataDevolucao = dataAluguelDate.plusDays(3).format(formatter);
            }

            status = "Alugado";

            Locacao locacao = LocacaoFactory.criar(cliente, filme, dataAluguel, dataDevolucao, 0.0, status);

            locacao.alugar();
            double valorCalculado = locacao.calcularValor();
            locacao.setValor(valorCalculado);

            filme.setDisponivel(false);
            filmeDAO.atualizar(filme);

            locacaoDAO.salvar(locacao);

            LoggerUtil.log("Locação realizada: Cliente ID " + clienteId + ", Filme ID " + filmeId +
                    ", Valor calculado: R$" + String.format("%.2f", valorCalculado));
        } catch (InvalidAttributesException | IllegalArgumentException | IllegalStateException e) {
            LoggerUtil.log("Erro ao adicionar locação: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar locação: " + e.getMessage(), e);
        }
    }

    public void atualizarLocacao(int clienteId, int filmeId, String dataAluguel, String dataDevolucao, double valor,
            String status, int id) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Filme filme = filmeDAO.buscarPorId(filmeId);

            Locacao locacaoExistente = locacaoDAO.buscarPorId(id);

            Locacao locacao = LocacaoFactory.criar(cliente, filme, dataAluguel, dataDevolucao, valor, status);
            locacao.setId(id);

            if (status.equalsIgnoreCase("Devolvido")) {
                locacao.setValor(locacao.calcularValor());
            }

            if (locacaoExistente.getFilme().getId() != filme.getId()) {
                locacaoExistente.getFilme().setDisponivel(true);
                filmeDAO.atualizar(locacaoExistente.getFilme());

                if (status.equalsIgnoreCase("Alugado")) {
                    filme.setDisponivel(false);
                    filmeDAO.atualizar(filme);
                }
            } else if (status.equalsIgnoreCase("Devolvido") &&
                    !locacaoExistente.getStatus().equalsIgnoreCase("Devolvido")) {
                filme.setDisponivel(true);
                filmeDAO.atualizar(filme);
            } else if (status.equalsIgnoreCase("Alugado") &&
                    !locacaoExistente.getStatus().equalsIgnoreCase("Alugado")) {
                filme.setDisponivel(false);
                filmeDAO.atualizar(filme);
            }

            locacaoDAO.atualizar(locacao);
            LoggerUtil.log("Locação atualizada: ID " + id);
        } catch (InvalidAttributesException | IllegalArgumentException e) {
            LoggerUtil.log("Erro ao atualizar locação: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar locação: " + e.getMessage(), e);
        }
    }

    public void deletarLocacao(int id) {
        try {
            Locacao locacao = locacaoDAO.buscarPorId(id);

            if (locacao.getStatus().equalsIgnoreCase("Alugado")) {
                Filme filme = locacao.getFilme();
                filme.setDisponivel(true);
                filmeDAO.atualizar(filme);
            }

            locacaoDAO.deletar(id);
            LoggerUtil.log("Locação deletada: ID " + id);
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao deletar locação: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar locação: " + e.getMessage(), e);
        }
    }

    public Locacao buscarLocacaoPorId(int id) {
        try {
            Locacao locacao = locacaoDAO.buscarPorId(id);
            LoggerUtil.log("Busca de locação por ID: " + id);
            return locacao;
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar locação: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar locação: " + e.getMessage(), e);
        }
    }

    public List<Locacao> listarTodasLocacoes() {
        try {
            return locacaoDAO.buscarTodos();
        } catch (Exception e) {
            LoggerUtil.log("Erro ao listar locações: " + e.getMessage());
            throw new RuntimeException("Erro ao listar locações: " + e.getMessage(), e);
        }
    }

    public void registrarDevolucao(Locacao locacao) {
        try {
            if (locacao != null && locacao.getStatus().equalsIgnoreCase("Alugado")) {
                if (locacao.getDataDevolucaoAsDate() == null) {
                    locacao.setDataDevolucao(LocalDate.now());
                }

                locacao.devolver();

                Filme filme = locacao.getFilme();
                filme.setDisponivel(true);
                filmeDAO.atualizar(filme);

                locacaoDAO.atualizar(locacao);

                LoggerUtil.log("Filme devolvido: Locação ID " + locacao.getId() +
                        ", Filme ID " + locacao.getFilme().getId() +
                        ", Valor final: R$" + String.format("%.2f", locacao.getValor()));
            } else if (locacao != null) {
                throw new IllegalStateException("Esta locação já foi devolvida anteriormente.");
            } else {
                throw new IllegalArgumentException("Locação não encontrada.");
            }
        } catch (Exception e) {
            LoggerUtil.log("Erro ao registrar devolução: " + e.getMessage());
            throw new RuntimeException("Erro ao registrar devolução: " + e.getMessage(), e);
        }
    }

    public double calcularValorAtual(int id) {
        try {
            Locacao locacao = locacaoDAO.buscarPorId(id);
            return locacao.calcularValor();
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao calcular valor atual: " + e.getMessage());
            throw new RuntimeException("Erro ao calcular valor atual: " + e.getMessage(), e);
        }
    }
}