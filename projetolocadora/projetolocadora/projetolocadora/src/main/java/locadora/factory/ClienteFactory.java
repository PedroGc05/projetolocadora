package locadora.factory;

import javax.naming.directory.InvalidAttributesException;

import locadora.model.Cliente;

public class ClienteFactory {
    public static Cliente criar(String nome, String cpf, String telefone, String endereco, String email, int id)
            throws Exception {
        nome = validaTexto(nome);
        cpf = validaTexto(cpf);
        telefone = validaTexto(telefone);
        endereco = validaTexto(endereco);
        email = validaTexto(email);

        validaCampoObrigatorio(nome, "Nome é obrigatório");
        validaCampoObrigatorio(endereco, "Endereço é obrigatório");
        validaCampoObrigatorio(cpf, "CPF é obrigatório");
        validaCampoObrigatorio(telefone, "Telefone é obrigatório");
        validaCampoObrigatorio(email, "E-mail é obrigatório");

        return new Cliente(nome, cpf, telefone, endereco, email, id);
    }

    private static String validaTexto(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private static void validaCampoObrigatorio(String campo, String mensagemErro) throws InvalidAttributesException {
        if (campo.isEmpty())
            throw new InvalidAttributesException(mensagemErro);
    }
}
