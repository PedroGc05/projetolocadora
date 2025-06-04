package locadora.dal;

import locadora.model.Cliente;
import locadora.util.SerializacaoUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import java.io.IOException;
import java.util.List;

public class ClienteDAO implements InterfaceDAO<Cliente> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ClienteDAO() {
        this.emf = Persistence.createEntityManagerFactory("java");
        this.em = emf.createEntityManager();

        if (buscarTodos().isEmpty()) {
            List<Cliente> clientesRestaurados = desserializarClientes();
            if (clientesRestaurados != null) {
                for (Cliente cliente : clientesRestaurados) {
                    try {
                        em.getTransaction().begin();
                        em.persist(cliente);
                        em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        em.getTransaction().rollback();
                        System.out.println("Erro de persistência ao restaurar cliente: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        em.getTransaction().rollback();
                        System.out.println("Erro de argumento ao restaurar cliente: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void salvar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            serializarClientes(); // <-- serializa após salvar
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            serializarClientes(); // <-- serializa após atualizar
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                em.remove(cliente);
            } else {
                throw new IllegalArgumentException("Cliente com ID " + id + " não encontrado.");
            }
            em.getTransaction().commit();
            serializarClientes(); // <-- serializa após deletar
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente com ID " + id + " não encontrado.");
        }
        return cliente;
    }

    @Override
    public List<Cliente> buscarTodos() {
        try {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao buscar todos os clientes: " + e.getMessage(), e);
        }
    }

    private void serializarClientes() {
        try {
            List<Cliente> clientes = buscarTodos();
            SerializacaoUtil.salvarLista(clientes, "data/clientes.ser");
        } catch (IOException e) {
            // Loga o erro, mas não lança para não afetar o fluxo principal
            System.out.println("Erro ao serializar clientes: " + e.getMessage());
        }
    }

    public List<Cliente> desserializarClientes() {
        try {
            return SerializacaoUtil.carregarLista("data/clientes.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar clientes: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        em.close();
        emf.close();
    }
}