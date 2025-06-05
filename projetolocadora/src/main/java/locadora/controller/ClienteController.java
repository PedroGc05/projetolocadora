package locadora.controller;

import locadora.dal.ClienteDAO;
import locadora.factory.ClienteFactory;
import locadora.model.Cliente;
import locadora.util.LoggerUtil;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;
import javax.persistence.PersistenceException;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public void adicionarCliente(String nome, String cpf, String telefone, String endereco, String email, int id) {
        try {
            Cliente cliente = ClienteFactory.criar(nome, cpf, telefone, endereco, email);
            clienteDAO.salvar(cliente);
            LoggerUtil.log("Cliente cadastrado: " + nome + " (CPF: " + cpf + ")");
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao adicionar cliente: " + e.getMessage());
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao adicionar cliente: " + e.getMessage());
            System.out.println("Erro de persistência ao adicionar cliente: " + e.getMessage());
        }
    }

    public void atualizarCliente(String nome, String cpf, String telefone, String endereco, String email, int id) {
        try {
            Cliente cliente = ClienteFactory.criar(nome, cpf, telefone, endereco, email);
            cliente.setId(id);
            clienteDAO.atualizar(cliente);
            LoggerUtil.log("Cliente atualizado: ID " + id);
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao atualizar cliente: " + e.getMessage());
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao atualizar cliente: " + e.getMessage());
            System.out.println("Erro de persistência ao atualizar cliente: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao atualizar cliente: " + e.getMessage());
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletarCliente(int id) {
        try {
            clienteDAO.deletar(id);
            LoggerUtil.log("Cliente deletado: ID " + id);
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao deletar cliente: " + e.getMessage());
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao deletar cliente: " + e.getMessage());
            System.out.println("Erro de persistência ao deletar cliente: " + e.getMessage());
        }
    }

    public Cliente buscarClientePorId(int id) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(id);
            LoggerUtil.log("Busca de cliente por ID: " + id);
            return cliente;
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar cliente: " + e.getMessage());
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao buscar cliente: " + e.getMessage());
            System.out.println("Erro de persistência ao buscar cliente: " + e.getMessage());
            return null;
        }
    }

    public List<Cliente> listarTodosClientes() {
        try {
            List<Cliente> clientes = clienteDAO.buscarTodos();
            LoggerUtil.log("Listagem de todos os clientes.");
            return clientes;
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro ao listar clientes: " + e.getMessage());
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        clienteDAO.fecharConexao();
        LoggerUtil.log("Conexão com ClienteDAO fechada.");
    }
}