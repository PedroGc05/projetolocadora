package dal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class ClienteDAO implements InterfaceDAO<Cliente> {
    private static final String ARQUIVO_SERIALIZACAO = "projetolocadora/projetolocadora/src/dados/cliente/clientes.ser";
    private List<Cliente> clientes;

    public ClienteDAO() {
        this.clientes = desserializarClientes();
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
        }
    }

    @Override
    public void salvar(Cliente cliente) {
        cliente.setId(gerarProximoId());
        clientes.add(cliente);
        serializarClientes();
    }

    private int gerarProximoId() {
        return clientes.stream()
                .mapToInt(Cliente::getId)
                .max()
                .orElse(0) + 1;
    }

    @Override
    public void atualizar(Cliente cliente) {
        int index = buscarIndicePorId(cliente.getId());
        if (index != -1) {
            clientes.set(index, cliente);
            serializarClientes();
        } else {
            throw new IllegalArgumentException("Cliente com ID " + cliente.getId() + " não encontrado.");
        }
    }

    @Override
    public void deletar(int id) {
        int index = buscarIndicePorId(id);
        if (index != -1) {
            clientes.remove(index);
            serializarClientes();
        } else {
            throw new IllegalArgumentException("Cliente com ID " + id + " não encontrado.");
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente com ID " + id + " não encontrado."));
    }

    @Override
    public List<Cliente> buscarTodos() {
        return new ArrayList<>(clientes);
    }

    private void serializarClientes() {
        try {
            SerializacaoDAO.salvarLista(clientes, ARQUIVO_SERIALIZACAO);
        } catch (IOException e) {
            System.out.println("Erro ao serializar clientes: " + e.getMessage());
        }
    }

    private List<Cliente> desserializarClientes() {
        try {
            return SerializacaoDAO.carregarLista(ARQUIVO_SERIALIZACAO);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar clientes: " + e.getMessage());
            return null;
        }
    }

    private int buscarIndicePorId(int id) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}