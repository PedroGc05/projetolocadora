package locadora.controller;

import locadora.dal.LocacaoDAO;
import locadora.dal.ClienteDAO;
import locadora.dal.FilmeDAO;
import locadora.factory.LocacaoFactory;
import locadora.model.Locacao;
import locadora.model.Cliente;
import locadora.model.Filme;
import locadora.util.LoggerUtil;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;
import javax.persistence.PersistenceException;

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
            String status, int id) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Filme filme = filmeDAO.buscarPorId(filmeId);

            Locacao locacao = LocacaoFactory.criar(cliente, filme, dataAluguel, dataDevolucao, valor, status, id);
            locacaoDAO.salvar(locacao);
            LoggerUtil
                    .log("Locação realizada: Cliente ID " + clienteId + ", Filme ID " + filmeId + ", Locação ID " + id);
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao adicionar locação: " + e.getMessage());
            System.out.println("Erro ao adicionar locação: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao adicionar locação: " + e.getMessage());
            System.out.println("Erro de persistência ao adicionar locação: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar cliente ou filme: " + e.getMessage());
            System.out.println("Erro ao buscar cliente ou filme: " + e.getMessage());
        }
    }

    public void atualizarLocacao(int clienteId, int filmeId, String dataAluguel, String dataDevolucao, double valor,
            String status, int id) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(clienteId);
            Filme filme = filmeDAO.buscarPorId(filmeId);

            Locacao locacao = LocacaoFactory.criar(cliente, filme, dataAluguel, dataDevolucao, valor, status, id);
            locacaoDAO.atualizar(locacao);
            LoggerUtil.log("Locação atualizada: ID " + id);
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao atualizar locação: " + e.getMessage());
            System.out.println("Erro ao atualizar locação: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao atualizar locação: " + e.getMessage());
            System.out.println("Erro de persistência ao atualizar locação: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar cliente ou filme: " + e.getMessage());
            System.out.println("Erro ao buscar cliente ou filme: " + e.getMessage());
        }
    }

    public void deletarLocacao(int id) {
        try {
            locacaoDAO.deletar(id);
            LoggerUtil.log("Locação deletada: ID " + id);
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao deletar locação: " + e.getMessage());
            System.out.println("Erro ao deletar locação: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao deletar locação: " + e.getMessage());
            System.out.println("Erro de persistência ao deletar locação: " + e.getMessage());
        }
    }

    public Locacao buscarLocacaoPorId(int id) {
        try {
            Locacao locacao = locacaoDAO.buscarPorId(id);
            LoggerUtil.log("Busca de locação por ID: " + id);
            return locacao;
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar locação: " + e.getMessage());
            System.out.println("Erro ao buscar locação: " + e.getMessage());
            return null;
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao buscar locação: " + e.getMessage());
            System.out.println("Erro de persistência ao buscar locação: " + e.getMessage());
            return null;
        }
    }

    public List<Locacao> listarTodasLocacoes() {
        try {
            List<Locacao> locacoes = locacaoDAO.buscarTodos();
            LoggerUtil.log("Listagem de todas as locações.");
            return locacoes;
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro ao listar locações: " + e.getMessage());
            System.out.println("Erro ao listar locações: " + e.getMessage());
            return null;
        }
    }

    public void registrarDevolucao(Locacao locacao) {
        if (locacao != null) {
            LoggerUtil
                    .log("Filme devolvido: Locação ID " + locacao.getId() + ", Filme ID " + locacao.getFilme().getId());
        }
    }

    public void fecharConexao() {
        locacaoDAO.fecharConexao();
        clienteDAO.fecharConexao();
        filmeDAO.fecharConexao();
        LoggerUtil.log("Conexões com DAOs de Locação, Cliente e Filme fechadas.");
    }
}