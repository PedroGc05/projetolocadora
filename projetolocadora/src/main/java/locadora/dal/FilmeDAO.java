package locadora.dal;

import locadora.model.Filme;
import locadora.util.SerializacaoUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import java.io.IOException;
import java.util.List;

public class FilmeDAO implements InterfaceDAO<Filme> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public FilmeDAO() {
        this.emf = Persistence.createEntityManagerFactory("java");
        this.em = emf.createEntityManager();

        if (buscarTodos().isEmpty()) {
            List<Filme> filmesRestaurados = desserializarFilmes();
            if (filmesRestaurados != null) {
                for (Filme filme : filmesRestaurados) {
                    try {
                        em.getTransaction().begin();
                        em.persist(filme);
                        em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        em.getTransaction().rollback();
                        System.out.println("Erro de persistência ao restaurar filme: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        em.getTransaction().rollback();
                        System.out.println("Erro de argumento ao restaurar filme: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void salvar(Filme filme) {
        try {
            em.getTransaction().begin();
            em.persist(filme);
            em.getTransaction().commit();
            serializarFilmes(); // <-- serializa após salvar
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
            serializarFilmes(); // <-- serializa após atualizar
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
            serializarFilmes(); // <-- serializa após deletar
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

    private void serializarFilmes() {
        try {
            List<Filme> filmes = buscarTodos();
            SerializacaoUtil.salvarLista(filmes, "data/filmes.ser");
        } catch (IOException e) {
            System.out.println("Erro ao serializar filmes: " + e.getMessage());
        }
    }

    public List<Filme> desserializarFilmes() {
        try {
            return SerializacaoUtil.carregarLista("data/filmes.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar filmes: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        em.close();
        emf.close();
    }
}