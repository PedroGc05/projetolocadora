package locadora.factory;

import javax.naming.directory.InvalidAttributesException;

import locadora.model.Cliente;
import locadora.model.Filme;
import locadora.model.Locacao;

public class LocacaoFactory {
    public static Locacao criar(Cliente cliente, Filme filme, String dataAluguel, String dataDevolucao, double valor,
            String status, int id)
            throws Exception {
        dataAluguel = validaTexto(dataAluguel);
        dataDevolucao = validaTexto(dataDevolucao);

        validaCampoObrigatorio(dataAluguel, "Data de aluguel é obrigatória");
        validaCampoObrigatorio(dataDevolucao, "Data de devolução é obrigatória");
        validaCliente(cliente);
        validaFilme(filme);

        Locacao locacao = new Locacao(id, cliente, filme, dataAluguel, dataDevolucao, valor, status);
        locacao.setValor(locacao.calcularValor());
        return locacao;
    }

    private static String validaTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private static void validaCampoObrigatorio(String campo, String mensagemErro) throws InvalidAttributesException {
        if (campo.isEmpty())
            throw new InvalidAttributesException(mensagemErro);
    }

    private static void validaCliente(Cliente cliente) throws InvalidAttributesException {
        if (cliente == null)
            throw new InvalidAttributesException("Cliente é obrigatório");
    }

    private static void validaFilme(Filme filme) throws InvalidAttributesException {
        if (filme == null)
            throw new InvalidAttributesException("Filme é obrigatório");
        if (!filme.isDisponivel())
            throw new InvalidAttributesException("Filme não está disponível para locação");
    }
}