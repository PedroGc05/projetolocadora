package locadora.dal;

import locadora.model.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public class ClienteDAO implements InterfaceDAO<Cliente> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ClienteDAO() {
        this.emf = Persistence.createEntityManagerFactory("java");
        this.em = emf.createEntityManager();
    }

    @Override
    public void salvar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
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

    public void fecharConexao() {
        em.close();
        emf.close();
    }
}