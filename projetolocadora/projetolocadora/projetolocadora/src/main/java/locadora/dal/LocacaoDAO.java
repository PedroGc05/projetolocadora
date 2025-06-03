package locadora.dal;

import locadora.model.Locacao;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public class LocacaoDAO implements InterfaceDAO<Locacao> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public LocacaoDAO() {
        this.emf = Persistence.createEntityManagerFactory("java");
        this.em = emf.createEntityManager();
    }

    @Override
    public void salvar(Locacao locacao) {
        try {
            em.getTransaction().begin();
            em.persist(locacao);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao salvar locação: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Locacao locacao) {
        try {
            em.getTransaction().begin();
            em.merge(locacao);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao atualizar locação: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        try {
            em.getTransaction().begin();
            Locacao locacao = em.find(Locacao.class, id);
            if (locacao != null) {
                em.remove(locacao);
            } else {
                throw new IllegalArgumentException("Locação com ID " + id + " não encontrada.");
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao deletar locação: " + e.getMessage(), e);
        }
    }

    @Override
    public Locacao buscarPorId(int id) {
        Locacao locacao = em.find(Locacao.class, id);
        if (locacao == null) {
            throw new IllegalArgumentException("Locação com ID " + id + " não encontrada.");
        }
        return locacao;
    }

    @Override
    public List<Locacao> buscarTodos() {
        try {
            return em.createQuery("SELECT l FROM Locacao l", Locacao.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao buscar todas as locações: " + e.getMessage(), e);
        }
    }

    public void fecharConexao() {
        em.close();
        emf.close();
    }
}