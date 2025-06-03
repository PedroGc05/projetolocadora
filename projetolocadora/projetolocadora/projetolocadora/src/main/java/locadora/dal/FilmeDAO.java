package locadora.dal;

import locadora.model.Filme;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

public class FilmeDAO implements InterfaceDAO<Filme> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public FilmeDAO() {
        this.emf = Persistence.createEntityManagerFactory("java");
        this.em = emf.createEntityManager();
    }

    @Override
    public void salvar(Filme filme) {
        try {
            em.getTransaction().begin();
            em.persist(filme);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao salvar filme: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Filme filme) {
        try {
            em.getTransaction().begin();
            em.merge(filme);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao atualizar filme: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        try {
            em.getTransaction().begin();
            Filme filme = em.find(Filme.class, id);
            if (filme != null) {
                em.remove(filme);
            } else {
                throw new IllegalArgumentException("Filme com ID " + id + " não encontrado.");
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Erro ao deletar filme: " + e.getMessage(), e);
        }
    }

    @Override
    public Filme buscarPorId(int id) {
        Filme filme = em.find(Filme.class, id);
        if (filme == null) {
            throw new IllegalArgumentException("Filme com ID " + id + " não encontrado.");
        }
        return filme;
    }

    @Override
    public List<Filme> buscarTodos() {
        try {
            return em.createQuery("SELECT f FROM Filme f", Filme.class).getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao buscar todos os filmes: " + e.getMessage(), e);
        }
    }

    public void fecharConexao() {
        em.close();
        emf.close();
    }
}