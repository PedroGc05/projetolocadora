package dal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Locacao;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LocacaoDAO implements InterfaceDAO<Locacao> {
    private static final String CAMINHO = "src/dados/locacao";
    private List<Locacao> locacoes;

    public static void salvar(List<Locacao> locacoes) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/locacoes.ser"))) {
            oos.writeObject(locacoes);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Locacao> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/locacoes.ser");
        if (!arquivo.exists())
            return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Locacao>) ois.readObject();
        }
    }

    public LocacaoDAO() {
        this.locacoes = desserializarLocacoes();
        if (this.locacoes == null) {
            this.locacoes = new ArrayList<>();
        }
    }

    @Override
    public void salvar(Locacao locacao) {
        locacao.setId(gerarProximoId());
        locacoes.add(locacao);
        serializarLocacoes();
    }

    @Override
    public void atualizar(Locacao locacao) {
        int index = buscarIndicePorId(locacao.getId());
        if (index != -1) {
            locacoes.set(index, locacao);
            serializarLocacoes();
        } else {
            throw new IllegalArgumentException("Locação com ID " + locacao.getId() + " não encontrada.");
        }
    }

    @Override
    public void deletar(int id) {
        int index = buscarIndicePorId(id);
        if (index != -1) {
            locacoes.remove(index);
            serializarLocacoes();
        } else {
            throw new IllegalArgumentException("Locação com ID " + id + " não encontrada.");
        }
    }

    @Override
    public Locacao buscarPorId(int id) {
        return locacoes.stream()
                .filter(locacao -> locacao.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Locação com ID " + id + " não encontrada."));
    }

    @Override
    public List<Locacao> buscarTodos() {
        return new ArrayList<>(locacoes);
    }

    private void serializarLocacoes() {
        try {
            salvar(locacoes);
        } catch (IOException e) {
            System.out.println("Erro ao serializar locações: " + e.getMessage());
        }
    }

    private List<Locacao> desserializarLocacoes() {
        try {
            return carregar();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar locações: " + e.getMessage());
            return null;
        }
    }

    private int buscarIndicePorId(int id) {
        for (int i = 0; i < locacoes.size(); i++) {
            if (locacoes.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int gerarProximoId() {
        return locacoes.stream()
                .mapToInt(Locacao::getId)
                .max()
                .orElse(0) + 1;
    }
}