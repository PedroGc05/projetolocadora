package locadora.util;

import java.io.*;
import java.util.List;

public class SerializacaoUtil {
    public static <T> void salvarLista(List<T> lista, String caminhoArquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(lista);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarLista(String caminhoArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (List<T>) ois.readObject();
        }
    }
}