package locadora.factory;

import javax.naming.directory.InvalidAttributesException;

import locadora.model.Filme;

public class FilmeFactory {
    public static Filme criar(String titulo, String genero, int anoLancamento, int duracao, boolean disponivel, int id)
            throws Exception {
        titulo = validaTexto(titulo);
        genero = validaTexto(genero);

        validaCampoObrigatorio(titulo, "Título é obrigatório");
        validaCampoObrigatorio(genero, "Gênero é obrigatório");
        validaAnoLancamento(anoLancamento);
        validaDuracao(duracao);

        return new Filme(titulo, genero, anoLancamento, duracao, disponivel, id);
    }

    private static String validaTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private static void validaCampoObrigatorio(String campo, String mensagemErro) throws InvalidAttributesException {
        if (campo.isEmpty())
            throw new InvalidAttributesException(mensagemErro);
    }

    private static void validaAnoLancamento(int anoLancamento) throws InvalidAttributesException {
        if (anoLancamento <= 0)
            throw new InvalidAttributesException("Ano de lançamento deve ser maior que zero");
    }

    private static void validaDuracao(int duracao) throws InvalidAttributesException {
        if (duracao <= 0)
            throw new InvalidAttributesException("Duração deve ser maior que zero");
    }
}