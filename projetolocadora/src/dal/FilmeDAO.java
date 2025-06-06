package dal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

public class FilmeDAO implements InterfaceDAO<Filme> {
    private static final String ARQUIVO_SERIALIZACAO = "projetolocadora/projetolocadora/src/dados/filme/filmes.ser";
    private List<Filme> filmes;

    public FilmeDAO() {
        this.filmes = desserializarFilmes();
        if (this.filmes == null) {
            this.filmes = new ArrayList<>();
        }
    }

    @Override
    public void salvar(Filme filme) {
        filme.setId(gerarProximoId());
        filmes.add(filme);
        serializarFilmes();
    }

    @Override
    public void atualizar(Filme filme) {
        int index = buscarIndicePorId(filme.getId());
        if (index != -1) {
            filmes.set(index, filme);
            serializarFilmes();
        } else {
            throw new IllegalArgumentException("Filme com ID " + filme.getId() + " não encontrado.");
        }
    }

    @Override
    public void deletar(int id) {
        int index = buscarIndicePorId(id);
        if (index != -1) {
            filmes.remove(index);
            serializarFilmes();
        } else {
            throw new IllegalArgumentException("Filme com ID " + id + " não encontrado.");
        }
    }

    @Override
    public Filme buscarPorId(int id) {
        return filmes.stream()
                .filter(filme -> filme.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Filme com ID " + id + " não encontrado."));
    }

    @Override
    public List<Filme> buscarTodos() {
        return new ArrayList<>(filmes);
    }

    private void serializarFilmes() {
        try {
            SerializacaoDAO.salvarLista(filmes, ARQUIVO_SERIALIZACAO);
        } catch (IOException e) {
            System.out.println("Erro ao serializar filmes: " + e.getMessage());
        }
    }

    private List<Filme> desserializarFilmes() {
        try {
            return SerializacaoDAO.carregarLista(ARQUIVO_SERIALIZACAO);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar filmes: " + e.getMessage());
            return null;
        }
    }

    private int buscarIndicePorId(int id) {
        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int gerarProximoId() {
        return filmes.stream()
                .mapToInt(Filme::getId)
                .max()
                .orElse(0) + 1;
    }
}