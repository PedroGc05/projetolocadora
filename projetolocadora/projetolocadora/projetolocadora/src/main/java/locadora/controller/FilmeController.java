package locadora.controller;

import locadora.dal.FilmeDAO;
import locadora.factory.FilmeFactory;
import locadora.model.Filme;
import locadora.util.LoggerUtil;
import locadora.util.SerializacaoUtil;

import java.io.IOException;
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
            LoggerUtil.log("Filme cadastrado: " + titulo + " (" + anoLancamento + ")");
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao adicionar filme: " + e.getMessage());
            System.out.println("Erro ao adicionar filme: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao adicionar filme: " + e.getMessage());
            System.out.println("Erro de persistência ao adicionar filme: " + e.getMessage());
        }
    }

    public void atualizarFilme(String titulo, String genero, int anoLancamento, int duracao, boolean disponivel,
            int id) {
        try {
            Filme filme = FilmeFactory.criar(titulo, genero, anoLancamento, duracao, disponivel, id);
            filmeDAO.atualizar(filme);
            LoggerUtil.log("Filme atualizado: ID " + id);
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao atualizar filme: " + e.getMessage());
            System.out.println("Erro ao atualizar filme: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao atualizar filme: " + e.getMessage());
            System.out.println("Erro de persistência ao atualizar filme: " + e.getMessage());
        }
    }

    public void deletarFilme(int id) {
        try {
            filmeDAO.deletar(id);
            LoggerUtil.log("Filme deletado: ID " + id);
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao deletar filme: " + e.getMessage());
            System.out.println("Erro ao deletar filme: " + e.getMessage());
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao deletar filme: " + e.getMessage());
            System.out.println("Erro de persistência ao deletar filme: " + e.getMessage());
        }
    }

    public Filme buscarFilmePorId(int id) {
        try {
            Filme filme = filmeDAO.buscarPorId(id);
            LoggerUtil.log("Busca de filme por ID: " + id);
            return filme;
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar filme: " + e.getMessage());
            System.out.println("Erro ao buscar filme: " + e.getMessage());
            return null;
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro de persistência ao buscar filme: " + e.getMessage());
            System.out.println("Erro de persistência ao buscar filme: " + e.getMessage());
            return null;
        }
    }

    public List<Filme> listarTodosFilmes() {
        try {
            List<Filme> filmes = filmeDAO.buscarTodos();
            LoggerUtil.log("Listagem de todos os filmes.");
            return filmes;
        } catch (PersistenceException e) {
            LoggerUtil.log("Erro ao listar filmes: " + e.getMessage());
            System.out.println("Erro ao listar filmes: " + e.getMessage());
            return null;
        }
    }

    public void fecharConexao() {
        filmeDAO.fecharConexao();
        LoggerUtil.log("Conexão com FilmeDAO fechada.");
    }
}