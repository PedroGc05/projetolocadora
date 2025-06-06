package dal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Filme;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FilmeDAO implements InterfaceDAO<Filme> {
    private static final String CAMINHO = "src/dados/filme";
    private List<Filme> filmes;

    public static void salvar(List<Filme> filmes) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/filmes.ser"))) {
            oos.writeObject(filmes);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Filme> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/filmes.ser");
        if (!arquivo.exists())
            return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Filme>) ois.readObject();
        }
    }

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
            salvar(filmes);
        } catch (IOException e) {
            System.out.println("Erro ao serializar filmes: " + e.getMessage());
        }
    }

    private List<Filme> desserializarFilmes() {
        try {
            return carregar();
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