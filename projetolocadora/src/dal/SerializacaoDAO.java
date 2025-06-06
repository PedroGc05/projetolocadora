package dal;

import java.io.*;
import java.util.List;

public class SerializacaoDAO {
    public static <T> void salvarLista(List<T> lista, String caminhoArquivo) throws IOException {
        criarDiretorioVazio(caminhoArquivo);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(lista);
        }
    }

    private static void criarDiretorioVazio(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        File diretorio = arquivo.getParentFile();
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarLista(String caminhoArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (List<T>) ois.readObject();
        }
    }
}