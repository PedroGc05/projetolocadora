package controller;

import dal.FilmeDAO;
import factory.FilmeFactory;
import java.util.List;
import javax.naming.directory.InvalidAttributesException;
import model.Filme;
import util.LoggerUtil;

public class FilmeController {
    private final FilmeDAO filmeDAO;

    public FilmeController() {
        this.filmeDAO = new FilmeDAO();
    }

    public void adicionarFilme(String titulo, String genero, int anoLancamento, int duracao, boolean disponivel) {
        try {
            Filme filme = FilmeFactory.criar(titulo, genero, anoLancamento, duracao, disponivel);
            filmeDAO.salvar(filme);
            LoggerUtil.log("Filme cadastrado: " + titulo + " (" + anoLancamento + ")");
        } catch (InvalidAttributesException e) {
            LoggerUtil.log("Erro ao adicionar filme: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar filme: " + e.getMessage(), e);
        }
    }

    public void atualizarFilme(String titulo, String genero, int anoLancamento, int duracao, boolean disponivel, int id) {
        try {
            Filme filme = FilmeFactory.criar(titulo, genero, anoLancamento, duracao, disponivel);
            filmeDAO.atualizar(filme);
            LoggerUtil.log("Filme atualizado: ID " + id);
        } catch (InvalidAttributesException | IllegalArgumentException e) {
            LoggerUtil.log("Erro ao atualizar filme: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar filme: " + e.getMessage(), e);
        }
    }

    public void deletarFilme(int id) {
        try {
            filmeDAO.deletar(id);
            LoggerUtil.log("Filme deletado: ID " + id);
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao deletar filme: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar filme: " + e.getMessage(), e);
        }
    }

    public Filme buscarFilmePorId(int id) {
        try {
            Filme filme = filmeDAO.buscarPorId(id);
            LoggerUtil.log("Busca de filme por ID: " + id);
            return filme;
        } catch (IllegalArgumentException e) {
            LoggerUtil.log("Erro ao buscar filme: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar filme: " + e.getMessage(), e);
        }
    }

    public List<Filme> listarTodosFilmes() {
        try {
            return filmeDAO.buscarTodos();
        } catch (Exception e) {
            LoggerUtil.log("Erro ao listar filmes: " + e.getMessage());
            throw new RuntimeException("Erro ao listar filmes: " + e.getMessage(), e);
        }
    }
}