package locadora.controller;

import locadora.dal.ClienteDAO;
import locadora.factory.ClienteFactory;
import locadora.model.Cliente;

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
        } catch (InvalidAttributesException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao adicionar cliente: " + e.getMessage());
        }
    }

    public void atualizarCliente(String nome, String cpf, String telefone, String endereco, String email, int id) {
        try {
            Cliente cliente = ClienteFactory.criar(nome, cpf, telefone, endereco, email);
            clienteDAO.atualizar(cliente);
        } catch (InvalidAttributesException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao atualizar cliente: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void deletarCliente(int id) {
        try {
            clienteDAO.deletar(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao deletar cliente: " + e.getMessage());
        }
    }

    public Cliente buscarClientePorId(int id) {
        try {
            return clienteDAO.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao buscar cliente: " + e.getMessage());
            return null;
        }
    }

    public List<Cliente> listarTodosClientes() {
        try {
            return clienteDAO.buscarTodos();
        } catch (PersistenceException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        clienteDAO.fecharConexao();
    }
}