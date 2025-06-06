package controller;

import dal.ClienteDAO;
import dal.FilmeDAO;
import dal.LocacaoDAO;
import factory.LocacaoFactory;
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

            Locacao locacao = LocacaoFactory.criar(cliente, filme, dataAluguel, dataDevolucao, valor, status);
            locacaoDAO.salvar(locacao);
            LoggerUtil.log("Locação realizada: Cliente ID " + clienteId + ", Filme ID " + filmeId);
        } catch (InvalidAttributesException | IllegalArgumentException e) {
            LoggerUtil.log("Erro ao adicionar locação: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar locação: " + e.getMessage(), e);
        }
    }

    public void atualizarLocacao(int clienteId, int filmeId, String dataAluguel, String dataDevolucao, double valor,
            String status, int id) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Filme filme = filmeDAO.buscarPorId(filmeId);

            Locacao locacao = LocacaoFactory.criar(cliente, filme, dataAluguel, dataDevolucao, valor, status);
            locacaoDAO.atualizar(locacao);
            LoggerUtil.log("Locação atualizada: ID " + id);
        } catch (InvalidAttributesException | IllegalArgumentException e) {
            LoggerUtil.log("Erro ao atualizar locação: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar locação: " + e.getMessage(), e);
        }
    }

    public void deletarLocacao(int id) {
        try {
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
        if (locacao != null) {
            LoggerUtil.log("Filme devolvido: Locação ID " + locacao.getId() + ", Filme ID " + locacao.getFilme().getId());
        }
    }
}