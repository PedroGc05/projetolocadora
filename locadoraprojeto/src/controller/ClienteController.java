package controller;

import dal.ClienteDAO;
import factory.ClienteFactory;
import java.util.List;
import javax.naming.directory.InvalidAttributesException;
import model.Cliente;
import util.LoggerUtil;

public class ClienteController {
    private final ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public void adicionarCliente(String nome, String cpf, String telefone, String endereco, String email) {
        try {
            Cliente cliente = ClienteFactory.criar(nome, cpf, telefone, endereco, email);
            clienteDAO.salvar(cliente);
            LoggerUtil.log("Cliente cadastrado: " + nome + " (CPF: " + cpf + ")");
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao adicionar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar cliente: " + e.getMessage(), e);
        }
    }

    public void atualizarCliente(String nome, String cpf, String telefone, String endereco, String email, int id) {
        try {
            Cliente cliente = ClienteFactory.criar(nome, cpf, telefone, endereco, email);
            cliente.setId(id);
            clienteDAO.atualizar(cliente);
            LoggerUtil.log("Cliente atualizado: ID " + id);
        } catch (InvalidAttributesException | IllegalArgumentException e) {
            LoggerUtil.log("Erro ao atualizar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    public void deletarCliente(int id) {
        try {
            clienteDAO.deletar(id);
            LoggerUtil.log("Cliente deletado: ID " + id);
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao deletar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }

    public Cliente buscarClientePorId(int id) {
        try {
            Cliente cliente = clienteDAO.buscarPorId(id);
            LoggerUtil.log("Busca de cliente por ID: " + id);
            return cliente;
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
        }
    }

    public List<Cliente> listarTodosClientes() {
        try {
            return clienteDAO.buscarTodos();
        } catch (Exception e) {
            LoggerUtil.log("Erro ao listar clientes: " + e.getMessage());
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
    }
}