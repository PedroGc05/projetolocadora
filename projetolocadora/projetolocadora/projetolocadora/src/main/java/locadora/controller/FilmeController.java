package locadora.controller;

import locadora.dal.FilmeDAO;
import locadora.factory.FilmeFactory;
import locadora.model.Filme;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;
import javax.persistence.PersistenceException;

public class FilmeController {
    private final FilmeDAO filmeDAO;

    public FilmeController() {
        this.filmeDAO = new FilmeDAO();
    }

    public void adicionarFilme(String titulo, String genero, int anoLancamento, int duracao, boolean disponivel,
            int id) {
        try {
            Filme filme = FilmeFactory.criar(titulo, genero, anoLancamento, duracao, disponivel, id);
            filmeDAO.salvar(filme);
        } catch (InvalidAttributesException e) {
            System.out.println("Erro ao adicionar filme: " + e.getMessage());
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao adicionar filme: " + e.getMessage());
        }
    }

    public void atualizarFilme(String titulo, String genero, int anoLancamento, int duracao, boolean disponivel,
            int id) {
        try {
            Filme filme = FilmeFactory.criar(titulo, genero, anoLancamento, duracao, disponivel, id);
            filmeDAO.atualizar(filme);
        } catch (InvalidAttributesException e) {
            System.out.println("Erro ao atualizar filme: " + e.getMessage());
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao atualizar filme: " + e.getMessage());
        }
    }

    public void deletarFilme(int id) {
        try {
            filmeDAO.deletar(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao deletar filme: " + e.getMessage());
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao deletar filme: " + e.getMessage());
        }
    }

    public Filme buscarFilmePorId(int id) {
        try {
            return filmeDAO.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao buscar filme: " + e.getMessage());
            return null;
        } catch (PersistenceException e) {
            System.out.println("Erro de persistência ao buscar filme: " + e.getMessage());
            return null;
        }
    }

    public List<Filme> listarTodosFilmes() {
        try {
            return filmeDAO.buscarTodos();
        } catch (PersistenceException e) {
            System.out.println("Erro ao listar filmes: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        filmeDAO.fecharConexao();
    }
}