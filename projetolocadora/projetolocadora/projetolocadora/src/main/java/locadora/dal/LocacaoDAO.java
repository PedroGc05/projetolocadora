package locadora.dal;

import locadora.model.Locacao;
import locadora.util.SerializacaoUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import java.io.IOException;
import java.util.List;

public class LocacaoDAO implements InterfaceDAO<Locacao> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public LocacaoDAO() {
        this.emf = Persistence.createEntityManagerFactory("java");
        this.em = emf.createEntityManager();

        if (buscarTodos().isEmpty()) {
            List<Locacao> locacoesRestauradas = desserializarLocacoes();
            if (locacoesRestauradas != null) {
                for (Locacao locacao : locacoesRestauradas) {
                    try {
                        em.getTransaction().begin();
                        em.persist(locacao);
                        em.getTransaction().commit();
                    } catch (PersistenceException e) {
                        em.getTransaction().rollback();
                        System.out.println("Erro de persistência ao restaurar locação: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        em.getTransaction().rollback();
                        System.out.println("Erro de argumento ao restaurar locação: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void salvar(Locacao locacao) {
        try {
            em.getTransaction().begin();
            em.persist(locacao);
            em.getTransaction().commit();
            serializarLocacoes(); // <-- serializa após salvar
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
            serializarLocacoes(); // <-- serializa após atualizar
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
            serializarLocacoes(); // <-- serializa após deletar
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

    private void serializarLocacoes() {
        try {
            List<Locacao> locacoes = buscarTodos();
            SerializacaoUtil.salvarLista(locacoes, "data/locacoes.ser");
        } catch (IOException e) {
            System.out.println("Erro ao serializar locações: " + e.getMessage());
        }
    }

    public List<Locacao> desserializarLocacoes() {
        try {
            return SerializacaoUtil.carregarLista("data/locacoes.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar locações: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        em.close();
        emf.close();
    }
}