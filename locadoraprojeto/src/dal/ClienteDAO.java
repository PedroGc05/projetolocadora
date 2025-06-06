package dal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ClienteDAO implements InterfaceDAO<Cliente> {
    private static final String CAMINHO = "src/dados/cliente";
    private List<Cliente> clientes;

    public static void salvar(List<Cliente> clientes) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/clientes.ser"))) {
            oos.writeObject(clientes);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Cliente> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/clientes.ser");
        if (!arquivo.exists())
            return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Cliente>) ois.readObject();
        }
    }

    public ClienteDAO() {
        this.clientes = desserializarClientes();
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
        }
    }

    @Override
    public void salvar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser nulo ou vazio.");
        }
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
            salvar(clientes);
        } catch (IOException e) {
            System.out.println("Erro ao serializar clientes: " + e.getMessage());
        }
    }

    private List<Cliente> desserializarClientes() {
        try {
            return carregar();
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